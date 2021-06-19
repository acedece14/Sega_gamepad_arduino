package by.katz.gamepad;

import by.katz.Log;
import by.katz.keys.KeyMap;

import java.awt.*;
import java.util.ArrayList;

/**
 * //
 * Created by katz on 17.06.2017.
 */
public class GamePad {
    private static final int UP = 12;
    private static final int DOWN = 13;
    private static final int LEFT = 14;
    private static final int RIGHT = 15;
    private static final int A = 0;
    private static final int B = 1;
    private static final int C = 2;
    private static final int X = 4;
    private static final int Y = 5;
    private static final int Z = 6;
    private static final int MODE = 7;
    private static final int START = 9;

    private ArrayList<Button> keys = new ArrayList<>();

    class Button {
        String name;

        Button(String name) {
            this.name = name;
        }
    }

    private static GamePad instance;

    private static final int BUTTONS_COUNT = 17;

    private boolean[] bits;
    private boolean[] lastBits;

    private GamePad() {
        bits = new boolean[BUTTONS_COUNT];
        lastBits = new boolean[BUTTONS_COUNT];
        keys = new Keys();
        for (Button key : keys)
            Log.log(key.toString());
    }

    public static GamePad getInstance() {
        if (instance == null) {
            instance = new GamePad();
        }
        return instance;
    }


    private class Keys extends ArrayList<Button> {


        private Keys() {
            keys.add(new Button("SMD_A"));
            keys.add(new Button("SMD_B"));
            keys.add(new Button("SMD_C"));
            keys.add(new Button("SMD_EMPTY_1"));
            keys.add(new Button("SMD_X"));
            keys.add(new Button("SMD_Y"));
            keys.add(new Button("SMD_Z"));
            keys.add(new Button("SMD_MODE"));
            keys.add(new Button("SMD_EMPTY_2"));
            keys.add(new Button("SMD_START"));
            keys.add(new Button("SMD_EMPTY_3"));
            keys.add(new Button("SMD_EMPTY_4"));
            keys.add(new Button("SMD_UP"));
            keys.add(new Button("SMD_DOWN"));
            keys.add(new Button("SMD_LEFT"));
            keys.add(new Button("SMD_RIGHT"));
            keys.add(new Button("SMD_MAX_KEYS"));
        }

    }

    public void runCommand(String args) {
        int value;
        try {
            value = Integer.valueOf(args);
        } catch (NumberFormatException e) {

            byte[] bytes = args.getBytes();
            String res = "";
            for (byte aByte : bytes)
                res += "#" + (int) aByte + " ";
            Toolkit.getDefaultToolkit().beep();
            Log.log("error decode: " + res);
            e.printStackTrace();
            //System.exit(1);
            return;
        }

        for (int i = BUTTONS_COUNT - 1; i >= 0; i--)
            bits[i] = (value & (1 << i)) != 0;
        for (int i = 0; i < BUTTONS_COUNT; i++)
            if (lastBits[i] != bits[i])
                applyButton(i, bits[i]);

        System.arraycopy(bits, 0, lastBits, 0, BUTTONS_COUNT);
    }

    private void applyButton(int key, boolean state) {
        switch (key) {
            case UP:
                KeyEmulator.getInstance().pressArrowUp(state);
                break;
            case DOWN:
                KeyEmulator.getInstance().pressArrowDown(state);
                break;
            case LEFT:
                KeyEmulator.getInstance().pressArrowLeft(state);
                break;
            case RIGHT:
                KeyEmulator.getInstance().pressArrowRight(state);
                break;


            case A:
                KeyEmulator.getInstance().pressA(state);
                break;
            case B:
                KeyEmulator.getInstance().pressB(state);
                break;
            case C:
                KeyEmulator.getInstance().pressC(state);
                break;
            case X:
                KeyEmulator.getInstance().pressX(state);
                break;
            case Y:
                KeyEmulator.getInstance().pressY(state);
                break;
            case Z:
                KeyEmulator.getInstance().pressZ(state);
                break;


            case START:
                KeyEmulator.getInstance().pressEnter(state);
                break;
            case MODE:
                KeyEmulator.getInstance().pressBackSpace(state);
                break;
        }
    }

}
