package Model;

import java.util.ArrayList;

public class Item {
    private int id;
    private String name;
    private int price;
    private ArrayList<ItemBuff> buffs = new ArrayList<>();

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
        this.name = item.name;
        this.price = item.price;
        this.buffs = item.buffs;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Item getItemByID(int id, Item... items) {
        for (Item item : items) {
            if (item.getId() == id) {
                return item;
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

    public ArrayList<ItemBuff> getBuffs() {
        return buffs;
    }
}
