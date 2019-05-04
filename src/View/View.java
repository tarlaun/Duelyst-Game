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

    public void printCollection(Collection collection, boolean isInShop) {
        for (int i = 0; i < collection.getCards().size(); i++) {
            if (collection.getCards().get(i) instanceof Hero) {
                System.out.print(i + 1 + " : ");
                printNonSpellCard(collection.getCards().get(i));
                if (isInShop)
                    System.out.println(collection.getCards().get(i).getPrice());
            }
        }
        for (int i = 0; i < collection.getItems().size(); i++) {
            System.out.print(i + 1 + " : ");
            printItem(collection.getItems().get(i));
            if (isInShop)
                System.out.println(collection.getCards().get(i).getPrice());
        }
        for (int i = 0; i < collection.getCards().size(); i++) {
            if (!(collection.getCards().get(i) instanceof Hero)) {
                System.out.print(i + 1 + " : ");
                if (collection.getCards().get(i) instanceof Spell)
                    printSpell((Spell) collection.getCards().get(i));
                else
                    printNonSpellCard(collection.getCards().get(i));
                if (isInShop)
                    System.out.println(collection.getCards().get(i).getPrice());

            }
        }
    }

    public void printSpell(Spell spell) {
        System.out.print("Name : " + spell.getName() + " - MP : " + spell.getManaPoint() + " - Desc :");
        printBuff(spell);
    }

    public void printBuff(Card card) {

    }

    public void printItem(Item item) {
        System.out.print("Name : " + item.getName() + " - Desc :");
        printItemBuff(item);
    }

    public void printItemBuff(Item item) {

    }

    public void printNonSpellCard(Card card) {
        System.out.print("Name : " + card.getName() + " - MP : " + card.getManaPoint() + " - AP : " +
                card.getAssaultPower() + " - HP : " + card.getHealthPoint() + " - Class : " + card.getActivationType());
        if (card.getAssaultType() != AssaultType.MELEE)
            System.out.print(" - Range : " + card.getMaxRange());
        System.out.print(" - Special power : ");
        printBuff(card);
    }

    public void printMinionsInfo(Card... cards) {
        for (Card card : cards) {
            printMinionInBattleInfo((Minion) card);
        }
    }

    public void printMinionInBattleInfo(Minion minion) {
        System.out.println(minion.getId() + " " + minion.getName() + " " + ", health : " + minion.getHealthPoint()
                + ", location : ( " + minion.getCoordinate().getX() + " , " + minion.getCoordinate().getY()
                + " ), power : " + minion.getAssaultPower());
    }

    public void printCardInfo(Card card) {

    }

    public void printItemInfo(Item item) {

    }

    public void printHand(Card... cards) {

    }

    public void printCollectables(Item... items) {

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

    public void printSellMessages(Boolean successful) {
        if (successful) {
            System.out.println("SUCCESSFUL SELL");
        }
        System.out.println("OBJECT NOT FOUNT");
    }

    public void printBuyMessages(Message message) {
        switch (message) {
            case OBJECT_NOT_FOUND:
                System.out.println("OBJECT NOT FOUND");
                break;
            case INSUFFICIENCY:
                System.out.println("INSUFFICIENCY");
                break;

            case MAXIMUM_ITEM_COUNT:
                System.out.println("MAXIMUM ITEM COUNT");
                break;

            case SUCCESSFUL_PURCHASE:
                System.out.println("SUCCESSFUL PURCHASE");
                break;
        }
    }

    public void showMovement(boolean validMove) {

    }

    public void showAttack(Message message) {
    }

    public void showCombo(int oppId, Card[] comboComrades) {
    }

    public void printInsertionMessage(Message message) {

    }

    public void printUnsuccessfulSelection(int collctablesLength) {
        if (collctablesLength == 0)
            System.out.println("Invalid card id");
        else
            System.out.println("No card/item exists with this id");
    }

    public void endTurn() {
    }

    public void printItemUsage(boolean valid) {

    }

    public void printCards(Card... cards) {
    }

    public void endGame(Battle battle) {
    }
}
