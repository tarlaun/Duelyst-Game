package Model;

public class Spell extends Card {
    public Spell(int id, String info) {
        super(id, info.split(Constants.CARD_INFO_SPLITTER));
    }
}
