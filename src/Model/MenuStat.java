package Model;

public enum MenuStat {
    MAIN,
    ACCOUNT,
    GAME,
    COLLECTION,
    SHOP,
    GAME_TYPE,
    BATTLE_MODE,
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
            case BATTLE:
                return BATTLE_MODE;
            case BATTLE_MODE:
                return GAME_TYPE;
            case GRAVEYARD:
                return BATTLE;
            case ITEM_SELECTION:
                return BATTLE;
            default:
                return GAME;
        }
    }
}
