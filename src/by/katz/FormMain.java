package by.katz;

import javax.swing.*;

/**
 * Created by katz on 23.06.2017.
 */
public class FormMain extends JFrame {
    private JPanel pnlMain;
    private JButton btnExit;

    FormMain(MyUart uart) {
        btnExit.addActionListener(e -> {

            uart.stop();
            System.exit(0);
        });
        setTitle("GamePad");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(pnlMain);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
