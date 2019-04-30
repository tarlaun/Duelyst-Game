package Model;

import java.util.Random;

public enum MenuStat {
    MAIN,
    ACCOUNT,
    GAME,
    COLLECTION,
    SHOP,
    BATTLE,
    ITEM_SELECTION,
    GRAVEYARD;

    public MenuStat prevMenu() {
        switch (this) {
            case MAIN:
                System.exit(0);
            case ACCOUNT:
                return MAIN;
            case GAME:
                return ACCOUNT;
            case GRAVEYARD:
                return BATTLE;
            default:
                return GAME;
        }
    }
}
