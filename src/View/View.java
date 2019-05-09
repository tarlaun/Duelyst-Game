package View;

import Model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import Model.*;

public class View {
    private static final View view = new View();
    private Game game = Game.getInstance();
    private Menu menu = Menu.getInstance();
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

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

    public void showMatchHistory(ArrayList<Match> matches, int level) {
        System.out.println("number level win/lose time");
        LocalDateTime time = LocalDateTime.now();
        int hour = time.getHour();
        int minutes = time.getMinute();

        for (int i = 0; i < matches.size(); i++) {
            if (matches.get(i).getTime().getHour() == hour) {
                int mins = minutes - matches.get(i).getTime().getMinute();
                System.out.println(i + " . LEVEL:" + level + "WIN OR LOST" + matches.get(i).getResult() + "TIME: " + mins);
            } else {
                int hours = hour - matches.get(i).getTime().getHour();
                System.out.println(i + " . LEVEL:" + level + "WIN OR LOST" + matches.get(i).getResult() + "TIME: " + hours);
            }
        }
    }

    public void showMatchHistory(ArrayList<Match> matches, String name) {
        LocalDateTime time = LocalDateTime.now();
        int hour = time.getHour();
        int minutes = time.getMinute();
        for (int i = 0; i < matches.size(); i++) {
            if (matches.get(i).getTime().getHour() == hour) {
                int mins = minutes - matches.get(i).getTime().getMinute();
                System.out.println(i + " . OPPONENT:" + name + "WIN OR LOST" + matches.get(i).getResult() + "TIME: " + mins);
            } else {
                int hours = hour - matches.get(i).getTime().getHour();
                System.out.println(i + " . NAME:" + name + "WIN OR LOST" + matches.get(i).getResult() + "TIME: " + hours);
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
        int index = 1;
        for (int i = 0; i < collection.getCards().size(); i++) {
            if (collection.getCards().get(i).getType().equals("Hero")) {
                System.out.print("Id: " + collection.getCards().get(i).getId() + " - ");
                System.out.print(index + " : ");
                printNonSpellCard(collection.getCards().get(i));
                if (isInShop)
                    System.out.println(collection.getCards().get(i).getPrice());
                index++;
            }
        }
        index = 1;
        System.out.println("Items :");
        for (int i = 0; i < collection.getItems().size(); i++) {
            System.out.print("Id: " + collection.getItems().get(i).getId() + " - ");
            System.out.print(index + " : ");
            printItem(collection.getItems().get(i));
            if (isInShop)
                System.out.println(collection.getItems().get(i).getPrice());
            index++;
        }
        System.out.println("Cards :");
        printCards(isInShop, collection.getCards().toArray(new Card[collection.getCards().size()]));
    }

    public void printSpell(Card spell) {
        System.out.print("Name : " + spell.getName() + " - MP : " + spell.getManaPoint() + " - Desc :");
        printBuff(spell);
    }

    public void battleCreating() {
        System.out.println("BATTLE CREATED");
    }

    public void printBuff(Card card) {
        System.out.println();
    }

    public void printItem(Item item) {
        System.out.print("Id: " + item.getId() + " - Name : " + item.getName() + " - Desc :");
        printItemBuff(item);
    }

    public void printItemBuff(Item item) {
        System.out.println();
    }

    public void printNonSpellCard(Card card) {
        System.out.print("Id: " + card.getId() + " - Name : " + card.getName() + " - MP : " + card.getManaPoint() + " - AP : " +
                card.getAssaultPower() + " - HP : " + card.getHealthPoint() + " - Class : " + card.getAssaultType());
        if (card.getAssaultType() != AssaultType.MELEE)
            System.out.print(" - Range : " + card.getMaxRange());
        System.out.print(" - Special power : ");
        printBuff(card);
    }

    public void printMinionsInfo(Card... cards) {
        for (Card card : cards) {
            try {
                if (card.getType().equals("Hero")) {
                    System.out.println("Hero: ");
                    printNonSpellCard(card);
                }
                if (card.getType().equals("Minion"))
                    printMinionInBattleInfo(card);
            } catch (NullPointerException e) {
            }
        }
    }

    public void printMinionInBattleInfo(Card minion) {
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
        int index = 1;
        for (int i = 0; i < cards.length; i++) {
            try {
                if (!(cards[i].getType().equals("Hero"))) {
                    System.out.print("Id: " + cards[i].getId() + " - ");
                    System.out.print(index + " : ");
                    if (cards[i].getType().equals("Spell")) {
                        System.out.print("Type : Spell - ");
                        printSpell(cards[i]);
                    } else {
                        System.out.print("Type : Minion - ");
                        printNonSpellCard(cards[i]);
                    }
                    if (isInShop)
                        System.out.println(cards[i].getPrice());
                    index++;

                }
            } catch (NullPointerException e) {
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
        if (deck == null) {
            System.out.println("Deck doesn't exist!");
            return;
        }
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
            return;
        }
        System.out.println("OBJECT NOT FOUND");
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

    public void showMovement(boolean validMove, Battle battle) {
        if (validMove)
            drawMap(battle);
        else
            System.out.println("Invalid move!");
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

    public void printInsertionMessage(Message message, Battle battle) {
        switch (message) {
            case FULL_CELL:
                System.out.println("This cell is full.");
                break;
            case INVALID_TARGET:
                System.out.println("You are out of range, please insert card near a comrade minion or your hero");
                break;
            case INSUFFICIENT_MANA:
                System.out.println("Your mana is not sufficient!");
                break;
            case SUCCESSFUL_INSERT:
                drawMap(battle);
        }
    }

    public void printSelectionResult(boolean card, boolean item) {
        if (card)
            System.out.println("Card selected");
        else if (item)
            System.out.println("Item selected");
        else
            System.out.println("No card/item found with this id!");
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

    public void endTurn(Account account) {
        System.out.println("Player: " + account.getName() + "  Mana: " + account.getMana());
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

    public void chooseBattleMode() {
        System.out.println("Choose battle mode:");
        System.out.println("KillEnemyHero");
        System.out.println("Collecting");
        System.out.println("Flag");
    }

    public void chooseMultiOrSingle() {
        System.out.println("Choose game type:");
        System.out.println("SinglePlayer");
        System.out.println("MultiPlayer");
    }

    public void chooseLevels() {
        System.out.println("Choose AI level:");
        System.out.println("level1");
        System.out.println("level2");
        System.out.println("level3");
    }

    public void comboErrors(Message message) {
        switch (message) {
            case INVALID_TARGET:
                System.out.println("Target doesn't exist");
                break;
            case UNAVAILABLE:
                System.out.println("Target out of range");
                break;
            case NOT_ABLE_TO_ATTACK:
                System.out.println("Not all selected cards are able to combo attack");
                break;
        }
    }

    public void printGameInfo(Battle battle) {
        System.out.println("Game Type: " + battle.getGameType());
        System.out.println("Battle Mode: " + battle.getMode());
    }

    public void chooseProcess() {
        System.out.println("Choose Process: ");
        System.out.println("Story");
        System.out.println("Custom");
    }

    public void playerAdded(Account account) {
        if (account == null) {
            System.out.println("Account doesn't exist!");
        } else {
            System.out.println(account.getName() + " successfully added!");
        }
    }

    public void drawMap(Battle battle) {
        int id;
        for (int i = 0; i < Constants.WIDTH; i++) {
            for (int j = 0; j < Constants.LENGTH; j++) {
                id = battle.getField(i, j).getCardID();
                if (id == 0) {
                    if (battle.getField(i, j).isHoly())
                        System.out.print("H");
                    else if (battle.getField(i, j).isFire())
                        System.out.print("F");
                    else if (battle.getField(i, j).isPoison())
                        System.out.print("P");
                    else
                        System.out.print("-");
                } else {
                    Card card = Card.getCardByID(id, battle.getFieldCards()[0]);
                    if (card != null) {
                        System.out.print(ANSI_GREEN + card.getType().charAt(0) + ANSI_RESET);
                    } else {
                        card = Card.getCardByID(id, battle.getFieldCards()[1]);
                        System.out.print(ANSI_RED + card.getType().charAt(0) + ANSI_RESET);
                    }
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }


}
