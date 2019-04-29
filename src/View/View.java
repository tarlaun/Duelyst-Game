package View;

import Model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class View {
    private Scanner scanner = new Scanner(System.in);
    private Request request;
    private ArrayList<String> printedMessages = new ArrayList<>();
    private Message Message;
    private static final View view = new View();
    private Game game = Game.getInstance();
    private Menu menu = Menu.getInstance();

    private View() {

    }

    public static View getInstance() {
        return view;
    }

    public void passwordInsertion() {
        System.out.println("Password: ");
    }

    public void accountCreation(Boolean valid) {
        if (valid) {
            System.out.println("Account created");
            return;
        }
        System.out.println("Account already exists");
    }

    public void login(Message message) {
        switch (message) {
            case INVALID_ACCOUNT:
                System.out.println("Account doesn't exist!");
                break;
            case INVALID_PASSWORD:
                System.out.println("Incorrect password");
                break;
            case SUCCESSFUL_LOGIN:
                System.out.println("Welcome");
                break;
        }
    }

    public void logout() {
        System.out.println("Successful logout!!!:))))");
    }

    public void printLeaderboard() {
        game.sortAccounts();
        for (int i = 0; i < game.getAccounts().size(); i++) {
            System.out.println(i + 1 + " - UserName : " + game.getAccounts().get(i).getName() +
                    " - Wins : " + game.getAccounts().get(i).getWins());
        }

    }

    public void printHelp() {
        for (String str : menu.getCommands()) {
            System.out.println(str);
        }
    }

    public void printOptions() {
        for (String str : menu.getOptions()) {
            System.out.println(str);
        }
    }

    public void printCollection(Collection collection) {
        for (int i = 0; i < collection.getCards().size(); i++) {
            if (collection.getCards().get(i) instanceof Hero) {
                printHero((Hero) collection.getCards().get(i));
            }
        }
        for (int i = 0; i < collection.getItems().size(); i++) {
            printItem(collection.getItems().get(i));
        }
        for (int i = 0; i < collection.getCards().size(); i++) {
            if (!(collection.getCards().get(i) instanceof Hero)) {
                printCard(collection.getCards().get(i));
            }
        }
    }

    public void printCard(Card card) {

    }

    public void printItem(Item item) {

    }

    public void printHero(Hero hero) {

    }

    public void printShopCollection(Collection collection) {
        for (int i = 0; i < collection.getCards().size(); i++) {
            if (collection.getCards().get(i) instanceof Hero) {
                printHero((Hero) collection.getCards().get(i));
                System.out.println(collection.getCards().get(i).getPrice());
            }
        }
        for (int i = 0; i < collection.getItems().size(); i++) {
            printItem(collection.getItems().get(i));
            System.out.println(collection.getItems().get(i).getPrice());
        }
        for (int i = 0; i < collection.getCards().size(); i++) {
            if (!(collection.getCards().get(i) instanceof Hero)) {
                printCard(collection.getCards().get(i));
                System.out.println(collection.getCards().get(i).getPrice());
            }
        }
    }

    public void printObjects() {

    }

    public void printMinionsInfo(ArrayList<Minion> minions) {

    }

    public void printCardInfo(Card card) {

    }

    public void printItemInfo(Item item) {

    }

    public void printHand(ArrayList<Card> cards) {

    }

    public void printCollectables(ArrayList<Item> items) {

    }

    public void printGraveyardMenu() {

    }

    public void printError(Message message) {

    }

    public void printId(Card... cards) {

    }

    public void printId(Item... items) {

    }

    public void createDeck(boolean okOrNot) {
        if (okOrNot) {
            System.out.println("DECK CREATED");
            return;
        }
        System.out.println("DECK ALREADY EXISTS");
    }

    public void deleteDeck(boolean deletedOrNot) {
        if (deletedOrNot) {
            System.out.println("DECK IS FUCKED UP");
            return;
        }
        System.out.println("DECK IS NOT FUCKED UP");
    }

    public void addToCollection(Message message) {

        switch (message) {

            case MAXIMUM_ITEM_COUNT:
                System.out.println(" AN ITEM EXISTS IN DECK");
                break;
            case OBJECT_ADDED:
                System.out.println(" OBJECT ADDED");
                break;
            case OBJECT_NOT_FOUND:
                System.out.println("OBJECT NOT FOUND");
                break;
            case FULL_DECK:
                System.out.println(" DECK IS FULL");
                break;
            case MAXIMUM_HERO_COUNT:
                System.out.println(" A HERO EXISTS IN THE DECK");
                break;
            case EXISTS_IN_DECK:
                System.out.println("OBJECT EXISTS IN DECK");
                break;
            case INVALID_DECK:
                System.out.println(" DECK IS INVALID");
                break;
        }
    }

    public void removeFromDeck(Message message) {
        switch (message) {
            case OBJECT_NOT_FOUND:
                System.out.println("OBJECT NOT FOUND");
                break;
            case INVALID_DECK:
                System.out.println("INVALID DECK");
                break;
        }
    }

    public void checkValidation(boolean validOrNot) {
        if (validOrNot) {
            System.out.println("DECK IS VALID");
            return;
        }
        System.out.println("DECK IS INVALID");
    }

    public void printDeckSelection(boolean selectedOrNot) {
        if (selectedOrNot) {
            System.out.println("DECK SELECTED");
            return;
        }
        System.out.println("DECK IS MOTHERFUCKER");
    }

    public void showAllDeck(ArrayList<Deck> decks) {
        for (int i = 0; i < decks.size(); i++) {
            System.out.println(i + 1 + "-");
            printDeck(decks.get(i));
        }
    }

    public void printDeck(Deck deck) {

    }
}
