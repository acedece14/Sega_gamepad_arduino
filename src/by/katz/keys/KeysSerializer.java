package by.katz.keys;

import by.katz.Log;
import com.google.gson.*;

import java.lang.reflect.Type;

public class KeysSerializer
        implements JsonSerializer<KeyMap>, JsonDeserializer<KeyMap> {

    @Override
    public JsonElement serialize(KeyMap keymap, Type type, JsonSerializationContext context) {

        final JsonObject jsonKeymap = new JsonObject();

        jsonKeymap.addProperty("keyA", KeyMap.getKeyNameByCode(keymap.getKeyA()));
        jsonKeymap.addProperty("keyB", KeyMap.getKeyNameByCode(keymap.getKeyB()));
        jsonKeymap.addProperty("keyC", KeyMap.getKeyNameByCode(keymap.getKeyC()));
        jsonKeymap.addProperty("keyX", KeyMap.getKeyNameByCode(keymap.getKeyX()));
        jsonKeymap.addProperty("keyY", KeyMap.getKeyNameByCode(keymap.getKeyY()));
        jsonKeymap.addProperty("keyZ", KeyMap.getKeyNameByCode(keymap.getKeyZ()));

        jsonKeymap.addProperty("keyUp", KeyMap.getKeyNameByCode(keymap.getKeyUp()));
        jsonKeymap.addProperty("keyDown", KeyMap.getKeyNameByCode(keymap.getKeyDown()));
        jsonKeymap.addProperty("keyLeft", KeyMap.getKeyNameByCode(keymap.getKeyLeft()));
        jsonKeymap.addProperty("keyRight", KeyMap.getKeyNameByCode(keymap.getKeyRight()));

        jsonKeymap.addProperty("keyStart", KeyMap.getKeyNameByCode(keymap.getKeyStart()));
        jsonKeymap.addProperty("keyMode", KeyMap.getKeyNameByCode(keymap.getKeyMode()));

        return jsonKeymap;
    }

    @Override
    public KeyMap deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
            throws JsonParseException {

        final KeyMap keyMap = KeyMap.get();
        try {
            final JsonObject data = jsonElement.getAsJsonObject();

            Log.log("Get keys...");
            keyMap.setKeyA(revealKey("keyA", data));
            keyMap.setKeyB(revealKey("keyB", data));
            keyMap.setKeyC(revealKey("keyC", data));
            keyMap.setKeyX(revealKey("keyX", data));
            keyMap.setKeyY(revealKey("keyY", data));
            keyMap.setKeyZ(revealKey("keyZ", data));

            keyMap.setKeyUp(revealKey("keyUp", data));
            keyMap.setKeyDown(revealKey("keyDown", data));
            keyMap.setKeyLeft(revealKey("keyLeft", data));
            keyMap.setKeyRight(revealKey("keyRight", data));

            keyMap.setKeyStart(revealKey("keyStart", data));
            keyMap.setKeyMode(revealKey("keyMode", data));

            Log.log("Keys deserialized");
        } catch (Exception e) {
            Log.log("Cant deserialize keys : " + e.getLocalizedMessage());
            e.printStackTrace();
        }
        return keyMap;
    }

    private static int revealKey(String key, JsonObject data) throws Exception {
        String temp = String.valueOf(data.get(key));
        temp = temp.substring(1, temp.length() - 1);
        Log.log(key + "\t" + temp);
        return KeyMap.getKeyCodeByName(temp);
    }
}
