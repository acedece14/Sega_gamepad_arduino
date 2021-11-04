package by.katz.comport;

import by.katz.IrController;
import by.katz.Log;
import by.katz.gamepad.GamePad;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

class SerialReader extends Thread {

    private final InputStream in;
    private boolean isNeedStop = false;

    void stopReader() {
        isNeedStop = true;
    }

    SerialReader(InputStream in) {
        //this.in = new DataInputStream(in);
        this.in = in;
    }

    private static final StringBuilder resultBytes = new StringBuilder();

    static String getStringFromBytes(byte[] buffer, int len) {
        resultBytes.setLength(0);
        for (int i = 0; i < len; i++)
            resultBytes.append((char) buffer[i]);
        return resultBytes.toString();
    }

    public void run() {
        final var buffer = new ArrayList<Byte>();
        try {
            //StringBuilder totalData = new StringBuilder();
            //while ((!isNeedStop) && (len = this.in.read(buffer)) > -1) {
            //int len = 0;
            while ((!isNeedStop)) {
                var readed = in.read();
                if (readed == -1) {
                    buffer.clear();
                    continue; // eof
                }
                buffer.add((byte) readed);
                //System.out.println("read: " + readed + " 0x" + Integer.toHexString(readed));
                if (buffer.size() > 2) {
                    byte firstChar = buffer.get(0);
                    byte preLastChar = buffer.get(buffer.size() - 2);
                    byte lastChar = buffer.get(buffer.size() - 1);
                    if (firstChar == 0 && preLastChar == 14 && lastChar == 88) {
                        // get full packet
                        byte[] bytes = new byte[buffer.size() - 3];
                        int counter = 0;
                        for (int i = 1; i < buffer.size() - 2; i++)
                            bytes[counter++] = buffer.get(i);


                        buffer.clear();

                        var strData = new String(bytes);
                        try {
                            var value = Integer.parseInt(strData);
                            GamePad.getInstance().runCommand(value);
                        } catch (NumberFormatException e) {
                            IrController.getInstance().processNewData(strData);
                        }
                    }
                }
            } // end while
        } catch (IOException e) {
            e.printStackTrace();
            Log.log("Error while read input buffer");
        }
    }

}
