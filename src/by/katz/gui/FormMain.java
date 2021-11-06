/*
 * Created by Konstantin Chuyasov
 * Last modified: 06.11.2021, 19:44
 * Contacts: acedece14@gmail.com
 *
 */

package by.katz.gui;

import by.katz.Log;
import by.katz.Main;
import by.katz.Settings;
import by.katz.comport.MyUart;
import by.katz.comport.PortEnumerator;
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
import static by.katz.gui.GuiUtils.showNotify;

public class FormMain extends JFrame {

    private static final int PORT_SPEED = Settings.getInstance().getSerialSpeed();

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
    private JButton btnShowIrForm;
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
        btnShowIrForm.addActionListener(a -> new DialogIrKeys());
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
        showNotify("Started");
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

        final var listModel = new DefaultListModel<String>();

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
            var port = ports.get(lstComPorts.getSelectedIndex());
            uart = new MyUart(port, PORT_SPEED);
            try {
                uart.start();
                btnOpenClosePort.setText("Close port");
                state = OPENED;
                Settings.getInstance().setLastOpenedPort(port.getName());
            } catch (PortInUseException e) {
                GuiUtils.showWarning("Port in use!");
                Log.log("Port in use!");
                state = CLOSED;
            } catch (UnsupportedCommOperationException | IOException e) {
                GuiUtils.showWarning("Cant open port: " + e.getLocalizedMessage());
                Log.log("Error while open port: " + e.getLocalizedMessage());
                state = CLOSED;
            }
        } else if (state == OPENED) {
            uart.stop();
            btnOpenClosePort.setText("Open port");
            state = CLOSED;
        }
    }


}
