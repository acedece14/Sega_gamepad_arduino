package by.katz.comport;

import by.katz.Log;
import by.katz.gamepad.GamePad;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

class SerialReader extends Thread {

    private DataInputStream in;
    private boolean isNeedStop = false;

    void stopReader() {
        isNeedStop = true;
    }

    SerialReader(InputStream in) {
        this.in = new DataInputStream(in);
    }

    public void run() {
        byte[] buffer = new byte[2048];
        int len;
        try {
            StringBuilder totalData = new StringBuilder();
            while ((!isNeedStop) && (len = this.in.read(buffer)) > -1) {

                if (len > 0) {
                    String rez = MyUart.getStringFromBytes(buffer, len);
                    totalData.append(rez);
                }
                if (totalData.toString().endsWith("\r\n")) {
                    GamePad.getInstance().runCommand(totalData.toString().trim());
                    totalData.setLength(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.log("Error while read input buffer");
        }
    }
}
