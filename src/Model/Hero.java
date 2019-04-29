package Model;

public class Hero extends Card {
    public Hero(String info) {
        super(info.split(Constants.CARD_INFO_SPLITTER));
    }
}
