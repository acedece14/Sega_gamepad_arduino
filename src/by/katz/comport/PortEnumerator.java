/*
 * Created by Konstantin Chuyasov
 * Last modified: 06.11.2021, 19:44
 * Contacts: acedece14@gmail.com
 *
 */

package by.katz.comport;

import by.katz.Log;
import gnu.io.CommPortIdentifier;

import java.util.ArrayList;
import java.util.Enumeration;


public class PortEnumerator {

    @SuppressWarnings("unchecked")
    public static ArrayList<CommPortIdentifier> getPorts() {
        final var ports = new ArrayList<CommPortIdentifier>();
        final Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
        var number = 0;
        final var res = new StringBuilder("Available COM-ports\n");
        while (portEnum.hasMoreElements()) {
            final var portIdentifier = portEnum.nextElement();
            ports.add(portIdentifier);
            res.append(String.format("\t%d : (%s) %s\n",
                    number++,
                    getPortTypeName(portIdentifier.getPortType()),
                    portIdentifier.getName()));
        }
        Log.log(res.toString().trim());
        return ports;
    }

    public static String getPortTypeName(int portType) {
        return switch (portType) {
            case CommPortIdentifier.PORT_I2C -> "I2C";
            case CommPortIdentifier.PORT_PARALLEL -> "Parallel";
            case CommPortIdentifier.PORT_RAW -> "Raw";
            case CommPortIdentifier.PORT_RS485 -> "RS485";
            case CommPortIdentifier.PORT_SERIAL -> "Serial";
            default -> "unknown type";
        };
    }
}
