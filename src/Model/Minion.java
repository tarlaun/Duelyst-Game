package Model;

public class Minion extends Card {
    public Minion(int id, String info) {
        super(id, info.split(Constants.CARD_INFO_SPLITTER));
    }

}


