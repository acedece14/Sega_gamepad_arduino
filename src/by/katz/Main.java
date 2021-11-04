package by.katz;

import by.katz.gui.FormMain;
import by.katz.gui.ThemeSetter;
import by.katz.keys.KeyMap;
import com.formdev.flatlaf.FlatDarculaLaf;


public class Main {


    public static void main(String[] args) {
        //noinspection ResultOfMethodCallIgnored
        KeyMap.get().getKeyA();
        Settings.getInstance().loadSettings();
        FlatDarculaLaf.setup();
        new FormMain();
    }
}
