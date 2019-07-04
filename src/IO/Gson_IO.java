package IO;

import Model.Hero;
import Model.Item;
import Model.Minion;
import Model.Spell;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Scanner;

public class Gson_IO {
    private static int index = 1;
    private static final String path = "src/Objects/";

    public static void main(String[] args) throws IOException {
        gsonGenerator(new File("src/Spells"), "Spell");
        gsonGenerator(new File("src/Items"), "Item");
        gsonGenerator(new File("src/Heros"), "Hero");
        gsonGenerator(new File("src/Minions"), "Minion");
    }

    private static void gsonGenerator(File file, String name) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Scanner scanner = new Scanner(file);
        String info;
        while (scanner.hasNextLine()) {
            info = scanner.nextLine();
            String json;

            switch (name) {
                case "Spell":
                    Spell spell = new Spell(info);
                    json = gson.toJson(spell);
                    writer(path + "Cards/Spells/" + spell.getName() + ".json", json);
                    break;
                case "Item":
                    Item item = new Item(info);
                    json = gson.toJson(item);
                    writer(path + "Items/" + item.getName() + ".json", json);
                    break;
                case "Minion":
                    Minion minion = new Minion(info);
                    json = gson.toJson(minion);
                    writer(path + "Cards/Minions/" + minion.getName() + ".json", json);

                    break;
                case "Hero":
                    Hero hero = new Hero(info);
                    json = gson.toJson(hero);
                    writer(hero.getName() + ".json", json);
                    break;
            }
            index++;
        }
    }

    private static void writer(String fileName, String json) throws IOException {
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(json);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
