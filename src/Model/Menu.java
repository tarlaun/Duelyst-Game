package Model;

import Controller.Controller;
import View.View;

import java.util.ArrayList;

public class Menu {
    private MenuStat stat;
    private View view = View.getInstance();
    private Controller controller = new Controller();
    private ArrayList<AI> ais = new ArrayList<>();
    private ArrayList<Account> accounts = new ArrayList<>();
    private Shop shop = new Shop();
    private String[][] commands =
            {
                    {
                            "create account [username]", "login [username]", "help", "exit", "show menu"
                    },
                    {
                            "game", "logout", "save", "help", "exit", "show menu"
                    },
                    {
                            "Enter [option name]", "help", "exit", "show menu"
                    },
                    {
                            "search [card name|item name]", "save", "create deck [deck name]", "delete deck [deck name]",
                            "add [card id|item id|hero id] from deck [deck name]", "Validate deck [deck name]",
                            "remove [card id|item id|hero id] from deck [deck name]", "select deck [deck name]",
                            "Show all decks", "show deck [deck name]", "show", "help", "exit", "show menu"
                    },
                    {
                            "search [card name|item name]", "search collection [item name|card name]", "show collection",
                            "buy [card name|item name]", "sell [card id|item it]", "show", "help", "exit", "show menu"
                    },
                    {
                            "Game info", "Show my minions", "Show opponent minions", "Show card info [card id]",
                            "Select [card id]", "Move to ([x],[y])", "Attack [opponent card id]", "Show hand",
                            "Attack combo [opponent card id][my card id][my card id][...]", "Use special power ([x],[y])",
                            "Insert [card name] in ([x],[y])", "End turn", "Show collectables", "Select [collectable id]",
                            "Show info", "Use ([x],[y])", "Show Next Card", "Enter graveyard", "Show info [card id]",
                            "Show cards", "Help", "End Game", "help", "exit", "show menu"
                    }
            };
    private String[][] options =
            {
                    {
                            "Help", "Exit"
                    },
                    {
                            "Game", "Help", "Exit"
                    },
                    {
                            "Collection", "Shop", "Battle", "Help", "Exit"
                    },
                    {
                            "Graveyard", "Help", "Exit"
                    },
                    {
                            "Help", "Exit"
                    }
            };
    private static final Menu menu = new Menu();

    private Menu() {

    }

    public static Menu getInstance() {
        return menu;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public MenuStat getStat() {
        return stat;
    }

    public void setStat(MenuStat stat) {
        this.stat = stat;
    }

    public void exitMenu() {
        this.stat = this.stat.prevMenu();
    }

    public String[] getStrings(String[][] strings) {
        switch (this.stat) {
            case MAIN:
                return strings[MenuStat.MAIN.ordinal()];
            case GAME:
                return strings[MenuStat.GAME.ordinal()];
            case COLLECTION:
                return strings[MenuStat.COLLECTION.ordinal()];
            case SHOP:
                return strings[MenuStat.SHOP.ordinal()];
            case BATTLE:
                return strings[MenuStat.BATTLE.ordinal()];
            case GRAVEYARD:
                return strings[MenuStat.GRAVEYARD.ordinal()];
            default:
                return strings[MenuStat.ACCOUNT.ordinal()];
        }
    }

    public String[] getCommands() {
        return getStrings(this.commands);
    }

    public String[] getOptions() {
        return getStrings(this.options);
    }
}
