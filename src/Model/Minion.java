package Model;

import java.util.ArrayList;
import java.util.Arrays;

public class Minion extends Card {
    public Minion(String name, int id, int maxPossibleMove, int price, int health, int minRange, int maxRange,
                  int mana, ActivationType activationType, Buff... buffs) {
        super(name, id, maxPossibleMove, price, health, minRange, maxRange, mana, activationType, buffs);
    }

}


