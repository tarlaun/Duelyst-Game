package IO;

import Model.Hero;
import Model.Item;
import Model.Minion;
import Model.Spell;
import com.google.gson.Gson;

import java.io.*;

public class SetSrcs {
    public static void main(String[] args) throws Exception {
        setSrc("Cards/Heroes");
        setSrc("Cards/Spells");
        setSrc("Cards/Minions");
    }

    private static void setSrc(String type) throws Exception {
        File dir = new File("./src/Objects/" + type);
        File gifDir = new File("gifs/" + type.split("/")[1]);
        BufferedReader list = new BufferedReader(new FileReader("./resources/" + gifDir + "/list.txt"));
        if (dir.exists()) {
            if (dir.isDirectory()) {
                for (File file : dir.listFiles()) {
                    if (file.isFile()) {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        String json = null;
                        String name = null;
                        switch (type) {
                            case "Cards/Heroes":
                                Hero hero = new Gson().fromJson(reader, Hero.class);
                                hero.setType("Hero");
                                hero.setAttackSrc(gifDir + "/" + list.readLine());
                                hero.setDeathSrc(gifDir + "/" + list.readLine());
                                hero.setIdleSrc(gifDir + "/" + list.readLine());
                                hero.setRunSrc(gifDir + "/" + list.readLine());
                                name = hero.getName();
                                json = new Gson().toJson(hero);
                                break;
                            case "Cards/Minions":
                                Minion minion = new Gson().fromJson(reader, Minion.class);
                                minion.setType("Minion");
                                minion.setAttackSrc(gifDir + "/" + list.readLine());
                                minion.setDeathSrc(gifDir + "/" + list.readLine());
                                minion.setIdleSrc(gifDir + "/" + list.readLine());
                                minion.setRunSrc(gifDir + "/" + list.readLine());
                                name = minion.getName();
                                json = new Gson().toJson(minion);
                                break;
                            case "Cards/Spells":
                                Spell spell = new Gson().fromJson(reader, Spell.class);
                                spell.setType("Spell");
                                spell.setIdleSrc(gifDir + "/" + list.readLine());
                                spell.setAttackSrc(gifDir + "/" + list.readLine());
                                spell.setDeathSrc(null);
                                spell.setRunSrc(null);
                                name = spell.getName();
                                json = new Gson().toJson(spell);
                                break;
                            case "Items":
                                break;
                        }
                        try {
                            FileWriter writer = new FileWriter("./src/Objects/" + type + "/" + name + ".json");
                            writer.write(json);
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }


}
