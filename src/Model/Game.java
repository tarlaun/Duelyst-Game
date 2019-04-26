package Model;

import java.util.ArrayList;

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

    public boolean save(Account account) {

    }
}
