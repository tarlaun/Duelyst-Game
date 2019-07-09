package Model;

import View.Message;
import com.google.gson.Gson;
//import com.fasterxml.jackson.databind.deser.Deserializers;

import java.io.Serializable;
import java.util.ArrayList;

public class Account {
    private String name;
    private int id;
    private String password;
    private int budget = Constants.initialBudget;
    private ArrayList<Match> matchHistory = new ArrayList<>();
    private Collection collection = new Collection();
    private transient static Game game = Game.getInstance();
    private transient boolean isLoggedIn = true;
    private int wins = 0;
    private int losses=0;
    private int ties = 0;

    public static Game getGame() {
        return game;
    }

    public static void setGame(Game game) {
        Account.game = game;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getTies() {
        return ties;
    }

    public void setTies(int ties) {
        this.ties = ties;
    }

    private transient int mana = 2;
    private transient int flagsCollected = 0;

    public int getFlagsCollected() {
        return flagsCollected;
    }

    public void setFlagsCollected(int flagsCollected) {
        this.flagsCollected = flagsCollected;
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
        this.password = password;
    }

    public static Message login(String username, String password) {
        if (accountIndex(username) == -1)
            return Message.INVALID_ACCOUNT;
        else if (!game.getAccounts().get(accountIndex(username)).password.equals(password)) {
            return Message.INVALID_PASSWORD;
        } else if (game.getAccounts().get(accountIndex(username)).isLoggedIn) {
            System.out.println("account: " + game.getAccounts().get(accountIndex(username)).getName() + " " +
                    game.getAccounts().get(accountIndex(username)).isLoggedIn());
            return Message.ALREADY_LOGGED_IN;
        }
        return Message.SUCCESSFUL_LOGIN;
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
        for (int i = 0; i < game.getAccounts().size(); i++) {
            if (game.getAccounts().get(i).name.equals(name))
                return i;
        }
        return -1;
    }

    public void modifyAccountBudget(int money) {
        this.budget += money;
    }

    private static String encrypted(String password) {
        return password;
    }

    public void modifyMana(int power) {
        this.mana += power;
    }

    public static Account getAccountByName(String name, ArrayList<Account> accounts) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).name.equals(name))
                return accounts.get(i);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", password='" + password + '\'' +
                ", budget=" + budget +
                ", matchHistory=" + matchHistory +
                ", collection=" + collection +
                ", wins=" + wins +
                '}';
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static Account fromJson(String json) {
        return new Gson().fromJson(json, Account.class);
    }
}
