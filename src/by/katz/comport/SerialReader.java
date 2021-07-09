package by.katz.comport;

import by.katz.Log;
import by.katz.gamepad.GamePad;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

class SerialReader extends Thread {

    private InputStream in;
    private boolean isNeedStop = false;

    void stopReader() {
        isNeedStop = true;
    }

    SerialReader(InputStream in) {
        //this.in = new DataInputStream(in);
        this.in = in;
    }

    public void run() {
        byte[] buffer = new byte[2048];
        int len;
        try {
            String  totalData ="";
            while ((!isNeedStop) && (len = this.in.read(buffer)) > -1) {

                if (len > 0) {
                    String rez = MyUart.getStringFromBytes(buffer, len);
                    //totalData.append(rez);
                    totalData += rez;
                }
                if (totalData.endsWith("\r\n")) {
                    GamePad.getInstance().runCommand(totalData.trim());
                    //totalData.setLength(0);
                    totalData = "";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.log("Error while read input buffer");
        }
    }
}
