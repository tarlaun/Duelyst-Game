package Model;

public class Hero extends Card {
    private int cooldown ;
    public Hero(int id, String[] info, Buff... buffs) {
        super(id, info);
    }
}
