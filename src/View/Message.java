package View;

import com.google.gson.Gson;

public enum Message {
    INVALID_ACCOUNT,
    INCORRECT_PASSWORD,
    INVALID_OPTION,
    OBJECT_NOT_FOUND,
    EXISTING_DECK,
    INVALID_DECK,
    IS_SELECTED,
    FULL_DECK,
    INVALID_HERO_ADDITION,
    INSUFFICIENCY,
    MAXIMUM_ITEM_COUNT,
    MAXIMUM_HERO_COUNT,
    INVALID_TARGET,
    UNAVAILABLE,
    NOT_IN_HAND,
    INSUFFICIENT_MANA,
    INVALID_TURN,
    SUCCESSFUL_LOGIN,
    SUCCESSFUL_PURCHASE,
    SUCCESSFUL_SELL,
    SUCCESSFUL_MOVEMENT_MESSAGE,
    INSERTION,
    NOT_ABLE_TO_ATTACK,
    INVALID_PASSWORD,
    ACCOUNT_CREATION,
    EXISTS_IN_DECK,
    OBJECT_ADDED,
    SUCCESSFUL_REMOVE,
    INVALID_PLAYERS,
    PLAYER1_INVALID_DECK,
    PLAYER2_INVALID_DECK,
    FULL_CELL,
    SUCCESSFUL_INSERT,
    INVALID_CARD,
    BATTLE_STARTED,
    NULL,
    BATTLE_FINISHED,
    UNSUCCESSFUL_MOVE,
    SUCCESSFUL_MOVE;

    public String toJson() {
        return new Gson().toJson(this.toString());
    }

    public static Message fromJson(String json) throws Exception{
        return new Gson().fromJson(json, Message.class);
    }
}

