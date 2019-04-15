package Model;

public class Hero extends Card {
    public Hero(String name, int id, int maxPossibleMove, int price, int health, int minRange, int maxRange,
                int mana, ActivationType activationType, BuffType... buffTypes) {
        super(name, id, maxPossibleMove, price, health, minRange, maxRange, mana, activationType, buffTypes);
    }

}
