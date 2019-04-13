package Model;

import java.util.ArrayList;

public class Card {
    private String name;
    private int id;
    private int price;
    private Coordinate coordinate = new Coordinate();

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public static Card getCardByID(int id, ArrayList<Card> cards) {
        for (Card card : cards) {
            if (card.getId() == id) {
                return card;
            }
        }
        return null;
    }
}
