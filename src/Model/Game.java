package Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import jdk.nashorn.internal.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Game {
    private ArrayList<Account> accounts = new ArrayList<>();
    private ArrayList<Account> loggedInAccounts = new ArrayList<>();
    private static final Game SINGLETON_CLASS = new Game();
    private GameType gameType;
    private BattleMode mode;
    private Menu menu = Menu.getInstance();

    private Game() {

    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public void setLoggedInAccounts(ArrayList<Account> loggedInAccounts) {
        this.loggedInAccounts = loggedInAccounts;
    }

    public static Game getSingletonClass() {
        return SINGLETON_CLASS;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public BattleMode getMode() {
        return mode;
    }

    public void setMode(BattleMode mode) {
        this.mode = mode;
    }

    private ArrayList<Account> getLoggedInAccounts() {
        for (Account account :
                accounts) {
            if (account.isLoggedIn()) {
                loggedInAccounts.add(account);
            }
        }
        return loggedInAccounts;
    }

    public void createBattle() {
        Battle battle = new Battle(loggedInAccounts.toArray(new Account[loggedInAccounts.size()]), gameType, mode);
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public static Game getInstance() {
        return SINGLETON_CLASS;
    }

    public boolean logout(Account account) {
        account.setLoggedIn(false);
        menu.setStat(MenuStat.MAIN);
        return true;
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
        menu.setStat(MenuStat.MAIN);
    }

    public void sortAccounts() {
        Comparator<Account> compareById = Comparator.comparingInt(Account::getWins);
        accounts.sort(compareById.reversed());

    }

    public void initializeAccounts() throws Exception {
        File dir = new File("./");
        if (dir.exists()) {
            if (dir.isDirectory()) {
                for (File file : dir.listFiles()) {
                    if (file.isFile()) {
                        Gson gson = new Gson();
                        System.out.println(file.getName());
                        JsonObject jsonObject = (JsonObject) readJson(file.getName());
                        Account account = gson.fromJson(jsonObject, Account.class);
                        accounts.add(account);
                    }
                }
            }
        }
    }

    private Object readJson(String filename) throws Exception {
        FileReader reader = new FileReader(filename);
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parse(reader);
    }
}
