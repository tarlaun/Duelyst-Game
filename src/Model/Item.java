package Model;

import java.util.ArrayList;

public class Item {
    private int id;
    private String name;
    Coordinate coordinate= new Coordinate();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public static Item getItemByID(int id, ArrayList<Item> items) {
        for (Item item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}
