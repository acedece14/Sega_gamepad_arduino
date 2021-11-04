package by.katz;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;

public class IrController {

    private final static Type TYPE_HASHMAP_STRING_STRING = new TypeToken<HashMap<String, String>>() {}.getType();


    public static final String REPEAT_LAST = "FFFFFFFF";
    private static final File FILE_IR_KEYS = new File("ir_keys.json");
    private static IrController instance;


    public static IrController getInstance() {
        if (instance == null)
            instance = new IrController();
        return instance;
    }

    private HashMap<String, String> irKeys;

    private IrController() {
        irKeys = new HashMap<>();
        loadFromFile();
    }

    private void loadFromFile() {
        if (FILE_IR_KEYS.exists()) {
            try (FileReader fr = new FileReader(FILE_IR_KEYS)) {
                irKeys = new Gson().fromJson(fr, TYPE_HASHMAP_STRING_STRING);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        } else {
            irKeys = new HashMap<>();
            irKeys.put("6685E959", "Power key");
            saveToFile();
        }
    }

    private void saveToFile() {
        try (FileWriter fw = new FileWriter(FILE_IR_KEYS)) {
            var json = new GsonBuilder().setPrettyPrinting().create().toJson(irKeys);
            fw.write(json);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }


    public void processNewData(String strData) {
        if (strData.equals(REPEAT_LAST)) {
            Log.log("repeat last");
        } else {
            Log.log("pressed: " + strData);
            //if (irKeys.containsKey(strData))
        }
    }
}
