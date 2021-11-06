/*
 * Created by Konstantin Chuyasov
 * Last modified: 06.11.2021, 19:44
 * Contacts: acedece14@gmail.com
 *
 */

package by.katz.gui;

import by.katz.IrController;

import javax.swing.*;
import java.awt.event.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static by.katz.IrController.REPEAT_LAST;

public class DialogIrKeys extends JDialog implements IrController.IIrListener {
    private boolean scanKey = false;
    private JPanel contentPane;
    private JButton buttonOK;
    private JList<String> lstKeys;
    private JLabel lblStatus;
    private JButton btnScanKey;
    private JTextField edtKeyName;

    private HashMap<String, Integer> pressedKeys;

    public DialogIrKeys() {

        IrController.get().addListener(this);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
        btnScanKey.addActionListener(e -> {
            if (!scanKey)
                onStartScan();
            else onStopScan();
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onOK();
            }
        });
        contentPane.registerKeyboardAction(e -> onOK(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent
                        .WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        setLocationRelativeTo(null);
        updateKeysList();
        pack();
        setVisible(true);
    }

    private void onStartScan() {
        lblStatus.setText("Start scan");
        scanKey = true;
        pressedKeys = new HashMap<>();
    }

    private void onStopScan() {
        var key = pressedKeys.entrySet()
                .stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .orElse(null);
        if (key == null)
            return;
        if (edtKeyName.getText().isEmpty()) {
            GuiUtils.showWarning("Enter key name");
            return;
        }
        lblStatus.setText("Scan complete");
        scanKey = false;
        var keyName = edtKeyName.getText();
        IrController.get().addNewKey(key.getKey(), keyName);
        edtKeyName.setText("");
        updateKeysList();
    }

    private void updateKeysList() {
        var keys = IrController.get().getKeys();
        var model = new DefaultListModel<String>();
        for (IrController.IrKey k : keys)
            model.add(model.getSize(), k.getKey() + " " + k.getName());
        lstKeys.setModel(model);
        lblStatus.setText("Keys update");
    }

    private void onOK() {
        IrController.get().removeListener(this);
        dispose();
    }

    @Override public void onNewKeyPressed(final String key) {
        if (scanKey) {
            if (key.equals(REPEAT_LAST))
                return;
            var count = 0;
            if (pressedKeys.containsKey(key))
                count = pressedKeys.get(key) + 1;
            lblStatus.setText("Count: " + count);
            pressedKeys.put(key, count);
        }
    }
}
