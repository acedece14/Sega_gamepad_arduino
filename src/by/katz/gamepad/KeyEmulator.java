package by.katz.gamepad;

import by.katz.Log;
import by.katz.keys.KeyMap;

import java.awt.*;
import java.awt.event.KeyEvent;
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

    public static void setFastKeys(boolean selected) { isUseFastKeys = selected; }

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

    void pressEnter(boolean state) {
        keystroke(KeyMap.get().getKeyStart(), state);
    }

    void pressBackSpace(boolean state) {
        long time = 50;
        sleep(time);
        if (isUseFastKeys && state) {
            keystroke(KeyEvent.VK_RIGHT, true);
            sleep(146);
            keystroke(KeyEvent.VK_UP, true);
            sleep(108);
            keystroke(KeyEvent.VK_RIGHT, false);
            sleep(80);
            keystroke(KeyEvent.VK_LEFT, true);
            sleep(119);
            keystroke(KeyEvent.VK_UP, false);
            sleep(22);
            keystroke(KeyEvent.VK_DOWN, true);
            sleep(25);
            keystroke(KeyEvent.VK_LEFT, false);
            sleep(16);
            keystroke(KeyEvent.VK_RIGHT, true);
            sleep(165);


            keystroke(KeyEvent.VK_B, true);
            sleep(14);
            keystroke(KeyEvent.VK_A, true);
            sleep(301);
            keystroke(KeyEvent.VK_DOWN, false);
            sleep(45);
            keystroke(KeyEvent.VK_A, false);
            sleep(5);
            keystroke(KeyEvent.VK_RIGHT, false);
            sleep(60);
            keystroke(KeyEvent.VK_B, false);

        } else keystroke(KeyMap.get().getKeyMode(), state);
    }

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
        Log.log("KEY: " + KeyMap.getKeyNameByCode(key) + " state: " + (state ? "Press" : "Release") + " T: " + time);
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