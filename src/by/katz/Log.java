package by.katz;

import javax.swing.*;
import java.util.Date;

public class Log {

    private static JTextArea txtLog;

    public static void bindTxtView(JTextArea txtLog) {
        Log.txtLog = txtLog;
    }

    public static void log(String logString) {

        logString = new Date().toString() + " " + logString;

        System.out.println(logString);
        if (txtLog != null)
            txtLog.setText(txtLog.getText() + (txtLog.getText().length() > 0 ? "\r\n" : "") + logString);
    }
}
