package IO;

import Model.Spell;
import com.google.gson.Gson;

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
        int index = 0;
        try {
            while (scanner.hasNextLine()) {
                info = scanner.nextLine();
                Spell spell = new Spell(index, info);
                System.out.println("Spell id:" + spell.getId());
                gson.toJson(spell, new FileWriter(spell.getName()));
                index++;
            }
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }
}
