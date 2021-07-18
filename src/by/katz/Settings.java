package by.katz;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Settings {

    private static File SETTINGS_FILE = new File("gp_settings.json");
    private static Settings instance;


    private String lastUsedKeymap = "";
    private String lastOpenedPort;
    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private boolean isNeedDarkTheme = false;
    private int serialTimeout = 200;

    private Settings() {}


    public static Settings getInstance() {
        if (instance == null)
            instance = new Settings();
        return instance;
    }

    public String getLastUsedKeymap() {
        return lastUsedKeymap;
    }

    public void setLastUsedKeymap(String lastUsedKeymap) {
        this.lastUsedKeymap = lastUsedKeymap;
        saveSettings();
    }


    void loadSettings() {
        if (!SETTINGS_FILE.exists())
            saveSettings();

        try (FileReader fileReader = new FileReader(SETTINGS_FILE)) {
            instance = new Gson().fromJson(fileReader, Settings.class);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(2);
        }
    }

    private void saveSettings() {
        try (FileWriter fileWriter = new FileWriter(SETTINGS_FILE)) {
            fileWriter.write(new GsonBuilder().setPrettyPrinting().create().toJson(this));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void setLastOpenedPort(String lastOpenedPort) {
        this.lastOpenedPort = lastOpenedPort;
        saveSettings();
    }

    public String getLastOpenedPort() {
        return lastOpenedPort;
    }

    boolean isNeedDarkTheme() {
        return isNeedDarkTheme;
    }

    public int getSerialTimeout() { return serialTimeout; }
}
