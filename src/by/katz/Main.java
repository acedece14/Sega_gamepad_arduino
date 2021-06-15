package by.katz;

import by.katz.gui.FormMain;
import by.katz.gui.ThemeSetter;
import by.katz.keys.KeyMap;


public class Main {


    public static void main(String[] args) {
        KeyMap.get().getKeyA();
        ThemeSetter.apply(3);
        new FormMain();
    }
}
