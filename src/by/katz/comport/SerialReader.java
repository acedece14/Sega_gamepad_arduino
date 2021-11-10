/*
 * Created by Konstantin Chuyasov
 * Last modified: 08.11.2021, 23:38
 * Contacts: acedece14@gmail.com
 *
 */

package by.katz.comport;

import by.katz.infrared.IrController;
import by.katz.Log;
import by.katz.gamepad.GamePad;

import java.io.*;
import java.util.ArrayList;

class SerialReader extends Thread {

    public static final int FIRST_BYTE = 0;
    public static final int SECOND_BYTE = 3;
    public static final int PRE_LAST_BYTE = 14;
    public static final int LAST_BYTE = 88;
    public static final int MIN_PACKET_SIZE = 5;
    public static final int IR_PACKET_SIZE = 8;
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

    private void processRawData() throws IOException {
        var readed = in.read();
        if (readed == -1) { // EOF
            buffer.clear();
            return;
        }
        buffer.add(readed);
        if (buffer.size() < MIN_PACKET_SIZE)
            return;

        final var firstByte = buffer.get(0);
        final var secondByte = buffer.get(1);
        final var preLastByte = buffer.get(buffer.size() - 2);
        final var lastByte = buffer.get(buffer.size() - 1);

        if (firstByte != FIRST_BYTE
                || secondByte != SECOND_BYTE
                || preLastByte != PRE_LAST_BYTE
                || lastByte != LAST_BYTE)
            return;

        final var bytes = new byte[buffer.size() - 4];
        int counter = 0;
        for (var i = 2; i < buffer.size() - 2; i++)
            bytes[counter++] = buffer.get(i).byteValue();

        final var strData = new String(bytes);
        if (strData.length() == IR_PACKET_SIZE)
            IrController.get().processNewData(strData);
        else try {
            final var value = Integer.parseInt(strData);
            GamePad.INSTANCE.runCommand(value);
        } catch (NumberFormatException e) { e.printStackTrace(); }
        Log.log("[SR] buffer: " + buffer + " data: " + strData);
        buffer.clear();
    }

    void stopReader() { isNeedStop = true; }

}
