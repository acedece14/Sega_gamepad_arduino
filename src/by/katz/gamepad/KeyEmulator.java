/*
 * Created by Konstantin Chuyasov
 * Last modified: 06.11.2021, 19:44
 * Contacts: acedece14@gmail.com
 *
 */

package by.katz.gamepad;

import by.katz.Log;
import by.katz.keys.KeyMap;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class KeyEmulator {

    private static KeyEmulator instance;
    private Robot robot;

    public KeyEmulator() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    static KeyEmulator getInstance() {
        return instance == null ? instance = new KeyEmulator() : instance;
    }

    void pressArrowUp(boolean state) { keyStroke(KeyMap.get().getKeyUp(), state); }

    void pressArrowDown(boolean state) {
        keyStroke(KeyMap.get().getKeyDown(), state);
    }

    void pressArrowLeft(boolean state) {
        keyStroke(KeyMap.get().getKeyLeft(), state);
    }

    void pressArrowRight(boolean state) {
        keyStroke(KeyMap.get().getKeyRight(), state);
    }

    void pressX(boolean state) {
        keyStroke(KeyMap.get().getKeyX(), state);
    }

    void pressY(boolean state) {
        keyStroke(KeyMap.get().getKeyY(), state);
    }

    void pressZ(boolean state) {
        keyStroke(KeyMap.get().getKeyZ(), state);
    }

    void pressA(boolean state) {
        keyStroke(KeyMap.get().getKeyA(), state);
    }

    void pressB(boolean state) {
        keyStroke(KeyMap.get().getKeyB(), state);
    }

    void pressC(boolean state) {
        keyStroke(KeyMap.get().getKeyC(), state);
    }

    void pressStart(boolean state) {
        keyStroke(KeyMap.get().getKeyStart(), state);
    }

    void pressMode(boolean state) { keyStroke(KeyMap.get().getKeyMode(), state); }

    private final Set<Integer> keysStates = new HashSet<>();
    private long lastTime = 0;

    private void keyStroke(int keyCode, boolean curState) {
        final var timeNow = System.currentTimeMillis();
        final var timeBetween = timeNow - lastTime;
        lastTime = timeNow;
        if (timeBetween > 5000)
            Log.log("\n");
        Log.log("KEY: " + KeyMap.getKeyNameByCode(keyCode) + "\tstate: " + (curState ? "Press" : "Release") + "\tT: " + timeBetween);
        if (timeBetween < 10 && curState)
            Log.log("ERROR TIMING");
        else if (curState && !keysStates.contains(keyCode)) {
            keysStates.add(keyCode);
            robot.keyPress(keyCode);
        } else if (!curState && keysStates.contains(keyCode)) {
            keysStates.remove(keyCode);
            robot.keyRelease(keyCode);
        }
    }
}