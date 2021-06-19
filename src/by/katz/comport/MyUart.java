package by.katz.comport;

import by.katz.Log;
import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by me on 29.11.2016.
 * acedece14@gmail.com
 */
public class MyUart {

    private final CommPortIdentifier port;
    private final int speed;
    private CommPort commPort;
    private SerialReader serialReader;

    public MyUart(CommPortIdentifier port, int speed) {
        this.port = port;
        this.speed = speed;
    }


    public void start() throws UnsupportedCommOperationException, IOException, PortInUseException {
        commPort = port.open(this.getClass().getName(), 2000);

        SerialPort serialPort = (SerialPort) commPort;
        serialPort.setSerialPortParams(speed, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

        InputStream in = serialPort.getInputStream();
        serialReader = new SerialReader(in);
        serialReader.start();
        Log.log("Port opened");
    }


    public void stop() {
        Log.log("Try to close port .");
        try {
            serialReader.stopReader();
            commPort.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.log("Error, while closing port! " + e.getLocalizedMessage());
            return;
        }
        Log.log("Port closed");
    }

    private static StringBuilder resultBytes = new StringBuilder();

    static String getStringFromBytes(byte[] buffer, int len) {
        //StringBuilder resultBytes = new StringBuilder();
        resultBytes.setLength(0);
        for (int i = 0; i < len; i++)
            resultBytes.append((char) buffer[i]);
        return resultBytes.toString();

    }
}

