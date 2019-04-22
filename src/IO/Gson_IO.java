package IO;

import Model.Spell;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Gson_IO {
    public static void main(String[] args) throws IOException {
        Gson gson = new Gson();
        File file = new File("src/Spells");
        Scanner scanner = new Scanner(file);
        String info;
        int index = 1;
        try {
            while (scanner.hasNextLine()) {
                info = scanner.nextLine();
                Spell spell = new Spell(index, info);
/*
                String buffInfo;
                String coordinateInfo;
                String cardInfo = "{\"id\":" + spell.getId() + ",\"name\":" + spell.getName() + ",\"maxPossibleMoving\":" +
                        spell.getPrice() + ",\"price\":" + spell.getPrice() + ",\"healthPoint\":" + spell.getHealthPoint() +
                        ",\"minRange\":" + spell.getMinRange() + ",\"maxRange\":" + spell.getMaxRange() + ",\"assaultPower\":" +
                        spell.getAssaultPower() + ",\"buffs\":[{\"type\":,\"power\":,\"targetType\":,\"attribute\":," +
                        "\"dispelType\":,\"activationType\":,\"turnCount\":,\"side\":,\"effectArea\":[{\"x\":,\"y\":,}]," +
                        "\"castedBuffs\":[],\"manaPoint\":,\"ableToAttack\":,\"ableToMove\":}";
*/
                gson.toJson(spell,System.out);
                gson.toJson(spell, new FileWriter(spell.getName() + ".json"));
                index++;
            }
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }
}
