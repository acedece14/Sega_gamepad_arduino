/*
 * Created by Konstantin Chuyasov
 * Last modified: 06.11.2021, 19:44
 * Contacts: acedece14@gmail.com
 *
 */

package by.katz.comport;

import by.katz.IrController;
import by.katz.Log;
import by.katz.gamepad.GamePad;

import java.io.*;
import java.util.ArrayList;

class SerialReader extends Thread {

    private final ArrayList<Integer> buffer = new ArrayList<>();
    private final InputStream in;
    private boolean isNeedStop = false;

    SerialReader(InputStream in) { this.in = in; }

    public void run() {
        try {
            while (!isNeedStop)
                processRawData();
        } catch (IOException e) {
            e.printStackTrace();
            Log.log("Error while read input buffer");
        }
    }

    void stopReader() { isNeedStop = true; }

    private void processRawData() throws IOException {
        var readed = in.read();
        if (readed == -1) {
            buffer.clear();
            return;
        }
        buffer.add(readed);
        if (buffer.size() > 4) {
            final var firstByte = buffer.get(0);
            final var secondByte = buffer.get(1);
            final var preLastByte = buffer.get(buffer.size() - 2);
            final var lastByte = buffer.get(buffer.size() - 1);
            if (firstByte == 0
                    && secondByte == 3
                    && preLastByte == 14
                    && lastByte == 88) {
                processFullPacket();
            }
        }
    }

    private void processFullPacket() {
        final var bytes = new byte[buffer.size() - 4];
        int counter = 0;
        for (int i = 2; i < buffer.size() - 2; i++)
            bytes[counter++] = buffer.get(i).byteValue();

        final var strData = new String(bytes);
        if (strData.length() == 8)
            IrController.get().processNewData(strData);
        else try {
            final var value = Integer.parseInt(strData);
            GamePad.INSTANCE.runCommand(value);
        } catch (NumberFormatException e) { e.printStackTrace(); }
        System.out.println("[SR] buffer: " + buffer + " data: " + strData);
        buffer.clear();
    }

}
