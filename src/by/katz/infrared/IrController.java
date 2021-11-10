/*
 * Created by Konstantin Chuyasov
 * Last modified: 10.11.2021, 18:34
 * Contacts: acedece14@gmail.com
 *
 */

package by.katz.infrared;

import by.katz.Log;
import by.katz.Settings;
import by.katz.gui.DialogMainMenu;
import by.katz.gui.GuiUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.text.SimpleDateFormat;
import java.util.*;

import static by.katz.gui.GuiUtils.showNotify;

public class IrController {

    private final static Type TYPE_HASHMAP_STRING_STRING = new TypeToken<Set<IrKey>>() {}.getType();

    public static final String REPEAT_LAST = "FFFFFFFF";
    private static final String UNKNOWN_KEY = "UNKNOWN";
    private static final File FILE_IR_KEYS = new File("ir_keys.json");
    public static final int MIN_CNT_ACTIONS = 4;

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
        try (var fw = new FileWriter(FILE_IR_KEYS)) {
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
        for (var irKey : irKeys)
            if (irKey.getKeyCode().equals(strData)) {
                key = irKey;
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
        if (key.getName().equals("info")) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            GuiUtils.showNotify(sdf.format(new Date()), 120);
            return;
        }
        if (key.getName().equals("menu")) {
            if (keyRepeated > 1 || DialogMainMenu.isCreated())
                return;
            Log.log("Create menu");
            var listener = new DialogMainMenu();
            addListener(listener);
            return;
        }
        if (isUseMouse && !key.getServerMouseCommand().isEmpty()) {
            var url = "http://127.0.0.1/mouse";
            var vals = new HashMap<String, String>();
            vals.put("action", "move");
            setCoordinates(vals, 0, 0);

            sendMouseAction(key, url, vals, keyRepeated);
            return;
        }

        if (listeners.size() > 0)
            for (var l : listeners)
                l.onNewKeyPressed(strData);
        else {
            if (key.getName().equals(UNKNOWN_KEY))
                return;
            if (keyRepeated > 0 && keyRepeated < MIN_CNT_ACTIONS)
                return;
            int cnt = (keyRepeated / 10) + 1;
            while (cnt-- > 0)
                sendServerCommand(key);
        }
        Log.log("[IR] " + strData + " cnt:" + keyRepeated + " " + key.getName());
    }

    private void sendMouseAction(IrKey key, String url, HashMap<String, String> vals, int keyRepeated) {
        var value = keyRepeated * keyRepeated;
        switch (key.getServerMouseCommand()) {
            case "up" -> setCoordinates(vals, 0, -value);
            case "down" -> setCoordinates(vals, 0, value);
            case "left" -> setCoordinates(vals, -value, 0);
            case "right" -> setCoordinates(vals, value, 0);
            case "l_click" -> {
                if (keyRepeated > 0 && keyRepeated < 5)
                    return;
                vals.put("action", "click");
                vals.put("duration", "10");
            }
        }
        Log.log("[MOUSE] value = " + vals);
        sendPostRequest(url, vals);
    }

    private static void setCoordinates(HashMap<String, String> vals, int x, int y) {
        vals.put("xLen", String.valueOf(x));
        vals.put("yLen", String.valueOf(y));
    }

    private void sendServerCommand(IrKey key) {
        try {
            if (!key.getServerCommand().isEmpty()) {
                var serverBaseUrl = Settings.getInstance().getBaseServerUrl();
                Jsoup.connect(serverBaseUrl + key.getServerCommand()).get();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendPostRequest(String url, Map<String, String> vals) {
        try {
            Jsoup.connect(url).data(vals).post();
        } catch (IOException e) { e.printStackTrace(); }
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
