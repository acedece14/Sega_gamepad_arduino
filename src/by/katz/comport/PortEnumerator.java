package by.katz.comport;

import gnu.io.CommPortIdentifier;

import java.util.ArrayList;
import java.util.Enumeration;

/**
 * //
 * Created by katz on 17.06.2017.
 */
public class PortEnumerator {

    public static ArrayList<CommPortIdentifier> getPorts() {
        ArrayList<CommPortIdentifier> ports = new ArrayList<>();
        Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();

        int number = 0;
        System.out.println("Available COM-ports");
        System.out.println("Number : description");
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier portIdentifier = portEnum.nextElement();
            ports.add(portIdentifier);
            System.out.println(number++ + " : (" + getPortTypeName(portIdentifier.getPortType()) + ") " + portIdentifier.getName());

        }
        return ports;
    }

    public static String getPortTypeName(int portType) {
        switch (portType) {
            case CommPortIdentifier.PORT_I2C:
                return "I2C";
            case CommPortIdentifier.PORT_PARALLEL:
                return "Parallel";
            case CommPortIdentifier.PORT_RAW:
                return "Raw";
            case CommPortIdentifier.PORT_RS485:
                return "RS485";
            case CommPortIdentifier.PORT_SERIAL:
                return "Serial";
            default:
                return "unknown type";
        }
    }
}
