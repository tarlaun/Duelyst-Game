package Model;

public class Minion extends Card {

    public Minion(String info) {
        super(info.split(Constants.CARD_INFO_SPLITTER));
    }


}


