package by.katz.gamepad;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * //
 * Created by katz on 17.06.2017.
 */
class KeyEmulator {

    private static KeyEmulator instance;
    private Robot robot;

    private KeyEmulator() {
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
        keystroke(KeyEvent.VK_UP, state);
    }

    void pressArrowDown(boolean state) {
        keystroke(KeyEvent.VK_DOWN, state);
    }

    void pressArrowLeft(boolean state) {
        keystroke(KeyEvent.VK_LEFT, state);
    }

    void pressArrowRight(boolean state) {
        keystroke(KeyEvent.VK_RIGHT, state);
    }

    void pressX(boolean state) {
        keystroke(KeyEvent.VK_X, state);
    }

    void pressY(boolean state) {
        keystroke(KeyEvent.VK_Y, state);
    }

    void pressZ(boolean state) {
        keystroke(KeyEvent.VK_Z, state);
    }

    void pressA(boolean state) {
        keystroke(KeyEvent.VK_A, state);
    }

    void pressB(boolean state) {
        keystroke(KeyEvent.VK_B, state);
    }

    void pressC(boolean state) {
        keystroke(KeyEvent.VK_C, state);
    }

    void pressEnter(boolean state) {
        keystroke(KeyEvent.VK_ENTER, state);
    }

    void pressBackSpace(boolean state) {
        keystroke(KeyEvent.VK_BACK_SPACE, state);
    }

    private Set<Integer> states = new HashSet<>();

    private void keystroke(int key, boolean state) {
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