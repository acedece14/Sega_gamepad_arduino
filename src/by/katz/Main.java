package by.katz;

import by.katz.gui.FormSelectPort;


public class Main {


    public static void main(String[] args) {

        new FormSelectPort();
/*
        if (true)
            return;
        ArrayList<CommPortIdentifier> ports = PortEnumerator.getPorts();

        //int portNumber =  Integer.valueOf(input.nextLine());
        int portNumber = 1;

        PortSpeedSelector.init();
        portNumber = PortSpeedSelector.readPortNumber();
        // PortSpeedSelector.showSpeeds();
        int portSpeed = 115200; //Integer.valueOf(input.nextLine());
        // MyUart uart = new MyUart(ports.get(portNumber), PortSpeedSelector.getSpeed(portSpeed));
        MyUart uart = new MyUart(ports.get(portNumber), portSpeed);
        Runtime.getRuntime().addShutdownHook(new Thread(uart::stop));
        JFrame form = new FormMain(uart);*/
    }
}
