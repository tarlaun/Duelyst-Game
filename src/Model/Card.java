package Model;

import View.CardView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Card {
    private String idleSrc;
    private String deathSrc;
    private String runSrc;
    private String attackSrc;
    private int id;
    private String name;
    private String type;
    private int maxPossibleMoving;
    private int price;
    private int healthPoint;
    private int minRange;
    private int maxRange;
    private int assaultPower;
    private int originalAssaultPower;
    private AssaultType assaultType;
    private ActivationType activationType;
    private ArrayList<Buff> buffs = new ArrayList<>();
    private ArrayList<Buff> castedBuffs = new ArrayList<>();
    private ArrayList<ItemBuff> castedItems = new ArrayList<>();
    private int manaPoint;
    private Coordinate coordinate;
    private boolean ableToAttack = false;
    private boolean ableToMove = false;
    private boolean ableToResist = false;
    private boolean ableToCounter = false;
    private int cardHolder;
    private int isHoly = 0;
    private int recievedHit = 0;
    private RangeType rangeType;
    private transient CardView cardView;
    private int[][] attackCount = new int[40][2];
    private int countInShop;

    public ArrayList<ItemBuff> getCastedItems() {
        return castedItems;
    }

    public int getCardHolder() {
        return cardHolder;
    }

    public CardView getCardView() {
        return cardView;
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
        cardView = new CardView(this);
        if (this instanceof Minion) {
            if (info.length > 9) {
                this.buffs.add(new Buff(info[MainInfoOrder.BUFF.ordinal()]));
            }
        } else {
            for (int i = MainInfoOrder.BUFF.ordinal(); i < info.length; i++) {
                System.out.println(this.name + "    " + info[i - 1] + "   " + info[i]);
                this.buffs.add(new Buff(info[i].split(Constants.BUFF_INFO_SPLITTER)));
            }
        }
        this.countInShop = Constants.SHOP_INITIAL_COUNT;
    }

    public int getRecievedHit() {
        return recievedHit;
    }

    public void setRecievedHit(int recievedHit) {
        this.recievedHit = recievedHit;
    }

    public boolean isAbleToCounter() {
        return ableToCounter;
    }

    public int getOriginalAssaultPower() {
        return originalAssaultPower;
    }

    public Card(Card card) {
        this.idleSrc = card.idleSrc;
        this.attackSrc = card.attackSrc;
        this.runSrc = card.runSrc;
        this.deathSrc = card.deathSrc;
        this.id = card.id;
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
        cardView = new CardView(this);
        this.countInShop = card.getCountInShop();
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

    public static Card getCardByID(int id, ArrayList<Card> cards) {
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

    public static Card getCardByName(String name,ArrayList<Card> cards) {
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

    public String getIdleSrc() {
        return idleSrc;
    }

    public void setIdleSrc(String idleSrc) {
        this.idleSrc = idleSrc;
    }

    public String getDeathSrc() {
        return deathSrc;
    }

    public void setDeathSrc(String deathSrc) {
        this.deathSrc = deathSrc;
    }

    public String getRunSrc() {
        return runSrc;
    }

    public void setRunSrc(String runSrc) {
        this.runSrc = runSrc;
    }

    public String getAttackSrc() {
        return attackSrc;
    }

    public void setAttackSrc(String attackSrc) {
        this.attackSrc = attackSrc;
    }

    public void setCardView() {
        cardView = new CardView(this);
    }

    public Card(String name, String type,
                String price, String healthPoint, String assaultPower, String activation, String rangeType, String range) {
        this.name = name;
        this.type = type;
        this.price = Integer.parseInt(price);
        this.healthPoint = Integer.parseInt(healthPoint);
        this.assaultPower = Integer.parseInt(assaultPower);
        this.rangeType = RangeType.valueOf(rangeType.toUpperCase());
        this.maxRange = Integer.parseInt(range);
        this.countInShop = Constants.SHOP_INITIAL_COUNT;
    }

    public int getCountInShop() {
        return countInShop;
    }

    public void setCountInShop(int countInShop) {
        this.countInShop = countInShop;
    }

    public void incrementCount() {
        this.countInShop++;
    }

    public void decrementCount() {
        this.countInShop--;
    }

    public static ArrayList<Card> matchSearch(String name, ArrayList<Card> cards) {
        ArrayList<Card> output = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getName().matches(name.toUpperCase() + "\\w*"))
                output.add(cards.get(i));
        }
        return output;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static Card fromJson(String json) {
        return new Gson().fromJson(json, Card.class);
    }
}