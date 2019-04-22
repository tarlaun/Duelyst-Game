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

/*
    public boolean createDeck(String deckName){

    }

    public boolean deleteDeck( String deckName){

    }

    public Message add(String deckName , int objectID){

    }

    public Message remove(String deckName , int objectID){

    }

    public boolean validate(String deckName){

    }

    public boolean selectDeck(String deckName){

    }
*/

}
