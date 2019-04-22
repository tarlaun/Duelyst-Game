package Model;

import java.util.ArrayList;

public class Item {
    private int id;
    private String name;
    private int price;
    private Coordinate coordinate = new Coordinate(-1, -1);

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
