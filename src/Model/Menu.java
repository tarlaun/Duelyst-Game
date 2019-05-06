package Model;

import Controller.Controller;
import View.View;

import java.util.ArrayList;

public class Menu {
    private MenuStat stat = MenuStat.MAIN;
    private ArrayList<Account> accounts = new ArrayList<>();
    private Shop shop = Shop.getInstance();
    private String[][] commands =
            {
                    {
                            "createAccount [username]", "login [username]", "help", "exit", "showMenu"
                    },
                    {
                            "game", "logout", "save", "showLeaderboard", "help", "exit", "showMenu"
                    },
                    {
                            "Enter [option name]", "help", "exit", "showMenu"
                    },
                    {
                            "search [card name|item name]", "save", "createDeck [deck name]", "deleteDeck [deck name]",
                            "add [card id|item id|hero id] from deck [deck name]", "validateDeck [deck name]",
                            "remove [card id|item id|hero id] from deck [deck name]", "selectDeck [deck name]",
                            "ShowAllDecks", "showDeck [deck name]", "show", "help", "exit", "showMenu"
                    },
                    {
                            "search [card name|item name]", "searchCollection [item name|card name]", "showCollection",
                            "buy [card name|item name]", "sell [card id|item it]", "show", "help", "exit", "showMenu"
                    },
                    {
                            "GameInfo", "ShowMyMinions", "ShowOpponentMinions", "ShowCardInfo [card id]",
                            "Select [card id]", "MoveTo( [x] , [y] )", "Attack [opponent card id]", "ShowHand",
                            "AttackCombo [opponent card id][my card id][my card id][...]", "Select [collectable id]",
                            "UseSpecialPower( [x] , [y] )", "Insert [card name] in ( [x] , [y] )", "EndTurn",
                            "ShowCollectables", "ShowInfo", "Use( [x] , [y] )", "ShowNextCard", "Enter graveyard",
                            "ShowInfo [card id]", "ShowCards", "Help", "EndGame", "help", "exit", "showMenu"
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
