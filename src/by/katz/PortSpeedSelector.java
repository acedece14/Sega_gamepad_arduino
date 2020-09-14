package by.katz;

import java.util.ArrayList;

/**
 * Created by katz on 17.06.2017.
 */
class PortSpeedSelector {

    private static ArrayList<Integer> speeds;

    static void init() {
        speeds = new ArrayList<>();
        speeds.add(9600);
        speeds.add(19200);
        speeds.add(38400);
        speeds.add(57600);
        speeds.add(115200);
    }

    static void showSpeeds() {
        System.out.println("Select speed: ");
        for (int i = 0; i < speeds.size(); i++)
            System.out.println(i + ": " + speeds.get(i));
    }

    static int getSpeed(int index) {
        return speeds.get(index);
    }
}
