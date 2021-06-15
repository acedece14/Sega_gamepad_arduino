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

    private Settings() {}

    private String lastUsedKeymap = "";

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

    public void saveSettings() {
        try (FileWriter fileWriter = new FileWriter(SETTINGS_FILE)) {
            fileWriter.write(new GsonBuilder().setPrettyPrinting().create().toJson(this));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
