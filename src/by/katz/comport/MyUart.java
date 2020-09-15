package by.katz.comport;

import by.katz.gamepad.GamePad;
import gnu.io.*;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by me on 29.11.2016.
 * acedece14@gmail.com
 */
public class MyUart {

    private CommPort commPort;
    SerialReader serialReader;

    public MyUart(CommPortIdentifier port, int speed) throws UnsupportedCommOperationException, IOException, PortInUseException {
            commPort = port.open(this.getClass().getName(), 2000);

            SerialPort serialPort = (SerialPort) commPort;
            serialPort.setSerialPortParams(speed, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

            InputStream in = serialPort.getInputStream();
            serialReader = new SerialReader(in);

            serialReader.start();
            System.out.println("Port opened");
    }


    public void stop() {
        System.out.println("Try to close port .");
        try {
            serialReader.stopReader();
            commPort.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class SerialReader extends Thread {

        private DataInputStream in;
        private boolean isStop = false;

        public void stopReader() {
            isStop = true;
        }

        SerialReader(InputStream in) {
            this.in = new DataInputStream(in);
        }

        public void run() {
            byte[] buffer = new byte[2048];
            int len;
            try {
                StringBuilder totalData = new StringBuilder();
                while ((!isStop) && (len = this.in.read(buffer)) > -1) {

                    if (len > 0) {
                        String rez = getStringFromBytes(buffer, len);
                        totalData.append(rez);
                    }
                    if (totalData.toString().endsWith("\r\n")) {
                        GamePad.getInstance().runCommand(totalData.toString().trim());
                        totalData = new StringBuilder();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getStringFromBytes(byte[] buffer, int len) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < len; i++)
            result.append((char) buffer[i]);
        return result.toString();

    }
}

