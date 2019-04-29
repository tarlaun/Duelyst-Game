package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Card {
    private int id;
    private String name;
    private int maxPossibleMoving;
    private int price;
    private int healthPoint;
    private int minRange;
    private int maxRange;
    private int assaultPower;
    private int originalAssaultPower ;
    private AssaultType assaultType;
    private ActivationType activationType;
    private ArrayList<Buff> buffs = new ArrayList<>();
    private ArrayList<Buff> castedBuffs = new ArrayList<>();
    private int manaPoint;
    private Coordinate coordinate;
    private boolean ableToAttack = false;
    private boolean ableToMove = false;
    private int cardHolder;
    private int isHoly = 0;

    public int getCardHolder() {
        return cardHolder;
    }

    public void setOriginalAssaultPower(int originalAssaultPower) {
        this.originalAssaultPower = originalAssaultPower;
    }

    public void setAssaultType(AssaultType assaultType) {
        this.assaultType = assaultType;
    }

    public void setActivationType(ActivationType activationType) {
        this.activationType = activationType;
    }

    public void setBuffs(ArrayList<Buff> buffs) {
        this.buffs = buffs;
    }

    public void setCastedBuffs(ArrayList<Buff> castedBuffs) {
        this.castedBuffs = castedBuffs;
    }


    public void setManaPoint(int manaPoint) {
        this.manaPoint = manaPoint;
    }

    public void setCardHolder(int cardHolder) {
        this.cardHolder = cardHolder;
    }

    public int getIsHoly() {
        return isHoly;
    }

    public void setIsHoly(int isHoly) {
        this.isHoly = isHoly;
    }

    public void addToBuffs(Buff buff){
        this.castedBuffs.add(buff);
    }
    public void removeFromBuffs(Buff buff){
        this.castedBuffs.remove(buff);
    }
    public Card(int id, String[] info) {
        this.id = id;
        this.name = info[MainInfoOrder.NAME.ordinal()];
        this.price = Integer.parseInt(info[MainInfoOrder.PRICE.ordinal()]);
        this.assaultPower = Integer.parseInt(info[MainInfoOrder.AP.ordinal()]);
        this.originalAssaultPower = this.assaultPower;
        this.maxPossibleMoving = Integer.parseInt(info[MainInfoOrder.MAX_MOVE.ordinal()]);
        this.healthPoint = Integer.parseInt(info[MainInfoOrder.HP.ordinal()]);
        this.minRange = Integer.parseInt(info[MainInfoOrder.MIN_RANGE.ordinal()]);
        this.maxRange = Integer.parseInt(info[MainInfoOrder.MAX_RANGE.ordinal()]);
        this.manaPoint = Integer.parseInt(info[MainInfoOrder.MANA.ordinal()]);
        this.assaultType = AssaultType.valueOf(info[MainInfoOrder.ASSAULT_TYPE.ordinal()]);
        for (int i = MainInfoOrder.BUFF.ordinal(); i < info.length; i++) {
            this.buffs.add(new Buff(info[i].split(Constants.BUFF_INFO_SPLITTER)));
        }
    }


    public int getOriginalAssaultPower() {
        return originalAssaultPower;
    }

    public Card(Card card) {
        this.id = card.id;
        this.name = card.name;
        this.price = card.price;
        this.assaultPower = card.assaultPower;
        this.maxPossibleMoving = card.maxPossibleMoving;
        this.healthPoint = card.healthPoint;
        this.minRange = card.minRange;
        this.maxRange = card.maxRange;
        this.manaPoint = card.manaPoint;
        this.assaultType = card.assaultType;
        this.buffs = card.buffs;
    }

    public String getName() {
        return name;
    }

    public AssaultType getAssaultType() {
        return assaultType;
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

    public ActivationType getActivationType() {
        return activationType;
    }

    public ArrayList<Buff> getBuffs() {
        return buffs;
    }

    public ArrayList<Buff> getCastedBuffs() {
        return castedBuffs;
    }

    public int getManaPoint() {
        return manaPoint;
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

    public static String[] cardInfoDecryption(String info) {

    }
}
