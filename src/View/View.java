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

    public void logout(){
        System.out.println("Successful logout!!!:))))");
    }

    public void printLeaderboard() {
        game.sortAccounts();
        for (int i = 0; i < game.getAccounts().size(); i++) {
            System.out.println(i + 1 + " - UserName : " + game.getAccounts().get(i).getName() +
                    " - Wins : " + game.getAccounts().get(i).getWins());
        }

    }

    public void accountHelp() {


    }

    public void printCollectionHelp() {

    }

    public void printShopHelp() {

    }

    public void printBattleHelp() {

    }

    public void printCollection(Collection collection) {

    }

    public void printAllDecks(ArrayList<Deck> decks) {

    }

    public void printShopCollection() {

    }

    public void printObjects() {

    }

    public void printMininsInfo(ArrayList<Minion> minions) {

    }

    public void printCardInfo(Card card) {

    }

    public void printItemInfo(Item item) {

    }

    public void printHand(ArrayList<Card> cards) {

    }

    public void printCollectables(ArrayList<Item> items) {

    }

    public void printMainMenu() {


    }

    public void printGraveyardMenu() {

    }

    public void printError(Message message) {

    }
}
