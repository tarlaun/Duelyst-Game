package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Card {
    private String name;
    private int id;
    private String nationality;
    private int price;
    private Coordinate coordinate = new Coordinate();
    private int maxPossibleMoving;
    private int minRange;
    private int maxRange;
    private boolean ableToAttack = false;
    private boolean ableToMove=false;
    private int healthPoint;
    private int assaultPower;

    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setMinRange(int minRange) {
        this.minRange = minRange;
    }

    public void setMaxRange(int maxRange) {
        this.maxRange = maxRange;
    }

    public boolean isAbleToMove() {
        return ableToMove;
    }

    public void setAbleToMove(boolean ableToMove) {
        this.ableToMove = ableToMove;
    }

    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }

    public void setAssaultPower(int assaultPower) {
        this.assaultPower = assaultPower;
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

    public static Card getCardByID(int id, Card... cards) {
        for (Card card : cards) {
            if (card.getId() == id) {
                return card;
            }
        }
        return null;
    }

    public static Card getCardByName(String name, Card... cards) {
        for (Card card : cards) {
            if (card.getName().equals(name))
                return card;
        }
        return null;
    }

    public void setAbleToAttack(boolean ableToAttack) {
        this.ableToAttack = ableToAttack;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public int getAssaultPower() {
        return assaultPower;
    }

    public int getMaxPossibleMoving() {
        return maxPossibleMoving;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void setMaxPossibleMoving(int maxPossibleMoving) {
        this.maxPossibleMoving = maxPossibleMoving;
    }

    public int getMinRange() {
        return minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public boolean isAbleToAttack() {
        return ableToAttack;
    }

    public void decreaseHealth(int decrement) {
        this.healthPoint -= decrement;
    }

    public static Card[] removeFromArray(Card[] cards, Card card) {
        List<Card> cardsArrayList = new ArrayList<>(Arrays.asList(cards));
        cardsArrayList.remove(card);
        return cardsArrayList.toArray(Card[]::new);
    }

    public static Card[] addToArray(Card[] cards, Card card) {
        List<Card> cardsArrayList = new ArrayList<>(Arrays.asList(cards));
        cardsArrayList.add(card);
        return cardsArrayList.toArray(Card[]::new);
    }
}
