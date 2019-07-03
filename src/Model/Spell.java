package Model;

public class Spell extends Card {
    public Spell(String info) {
        super(info.split(Constants.CARD_INFO_SPLITTER));
    }

    public Spell(Spell spell) {
        super(spell);
    }

}
