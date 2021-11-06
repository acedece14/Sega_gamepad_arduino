/*
 * Created by Konstantin Chuyasov
 * Last modified: 06.11.2021, 19:44
 * Contacts: acedece14@gmail.com
 *
 */

package by.katz.comport;

import by.katz.Log;

import java.util.ArrayList;
import java.util.Scanner;

class PortSpeedSelector {

    private static ArrayList<Integer> speeds;

    private static Scanner input = new Scanner(System.in);

    static void init() {
        speeds = new ArrayList<>();
        speeds.add(9600);
        speeds.add(19200);
        speeds.add(38400);
        speeds.add(57600);
        speeds.add(115200);
    }

    static void showSpeeds() {
        Log.log("Select speed: ");
        for (int i = 0; i < speeds.size(); i++)
            Log.log(i + ": " + speeds.get(i));
    }

    static int getSpeed(int index) {
        return speeds.get(index);
    }

    public static int readPortNumber() {
        Log.log("Select port");
        return input.nextInt();
    }
}
