package Model;

import View.Message;

import java.util.ArrayList;


public class Collection {

    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Deck> decks = new ArrayList<>();
    private Deck mainDeck;
    private ArrayList<Deck> levelDecks = new ArrayList<>();

    public Collection() {

    }


    public Collection(ArrayList<Card> cards, ArrayList<Item> items) {
        this.cards = cards;
        this.items = items;
    }

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
            Card card = Card.getCardByID(objectID, this.cards.toArray(new Card[cards.size()]));
            if (card != null) {
                if (deck.getCards().indexOf(card) == -1) {
                    System.out.println(card.getClass().getName());
                    if (card.getType().equals("Hero") && deck.getHero() == null) {
                        deck.setHero((Hero) Card.getCardByID(objectID, this.getCards().toArray(new Card[this.getCards().size()])));
                        return Message.OBJECT_ADDED;
                    } else if (card.getType().equals("Hero") && deck.getHero() != null) {
                        return Message.MAXIMUM_HERO_COUNT;
                    }
                    if ((card.getType().equals("Minion") || card.getType().equals("Spell"))
                            && deck.getCards().size() < 20) {
                        System.out.println("Buban");
                        deck.getCards().add(Card.getCardByID(objectID, this.getCards().toArray(new Card[this.getCards().size()])));
                        return Message.OBJECT_ADDED;
                    } else if (deck.getCards().size() == 20) {
                        return Message.FULL_DECK;
                    }
                }
                return Message.EXISTS_IN_DECK;
            }
            Item item = Item.getItemByID(objectID, this.items.toArray(new Item[items.size()]));
            if (item != null) {
                if (deck.getItem() == null || deck.getItem().getId() != item.getId()) {
                    deck.setItem(Item.getItemByID(objectID, this.getItems().toArray(new Item[this.getItems().size()])));
                    return Message.OBJECT_ADDED;
                } else if (deck.getItem() != null) {
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
        if (deckExistance(deckName) != -1) {
            Deck deck = decks.get(deckExistance(deckName));
            Card card = Card.getCardByID(objectID, this.cards.toArray(new Card[cards.size()]));
            Item item = Item.getItemByID(objectID, this.items.toArray(new Item[items.size()]));
            if (card != null) {
                if (card.getType().equals("Hero")) {
                    deck.setHero(null);
                    return Message.UNAVAILABLE;
                }
                deck.getCards().remove(card);
                return Message.SUCCESSFUL_REMOVE;
            }
            if (item != null) {
                deck.setItem(null);
                return Message.SUCCESSFUL_REMOVE;
            }
            return Message.UNAVAILABLE;
        }
        return Message.INVALID_DECK;
    }

    public boolean validate(String deckName) {
        if (deckExistance(deckName) != -1) {
            Deck deck = decks.get(deckExistance(deckName));
            return deck.getCards().size() == 20 && deck.getItem() != null && deck.getHero() != null;
        }
        return false;
    }

    public boolean selectDeck(String deckName) {
        try {
            mainDeck = decks.get(deckExistance(deckName));
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public void deleteFromAllDecks(int id) {
        for (Deck deck : decks) {
            deck.getCards().remove(Card.getCardByID(id, deck.getCards().toArray(new Card[deck.getCards().size()])));
            try {
                if (deck.getItem().getId() == id)
                    deck.setItem(null);

            } catch (NullPointerException e) {

            }
        }
    }
}
