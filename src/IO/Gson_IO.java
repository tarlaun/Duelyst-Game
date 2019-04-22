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
    public static void main(String[] args) throws IOException {
        gsonGenerator(new File("src/Spells"), "Spell");
        gsonGenerator(new File("src/Items"), "Item");
    }

    private static void gsonGenerator(File file, String name) throws IOException {
        Gson gson = new Gson();
        Scanner scanner = new Scanner(file);
        String info;
        int index = 1;
        while (scanner.hasNextLine()) {
            info = scanner.nextLine();
            String json;
            System.out.println("\n" + name);
            switch (name) {
                case "Spell":
                    Spell spell = new Spell(index, info);
                    gson.toJson(spell, System.out);
                    json = gson.toJson(spell);
                    writer(spell.getName() + ".json", json);
                    break;
                case "Item":
                    Item item = new Item(index, info);
                    gson.toJson(item, System.out);
                    json = gson.toJson(item);
                    writer(item.getName() + ".json", json);
                    break;
                case "Minion":
                    Minion minion = new Minion(index, info);
                    gson.toJson(minion, System.out);
                    json = gson.toJson(minion);
                    writer(minion.getName() + ".json", json);
                    break;
                case "Hero":
                    Hero hero = new Hero(index, info);
                    gson.toJson(hero, System.out);
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
