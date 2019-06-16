package View;

import Model.*;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;

import Model.Menu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;

public class View {
    private transient AnchorPane root = new AnchorPane();
    private transient Scene scene = new Scene(root, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
    private Menu menu = Menu.getInstance();
    private static final View view = new View();
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";

    private View() {

    }

    public Scene getScene() {
        return scene;
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

    public void printLeaderboard(ArrayList<Account> accounts) {
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println(i + 1 + " - UserName : " + accounts.get(i).getName() +
                    " - Wins : " + accounts.get(i).getWins());
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
        try {
            System.out.println("coordinates  x: " + card.getCoordinate().getX() + " y " + card.getCoordinate().getY());
        } catch (NullPointerException e) {
        }
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
        if (message == null) {
            System.out.println("ATTACK DONE!!!");
            return;
        }
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
            case BATTLE_FINISHED:
                System.out.println("Battle finished >:D");
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
                break;
            case NULL:
                System.out.println("Valid Special Power.");

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

    public void showEntrance(Request request) {
        switch (request.getMenu(request.getCommand())) {
            case "Game":
                System.out.println("Successful Entrance to Game");
                break;
            case "Collection":
                System.out.println("Successful Entrance to Collection");
                break;
            case "Shop":
                System.out.println("Successful Entrance to Shop");
                break;
        }
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
        if (battle.getMode().equals(BattleMode.COLLECTING)) {
            System.out.println("Player1 :" + battle.getAccounts()[0].getName() + " flags collected:" + battle.getAccounts()[0].getFlagsCollected());
            System.out.println("Player2 :" + battle.getAccounts()[1].getName() + " flags collected:" + battle.getAccounts()[1].getFlagsCollected());
        }
        if (battle.getMode().equals(BattleMode.FLAG)) {
            System.out.println("Flagholder: ");
            if (battle.getMainFlag().getFlagHolder() != null) {
                Card card = Card.getCardByID(battle.getMainFlag().getFlagHolder().getId(), battle.getFieldCards()[0]);
                if (card != null)
                    System.out.print(battle.getAccounts()[0].getName());
                else System.out.print(battle.getAccounts()[1].getName());
                System.out.print(" For number of turns: " + battle.getMainFlag().getTurnCounter());
            } else {
                System.out.print("NO ONE !");
            }


        }
        if (battle.getMode().equals(BattleMode.KILLENEMYHERO)) {
            System.out.println("Player1 :" + battle.getAccounts()[0].getName() + " hero health points:" + battle.getFieldCards()[0][0].getHealthPoint());
            System.out.println("Player2 :" + battle.getAccounts()[1].getName() + " hero health points:" + battle.getFieldCards()[1][0].getHealthPoint());

        }
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

    public void Success() {
        System.out.println("AI WON THE GAME");
    }

    public void drawMap(Battle battle) {
        int id;
        boolean isPrinted = false;
        for (int i = 0; i < Constants.WIDTH; i++) {
            for (int j = 0; j < Constants.LENGTH; j++) {
                id = battle.getField(i, j).getCardID();
                for (Flag flag :
                        battle.getFlagsOnTheGround()) {
                    if (flag.getCoordinate().getX() == i && flag.getCoordinate().getY() == j) {
                        System.out.print("*");
                        isPrinted = true;
                    }
                }
                if (battle.getMode().equals(BattleMode.FLAG) &&
                        battle.getMainFlag().getCoordinate().getX() == i && battle.getMainFlag().getCoordinate().getY() == j) {
                    System.out.print("#");
                    isPrinted = true;
                }
                if (id == 0 && !isPrinted) {
                    if (battle.getField(i, j).isHoly())
                        System.out.print("H");
                    else if (battle.getField(i, j).isFire())
                        System.out.print("F");
                    else if (battle.getField(i, j).isPoison())
                        System.out.print("P");

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
                isPrinted = false;
            }
            System.out.println();
        }

    }
    //Graphic

    public void graveYardMenu() {

    }


    public void selectUserMenu(ArrayList<Account> accounts, Label label, TextField textField) {
        root.getChildren().clear();
        Image background = new Image("resources/scenes/shimzar/bg@2x.jpg");
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(Constants.WINDOW_WIDTH);
        backgroundView.setFitHeight(Constants.WINDOW_HEIGHT);
        ArrayList<Users> users = new ArrayList<>();

        for (int i = 0; i < accounts.size(); i++) {
            String s = "OFFLINE";
            if (accounts.get(i).isLoggedIn()) s = "ONLINE";
            Users users1 = new Users(accounts.get(i).getName(), s);
            users.add(users1);
        }

        ListView<Users> list = new ListView<>();
        ObservableList<Users> items = FXCollections.observableArrayList(users);
        list.setItems(items);
        list.setOrientation(Orientation.VERTICAL);
        if (accounts.size() == 0) {
            list.getItems().add(new Users("NOBODY", "ONLINE"));
        }
        list.setStyle("-fx-control-inner-background: #74e9cf;-fx-font-size:20;");
        list.setMinWidth(200);
        list.setMaxHeight(300);
        list.relocate(175, 125);
        label.setText("FRIENDS");
        label.relocate(225, 80);
        label.setFont(Font.font(35));
        textField.relocate(215, 450);
        textField.setStyle("-fx-control-inner-background: #2b7b71;");
        root.getChildren().addAll(backgroundView, list, label, textField);
    }

    public void battleMenu(Account[] accounts , ImageView imageView1, ImageView imageView2) {
        root.getChildren().clear();
        Image background = new Image("resources/maps/abyssian/background@2x.jpg");
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(Constants.WINDOW_WIDTH);
        backgroundView.setFitHeight(Constants.WINDOW_HEIGHT);
        Image foreground = new Image("resources/maps/abyssian/midground@2x.png");
        ImageView foregroundView = getImageView(background, foreground);
        Polygon[] polygon = new Polygon[45];
        battleField(polygon);
        root.getChildren().addAll(backgroundView, foregroundView);
        for (int i = 0; i < 45; i++) {
            root.getChildren().add(polygon[i]);
        }
        Image firstHero, secondHero;
        firstHero = getImage(accounts[0]);
        secondHero = getImage(accounts[1]);
        ImageView firstHeroView = new ImageView(firstHero);
        ImageView secondHeroView = new ImageView(secondHero);
        bossImageSettings(firstHeroView, secondHeroView);
        imageView2.relocate((polygon[26].getPoints().get(0)+polygon[26].getPoints().get(2))/2-55,(polygon[26].getPoints().get(1)+polygon[26].getPoints().get(5))/2-105);
        imageView2.setScaleX(-1);
        imageView1.relocate((polygon[18].getPoints().get(0)+polygon[18].getPoints().get(2))/2-60,(polygon[18].getPoints().get(1)+polygon[18].getPoints().get(5))/2-105);
        lightning(imageView1,imageView2);
        root.getChildren().addAll(firstHeroView, secondHeroView,imageView1,imageView2);

    }


    private void bossImageSettings(ImageView firstHeroView, ImageView secondHeroView) {
        firstHeroView.relocate(150, -50);
        firstHeroView.setFitHeight(250);
        firstHeroView.setFitWidth(250);
        secondHeroView.relocate(900, -50);
        secondHeroView.setFitHeight(250);
        secondHeroView.setFitWidth(250);
        lightning(firstHeroView);
        lightning(secondHeroView);
    }

    private Image getImage(Account account) {
        Image firstHero = null;
        switch (account.getCollection().getMainDeck().getHero().getName()) {
            case "WHITE_DIV":
                firstHero = new Image("resources/boss_battles/boss_shinkage_zendo_portrait_image_hex@2x.png");
                break;
            case "ZAHAK":
                firstHero = new Image("resources/boss_battles/boss_calibero_portrait_image_hex@2x.png");
                break;
            case "ARASH":
                firstHero = new Image("resources/boss_battles/boss_boreal_juggernaut_portrait_image_hex@2x.png");
                break;
            case "SIMORGH":
                firstHero = new Image("resources/boss_battles/boss_shinkage_zendo_portrait_image_hex@2x.png");
                break;
            case "SEVEN_HEADED_DRAGON":
                firstHero = new Image("resources/boss_battles/boss_crystal_portrait_hex.png");
                break;
            case "RAKHSH":
                firstHero = new Image("resources/boss_battles/boss_wraith_portrait_hex@2x.png");
                break;
            case "KAVEH":
                firstHero = new Image("resources/boss_battles/boss_vampire_portrait_hex@2x.png");
                break;
            case "AFSANEH":
                firstHero = new Image("resources/boss_battles/boss_spelleater_portrait_hex@2x.png");
                break;
            case "ESFANDIAR":
                firstHero = new Image("resources/boss_battles/boss_skurge_portrait_hex@2x.png");
                break;
            case "ROSTAM":
                firstHero = new Image("resources/boss_battles/boss_shinkage_zendo_portrait_image_hex@2x.png");
                break;
        }
        return firstHero;
    }

    private void battleField(Polygon[] polygon) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.7);
        Glow glow = new Glow();
        glow.setLevel(0.9);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                polygon[j + 9 * i] = new Polygon();
                polygon[j + 9 * i].getPoints().addAll(-i * 8 + 380.0 + (60 + i * 2) * j + 2, 205.0 + i * 50 + 2, -i * 8 + 380.0 + (60 + i * 2) * (j + 1) - 2, 205.0 + i * 50 + 2, -(i + 1) * 8 + 380.0 + (60 + (i + 1) * 2) * (j + 1) - 2, 205.0 + ((i + 1) * 50) - 2, -(i + 1) * 8 + 380.0 + (60 + (i + 1) * 2) * j + 2, 205.0 + ((i + 1) * 50) - 2);
                polygon[j + 9 * i].setFill(Color.rgb(119, 104, 180, 0.6));
                glowPolygon(colorAdjust, polygon[j + 9 * i]);
            }
        }
    }

    private void glowPolygon(ColorAdjust colorAdjust, Polygon polygon1) {
        polygon1.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {

            polygon1.setEffect(colorAdjust);
            ;

        });
        polygon1.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
            polygon1.setEffect(null);
        });
    }

    public void mainMenu(Button login, Button create, Button exit, TextField username, TextField password) {
        Image background = new Image("resources/scenes/obsidian_woods/obsidian_woods_background.jpg");
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(Constants.WINDOW_WIDTH);
        backgroundView.setFitHeight(Constants.WINDOW_HEIGHT);
        Image foreground = new Image("resources/scenes/obsidian_woods/obsidian_woods_cliff.png");
        ImageView foregroundView = getImageView(background, foreground);
        login.setText("Login");
        create.setText("Create Account");
        exit.setText("Exit");
        verticalList(Alignment.CENTRE, Constants.CENTRE_X, Constants.CENTRE_Y, login, create, exit);
        password.setPrefWidth(Constants.FIELD_WIDTH);
        password.setPrefHeight(Constants.FIELD_HEIGHT);
        password.setLayoutX(Constants.CENTRE_X - password.getPrefWidth() / 2);
        password.setLayoutY(login.getLayoutY() - 2 * password.getPrefHeight());
        username.setPrefWidth(Constants.FIELD_WIDTH);
        username.setPrefHeight(Constants.FIELD_HEIGHT);
        username.setLayoutX(password.getLayoutX());
        username.setLayoutY(password.getLayoutY() - 2 * Constants.FIELD_HEIGHT);
        root.getChildren().addAll(backgroundView, foregroundView, login, create, exit, username, password);
    }

    public void shopMenu(ImageView[] heroes, ImageView[] mininos, ImageView[] spells, ImageView[] items,
                         ImageView back, ImageView next, ImageView prev) {
        root.getChildren().clear();
        Image slide = new Image("resources/ui/sliding_panel/sliding_panel_paging_button.png");
        Image arrow = new Image("resources/ui/sliding_panel/sliding_panel_paging_button_text.png");
        Image backArrow = new Image("resources/ui/button_back_corner.png");
        Image background = new Image("resources/scenes/load/scene_load_background@2x.jpg");
        ImageView backView = new ImageView(background);
        ImageView leftArrow = new ImageView(arrow);
        ImageView rightArrow = new ImageView(arrow);
        rightArrow.setRotate(180);
        backView.setFitHeight(Constants.WINDOW_HEIGHT);
        backView.setFitWidth(Constants.WINDOW_WIDTH);
        backView.setOpacity(0.5);
        next.setImage(slide);
        prev.setImage(slide);
        back.setImage(backArrow);
        horizontalList(Alignment.UP, 0, 0, back);
        horizontalList(Alignment.CENTRE, Constants.CENTRE_X, Constants.WINDOW_HEIGHT - 100, prev, next);
        horizontalList(Alignment.CENTRE, Constants.CENTRE_X, Constants.WINDOW_HEIGHT - 100, leftArrow, rightArrow);
        setImageSize(Constants.ARROW, leftArrow, rightArrow);
        root.getChildren().addAll(backView, next, prev, back, leftArrow, rightArrow);
        lightning(back);
        lightning(prev, leftArrow);
        lightning(next, rightArrow);
    }

    public void verticalList(Alignment alignment, double x, double y, Node... nodes) {
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] instanceof Button) {
                Button button = (Button) nodes[i];
                setButtonSize(button);
                if (alignment == Alignment.CENTRE)
                    button.setLayoutX(x - button.getPrefWidth() / 2);
            }
            if (nodes[i] instanceof ImageView) {
                ImageView imageView = (ImageView) nodes[i];
                if (alignment == Alignment.CENTRE)
                    imageView.setLayoutX(x - imageView.getFitWidth() / 2);
            }
            if (alignment == Alignment.LEFT)
                nodes[i].setLayoutX(x);
        }
        if (nodes[0] instanceof Button) {
            nodes[nodes.length / 2].setLayoutY(y +
                    ((nodes.length + 1) % 2) * ((Button) nodes[nodes.length / 2]).getPrefHeight() / 2);
            for (int i = nodes.length / 2 - 1; i >= 0; i--) {
                nodes[i].setLayoutY(nodes[i + 1].getLayoutY() - 2 * Constants.BUTTON_HEIGHT);
            }
            for (int i = nodes.length / 2 + 1; i < nodes.length; i++) {
                nodes[i].setLayoutY(nodes[i - 1].getLayoutY() + 2 * Constants.BUTTON_HEIGHT);
            }
        }
        if (nodes[0] instanceof ImageView) {
            nodes[nodes.length / 2].setLayoutY(y +
                    ((nodes.length + 1) % 2) * ((ImageView) nodes[nodes.length / 2]).getFitHeight() / 2);
            for (int i = nodes.length / 2 - 1; i >= 0; i--) {
                nodes[i].setLayoutY(nodes[i + 1].getLayoutY()
                        - ((ImageView) nodes[i]).getFitHeight() - ((ImageView) nodes[i + 1]).getFitHeight());
            }
            for (int i = nodes.length / 2 + 1; i < nodes.length; i++) {
                nodes[i].setLayoutY(nodes[i - 1].getLayoutY() +
                        ((ImageView) nodes[i]).getFitHeight() + ((ImageView) nodes[i - 1]).getFitHeight());
            }
        }

    }

    public void setButtonSize(Button... buttons) {
        for (Button button : buttons) {
            button.setPrefHeight(Constants.BUTTON_HEIGHT);
            if (button.getText().length() >= 8)
                button.setPrefWidth(2 * Constants.BUTTON_WIDTH);
            else
                button.setPrefWidth(Constants.BUTTON_WIDTH);
        }
    }

    public void setImageSize(double size, ImageView... imageViews) {
        double currentSize;
        for (ImageView imageView : imageViews) {
            currentSize = imageView.getFitHeight();
            imageView.setFitHeight(size);
            imageView.setFitWidth(size);
            imageView.setLayoutX(imageView.getLayoutX() + (currentSize - size) / 2);
            imageView.setLayoutY(imageView.getLayoutY() + (currentSize - size) / 2);
        }

    }

    public void horizontalList(Alignment alignment, double x, double y, Node... nodes) {
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] instanceof Button) {
                Button button = (Button) nodes[i];
                setButtonSize(button);
                if (alignment == Alignment.CENTRE)
                    button.setLayoutY(y - button.getPrefHeight() / 2);
            }
            if (nodes[i] instanceof ImageView) {
                ImageView imageView = (ImageView) nodes[i];
                imageView.setFitWidth(Constants.SLIDE);
                imageView.setFitHeight(Constants.SLIDE);
                if (alignment == Alignment.CENTRE)
                    imageView.setLayoutY(y - imageView.getFitHeight() / 2);
            }
            if (alignment == Alignment.UP || alignment == Alignment.DOWN)
                nodes[i].setLayoutY(y);
        }
        if (nodes[0] instanceof Button) {
            nodes[nodes.length / 2].setLayoutX(x +
                    ((nodes.length + 1) % 2) * ((Button) nodes[nodes.length / 2]).getPrefWidth() / 2);
            for (int i = nodes.length / 2 - 1; i >= 0; i--) {
                nodes[i].setLayoutX(nodes[i + 1].getLayoutX() - 2 * Constants.BUTTON_WIDTH);
            }
            for (int i = nodes.length / 2 + 1; i < nodes.length; i++) {
                nodes[i].setLayoutX(nodes[i - 1].getLayoutX() + 2 * Constants.BUTTON_WIDTH);
            }
        }
        if (nodes[0] instanceof ImageView) {
            nodes[nodes.length / 2].setLayoutX(x +
                    ((nodes.length + 1) % 2) * ((ImageView) nodes[nodes.length / 2]).getFitWidth() / 2);
            for (int i = nodes.length / 2 - 1; i >= 0; i--) {
                nodes[i].setLayoutX(nodes[i + 1].getLayoutX()
                        - ((ImageView) nodes[i]).getFitWidth() - ((ImageView) nodes[i + 1]).getFitWidth());
            }
            for (int i = nodes.length / 2 + 1; i < nodes.length; i++) {
                nodes[i].setLayoutX(nodes[i - 1].getLayoutX() +
                        ((ImageView) nodes[i]).getFitWidth() + ((ImageView) nodes[i - 1]).getFitWidth());
            }
        }
    }

    public void accountMenu(Button play, Button collection, Button shop, Button leaderboard, Button logout) {
        root.getChildren().clear();
        Image background = new Image("resources/scenes/frostfire/background.jpg");
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(Constants.WINDOW_WIDTH);
        backgroundView.setFitHeight(Constants.WINDOW_HEIGHT);
        Image foreground = new Image("resources/scenes/frostfire/foreground.png");
        ImageView foregroundView = getImageView(background, foreground);
        play.setText("Play");
        collection.setText("Collection");
        shop.setText("Shop");
        leaderboard.setText("Leaderboard");
        logout.setText("Logout");
        verticalList(Alignment.LEFT, 200, Constants.CENTRE_Y, play, collection, shop, leaderboard, logout);
        root.getChildren().addAll(backgroundView, foregroundView, play, collection, shop, leaderboard, logout);
    }

    private ImageView getImageView(Image background, Image foreground) {
        ImageView foregroundView = new ImageView(foreground);
        foregroundView.setFitWidth(foreground.getWidth() / background.getWidth() * Constants.WINDOW_WIDTH);
        foregroundView.setFitHeight(foreground.getWidth() / background.getWidth() * Constants.WINDOW_HEIGHT);
        foregroundView.setLayoutY(Constants.WINDOW_HEIGHT - foregroundView.getFitHeight());
        return foregroundView;
    }

    public void battleMode(Button first, Button second, Button third) {
        root.getChildren().clear();
        // Image background = new Image("resources/scenes/magaari_ember_highlands/magaari_ember_highlands_background@2x.jpg");
        Image background = new Image("resources/scenes/load/scene_load_background@2x.jpg");
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(Constants.WINDOW_WIDTH);
        backgroundView.setFitHeight(Constants.WINDOW_HEIGHT);
        Image firstImage = new Image("resources/challenges/gate_013@2x.jpg");
        ImageView firstImageView = new ImageView(firstImage);
        firstImageView.setFitWidth(425);
        firstImageView.setFitHeight(Constants.WINDOW_HEIGHT);
        firstImageView.setLayoutX(0);
        Image secondImage = new Image("resources/challenges/gate_005@2x.jpg");
        ImageView secondImageView = new ImageView(secondImage);
        secondImageView.setFitWidth(425);
        secondImageView.setFitHeight(Constants.WINDOW_HEIGHT);
        secondImageView.setLayoutX(425);
        Image thirdImage = new Image("resources/challenges/gate_004@2x.jpg");
        ImageView thirdImageView = new ImageView(thirdImage);
        thirdImageView.setFitWidth(425);
        thirdImageView.setFitHeight(Constants.WINDOW_HEIGHT);
        thirdImageView.setLayoutX(850);
        lightning(firstImageView);
        lightning(secondImageView);
        lightning(thirdImageView);
        buttonSettings(first, 40, "-fx-background-color: #091841; ", 209, 188, 208, "KILL ENEMY HERO", 10, 50);
        buttonSettings(second, 37, "-fx-background-color: #091841; ", 209, 188, 208, "COLLECTING FLAGS", 430, 50);
        buttonSettings(third, 37, "-fx-background-color: #091841; ", 209, 188, 208, "HOLD SPECIAL FLAG", 855, 50);
        root.getChildren().addAll(backgroundView, firstImageView, secondImageView, thirdImageView, first, second, third);
    }

    private void buttonSettings(Button first, int font, String s, int a, int b, int c, String t, int x, int y) {
        first.relocate(x, y);
        first.setText(t);
        first.setFont(Font.font(font));
        first.setStyle(s);
        first.setTextFill(Color.rgb(a, b, c));
    }

    public void gameTypeMenu(Button single, Button multi) {
        root.getChildren().clear();
        Image background = new Image("resources/resources/scenes/vetruvian/bg@2x.jpg");
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(Constants.WINDOW_WIDTH);
        backgroundView.setFitHeight(Constants.WINDOW_HEIGHT);
        Image singleP = new Image("resources/resources/crests/crest_f1@2x.png");
        ImageView singlePview = new ImageView(singleP);
        singlePview.setFitWidth(Constants.SINGLE_PLAYER_HEIGHT);
        singlePview.setFitHeight(Constants.SINGLE_PLAYER_HEIGHT);
        singlePview.setLayoutY(200);
        singlePview.setLayoutX(Constants.SINGLE_PLAYER_HEIGHT);
        Image singleM = new Image("resources/resources/crests/crest_f2@2x.png");
        ImageView singleMview = new ImageView(singleM);
        singleMview.setFitWidth(Constants.SINGLE_PLAYER_HEIGHT);
        singleMview.setFitHeight(Constants.SINGLE_PLAYER_HEIGHT);
        singleMview.setLayoutY(200);
        singleMview.setLayoutX(700);
        single.setText("SINGLE PLAYER");
        multi.setText("MULTI PLAYER");
        single.setTextFill(Color.rgb(255, 255, 255));
        multi.setTextFill(Color.rgb(255, 255, 255));
        single.relocate(400, 500);
        multi.relocate(800, 500);
        single.setStyle("-fx-background-color: #111143; ");
        multi.setStyle("-fx-background-color: #091841; ");
        lightning(singlePview);
        lightning(singleMview);
        root.getChildren().addAll(backgroundView, singlePview, singleMview, single, multi);

    }

    public void collectionMenu(Button createDeck, Button exit, TextField name) {
        root.getChildren().clear();
        createDeck.setText("Create Deck");
        Image background = new Image("resources/scenes/load/scene_load_background.jpg");
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(Constants.WINDOW_WIDTH);
        backgroundView.setFitHeight(Constants.WINDOW_HEIGHT);
        verticalList(Alignment.CENTRE, Constants.CENTRE_X, Constants.CENTRE_Y, createDeck, exit);
        name.setPrefWidth(Constants.FIELD_WIDTH);
        name.setPrefHeight(Constants.FIELD_HEIGHT);
        name.setLayoutX(createDeck.getLayoutX());
        name.setLayoutY(createDeck.getLayoutY() - Constants.FIELD_HEIGHT - Constants.BUTTON_HEIGHT);
        root.getChildren().addAll(backgroundView, createDeck, exit, name);
    }

    private void lightning(ImageView ... imageViews) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        Glow glow = new Glow();
        glow.setLevel(0.9);
        for (ImageView singlePview:
             imageViews) {
            singlePview.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {

                singlePview.setEffect(colorAdjust);

            });
            singlePview.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
                singlePview.setEffect(null);
            });
        }
    }
    public void setCardImage(String name) {
        Image image = new Image("resources/generals/general_f4.jpg");
        switch (name) {
            case "WHITE_DIV":
                image = new Image("resources/generals/general_f6third.jpg");
                break;
            case "SIMORGH":
                image = new Image("resources/generals/general_f5alt.jpg");
                break;
            case "SEVEN_HEADED_DRAGON":
                image = new Image("resources/generals/general_f5third.jpg");
                break;
            case "RAKHSH":
                image = new Image("resources/generals/general_f3third.jpg");
                break;
            case "ZAHAK":
                image = new Image("resources/generals/general_f2.jpg");
                break;
            case "KAVEH":
                image = new Image("resources/generals/general_f3.jpg");
                break;
            case "ARASH":
                image = new Image("resources/generals/general_f3alt.jpg");
                break;
            case "AFSANEH":
                image = new Image("resources/generals/general_f4.jpg");
                break;
            case "ESFANDIAR":
                image = new Image("resources/generals/general_f6.jpg");
                break;
            case "ROSTAM":
                image = new Image("resources/generals/general_f1.jpg");
                break;
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(Constants.CARD_WIDTH);
        imageView.setFitHeight(Constants.CARD_HEIGHT);
    }

}



