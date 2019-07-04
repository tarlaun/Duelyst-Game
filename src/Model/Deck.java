package Model;

import java.util.ArrayList;

public class Deck {
    private String name;
    private ArrayList<Card> cards = new ArrayList<>();
    private Card hero;
    private Item item;

    public Deck(String name) {
        this.name = name;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public Card getHero() {
        return hero;
    }

    public void setHero(Card hero) {
        this.hero = hero;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Card findCardInDeck(int id) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getId() == id)
                return cards.get(i);
        }
        return null;
    }

    public Item findItemInDeck(int id) {
        if (item.getId() == id)
            return item;
        return null;
    }
}
