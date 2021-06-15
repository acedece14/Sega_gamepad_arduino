package by.katz.gui;

import javax.swing.*;
import java.util.Random;

/**
 * //
 * Created by katz on 26.05.2017.
 */
public class ThemeSetter {

    public static void apply(int r) {

        ToolTipManager.sharedInstance().setInitialDelay(500);
        ToolTipManager.sharedInstance().setDismissDelay(60000);
        ToolTipManager.sharedInstance().setReshowDelay(0);
        com.jtattoo.plaf.aluminium.AluminiumLookAndFeel.setTheme("Default");
        GUIProperties guiProps = new GUIProperties();
        switch (r) {
            case 0:
                guiProps.setLookAndFeel(GUIProperties.PLAF_GRAPHITE);
                break;
            case 1:
                guiProps.setLookAndFeel(GUIProperties.PLAF_MINT);
                break;
            case 2:
                guiProps.setLookAndFeel(GUIProperties.PLAF_MCWIN);
                break;
            case 3:
                guiProps.setLookAndFeel(GUIProperties.PLAF_ALUMINIUM);
                break;
        }
        try {
            UIManager.setLookAndFeel(guiProps.getLookAndFeel());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private static int getRand(int min, int max) { return new Random().nextInt((max - min) + 1) + min; }
}
