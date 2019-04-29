package Model;

import java.util.ArrayList;

public class Item {
    private int id;
    private String name;
    private int price;
    private Coordinate coordinate = new Coordinate();

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


}
