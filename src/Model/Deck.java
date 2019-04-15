package Model;

import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards= new ArrayList<>();
    private Hero hero = new Hero();
    private Item item= new Item();

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
