/*
 * Created by Konstantin Chuyasov
 * Last modified: 06.11.2021, 19:44
 * Contacts: acedece14@gmail.com
 *
 */

package by.katz.comport;

import by.katz.Log;
import by.katz.Settings;
import gnu.io.*;

import java.io.IOException;

import static gnu.io.SerialPort.*;

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
        commPort = port.open(this.getClass().getName(), Settings.getInstance().getSerialTimeout());
        var serialPort = (SerialPort) commPort;
        serialPort.setSerialPortParams(speed, DATABITS_8, STOPBITS_1, PARITY_NONE);
        serialReader = new SerialReader(serialPort.getInputStream());
        serialReader.start();
        Log.log(port.getName() + " opened");
    }


    public void stop() {
        Log.log("Try to close " + port.getName());
        try {
            if (serialReader != null && serialReader.isAlive())
                serialReader.stopReader();
            if (commPort != null)
                commPort.close();
            Log.log(port.getName() + " closed");
        } catch (Exception e) {
            e.printStackTrace();
            Log.log("Error, while closing " + port.getName() + ": " + e.getLocalizedMessage());
        }
    }
}

