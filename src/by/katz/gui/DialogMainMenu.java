/*
 * Created by Konstantin Chuyasov
 * Last modified: 09.11.2021, 12:57
 * Contacts: acedece14@gmail.com
 *
 */

package by.katz.gui;

import by.katz.infrared.IrController;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DialogMainMenu
        extends JFrame
        implements IrController.IIrListener {
    private JPanel contentPane;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JButton button9;
    private JButton button10;
    private JButton button11;
    private JButton buttonExit;

    private static boolean isCreated = false;

    public DialogMainMenu() {
        setContentPane(contentPane);
        //setModal(true);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        pack();
        setLocationRelativeTo(null);
        isCreated = true;
        setVisible(true);
    }

    public static boolean isCreated() { return isCreated; }

    private void onCancel() {
        isCreated = false;
        IrController.get().removeListener(this);
        dispose();
    }

    @Override public void onNewKeyPressed(String key) { }
}
