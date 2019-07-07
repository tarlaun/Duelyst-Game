package Model;

import com.google.gson.Gson;

public enum BattleMode {
    KILLENEMYHERO,
    FLAG,
    COLLECTING;

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static BattleMode fromJson(String json) {
        return new Gson().fromJson(json, BattleMode.class);
    }
}
