package Model;

import com.google.gson.Gson;

public enum GameType {
    MULTIPLAYER,
    SINGLEPLAYER;

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static GameType fromJson(String json) {
        return new Gson().fromJson(json, GameType.class);
    }

    public static GameType findGameType(String s){
        if(s.equals(GameType.SINGLEPLAYER.toString()))
            return GameType.SINGLEPLAYER;
        else
            return GameType.MULTIPLAYER;
    }
}
