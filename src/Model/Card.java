package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Card {
    private int id;
    private String name;
    private transient String type;
    private int maxPossibleMoving;
    private int price;
    private int healthPoint;
    private int minRange;
    private int maxRange;
    private int assaultPower;
    private transient int originalAssaultPower;
    private AssaultType assaultType;
    private transient ActivationType activationType;
    private ArrayList<Buff> buffs = new ArrayList<>();
    private transient ArrayList<Buff> castedBuffs = new ArrayList<>();
    private transient ArrayList<ItemBuff> castedItems = new ArrayList<>();
    private int manaPoint;
    private transient Coordinate coordinate;
    private transient boolean ableToAttack = false;
    private transient boolean ableToMove = false;
    private transient boolean ableToResist = false;
    private transient boolean ableToCounter = false;
    private transient int cardHolder;
    private transient int isHoly = 0;
    private transient RangeType rangeType;
    transient int[][] attackCount = new int[40][2];

    public ArrayList<ItemBuff> getCastedItems() {
        return castedItems;
    }

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

    public void addToBuffs(Buff buff) {
        this.castedBuffs.add(buff);
    }

    public void removeFromBuffs(Buff buff) {
        this.castedBuffs.remove(buff);
    }

    public Card(String[] info) {
        this.name = info[MainInfoOrder.NAME.ordinal()];
        this.price = Integer.parseInt(info[MainInfoOrder.PRICE.ordinal()]);
        this.assaultPower = Integer.parseInt(info[MainInfoOrder.AP.ordinal()]);
        this.setOriginalAssaultPower(assaultPower);
        this.maxPossibleMoving = Integer.parseInt(info[MainInfoOrder.MAX_MOVE.ordinal()]);
        this.healthPoint = Integer.parseInt(info[MainInfoOrder.HP.ordinal()]);
        this.minRange = Integer.parseInt(info[MainInfoOrder.MIN_RANGE.ordinal()]);
        this.maxRange = Integer.parseInt(info[MainInfoOrder.MAX_RANGE.ordinal()]);
        this.manaPoint = Integer.parseInt(info[MainInfoOrder.MANA.ordinal()]);
        this.assaultType = AssaultType.valueOf(info[MainInfoOrder.ASSAULT_TYPE.ordinal()]);
        if (this instanceof Minion) {
            if (info.length > 9) {
                this.buffs.add(new Buff(info[MainInfoOrder.BUFF.ordinal()]));
            }
        } else {
            for (int i = MainInfoOrder.BUFF.ordinal(); i < info.length; i++) {
                System.out.println(this.name + "    " + info[i-1] + "   " + info[i]);
                this.buffs.add(new Buff(info[i].split(Constants.BUFF_INFO_SPLITTER)));
            }
        }
    }

    public boolean isAbleToCounter() {
        return ableToCounter;
    }

    public int getOriginalAssaultPower() {
        return originalAssaultPower;
    }

    public Card(Card card) {
        this.name = card.name;
        this.price = card.price;
        this.type = card.type;
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
        if (cards == null)
            return null;
        for (Card card : cards) {
            try {
                if (card.getId() == id) {
                    return card;
                }
            } catch (NullPointerException e) {
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

    public void modifyHealth(int value) {
        this.healthPoint += value;
    }

    public void modifyHit(int value) {
        this.assaultPower += value;
    }

    public static Card[] removeFromArray(Card[] cards, Card card) {
        List<Card> cardsArrayList = new ArrayList<>(Arrays.asList(cards));
        cardsArrayList.remove(card);
        return cardsArrayList.toArray(new Card[cardsArrayList.size() + 1]);
    }

    public static Card[] addToArray(Card[] cards, Card card) {
        List<Card> cardsArrayList = new ArrayList<>(Arrays.asList(cards));
        cardsArrayList.add(card);
        return cardsArrayList.toArray(new Card[cardsArrayList.size()]);
    }

    public boolean isAbleToResist() {
        return ableToResist;
    }

    public void setAbleToResist(boolean ableToResist) {
        this.ableToResist = ableToResist;
    }

    public void setAbleToCounter(boolean ableToCounter) {
        this.ableToCounter = ableToCounter;
    }

    public static ArrayList<Card> getAllCardsId(String name, Card... cards) {
        ArrayList<Card> output = new ArrayList<>();
        for (Card card : cards) {
            if (card.getName().equals(name))
                output.add(card);
        }
        return output;
    }

    public RangeType getRangeType() {
        return rangeType;
    }

    public boolean isClass(String name) {
        return this.getType().equals(name);
    }

    public void setRangeType(RangeType rangeType) {
        this.rangeType = rangeType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAttackCount(int id) {
        for (int i = 0; i < 40; i++) {
            if (this.attackCount[i][0] == id) {
                return attackCount[i][1];
            }
        }
        return 0;
    }

    public void setAttackCount(int i, int j, int k) {
        this.attackCount[i][j] = k;
    }
}