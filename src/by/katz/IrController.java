/*
 * Created by Konstantin Chuyasov
 * Last modified: 06.11.2021, 19:44
 * Contacts: acedece14@gmail.com
 *
 */

package by.katz;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static by.katz.gui.GuiUtils.showNotify;

public class IrController {

    private final static Type TYPE_HASHMAP_STRING_STRING = new TypeToken<Set<IrKey>>() {}.getType();

    public static final String REPEAT_LAST = "FFFFFFFF";
    private static final File FILE_IR_KEYS = new File("ir_keys.json");
    public static final String UNKNOWN_KEY = "UNKNOWN";
    public static final int MIN_CNT_ACTIONS = 5;
    private static IrController instance;


    private final Set<IIrListener> listeners = new HashSet<>();

    public static IrController get() {
        if (instance == null)
            instance = new IrController();
        return instance;
    }

    private Set<IrKey> irKeys;
    private boolean isUseMouse = false;

    private IrController() {
        irKeys = new HashSet<>();
        loadFromFile();
    }


    public static class IrKey {
        private final String key;
        private final String name;
        private final String serverCommand;
        private final String serverMouseCommand;

        public IrKey(String keyCode, String keyName, String serverCommand) {
            this.key = keyCode;
            this.name = keyName;
            this.serverCommand = serverCommand;
            this.serverMouseCommand = "";
        }

        public String getKey() { return key; }

        public String getName() { return name; }

        public String getServerCommand() { return serverCommand; }
    }

    private void loadFromFile() {
        if (FILE_IR_KEYS.exists()) {
            try (FileReader fr = new FileReader(FILE_IR_KEYS)) {
                irKeys = new Gson().fromJson(fr, TYPE_HASHMAP_STRING_STRING);
                Log.log("Ir keys loaded");
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        } else {
            irKeys = new HashSet<>();
            irKeys.add(new IrKey("6685E959", "Power key", ""));
            saveToFile();
        }
    }

    private void saveToFile() {
        try (FileWriter fw = new FileWriter(FILE_IR_KEYS)) {
            var json = new GsonBuilder().setPrettyPrinting().create().toJson(irKeys);
            fw.write(json);
            Log.log("Ir keys saved");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }


    private String lastKey = "";
    private int keyRepeated = -1;

    public void processNewData(final String startStrData) {

        var strData = startStrData;
        if (strData.equals(REPEAT_LAST) && !lastKey.equals("")) {
            strData = lastKey;
            keyRepeated++;
        }

        var key = new IrKey(strData, UNKNOWN_KEY, "");
        for (IrKey k : irKeys)
            if (k.key.equals(strData)) {
                key = k;
                break;
            }

        if (!startStrData.equals(REPEAT_LAST)) {
            lastKey = strData;
            keyRepeated = 0;
            if (key.getName().equals("SWITCH")) {
                isUseMouse = !isUseMouse;
                showNotify("Mouse mode: " + (isUseMouse ? "ON" : "OFF"));
                return;
            }
        }

        if (isUseMouse && key.serverMouseCommand != null) {
            var url = "http://127.0.0.1/mouse";
            var vals = new HashMap<String, String>();
            vals.put("action", "move");
            vals.put("xLen", String.valueOf(0));
            vals.put("yLen", String.valueOf(0));

            sendMouseAction(key, url, vals, keyRepeated);
            return;
        }

        if (listeners.size() > 0)
            for (var l : listeners)
                l.onNewKeyPressed(strData);
        else {
            if (key.name.equals(UNKNOWN_KEY))
                return;
            if (keyRepeated > 0 && keyRepeated < MIN_CNT_ACTIONS)
                return;
            int cnt = (keyRepeated / 10) + 1;
            while (cnt-- > 0)
                executeAction(key);
        }
        Log.log("[IR] " + strData + " cnt:" + keyRepeated + " " + key.name);
    }

    private void sendMouseAction(IrKey key, String url, HashMap<String, String> vals, int keyRepeated) {
        var value = keyRepeated * keyRepeated;
        switch (key.getName()) {
            case "up" -> {
                vals.put("xLen", String.valueOf(0));
                vals.put("yLen", String.valueOf(-value));
            }
            case "down" -> {
                vals.put("xLen", String.valueOf(0));
                vals.put("yLen", String.valueOf(value));
            }
            case "left" -> {
                vals.put("xLen", String.valueOf(-value));
                vals.put("yLen", String.valueOf(0));
            }
            case "right" -> {
                vals.put("xLen", String.valueOf(value));
                vals.put("yLen", String.valueOf(0));
            }
            case "OK" -> {
                if (keyRepeated > 0 && keyRepeated < 5)
                    return;
                vals.put("action", "click");
                vals.put("duration", "10");
            }
        }
        Log.log("[MOUSE] " + vals + " value=" + vals);
        sendPostRequest(url, vals);
    }

    private void executeAction(IrKey key) {
        try {
            if (!key.getServerCommand().isEmpty()) {
                var serverBaseUrl = Settings.getInstance().getBaseServerUrl();
                Jsoup.connect(serverBaseUrl + key.getServerCommand()).get();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void sendPostRequest(String url, Map<String, String> vals) {
        try {
            Jsoup.connect(url).data(vals).post();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addNewKey(String key, String keyName) {
        irKeys.add(new IrKey(key, keyName, ""));
        saveToFile();
    }

    public Set<IrKey> getKeys() { return irKeys; }

    public interface IIrListener {
        void onNewKeyPressed(String key);
    }

    public void addListener(IIrListener listener) { listeners.add(listener); }

    public void removeListener(IIrListener listener) { listeners.remove(listener); }
}
