package Model;

import View.Message;

import java.util.ArrayList;

public class Collection {

    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Deck> decks = new ArrayList<>();
    private Deck mainDeck;

    public ArrayList<Card> getCards() {
        return cards;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }

    public boolean createDeck(String deckName) {
        if (deckExistance(deckName) == -1) {
            Deck deck = new Deck();
            deck.setName(deckName);
            decks.add(deck);
            return true;
        }
        return false;
    }


    public boolean deleteDeck(String deckName) {
        if (deckExistance(deckName) != -1) {
            decks.remove(deckExistance(deckName));
            return true;
        }
        return false;
    }

    public Message add(String deckName, int objectID) {
        if (deckExistance(deckName) != -1) {
            Deck deck = decks.get(deckExistance(deckName));
            Card card = Card.getCardByID(objectID, this.cards.toArray(Card[]::new));
            if (card != null) {
                if (Card.getCardByID(objectID, deck.getCards().toArray(Card[]::new)) == null) {
                    if (card instanceof Hero && deck.getHero() == null) {
                        deck.setHero((Hero) Card.getCardByID(objectID));
                        return Message.OBJECT_ADDED;
                    }else if (deck.getHero() != null) {
                        return Message.MAXIMUM_HERO_COUNT;
                    }
                    if ((card instanceof Spell || Card.getCardByID(objectID) instanceof Minion)
                            && deck.getCards().size() < 20) {
                        deck.getCards().add(Card.getCardByID(objectID));
                        return Message.OBJECT_ADDED;
                    }else if(deck.getCards().size() == 20){
                        return Message.FULL_DECK;
                    }
                }
                return Message.EXISTS_IN_DECK;
            }
            Item item = Item.getItemByID(objectID, this.items.toArray(Item[]::new));
            if (item != null) {
                if (Item.getItemByID(objectID) != null && deck.getItem() == null) {
                    deck.setItem(Item.getItemByID(objectID));
                    return Message.OBJECT_ADDED;
                }else if(deck.getItem() != null){
                    return Message.MAXIMUM_ITEM_COUNT;
                }
            }
            return Message.OBJECT_NOT_FOUND;
        }
        return Message.INVALID_DECK;
    }

    public int deckExistance(String deckName) {
        for (int i = 0; i < decks.size(); i++) {
            if (deckName.equals(decks.get(i).getName())) {
                return i;
            }
        }
        return -1;
    }

    public Message remove(String deckName, int objectID) {

    }

    public boolean validate(String deckName) {

    }

    public boolean selectDeck(String deckName) {

    }

}
