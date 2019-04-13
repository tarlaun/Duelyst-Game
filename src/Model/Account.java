package Model;

import java.util.ArrayList;

public class Account {
    private String name;
    private int id;
    private String password;
    private int budget;
    private ArrayList<Match> matchHistory = new ArrayList<>();
    private Collection collection;
    static Menu menu = new Menu();

    public Account(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public static boolean createAccount(Account account) {
        if(accountIndex(account.name) != -1)
            return false;
        account.id = menu.getAccounts().size() + 1;
        menu.getAccounts().add(account);
        return true;
    }

    public static boolean login(String username, String password) {
        if(accountIndex(username) != -1 && menu.getAccounts().get(accountIndex(username)).password.equals(password))
            return true;
        return false;
    }

    public void logout() {

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

    public static int accountIndex(String name){
        for (Account account: menu.getAccounts()) {
            if(account.name.equals(name))
                return account.id-1;
        }
        return -1;
    }

    public  void modifyAccountBudget(int money){
       this.budget+=money;
    }
}
