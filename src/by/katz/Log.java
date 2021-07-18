package by.katz;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    public static final int MAX_SYMBOLS = 2000;
    private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss(SSS)");

    private static JTextArea txtLog;

    public static void bindTxtView(JTextArea txtLog) {
        Log.txtLog = txtLog;
    }

    public static void log(String logString) {

        logString = sdf.format(new Date()) + "\t" + logString;

        System.out.println(logString);
        if (txtLog == null)
            return;
        if (txtLog.getText().length() > MAX_SYMBOLS)
            txtLog.setText(txtLog.getText().substring(0, MAX_SYMBOLS / 2));

        txtLog.setText(logString + "\r\n" + txtLog.getText());
    }
}
