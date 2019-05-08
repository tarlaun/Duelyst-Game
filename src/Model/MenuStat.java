package Model;

public enum MenuStat {
    MAIN,
    ACCOUNT,
    GAME,
    COLLECTION,
    SHOP,
    BATTLE_TYPE,
    GAME_MODE,
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
                return GAME_MODE;
            case GAME_MODE:
                return BATTLE_TYPE;
            case GRAVEYARD:
                return BATTLE;
            case ITEM_SELECTION:
                return BATTLE;
            default:
                return GAME;
        }
    }
}
