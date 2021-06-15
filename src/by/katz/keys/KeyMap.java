package by.katz.keys;

import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class KeyMap {

    private static final Map<String, Integer> keys = new HashMap<>();


    private KeyMap() throws Exception { }

    private static void loadKeys() {
        try {
            final Field[] fields = java.awt.event.KeyEvent.class.getDeclaredFields();
            for (Field f : fields)
                if (Modifier.isStatic(f.getModifiers()) && !Modifier.isPrivate(f.getModifiers()))
                    keys.put(f.getName(), f.getInt(null));
        } catch (IllegalAccessException ignored) { }
        System.out.println("Keys loaded");
    }

    private static KeyMap instance;

    public static KeyMap get() {
        if (instance == null) {
            loadKeys();
            try {
                instance = new KeyMap();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return instance;
    }

    private int keyUp = getKeyCodeByName("VK_UP");
    private int keyDown = getKeyCodeByName("VK_DOWN");
    private int keyLeft = getKeyCodeByName("VK_LEFT");
    private int keyRight = getKeyCodeByName("VK_RIGHT");

    private int keyA = getKeyCodeByName("VK_A");
    private int keyB = getKeyCodeByName("VK_B");
    private int keyC = getKeyCodeByName("VK_C");

    private int keyX = getKeyCodeByName("VK_X");
    private int keyY = getKeyCodeByName("VK_Y");
    private int keyZ = getKeyCodeByName("VK_Z");

    private int keyStart = getKeyCodeByName("VK_ENTER");
    private int keyMode = getKeyCodeByName("VK_BACK_SPACE");

    public static int getKeyCodeByName(String keyName) throws Exception {
        if (keys.containsKey(keyName))
            return keys.get(keyName);
        else {
            keys.forEach((k, v) -> System.out.println("key: " + k + " value:" + v));
            throw new Exception("bad key: " + keyName);
        }
    }

    public static String getKeyNameByCode(int code) {
        for (Map.Entry<String, Integer> key : keys.entrySet()) {
            if (key.getValue() == code)
                return key.getKey();
        }
        return "UNKNOWN!";
    }

    public static void saveKeyMap(String name) {
        try (FileWriter fw = new FileWriter("keymap_" + name + ".kmap")) {

            String json = new GsonBuilder().setPrettyPrinting()
                    .registerTypeAdapter(KeyMap.class, new KeysSerializer())
                    .create()
                    .toJson(instance);
            fw.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadKeyMap(String name) {
        try (FileReader fr = new FileReader("keymap_" + name + ".kmap")) {
            instance = new GsonBuilder().setPrettyPrinting()
                    .registerTypeAdapter(KeyMap.class, new KeysSerializer())
                    .create()
                    .fromJson(fr, KeyMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getKeyUp() {
        return keyUp;
    }

    public int getKeyDown() {
        return keyDown;
    }

    public int getKeyLeft() {
        return keyLeft;
    }

    public int getKeyRight() {
        return keyRight;
    }

    public int getKeyA() {
        return keyA;
    }

    public int getKeyB() {
        return keyB;
    }

    public int getKeyC() {
        return keyC;
    }

    public int getKeyX() {
        return keyX;
    }

    public int getKeyY() {
        return keyY;
    }

    public int getKeyZ() {
        return keyZ;
    }

    public int getKeyStart() {
        return keyStart;
    }

    public int getKeyMode() {
        return keyMode;
    }

    public void setKeyUp(int keyUp) {
        this.keyUp = keyUp;
    }

    void setKeyDown(int keyDown) {
        this.keyDown = keyDown;
    }

    void setKeyLeft(int keyLeft) {
        this.keyLeft = keyLeft;
    }

    public void setKeyRight(int keyRight) {
        this.keyRight = keyRight;
    }

    public void setKeyA(int keyA) {
        this.keyA = keyA;
    }

    public void setKeyB(int keyB) {
        this.keyB = keyB;
    }

    public void setKeyC(int keyC) {
        this.keyC = keyC;
    }

    public void setKeyX(int keyX) {
        this.keyX = keyX;
    }

    public void setKeyY(int keyY) {
        this.keyY = keyY;
    }

    public void setKeyZ(int keyZ) {
        this.keyZ = keyZ;
    }

    public void setKeyStart(int keyStart) {
        this.keyStart = keyStart;
    }

    public void setKeyMode(int keyMode) {
        this.keyMode = keyMode;
    }
}
