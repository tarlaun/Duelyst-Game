package Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Game {
    private ArrayList<Account> accounts = new ArrayList<>();
    private static final Game SINGLETON_CLASS = new Game();

    private Game() {

    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public static Game getInstance() {
        return SINGLETON_CLASS;
    }

    public boolean logout(Account account) {

    }

    public void save(Account account) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(account);
        try {
            FileWriter writer = new FileWriter(account.getName() + ".json");
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sortAccounts(){
        Comparator<Account> compareById = Comparator.comparingInt(Account::getWins);
        accounts.sort(compareById.reversed());

    }
}
