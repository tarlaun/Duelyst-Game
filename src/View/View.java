package View;

import Model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class View {
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

    public void showMatchHistory(ArrayList<Match> matches, int level){
        System.out.println("number level win/lose time");
        LocalDateTime time = LocalDateTime.now();
        int hour = time.getHour();
        int minutes = time.getMinute();

        for (int i = 0; i < matches.size() ; i++) {
            if(matches.get(i).getTime().getHour()== hour){
                int mins = minutes - matches.get(i).getTime().getMinute();
                System.out.println(i+" . LEVEL:"+ level + "WIN OR LOST"+matches.get(i).getResult()+"TIME: "+ mins  );
            }else {
                int hours = hour - matches.get(i).getTime().getHour();
                System.out.println(i+" . LEVEL:"+ level + "WIN OR LOST"+matches.get(i).getResult()+"TIME: "+hours  );
            }
        }
    }

    public void showMatchHistory(ArrayList<Match> matches,String name){
        LocalDateTime time = LocalDateTime.now();
        int hour = time.getHour();
        int minutes = time.getMinute();
        for (int i = 0; i < matches.size() ; i++) {
            if(matches.get(i).getTime().getHour()== hour){
                int mins = minutes - matches.get(i).getTime().getMinute();
                System.out.println(i+" . OPPONENT:"+ name + "WIN OR LOST"+matches.get(i).getResult()+"TIME: "+ mins  );
            }else {
                int hours = hour - matches.get(i).getTime().getHour();
                System.out.println(i+" . NAME:"+ name + "WIN OR LOST"+matches.get(i).getResult()+"TIME: "+hours  );
            }
        }
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
        System.out.println("Heroes :");
        for (int i = 0; i < collection.getCards().size(); i++) {
            if (collection.getCards().get(i) instanceof Hero) {
                System.out.print(i + 1 + " : ");
                printNonSpellCard(collection.getCards().get(i));
                if (isInShop)
                    System.out.println(collection.getCards().get(i).getPrice());
            }
        }
        System.out.println("Items :");
        for (int i = 0; i < collection.getItems().size(); i++) {
            System.out.print(i + 1 + " : ");
            printItem(collection.getItems().get(i));
            if (isInShop)
                System.out.println(collection.getCards().get(i).getPrice());
        }
        System.out.println("Cards :");
        printCards(isInShop, collection.getCards().toArray(new Card[collection.getCards().size()]));
    }

    public void printSpell(Spell spell) {
        System.out.print("Name : " + spell.getName() + " - MP : " + spell.getManaPoint() + " - Desc :");
        printBuff(spell);
    }

    public void printBuff(Card card) {
        System.out.println();
    }

    public void printItem(Item item) {
        System.out.print("Name : " + item.getName() + " - Desc :");
        printItemBuff(item);
    }

    public void printItemBuff(Item item) {
        System.out.println();
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
        System.out.println("Name : " + card.getName());
        switch (card.getClass().getName()) {
            case "Hero":
                System.out.println("Cost : " + card.getPrice());
                break;
            case "Minion":
                System.out.println("HP : " + card.getHealthPoint() + " AP : " + card.getAssaultPower()
                        + " MP : " + card.getManaPoint());
                System.out.print("Range : " + card.getRangeType());
                if (card.getRangeType() != RangeType.MELEE)
                    System.out.println(" - " + card.getMaxRange());
                System.out.print("Combo-ability : ");
                if (card.getActivationType() == ActivationType.COMBO)
                    System.out.println("Yes");
                else
                    System.out.println("No");
                System.out.println("Cost : " + card.getPrice());
                break;
            case "Spell":
                System.out.println("MP : " + card.getManaPoint());
                System.out.println("Cost : " + card.getPrice());
                break;
        }
        System.out.print("Desc :");
        printBuff(card);
    }

    private void printCards(boolean isInShop, Card... cards) {
        for (int i = 0; i < cards.length; i++) {
            if (!(cards[i] instanceof Hero)) {
                System.out.print(i + 1 + " : ");
                if (cards[i] instanceof Spell) {
                    System.out.print("Type : Spell - ");
                    printSpell((Spell) cards[i]);
                } else {
                    System.out.print("Type : Minion - ");
                    printNonSpellCard(cards[i]);
                }
                if (isInShop)
                    System.out.println(cards[i].getPrice());

            }
        }
    }

    public void printCollectables(Item... items) {
        for (Item item : items) {
            printItem(item);
        }
    }

    public void printId(Card... cards) {
        for (Card card : cards) {
            System.out.println(card.getId());
        }
    }

    public void printId(Item... items) {
        for (Item item : items) {
            System.out.println(item.getId());
        }
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
        System.out.println("Name: " + deck.getName());
        System.out.println("Heroes :");
        try {
            printNonSpellCard(deck.getHero());
        } catch (NullPointerException e) {
        }
        System.out.println("Items :");
        try {
            printItem(deck.getItem());
        } catch (NullPointerException e) {
        }
        System.out.println("Cards :");
        try {
            printCards(false, deck.getCards().toArray(new Card[deck.getCards().size()]));
        } catch (NullPointerException e) {
        }


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
        switch (message) {
            case INVALID_TARGET:
                System.out.println("Card doesn't exist on field");
                break;
            case UNAVAILABLE:
                System.out.println("Target is out of range!");
                break;
            case NOT_ABLE_TO_ATTACK:
                System.out.println("You are not able to attack right now... (exhausted)");
                break;

        }
    }

    public void showCombo(int oppId, Card[] comboComrades) {
    }

    public void printInsertionMessage(Message message) {
        switch (message) {
            case FULL_CELL:
                System.out.println("This cell is full.");
                break;
            case INVALID_TARGET:
                System.out.println("You are out of range, please insert card near a comrade minion or your hero");
                break;
        }
    }

    public void printUnsuccessfulSelection(int collctablesLength) {
        if (collctablesLength == 0)
            System.out.println("Invalid card id");
        else
            System.out.println("No card/item exists with this id");
    }

    public void specialPowerValidation(Message message) {
        switch (message) {
            case INVALID_TARGET:
                System.out.println("Cell is empty");
                break;
            case OBJECT_NOT_FOUND:
                System.out.println("Card doesn't exist on field");
                break;
            case NOT_ABLE_TO_ATTACK:
                System.out.println("Card doesn't have a special power");
        }
    }

    public void endTurn() {
    }

    public void printItemUsage(boolean valid) {

    }

    public void printShopCollection(Collection collection) {
        printCards(true, collection.getCards().toArray(new Card[collection.getCards().size()]));
        printItems(true, collection.getItems().toArray(new Item[collection.getItems().size()]));
    }

    private void printItems(boolean isInShop, Item... items) {
        for (int i = 0; i < items.length; i++) {
            System.out.print(i + 1 + " : ");
            printItem(items[i]);
            if (isInShop)
                System.out.println(" - Price : " + items[i].getPrice());

        }
    }

    public void printCards(Card... cards) {
        printCards(false, cards);
    }

    public void endGame(Battle battle) {
    }

    public void printInvalidCommand() {
        System.out.println("INVALID COMMAND");
    }
}
