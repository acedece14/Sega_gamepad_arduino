/*
 * Created by Konstantin Chuyasov
 * Last modified: 06.11.2021, 19:44
 * Contacts: acedece14@gmail.com
 *
 */

package by.katz.gui;

import javax.swing.*;
import java.awt.*;

public class GuiUtils {
    public static void showWarning(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message, "Error!", JOptionPane.WARNING_MESSAGE);
    }

    private static JDialog lastNotify = null;

    public static void showNotify(String text) {
        if (lastNotify != null) {
            lastNotify.dispose();
            lastNotify = null;
        }
        var dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        dialog.setUndecorated(true);
        dialog.setLocationByPlatform(true);
        var label = new JLabel(text);
        label.setFont(new Font("Calibri", Font.PLAIN, 72));
        dialog.setOpacity(0.5f);
        dialog.add(label);
        dialog.pack();
        dialog.setVisible(true);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) { }
            dialog.dispose();
        }).start();
        lastNotify = dialog;
    }
}
