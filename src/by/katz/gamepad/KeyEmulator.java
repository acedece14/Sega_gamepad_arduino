package by.katz.gamepad;

import by.katz.Log;
import by.katz.keys.KeyMap;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * //
 * Created by katz on 17.06.2017.
 */
public class KeyEmulator {

    private static KeyEmulator instance;
    private static boolean isUseFastKeys;
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

    void pressArrowUp(boolean state) {
        keystroke(KeyMap.get().getKeyUp(), state);
    }

    void pressArrowDown(boolean state) {
        keystroke(KeyMap.get().getKeyDown(), state);
    }

    void pressArrowLeft(boolean state) {
        keystroke(KeyMap.get().getKeyLeft(), state);
    }

    void pressArrowRight(boolean state) {
        keystroke(KeyMap.get().getKeyRight(), state);
    }

    void pressX(boolean state) {
        keystroke(KeyMap.get().getKeyX(), state);
    }

    void pressY(boolean state) {
        keystroke(KeyMap.get().getKeyY(), state);
    }

    void pressZ(boolean state) {
        keystroke(KeyMap.get().getKeyZ(), state);
    }

    void pressA(boolean state) {
        keystroke(KeyMap.get().getKeyA(), state);
    }

    void pressB(boolean state) {
        keystroke(KeyMap.get().getKeyB(), state);
    }

    void pressC(boolean state) {
        keystroke(KeyMap.get().getKeyC(), state);
    }

    void pressStart(boolean state) {
        keystroke(KeyMap.get().getKeyStart(), state);
    }

    void pressMode(boolean state) { keystroke(KeyMap.get().getKeyMode(), state); }

    private void sleep(long time) {
        time = 20;
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Set<Integer> states = new HashSet<>();


    private long lastTime = 0;

    private void keystroke(int key, boolean state) {

        long now = System.currentTimeMillis();
        long time = now - lastTime;
        lastTime = now;
        if (time > 5000)
            Log.log("\n");
        Log.log("KEY: " + KeyMap.getKeyNameByCode(key) + "\tstate: " + (state ? "Press" : "Release") + "\tT: " + time);
        if (state && !states.contains(key)) {
            states.add(key);
            robot.keyPress(key);
        }
        if (!state && states.contains(key)) {
            states.remove(key);
            robot.keyRelease(key);
        }
    }

}