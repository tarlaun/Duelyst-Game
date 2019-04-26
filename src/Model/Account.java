package Model;

import java.util.ArrayList;

public class Account {
    private String name;
    private int id;
    private String password;
    private int budget;
    private ArrayList<Match> matchHistory = new ArrayList<>();
    private Collection collection;
    private static Game game = Game.getInstance();

    public Account(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public boolean createAccount() {
        if (accountIndex(this.name) != -1)
            return false;
        this.id = game.getAccounts().size() + 1;
        game.getAccounts().add(this);
        return true;
    }

    public static boolean login(String username, String password) {
        if (accountIndex(username) != -1 && game.getAccounts().get(accountIndex(username)).password.equals(password))
            return true;
        return false;
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

    public void save() {

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
}
