package Model;

import Controller.Controller;
import View.Message;

import java.util.ArrayList;

public class Account {
    private String name;
    private int id;
    private String password;
    private int budget;
    private ArrayList<Match> matchHistory = new ArrayList<>();
    private Collection collection;
    private static Game game = Game.getInstance();
    private boolean isLoggedIn = true;
    private int wins = 0;
    private int mana;
    private Controller controller = Controller.getInstance();

    public static Game getGame() {
        return game;
    }

    public Account() {

    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public void setMatchHistory(ArrayList<Match> matchHistory) {
        this.matchHistory = matchHistory;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public static void setGame(Game game) {
        Account.game = game;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public int getWins() {
        return wins;
    }

    public Account(String name, String password) {
        this.name = name;
        this.password = encrypted(password);
    }

    public boolean createAccount() {
        if (accountIndex(this.name) != -1)
            return false;
        this.id = game.getAccounts().size() + 1;
        game.getAccounts().add(this);
        return true;
    }

    public static Message login(String username, String password) {
        if (accountIndex(username) != -1)
            return Message.INVALID_ACCOUNT;
        if (!game.getAccounts().get(accountIndex(username)).password.equals(encrypted(password)))
            return Message.INVALID_PASSWORD;
        return Message.ACCOUNT_CREATION;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public int getBudget() {
        return budget;
    }

    public ArrayList<Match> getMatchHistory() {
        return matchHistory;
    }

    public Collection getCollection() {
        return collection;
    }

    public static int accountIndex(String name) {
        for (Account account : game.getAccounts()) {
            if (account.name.equals(name))
                return account.id - 1;
        }
        return -1;
    }

    public void modifyAccountBudget(int money) {
        this.budget += money;
    }

    private static String encrypted(String password) {
        return password;
    }
}
