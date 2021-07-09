package by.katz.gui;

import by.katz.Log;
import by.katz.Main;
import by.katz.Settings;
import by.katz.comport.MyUart;
import by.katz.comport.PortEnumerator;
import by.katz.gamepad.KeyEmulator;
import by.katz.keys.KeyMap;
import by.katz.keys.MouseClickListenerImpl;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

import static by.katz.comport.PortEnumerator.getPortTypeName;
import static by.katz.gui.FormMain.STATE.CLOSED;
import static by.katz.gui.FormMain.STATE.OPENED;

public class FormMain extends JFrame {

    public static final int PORT_SPEED = 115200;

    enum STATE {
        CLOSED,
        OPENED
    }

    private JButton btnOpenClosePort;
    private JPanel pnlMain;
    private JList<String> lstComPorts;
    private JTextArea txtLog;
    private JTextField edtKeymapName;
    private JButton btnSaveKeyMap;
    private JButton btnLoadKeymap;
    private JComboBox<String> cbKeymaps;
    private JCheckBox cbFastKeys;
    private JButton btnUpdateKeymap;
    private JButton btnEdit;
    private MyUart uart;

    private STATE state = CLOSED;

    private final ArrayList<CommPortIdentifier> ports;


    public FormMain() {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Log.log("Shutdown...");
            uart.stop();
        }));

        URL url = Main.class.getResource("/tray.png");
        setIconImage(Toolkit.getDefaultToolkit().getImage(url));

        Log.bindTxtView(txtLog);
        ports = PortEnumerator.getPorts();

        setTitle("Sega Megadrive Gamepad driver");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(pnlMain);
        pack();
        setSize(800, 400);
        setLocationRelativeTo(null);

        try {
            createTrayIcon();
        } catch (IOException | AWTException e) {
            e.printStackTrace();
        }

        addWindowStateListener(e -> {
            System.out.println("> " + e.getNewState());
            if (e.getNewState() == 1) showHideForm();
        });

        initVisible();
        setVisible(true);

        String port = Settings.getInstance().getLastOpenedPort();
        if (port != null) {
            Log.log("Try to open " + port + " from settings");
            for (int i = 0; i < lstComPorts.getModel().getSize() - 1; i++) {
                String el = lstComPorts.getModel().getElementAt(i);
                if (port.equals(el.split(" ")[0])) {
                    lstComPorts.setSelectedIndex(i);
                    btnOpenClosePort.doClick();
                    break;
                }
            }
        }
    }

    private void createTrayIcon() throws IOException, AWTException {

        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();

            Image image = ImageIO.read(Objects.requireNonNull(FormMain.class.getClassLoader()
                    .getResourceAsStream("tray.png")));
            PopupMenu popup = new PopupMenu();
            MenuItem miShowHide = new MenuItem("Open/hide");
            MenuItem miExit = new MenuItem("Exit");
            miShowHide.addActionListener(a -> showHideForm());
            miExit.addActionListener(a -> FormMain.this.dispose());
            popup.add(miShowHide);
            popup.add(miExit);

            TrayIcon trayIcon = new TrayIcon(image, "Gamepad", popup);
            trayIcon.addMouseListener(new TrayLeftClickListener(this));
            tray.add(trayIcon);
        }

    }

    private void showHideForm() { setVisible(!isVisible()); }

    private void initVisible() {

        updateKeymaps();
        cbKeymaps.addActionListener(a -> edtKeymapName.setText(String.valueOf(cbKeymaps.getSelectedItem())));

        final DefaultListModel<String> listModel = new DefaultListModel<>();

        for (CommPortIdentifier p : ports)
            listModel.addElement(p.getName() + " " + getPortTypeName(p.getPortType()));
        lstComPorts.setModel(listModel);

        lstComPorts.addMouseListener(new MouseClickListenerImpl() {
            @Override public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && state == CLOSED)
                    openClosePort();
            }
        });
        btnOpenClosePort.addActionListener(e -> openClosePort());
        btnSaveKeyMap.addActionListener(e -> KeyMap.saveKeyMap(edtKeymapName.getText()));
        btnLoadKeymap.addActionListener(e -> KeyMap.loadKeyMap(edtKeymapName.getText()));
        edtKeymapName.setText(Settings.getInstance().getLastUsedKeymap());
        cbFastKeys.addActionListener(e -> KeyEmulator.setFastKeys(cbFastKeys.isSelected()));
        btnUpdateKeymap.addActionListener(a -> updateKeymaps());
        btnEdit.addActionListener(a -> new DialogEditKeymap());
    }


    private void updateKeymaps() {
        final DefaultComboBoxModel<String> cbModel = new DefaultComboBoxModel<>();
        KeyMap.getAvailableKeymaps().forEach(cbModel::addElement);
        cbKeymaps.setModel(cbModel);
    }


    private void openClosePort() {

        if (state == CLOSED) {
            CommPortIdentifier port = ports.get(lstComPorts.getSelectedIndex());
            uart = new MyUart(port, PORT_SPEED);
            try {
                uart.start();
                /*Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    Log.log("Shutdown hook");
                    uart.stop();
                }));*/
                btnOpenClosePort.setText("Close port");
                state = OPENED;
                Settings.getInstance().setLastOpenedPort(port.getName());
            } catch (UnsupportedCommOperationException | IOException | PortInUseException e2) {
                JOptionPane.showMessageDialog(null, e2.getLocalizedMessage());
                Log.log("Error while open port: " + e2.getLocalizedMessage());
                state = CLOSED;
            }
        } else if (state == OPENED) {
            uart.stop();
            btnOpenClosePort.setText("Open port");
            state = CLOSED;
        }
    }


}
