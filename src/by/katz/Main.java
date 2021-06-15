package by.katz;

import by.katz.gui.FormMain;
import by.katz.gui.ThemeSetter;
import by.katz.keys.KeyMap;


public class Main {


    public static void main(String[] args) {
        // test
        KeyMap.get().getKeyA();
        Settings.getInstance().loadSettings();
        ThemeSetter.apply(3);
        new FormMain();
    }
}
