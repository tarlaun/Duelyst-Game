package Model;

import Controller.Controller;
import View.View;

import java.util.ArrayList;

public class Menu {
    private MenuStat stat;
    private View view = new View();
    private Controller controller= new Controller();
    private ArrayList<AI> ais = new ArrayList<>();
    private ArrayList<Account> accounts = new ArrayList<>();
    private Shop shop= new Shop();

    public ArrayList<Account> getAccounts() {
        return accounts;
    }
}
