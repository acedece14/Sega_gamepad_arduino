package by.katz.gui;

import by.katz.Log;
import by.katz.Main;
import by.katz.Settings;
import by.katz.comport.MyUart;
import by.katz.comport.PortEnumerator;
import by.katz.gamepad.KeyEmulator;
import by.katz.keys.KeyMap;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

import static by.katz.comport.PortEnumerator.getPortTypeName;
import static by.katz.gui.FormMain.STATE.CLOSED;
import static by.katz.gui.FormMain.STATE.OPENED;

public class FormMain extends JFrame {

    private static final int PORT_SPEED = 115200;

    enum STATE {
        CLOSED,
        OPENED
    }

    private JButton btnOpenClosePort;
    private JButton btnSaveKeyMap;
    private JButton btnLoadKeymap;
    private JButton btnUpdateKeymap;
    private JButton btnEdit;
    private JPanel pnlMain;
    private JList<String> lstComPorts;
    private JTextArea txtLog;
    private JTextField edtKeymapName;
    private JComboBox<String> cbKeymaps;
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
            final SystemTray tray = SystemTray.getSystemTray();

            final Image image = ImageIO.read(Objects.requireNonNull(FormMain.class.getClassLoader()
                    .getResourceAsStream("tray.png")));
            final PopupMenu popup = new PopupMenu();
            final MenuItem miShowHide = new MenuItem("Open/hide");
            miShowHide.addActionListener(a -> showHideForm());
            final MenuItem miExit = new MenuItem("Exit");
            miExit.addActionListener(a -> FormMain.this.dispatchEvent(new WindowEvent(FormMain.this, WindowEvent.WINDOW_CLOSING)));

            popup.add(miShowHide);
            popup.add(miExit);

            final TrayIcon trayIcon = new TrayIcon(image, "Gamepad", popup);
            trayIcon.addMouseListener(new MouseClickListenerImpl() {
                @Override public void mouseClicked(MouseEvent e) {
                    if (e.getButton() != MouseEvent.BUTTON1)
                        return;
                    setVisible(!isVisible());
                    setState(Frame.NORMAL);
                }
            });
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
