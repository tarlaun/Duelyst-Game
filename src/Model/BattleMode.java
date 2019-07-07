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

    public static BattleMode findBattleMode(String s){
        if(s.equals(BattleMode.COLLECTING.toString()))
            return BattleMode.COLLECTING;
        else if(s.equals(BattleMode.FLAG.toString()))
            return BattleMode.FLAG;
        else
            return BattleMode.KILLENEMYHERO;
    }
}
