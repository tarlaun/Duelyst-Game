package Model;

import java.util.ArrayList;

public class Item {
    private int id;
    private String name;
    private int price;
    private Coordinate coordinate = new Coordinate(-1, -1);
    private ArrayList<ItemBuff> buffs = new ArrayList<>();

    public Item(int id, String[] info) {
        this.id = id;
        this.name = info[MainInfoOrder.NAME.ordinal()];
        this.price = Integer.parseInt(info[MainInfoOrder.PRICE.ordinal()]);
        for (int i = MainInfoOrder.BUFF.ordinal(); i < info.length; i++) {
            this.buffs.add(new ItemBuff(info[i].split(Constants.BUFF_INFO_SPLITTER)));
        }
    }

    public Item(Item item) {
        this.id = item.id;
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

    public Coordinate getCoordinate() {
        return coordinate;
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
}
