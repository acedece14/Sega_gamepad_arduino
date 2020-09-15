package by.katz.gui;

import by.katz.comport.MyUart;
import by.katz.comport.PortEnumerator;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

import static by.katz.comport.PortEnumerator.getPortTypeName;
import static by.katz.gui.FormSelectPort.STATE.*;

public class FormSelectPort extends JFrame {
    enum STATE {
        CLOSED,
        OPENED
    }

    private JButton btnOpenClosePort;
    private JPanel pnlMain;
    private JList<String> lstComPorts;
    private MyUart uart;

    private STATE state = CLOSED;

    final ArrayList<CommPortIdentifier> ports = PortEnumerator.getPorts();


    public FormSelectPort() {

        initVisible();

        setTitle("GamePad");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(pnlMain);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initVisible() {
        final DefaultListModel<String> listModel = new DefaultListModel<>();

        for (CommPortIdentifier p : ports)
            listModel.addElement(p.getName() + " " + getPortTypeName(p.getPortType()));
        lstComPorts.setModel(listModel);
        btnOpenClosePort.addActionListener(e -> openClosePort());
    }


    private void openClosePort() {

        if (state == CLOSED) {
            CommPortIdentifier port = ports.get(lstComPorts.getSelectedIndex());
            try {
                uart = new MyUart(port, 115200);
                Runtime.getRuntime().addShutdownHook(new Thread(uart::stop));
                btnOpenClosePort.setText("Close port");
                state = OPENED;
            } catch (UnsupportedCommOperationException | IOException | PortInUseException e2) {
                JOptionPane.showMessageDialog(null, e2.getLocalizedMessage());
                state = CLOSED;
            }
        } else if (state == OPENED) {
            uart.stop();
            btnOpenClosePort.setText("Open port");
            state = CLOSED;
        }
    }
}
