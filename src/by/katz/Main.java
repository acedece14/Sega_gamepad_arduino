package by.katz;

import gnu.io.CommPortIdentifier;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayList<CommPortIdentifier> ports = PortEnumerator.getPorts();
        System.out.println("Select port");
        //int portNumber =  Integer.valueOf(input.nextLine());
        int portNumber = 1;

        PortSpeedSelector.init();
        // PortSpeedSelector.showSpeeds();
        int portSpeed = 115200; //Integer.valueOf(input.nextLine());
        // MyUart uart = new MyUart(ports.get(portNumber), PortSpeedSelector.getSpeed(portSpeed));
        MyUart uart = new MyUart(ports.get(portNumber), portSpeed);
        Runtime.getRuntime().addShutdownHook(new Thread(uart::stop));
        JFrame form = new FormMain(uart);
    }
}
