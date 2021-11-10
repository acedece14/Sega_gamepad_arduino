/*
 * Created by Konstantin Chuyasov
 * Last modified: 08.11.2021, 23:38
 * Contacts: acedece14@gmail.com
 *
 */

package by.katz.gui;

import by.katz.Log;
import by.katz.keyboard.KeyMap;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DialogEditKeymap extends JDialog {
    private static String keymap;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField edtUp;
    private JTextField edtDown;
    private JTextField edtLeft;
    private JTextField edtRight;
    private JTextField edtA;
    private JTextField edtB;
    private JTextField edtC;
    private JTextField edtX;
    private JTextField edtY;
    private JTextField edtZ;
    private JTextField edtStart;
    private JTextField edtMode;

    public DialogEditKeymap() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        updateKeysBindings();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateKeysBindings() {
        edtUp.setText(KeyMap.getKeyNameByCode(KeyMap.get().getKeyUp()));
        edtDown.setText(KeyMap.getKeyNameByCode(KeyMap.get().getKeyDown()));
        edtLeft.setText(KeyMap.getKeyNameByCode(KeyMap.get().getKeyLeft()));
        edtRight.setText(KeyMap.getKeyNameByCode(KeyMap.get().getKeyRight()));

        edtA.setText(KeyMap.getKeyNameByCode(KeyMap.get().getKeyA()));
        edtB.setText(KeyMap.getKeyNameByCode(KeyMap.get().getKeyB()));
        edtC.setText(KeyMap.getKeyNameByCode(KeyMap.get().getKeyC()));
        edtX.setText(KeyMap.getKeyNameByCode(KeyMap.get().getKeyX()));
        edtY.setText(KeyMap.getKeyNameByCode(KeyMap.get().getKeyY()));
        edtZ.setText(KeyMap.getKeyNameByCode(KeyMap.get().getKeyZ()));

        edtStart.setText(KeyMap.getKeyNameByCode(KeyMap.get().getKeyStart()));
        edtMode.setText(KeyMap.getKeyNameByCode(KeyMap.get().getKeyMode()));

        KeyListener keyListener = new KeyListener() {
            @Override public void keyTyped(KeyEvent e) { }

            @Override public void keyPressed(KeyEvent e) { }

            @Override public void keyReleased(KeyEvent e) {
                JTextField edt = (JTextField) e.getComponent();
                Log.log("Key: " + e.getKeyCode() + " " + KeyMap.getKeyNameByCode(e.getKeyCode()));
                edt.setText(KeyMap.getKeyNameByCode(e.getKeyCode()));
                edt.transferFocus();
            }
        };
        setKeyListener(keyListener, edtUp, edtDown, edtLeft, edtRight,
                edtA, edtB, edtC, edtX, edtY, edtZ,
                edtStart, edtMode);
    }

    private static void setKeyListener(KeyListener listener, JTextField... edts) {
        for (JTextField e : edts)
            e.addKeyListener(listener);
    }

    private void onOK() {
        KeyMap keyMap = KeyMap.get();
        try {
            keyMap.setKeyUp(KeyMap.getKeyCodeByName(edtUp.getText()));
            keyMap.setKeyDown(KeyMap.getKeyCodeByName(edtDown.getText()));
            keyMap.setKeyLeft(KeyMap.getKeyCodeByName(edtLeft.getText()));
            keyMap.setKeyRight(KeyMap.getKeyCodeByName(edtRight.getText()));

            keyMap.setKeyA(KeyMap.getKeyCodeByName(edtA.getText()));
            keyMap.setKeyB(KeyMap.getKeyCodeByName(edtB.getText()));
            keyMap.setKeyC(KeyMap.getKeyCodeByName(edtC.getText()));
            keyMap.setKeyX(KeyMap.getKeyCodeByName(edtX.getText()));
            keyMap.setKeyY(KeyMap.getKeyCodeByName(edtY.getText()));
            keyMap.setKeyZ(KeyMap.getKeyCodeByName(edtZ.getText()));

            keyMap.setKeyStart(KeyMap.getKeyCodeByName(edtStart.getText()));
            keyMap.setKeyMode(KeyMap.getKeyCodeByName(edtMode.getText()));

            Log.log("Keymap is changed");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        dispose();
    }

    private void onCancel() { dispose(); }
}
