package by.katz;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:sss");

    private static JTextArea txtLog;

    public static void bindTxtView(JTextArea txtLog) {
        Log.txtLog = txtLog;
    }

    public static void log(String logString) {

        logString = sdf.format(new Date()) + "\t" + logString;

        System.out.println(logString);
        if (txtLog != null)
            txtLog.setText(txtLog.getText() + (txtLog.getText().length() > 0 ? "\r\n" : "") + logString);
    }
}
