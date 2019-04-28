package Model;

public enum MenuStat {
    MAIN(0),
    ACCOUNT(1),
    GAME(2),
    COLLECTION(3),
    SHOP(3),
    BATTLE(3),
    GRAVEYARD(4);

    MenuStat(int order) {
        this.order = order;
    }

    private int order;

    public int inOrder() {
        return order;
    }
}
