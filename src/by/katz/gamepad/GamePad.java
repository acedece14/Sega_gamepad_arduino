/*
 * Created by Konstantin Chuyasov
 * Last modified: 07.11.2021, 13:30
 * Contacts: acedece14@gmail.com
 *
 */

package by.katz.gamepad;

public enum GamePad {
    INSTANCE;
    private static final int A = 0;
    private static final int B = 1;
    private static final int C = 2;
    private static final int X = 4;
    private static final int Y = 5;
    private static final int Z = 6;
    private static final int MODE = 7;
    private static final int START = 9;
    private static final int UP = 12;
    private static final int DOWN = 13;
    private static final int LEFT = 14;
    private static final int RIGHT = 15;

    private static final int BUTTONS_COUNT = 17;

    private final boolean[] bits = new boolean[BUTTONS_COUNT];
    private final boolean[] lastBits = new boolean[BUTTONS_COUNT];

    public void runCommand(int value) {
        for (var i = BUTTONS_COUNT - 1; i >= 0; i--)
            bits[i] = (value & (1 << i)) != 0;
        for (var i = 0; i < BUTTONS_COUNT; i++)
            if (lastBits[i] != bits[i])
                applyButton(i, bits[i]);
        System.arraycopy(bits, 0, lastBits, 0, BUTTONS_COUNT);
    }

    private void applyButton(int key, boolean state) {
        var kEm = KeyEmulator.getInstance();
        switch (key) {
            case UP -> kEm.pressArrowUp(state);
            case DOWN -> kEm.pressArrowDown(state);
            case LEFT -> kEm.pressArrowLeft(state);
            case RIGHT -> kEm.pressArrowRight(state);
            case A -> kEm.pressA(state);
            case B -> kEm.pressB(state);
            case C -> kEm.pressC(state);
            case X -> kEm.pressX(state);
            case Y -> kEm.pressY(state);
            case Z -> kEm.pressZ(state);
            case START -> kEm.pressStart(state);
            case MODE -> kEm.pressMode(state);
        }
    }

}
