package Model;

public class Hero extends Card {
    public Hero(int id, String info) {
        super(id, info.split(Constants.CARD_INFO_SPLITTER));
    }
}
