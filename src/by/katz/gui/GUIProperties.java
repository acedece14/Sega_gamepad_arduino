/*
 * Created by Konstantin Chuyasov
 * Last modified: 06.11.2021, 19:44
 * Contacts: acedece14@gmail.com
 *
 */

package by.katz.gui;

import javax.swing.*;

@SuppressWarnings({"WeakerAccess", "unused"})
public class GUIProperties {
    public static final boolean IS_CUSTOM_ENABLED = false;
    // see definition in class com.jtattoo.plaf.texture.TextureUtils
    //public static final Integer TEXTURE_TYPE = new Integer(0); // WindowTexture
    public static final Integer TEXTURE_TYPE = 1; // BackgroundTexture

    public static final String PLAF_METAL = "javax.swing.plaf.metal.MetalLookAndFeel";
    public static final String PLAF_NIMBUS = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
    public static final String PLAF_MOTIF = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
    public static final String PLAF_SYSTEM = UIManager.getSystemLookAndFeelClassName();

    public static final String PLAF_ACRYL = "com.jtattoo.plaf.acryl.AcrylLookAndFeel";
    public static final String PLAF_AERO = "com.jtattoo.plaf.aero.AeroLookAndFeel";
    public static final String PLAF_ALUMINIUM = "com.jtattoo.plaf.aluminium.AluminiumLookAndFeel";
    public static final String PLAF_BERNSTEIN = "com.jtattoo.plaf.bernstein.BernsteinLookAndFeel";
    public static final String PLAF_FAST = "com.jtattoo.plaf.fast.FastLookAndFeel";
    public static final String PLAF_GRAPHITE = "com.jtattoo.plaf.graphite.GraphiteLookAndFeel";
    public static final String PLAF_HIFI = "com.jtattoo.plaf.hifi.HiFiLookAndFeel";
    public static final String PLAF_LUNA = "com.jtattoo.plaf.luna.LunaLookAndFeel";
    public static final String PLAF_MCWIN = "com.jtattoo.plaf.mcwin.McWinLookAndFeel";
    public static final String PLAF_MINT = "com.jtattoo.plaf.mint.MintLookAndFeel";
    public static final String PLAF_NOIRE = "com.jtattoo.plaf.noire.NoireLookAndFeel";
    public static final String PLAF_SMART = "com.jtattoo.plaf.smart.SmartLookAndFeel";
    public static final String PLAF_TEXTURE = "com.jtattoo.plaf.texture.TextureLookAndFeel";

    public static final String PLAF_CUSTOM = "com.jtattoo.plaf.custom.flx.FLXLookAndFeel";
    //public static final String PLAF_CUSTOM = "com.jtattoo.plaf.custom.quantycarlo.DarkRoastLookAndFeel";
    //public static final String PLAF_CUSTOM = "com.jtattoo.plaf.custom.systemx.SystemXLookAndFeel";

    private String lookAndFeel = PLAF_ACRYL;
    //private String lookAndFeel = PLAF_CUSTOM;
    private String theme = "Default";

    public GUIProperties() {
    }

    public void setLookAndFeel(String aLookAndFeel) {
        lookAndFeel = aLookAndFeel;
    }

    public String getLookAndFeel() {
        return lookAndFeel;
    }

    public void setTheme(String aTheme) {
        theme = aTheme;
    }

    public String getTheme() {
        return theme;
    }

    public boolean isMetalLook() {
        return lookAndFeel.equals(PLAF_METAL);
    }

    public boolean isNimbusLook() {
        return lookAndFeel.equals(PLAF_NIMBUS);
    }

    public boolean isMotifLook() {
        return lookAndFeel.equals(PLAF_MOTIF);
    }

    public boolean isSystemLook() {
        return lookAndFeel.equals(PLAF_SYSTEM);
    }

    public boolean isAcrylLook() {
        return lookAndFeel.equals(PLAF_ACRYL);
    }

    public boolean isAeroLook() {
        return lookAndFeel.equals(PLAF_AERO);
    }

    public boolean isAluminiumLook() {
        return lookAndFeel.equals(PLAF_ALUMINIUM);
    }

    public boolean isBernsteinLook() {
        return lookAndFeel.equals(PLAF_BERNSTEIN);
    }

    public boolean isFastLook() {
        return lookAndFeel.equals(PLAF_FAST);
    }

    public boolean isGraphiteLook() {
        return lookAndFeel.equals(PLAF_GRAPHITE);
    }

    public boolean isHiFiLook() {
        return lookAndFeel.equals(PLAF_HIFI);
    }

    public boolean isLunaLook() {
        return lookAndFeel.equals(PLAF_LUNA);
    }

    public boolean isMcWinLook() {
        return lookAndFeel.equals(PLAF_MCWIN);
    }

    public boolean isMintLook() {
        return lookAndFeel.equals(PLAF_MINT);
    }

    public boolean isNoireLook() {
        return lookAndFeel.equals(PLAF_NOIRE);
    }

    public boolean isSmartLook() {
        return lookAndFeel.equals(PLAF_SMART);
    }

    public boolean isTextureLook() {
        return lookAndFeel.equals(PLAF_TEXTURE);
    }

    public static boolean isCustomEnabled() {
        return IS_CUSTOM_ENABLED;
    }

    public boolean isCustomLook() {
        return lookAndFeel.equals(PLAF_CUSTOM);
    }
}
