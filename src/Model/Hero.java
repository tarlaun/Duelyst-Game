package Model;

public class Hero extends Card {
    private int cooldown ;
    public Hero(String info) {
        super(info.split(Constants.CARD_INFO_SPLITTER));
    }
}
