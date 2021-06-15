package by.katz.gui;

import by.katz.keys.KeyMap;
import by.katz.Log;
import by.katz.Main;
import by.katz.comport.MyUart;
import by.katz.comport.PortEnumerator;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static by.katz.comport.PortEnumerator.getPortTypeName;
import static by.katz.gui.FormMain.STATE.CLOSED;
import static by.katz.gui.FormMain.STATE.OPENED;

public class FormMain extends JFrame {
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
    private MyUart uart;

    private STATE state = CLOSED;

    private final ArrayList<CommPortIdentifier> ports;


    public FormMain() {

        URL url = Main.class.getResource("/GamePad.png");
        setIconImage(Toolkit.getDefaultToolkit().getImage(url));

        Log.bindTxtView(txtLog);
        ports = PortEnumerator.getPorts();

        setTitle("Sega Megadrive Gamepad driver");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(pnlMain);
        pack();
        setSize(500, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        initVisible();
    }

    private void initVisible() {
        final DefaultListModel<String> listModel = new DefaultListModel<>();

        for (CommPortIdentifier p : ports)
            listModel.addElement(p.getName() + " " + getPortTypeName(p.getPortType()));
        lstComPorts.setModel(listModel);
        lstComPorts.addMouseListener(new MouseListener() {
            @Override public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && state == CLOSED)
                    openClosePort();
            }

            @Override public void mousePressed(MouseEvent e) { }

            @Override public void mouseReleased(MouseEvent e) { }

            @Override public void mouseEntered(MouseEvent e) { }

            @Override public void mouseExited(MouseEvent e) { }
        });
        btnOpenClosePort.addActionListener(e -> openClosePort());
        btnSaveKeyMap.addActionListener(e -> KeyMap.saveKeyMap(edtKeymapName.getText()));
        btnLoadKeymap.addActionListener(e -> KeyMap.loadKeyMap(edtKeymapName.getText()));
    }


    private void openClosePort() {

        if (state == CLOSED) {
            CommPortIdentifier port = ports.get(lstComPorts.getSelectedIndex());
            uart = new MyUart(port, 115200);
            try {
                uart.start();
                /*Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    Log.log("Shutdown hook");
                    uart.stop();
                }));*/
                btnOpenClosePort.setText("Close port");
                state = OPENED;
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
