package Model;

import java.util.HashMap;

public class CheatData {
    private HashMap<String, String> cheats = new HashMap<>();
    public transient static final CheatData CHEAT_DATA = new CheatData();

    private CheatData() {

    }

    public static CheatData getInstance() {
        return CHEAT_DATA;
    }

    public HashMap<String, String> getCheats() {
        return cheats;
    }

    public String getResult(String code) {
        return cheats.getOrDefault(code, "Fail");
    }

    public void addCode(String code, String result) {
        cheats.put(code, result);
    }
}
