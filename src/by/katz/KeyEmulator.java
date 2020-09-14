package by.katz;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * //
 * Created by katz on 17.06.2017.
 */
class KeyEmulator {
    private Robot robot;

    private KeyEmulator() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }


    private static KeyEmulator instance;

    static KeyEmulator getInstance() {
        if (instance == null)
            instance = new KeyEmulator();
        return instance;
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

    void pressE(boolean state) {
        keystroke(KeyEvent.VK_E, state);
    }
    void pressBackSpace(boolean state) {
        keystroke(KeyEvent.VK_BACK_SPACE, state);
    }

    private Map<Integer, Boolean> states = new HashMap<>();

    private void keystroke(int key, boolean state) {

        if (state && !states.containsKey(key)) {
            states.put(key, state);
            robot.keyPress(key);
        }
        if (!state && states.containsKey(key)) {
            states.remove(key);
            robot.keyRelease(key);
        }
    }
}