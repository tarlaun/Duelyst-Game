package Model;

import View.CardView;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Item {
    private int id;
    private String name;
    private int price;
    private String idleSrc;
    private String attackSrc;
    private ArrayList<ItemBuff> buffs = new ArrayList<>();
    private transient CardView cardView = new CardView(this);
    private int countInShop;

    public Item() {

    }

    public Item(String information) {
        String[] info = information.split(Constants.CARD_INFO_SPLITTER);
        this.name = info[ItemInfoOrder.NAME.ordinal()];
        this.price = Integer.parseInt(info[ItemInfoOrder.PRICE.ordinal()]);
        for (int i = ItemInfoOrder.BUFF.ordinal(); i < info.length; i++) {
            this.buffs.add(new ItemBuff(info[i].split(Constants.BUFF_INFO_SPLITTER)));
        }
    }

    public ArrayList<ItemBuff> getBuffs() {
        return buffs;
    }

    public Item(Item item) {
        this.idleSrc = item.idleSrc;
        this.attackSrc = item.attackSrc;
        this.id = item.id;
        this.name = item.name;
        this.price = item.price;
        this.buffs = item.buffs;
        cardView = new CardView(item);
        this.countInShop = item.getCountInShop();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Item getItemByID(int id, Item... items) {
        if (items == null)
            return null;
        for (Item item : items) {
            try {
                if (item.getId() == id) {
                    return item;
                }
            } catch (NullPointerException e) {
            }
        }
        return null;
    }

    public int getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static ArrayList<Item> getAllItemsId(String name, Item... items) {
        ArrayList<Item> output = new ArrayList<>();
        for (Item item : items) {
            if (item.getName().equals(name))
                output.add(item);
        }
        return output;
    }

    public static Item getItemByName(String name, Item... items) {
        for (Item item : items) {
            if (item.getName().equals(name))
                return item;
        }
        return null;
    }

    public CardView getCardView() {
        return this.cardView;
    }

    public String getIdleSrc() {
        return idleSrc;
    }

    public void setIdleSrc(String idleSrc) {
        this.idleSrc = idleSrc;
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

    public static ArrayList<Item> matchSearch(String name, ArrayList<Item> items) {
        ArrayList<Item> output = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().matches(name.toUpperCase() + "\\w*"))
                output.add(items.get(i));
        }
        return output;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static Item fromJson(String json) {
        return new Gson().fromJson(json, Item.class);
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
}
