package Model;

public enum MenuStat {
    MAIN,
    ACCOUNT,
    COLLECTION,
    SHOP,
    GAME_TYPE,
    PROCESS,
    BATTLE_MODE,
    SELECT_USER,
    BATTLE,
    ITEM_SELECTION,
    BACK_GROUND,
    CUSTOM_CARD,
    CUSTOM_BUFF,
    MATCH_HISTORY,
    GRAVEYARD;

    public MenuStat prevMenu() {
        switch (this) {
            case MAIN:
                System.exit(0);
            case ACCOUNT:
                return MAIN;
            case BATTLE:
                return BATTLE_MODE;
            case BATTLE_MODE:
                return GAME_TYPE;
            case GRAVEYARD:
                return BATTLE;
            case ITEM_SELECTION:
                return BATTLE;
            case PROCESS:
                return GAME_TYPE;
            case SELECT_USER:
                return GAME_TYPE;
            default:
                return ACCOUNT;
        }
    }
}
