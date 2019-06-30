package View;

import Model.*;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import Model.Menu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.ImageCursor;
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
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class View {
    private transient AnchorPane root = new AnchorPane();
    private transient Scene scene = new Scene(root, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
    private transient Menu menu = Menu.getInstance();
    private static final View view = new View();
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";
    private transient Image cursor = new Image("ui/mouse_auto.png");
    private transient Image battleCursor = new Image("ui/mouse_attack.png");


    private View() {

    }

    public Scene getScene() {
        Image icon = new Image("booster_pack_opening/booster_orb.png");

        scene.setCursor(new ImageCursor(cursor, Constants.CURSOR_LENGTH, Constants.CURSOR_LENGTH));
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

    public void graveYardMenu(Card[][] cards, AnchorPane next, int turn, AnchorPane prev, AnchorPane back, int page) {
        root.getChildren().clear();
        ImageView backView = new ImageView(new Image("scenes/shimzar/bg@2x.jpg"));
        scrollPane(backView, next, prev, back);
        root.getChildren().addAll(backView, next, prev, back);
        showCards((ArrayList<Card>) Arrays.asList(cards[turn % 2]), new ArrayList<>(), new Label(), page);
    }


    public void selectUserMenu(ArrayList<Account> accounts, Label label, TextField textField) {
        root.getChildren().clear();
        Image background = new Image("scenes/shimzar/bg@2x.jpg");
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

    public void battleMenu(Account[] accounts) {
        root.getChildren().clear();
        Image background = new Image("maps/abyssian/background@2x.jpg");
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(Constants.WINDOW_WIDTH);
        backgroundView.setFitHeight(Constants.WINDOW_HEIGHT);
        Image foreground = new Image("maps/abyssian/midground@2x.png");
        ImageView foregroundView = getImageView(background, foreground);
        Polygon[] polygon = new Polygon[45];
        battleField(polygon);
        root.getChildren().addAll(backgroundView, foregroundView);
        for (int i = 0; i < 45; i++) {
            root.getChildren().add(polygon[i]);
        }
        Image firstHero, secondHero, firstHeroGif, secondHeroGif;
        firstHero = getImage(accounts[0]);
        secondHero = getImage(accounts[1]);
        ImageView firstHeroView = new ImageView(firstHero);
        ImageView secondHeroView = new ImageView(secondHero);
        bossImageSettings(firstHeroView, secondHeroView);
        firstHeroGif = getImageGif(accounts[0]);
        secondHeroGif = getImageGif(accounts[1]);
        ImageView imageView2 = new ImageView(secondHeroGif);
        ImageView imageView1 = new ImageView(firstHeroGif);
        imageView2.relocate((polygon[26].getPoints().get(0) + polygon[26].getPoints().get(2)) / 2 - 55, (polygon[26].getPoints().get(1) + polygon[26].getPoints().get(5)) / 2 - 105);
        imageView2.setScaleX(-1);
        imageView1.relocate((polygon[18].getPoints().get(0) + polygon[18].getPoints().get(2)) / 2 - 60, (polygon[18].getPoints().get(1) + polygon[18].getPoints().get(5)) / 2 - 105);
        lightning(imageView1, imageView2);
        for (int i = 0; i < polygon[26].getPoints().size(); i++) {
            System.out.println(polygon[26].getPoints().get(i));
        }
        root.getChildren().addAll(firstHeroView, secondHeroView, imageView1, imageView2);

    }

    private Image getImageGif(Account account) {
        Image firstHero = null;
        switch (account.getCollection().getMainDeck().getHero().getName()) {
            case "WHITE_DIV":
                firstHero = new Image("gifs/Abomination_idle.gif");
                break;
            case "ZAHAK":
                firstHero = new Image("gifs/Abomination_idle.gif");
                break;
            case "ARASH":
                firstHero = new Image("resources/gifs/f6_altgeneraltier2_idle.gif");
                break;
            case "SIMORGH":
                firstHero = new Image("gifs/f4_altgeneraltier2_idle.gif");
                break;
            case "SEVEN_HEADED_DRAGON":
                firstHero = new Image("gifs/f5_altgeneraltier2_idle.gif");
                break;
            case "RAKHSH":
                firstHero = new Image("gifs/f6_altgeneraltier2_idle.gif");
                break;
            case "KAVEH":
                firstHero = new Image("gifs/boss_cindera_idle.gif");
                break;
            case "AFSANEH":
                firstHero = new Image("gifs/f6_altgeneraltier2_idle.gif");
                break;
            case "ESFANDIAR":
                firstHero = new Image("gifs/Brome Warcrest_idle.gif");
                break;
            case "ROSTAM":
                firstHero = new Image("gifs/f1_tier2general_idle.gif");
                break;
        }
        return firstHero;
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
                firstHero = new Image("boss_battles/boss_shinkage_zendo_portrait_image_hex@2x.png");
                break;
            case "ZAHAK":
                firstHero = new Image("boss_battles/boss_calibero_portrait_image_hex@2x.png");
                break;
            case "ARASH":
                firstHero = new Image("boss_battles/boss_boreal_juggernaut_portrait_image_hex@2x.png");
                break;
            case "SIMORGH":
                firstHero = new Image("boss_battles/boss_shinkage_zendo_portrait_image_hex@2x.png");
                break;
            case "SEVEN_HEADED_DRAGON":
                firstHero = new Image("boss_battles/boss_crystal_portrait_hex.png");
                break;
            case "RAKHSH":
                firstHero = new Image("boss_battles/boss_wraith_portrait_hex@2x.png");
                break;
            case "KAVEH":
                firstHero = new Image("boss_battles/boss_vampire_portrait_hex@2x.png");
                break;
            case "AFSANEH":
                firstHero = new Image("boss_battles/boss_spelleater_portrait_hex@2x.png");
                break;
            case "ESFANDIAR":
                firstHero = new Image("boss_battles/boss_skurge_portrait_hex@2x.png");
                break;
            case "ROSTAM":
                firstHero = new Image("boss_battles/boss_shinkage_zendo_portrait_image_hex@2x.png");
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
        polygon1.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> polygon1.setEffect(colorAdjust));
        polygon1.addEventFilter(MouseEvent.MOUSE_EXITED, e -> polygon1.setEffect(null));
    }

    public void mainMenu(AnchorPane login, AnchorPane create, AnchorPane exit, TextField username, PasswordField password) {
        Image background = new Image("scenes/obsidian_woods/obsidian_woods_background.jpg");
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(Constants.WINDOW_WIDTH);
        backgroundView.setFitHeight(Constants.WINDOW_HEIGHT);
        Image foreground = new Image("scenes/obsidian_woods/obsidian_woods_cliff.png");
        ImageView foregroundView = getImageView(background, foreground);
        ImageView buttonImage = new ImageView(new Image("ui/button_primary_middle_glow@2x.png"));
        buttonImage.setFitWidth(Constants.PRIMITIVE_WIDTH);
        buttonImage.setFitHeight(Constants.PRIMITIVE_HEIGHT);
        login.getChildren().addAll(new ImageButton(new ImageView(buttonImage.getImage()), buttonImage.getFitWidth(),
                buttonImage.getFitHeight(), "Login", Constants.FONT_SIZE, Color.WHEAT).getPane().getChildren());
        create.getChildren().addAll(new ImageButton(new ImageView(buttonImage.getImage()), buttonImage.getFitWidth(),
                buttonImage.getFitHeight(), "Create", Constants.FONT_SIZE, Color.WHEAT).getPane().getChildren());
        exit.getChildren().addAll(new ImageButton(new ImageView(buttonImage.getImage()), buttonImage.getFitWidth(),
                buttonImage.getFitHeight(), "Exit", Constants.FONT_SIZE, Color.WHEAT).getPane().getChildren());
        verticalList(Alignment.CENTRE, Constants.MAIN_MENU_X, Constants.CENTRE_Y, buttonImage.getFitWidth(),
                buttonImage.getFitHeight(), login, create, exit);
        password.setPrefWidth(Constants.FIELD_WIDTH);
        password.setPrefHeight(Constants.FIELD_HEIGHT);
        password.setLayoutX(Constants.CENTRE_X - password.getPrefWidth() / 2);
        password.setLayoutY(login.getLayoutY() - 2 * password.getPrefHeight());
        username.setPrefWidth(Constants.FIELD_WIDTH);
        username.setPrefHeight(Constants.FIELD_HEIGHT);
        username.setLayoutX(password.getLayoutX());
        username.setLayoutY(password.getLayoutY() - 2 * Constants.FIELD_HEIGHT);
        lightning(login, create, exit);
        root.getChildren().addAll(backgroundView, foregroundView, login, create, exit, username, password);
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
            if (nodes[i] instanceof TextField) {
                TextField field = (TextField) nodes[i];
                field.setPrefWidth(Constants.FIELD_WIDTH);
                field.setPrefHeight(Constants.FIELD_HEIGHT);
                if (alignment == Alignment.CENTRE)
                    field.setLayoutY(y - field.getPrefHeight() / 2);
            }
            if (nodes[i] instanceof ComboBox) {
                ComboBox box = (ComboBox) nodes[i];
                box.setPrefWidth(Constants.SLIDE);
                box.setPrefWidth(Constants.SLIDE);
                if (alignment == Alignment.CENTRE)
                    box.setLayoutY(y - box.getPrefWidth() / 2);
            }
            if (nodes[i] instanceof Label) {
                Label label = (Label) nodes[i];
                label.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, Constants.FONT_SIZE));
                label.setTextFill(Color.NAVY);
                if (alignment == Alignment.CENTRE)
                    label.setLayoutY(y - 2 * Constants.LABEL_HEIGHT);
            }
            if (alignment == Alignment.UP || alignment == Alignment.DOWN) {
                nodes[i].setLayoutY(y);
                if (nodes[i] instanceof Label) {
                    nodes[i].setLayoutY(y - Constants.LABEL_HEIGHT);
                }
            }
        }
        if (nodes[0] instanceof TextField) {
            nodes[nodes.length / 2].setLayoutX(x +
                    ((nodes.length + 1) % 2) * ((TextField) nodes[nodes.length / 2]).getPrefWidth() / 2);
            for (int i = nodes.length / 2 - 1; i >= 0; i--) {
                nodes[i].setLayoutX(nodes[i + 1].getLayoutX() - 2 * Constants.FIELD_WIDTH);
            }
            for (int i = nodes.length / 2 + 1; i < nodes.length; i++) {
                nodes[i].setLayoutX(nodes[i - 1].getLayoutX() + 2 * Constants.FIELD_WIDTH);
            }
        }
        if (nodes[0] instanceof ComboBox) {
            nodes[nodes.length / 2].setLayoutX(x +
                    ((nodes.length + 1) % 2) * ((ComboBox) nodes[nodes.length / 2]).getPrefWidth() / 2);
            for (int i = nodes.length / 2 - 1; i >= 0; i--) {
                nodes[i].setLayoutX(nodes[i + 1].getLayoutX() - 2 * Constants.COMBO_WIDTH);
            }
            for (int i = nodes.length / 2 + 1; i < nodes.length; i++) {
                nodes[i].setLayoutX(nodes[i + 1].getLayoutX() - 2 * Constants.COMBO_WIDTH);
            }
        }
        if (nodes[0] instanceof Label) {
            nodes[nodes.length / 2].setLayoutX(x);
            for (int i = nodes.length / 2 - 1; i >= 0; i--) {
                nodes[i].setLayoutX(nodes[i + 1].getLayoutX() - 2 * Constants.LABEL_WIDTH);
            }
            for (int i = nodes.length / 2 + 1; i < nodes.length; i++) {
                nodes[i].setLayoutX(nodes[i - 1].getLayoutX() + 2 * Constants.LABEL_WIDTH);
            }
        }
    }

    public void horizantalList(Alignment alignment, double x, double y, AnchorPane... anchorPanes) {
        for (int i = 0; i < anchorPanes.length; i++) {
            if (alignment == Alignment.CENTRE)
                anchorPanes[i].setLayoutY(y);
            if (alignment == Alignment.UP || alignment == Alignment.DOWN) {
                anchorPanes[i].setLayoutY(y + anchorPanes[i].getPrefHeight() / 2);
            }
        }
        anchorPanes[anchorPanes.length / 2].setLayoutX(x + anchorPanes[anchorPanes.length / 2].getPrefWidth() / 2 +
                ((anchorPanes.length + 1) % 2) * (anchorPanes[anchorPanes.length / 2]).getPrefWidth() / 2);
        for (int i = anchorPanes.length / 2 - 1; i >= 0; i--) {
            anchorPanes[i].setLayoutX(anchorPanes[i + 1].getLayoutX() - 2 * anchorPanes[i].getPrefWidth());
        }
        for (int i = anchorPanes.length / 2 + 1; i < anchorPanes.length; i++) {
            anchorPanes[i].setLayoutX(anchorPanes[i - 1].getLayoutX() + 2 * anchorPanes[i].getPrefWidth());
        }
    }

    public void verticalList(Alignment alignment, double x, double y, double width, double height,
                             AnchorPane... anchorPanes) {
        for (int i = 0; i < anchorPanes.length; i++) {
            anchorPanes[i].setPrefWidth(width);
            anchorPanes[i].setPrefHeight(height);
            if (alignment == Alignment.CENTRE)
                anchorPanes[i].setLayoutX(x);
            if (alignment == Alignment.RIGHT || alignment == Alignment.LEFT) {
                anchorPanes[i].setLayoutX(x + anchorPanes[i].getPrefWidth() / 2);
            }
        }
        anchorPanes[anchorPanes.length / 2].setLayoutY(y + anchorPanes[anchorPanes.length / 2].getPrefHeight() / 2 +
                ((anchorPanes.length + 1) % 2) * (anchorPanes[anchorPanes.length / 2]).getPrefHeight() / 2);
        for (int i = anchorPanes.length / 2 - 1; i >= 0; i--) {
            anchorPanes[i].setLayoutY(anchorPanes[i + 1].getLayoutY() - 2 * anchorPanes[i].getPrefHeight());
        }
        for (int i = anchorPanes.length / 2 + 1; i < anchorPanes.length; i++) {
            anchorPanes[i].setLayoutY(anchorPanes[i - 1].getLayoutY() + 2 * anchorPanes[i].getPrefHeight());
        }
    }

    private void setPaneSize(double width, double height, AnchorPane... panes) {
        for (AnchorPane pane : panes) {
            pane.setPrefWidth(width);
            pane.setPrefHeight(height);
        }
    }

    public void accountMenu(AnchorPane play, AnchorPane collection, AnchorPane shop, AnchorPane leaderboard,
                            AnchorPane logout, AnchorPane customCard, AnchorPane customBuff, AnchorPane save) {
        root.getChildren().clear();
        Image background = new Image("scenes/frostfire/background.jpg");
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(Constants.WINDOW_WIDTH);
        backgroundView.setFitHeight(Constants.WINDOW_HEIGHT);
        Image foreground = new Image("scenes/frostfire/foreground.png");
        ImageView foregroundView = getImageView(background, foreground);
        ImageView buttonImage = new ImageView(new Image("ui/button_primary_middle_glow@2x.png"));
        buttonImage.setFitWidth(Constants.PRIMITIVE_WIDTH);
        buttonImage.setFitHeight(Constants.PRIMITIVE_HEIGHT);
        play.getChildren().addAll(new ImageButton(new ImageView(buttonImage.getImage()), buttonImage.getFitWidth(),
                buttonImage.getFitHeight(), "Play", Constants.FONT_SIZE, Color.WHEAT).getPane().getChildren());
        collection.getChildren().addAll(new ImageButton(new ImageView(buttonImage.getImage()), buttonImage.getFitWidth(),
                buttonImage.getFitHeight(), "Collection", Constants.FONT_SIZE, Color.WHEAT).getPane().getChildren());
        shop.getChildren().addAll(new ImageButton(new ImageView(buttonImage.getImage()), buttonImage.getFitWidth(),
                buttonImage.getFitHeight(), "Shop", Constants.FONT_SIZE, Color.WHEAT).getPane().getChildren());
        leaderboard.getChildren().addAll(new ImageButton(new ImageView(buttonImage.getImage()), buttonImage.getFitWidth(),
                buttonImage.getFitHeight(), "LeaderBoard", Constants.FONT_SIZE, Color.WHEAT).getPane().getChildren());
        save.getChildren().addAll(new ImageButton(new ImageView(buttonImage.getImage()), buttonImage.getFitWidth(),
                buttonImage.getFitHeight(), "Save", Constants.FONT_SIZE, Color.WHEAT).getPane().getChildren());
        logout.getChildren().addAll(new ImageButton(new ImageView(buttonImage.getImage()), buttonImage.getFitWidth(),
                buttonImage.getFitHeight(), "Logout", Constants.FONT_SIZE, Color.WHEAT).getPane().getChildren());
        customCard.getChildren().addAll(new ImageButton(new ImageView(buttonImage.getImage()), buttonImage.getFitWidth(),
                buttonImage.getFitHeight(), "Custom Card", Constants.FONT_SIZE, Color.WHEAT).getPane().getChildren());
        customBuff.getChildren().addAll(new ImageButton(new ImageView(buttonImage.getImage()), buttonImage.getFitWidth(),
                buttonImage.getFitHeight(), "Custom Buff", Constants.FONT_SIZE, Color.WHEAT).getPane().getChildren());
        verticalList(Alignment.CENTRE, Constants.ACCOUNT_MENU_X, Constants.CENTRE_Y, buttonImage.getFitWidth(),
                buttonImage.getFitHeight(), play, collection, shop, customCard, customBuff, leaderboard, save, logout);
        verticalList(Alignment.LEFT, 200, Constants.CENTRE_Y, play, collection, shop, customCard,
                customBuff, leaderboard, save, logout);
        lightning(play, collection, shop, customCard, customBuff, leaderboard, save, logout);
        root.getChildren().addAll(backgroundView, foregroundView, play, collection, shop, customCard, customBuff,
                leaderboard, save, logout);
    }

    public void customCardMenu(AnchorPane back, AnchorPane next, AnchorPane prev, AnchorPane detail,
                               ComboBox<String> type, TextField name, TextField price, TextField mana) {
        root.getChildren().clear();
        ImageView backView = new ImageView(new Image("scenes/vetruvian/bg@2x.jpg"));
        ImageView detailView = new ImageView(new Image("ui/button_icon_middle_glow@2x.png"));
        detail.getChildren().addAll(new ImageButton(detailView, Constants.SELL_WIDTH, Constants.SELL_HEIGHT, "DETAIL",
                Constants.SELL_TEXT_SIZE, Color.NAVY).getPane().getChildren());
        type.getItems().clear();
        type.getItems().addAll("Hero", "Minion", "Spell");
        type.setEditable(false);
        Label nameLabel = new Label("Name");
        Label typeLabel = new Label("Type");
        Label priceLabel = new Label("Price");
        Label manaLabel = new Label("Mana");
        type.relocate(Constants.CUSTOM_CARD_X * 1.1, Constants.CUSTOM_CARD_Y);
        horizontalList(Alignment.UP, 3 * Constants.CUSTOM_CARD_X, Constants.CUSTOM_CARD_Y, name, price, mana);
        horizontalList(Alignment.UP, 3 * Constants.CUSTOM_CARD_X, Constants.CUSTOM_CARD_Y, nameLabel, typeLabel,
                priceLabel, manaLabel);
        verticalList(Alignment.CENTRE, Constants.CENTRE_X, Constants.CENTRE_Y * 0.4,
                detailView.getFitWidth(), detailView.getFitHeight(), detail);
        scrollPane(backView, next, prev, back);
        lightning(detail);
        root.getChildren().addAll(backView, next, prev, back, type, name, price, mana, nameLabel, typeLabel,
                priceLabel, manaLabel, detail);
    }

    public void customUnitMenu(String type, ComboBox<String> attackType, TextField ap, TextField hp, TextField range,
                               ComboBox<String> target) {
        root.getChildren().remove(root.getChildren().size() - 1);
    }

    public void customBuffMenu(AnchorPane back, AnchorPane create, TextField name, ComboBox<String> type,
                               TextField power, TextField turn, ComboBox<String> side, ComboBox<String> attribute) {
        root.getChildren().clear();
        ImageView backView = new ImageView(new Image("scenes/vetruvian/bg@2x.jpg"));
        ImageView createView = new ImageView(new Image("ui/button_icon_middle_glow@2x.png"));
        create.getChildren().addAll(new ImageButton(createView, Constants.SELL_WIDTH, Constants.SELL_HEIGHT, "CREATE",
                Constants.SELL_TEXT_SIZE, Color.NAVY).getPane().getChildren());
        type.getItems().clear();
        type.getItems().addAll("Holy", "Poison", "Power", "Weakness", "Stun", "Disarm");
        type.setEditable(false);
        Label nameLabel = new Label("Name");
        Label typeLabel = new Label("Type");
        Label powerLabel = new Label("Power");
        Label turnLabel = new Label("Turn");
        Label sideLabel = new Label("Side");
        Label attributeLabel = new Label("Attribute");
        type.relocate(Constants.CUSTOM_CARD_X * 1.1, Constants.CUSTOM_CARD_Y);
        horizontalList(Alignment.UP, 3 * Constants.CUSTOM_CARD_X, Constants.CUSTOM_CARD_Y, name, type, power, turn);
        horizontalList(Alignment.UP, 3 * Constants.CUSTOM_CARD_X, Constants.CUSTOM_CARD_Y, nameLabel, typeLabel,
                powerLabel, turnLabel);
        side.getItems().clear();
        side.getItems().addAll("Comrade", "Enemy");
        side.setEditable(false);
        attribute.getItems().clear();
        attribute.getItems().addAll("Health", "Hit", "Attack", "Counter");
        attribute.setEditable(false);
        side.relocate(Constants.CUSTOM_CARD_X * 2, Constants.CUSTOM_CARD_Y * 3);
        attribute.relocate(Constants.CUSTOM_CARD_X * 2 + 2 * Constants.LABEL_WIDTH, Constants.CUSTOM_CARD_Y * 3);
        horizontalList(Alignment.UP, 2.9 * Constants.CUSTOM_CARD_X, Constants.CUSTOM_CARD_Y * 3, sideLabel, attributeLabel);
        verticalList(Alignment.CENTRE, Constants.CENTRE_X, Constants.CENTRE_Y,
                createView.getFitWidth(), createView.getFitHeight(), create);
        lightning(create);
        scrollPane(backView, new AnchorPane(), new AnchorPane(), back);
        root.getChildren().addAll(backView, back, type, name, power, turn, nameLabel, typeLabel, powerLabel, turnLabel,
                side, attribute, sideLabel, attributeLabel, create);
    }

    public void customSpellMenu(ComboBox<String> target) {

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
        // Image background = new Image("scenes/magaari_ember_highlands/magaari_ember_highlands_background@2x.jpg");
        Image background = new Image("scenes/load/scene_load_background@2x.jpg");
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(Constants.WINDOW_WIDTH);
        backgroundView.setFitHeight(Constants.WINDOW_HEIGHT);
        Image firstImage = new Image("challenges/gate_013@2x.jpg");
        ImageView firstImageView = new ImageView(firstImage);
        firstImageView.setFitWidth(425);
        firstImageView.setFitHeight(Constants.WINDOW_HEIGHT);
        firstImageView.setLayoutX(0);
        Image secondImage = new Image("challenges/gate_005@2x.jpg");
        ImageView secondImageView = new ImageView(secondImage);
        secondImageView.setFitWidth(425);
        secondImageView.setFitHeight(Constants.WINDOW_HEIGHT);
        secondImageView.setLayoutX(425);
        Image thirdImage = new Image("challenges/gate_004@2x.jpg");
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
        Image background = new Image("scenes/vetruvian/bg@2x.jpg");
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(Constants.WINDOW_WIDTH);
        backgroundView.setFitHeight(Constants.WINDOW_HEIGHT);
        Image singleP = new Image("crests/crest_f1@2x.png");
        ImageView singlePview = new ImageView(singleP);
        singlePview.setFitWidth(Constants.SINGLE_PLAYER_HEIGHT);
        singlePview.setFitHeight(Constants.SINGLE_PLAYER_HEIGHT);
        singlePview.setLayoutY(200);
        singlePview.setLayoutX(Constants.SINGLE_PLAYER_HEIGHT);
        Image singleM = new Image("crests/crest_f2@2x.png");
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

    public void shopMenu(Account account, boolean mode, TextField object, ArrayList<Card> cards, ArrayList<Item> items,
                         AnchorPane back, AnchorPane next, AnchorPane prev, AnchorPane sell, AnchorPane buy, int page) {
        root.getChildren().clear();
        ImageView backView = new ImageView(new Image("scenes/load/scene_load_background.jpg"));
        ImageView buyView = new ImageView(new Image("ui/button_confirm_glow@2x.png"));
        ImageView sellView = new ImageView(new Image("ui/button_cancel_glow@2x.png"));
        Label modeLabel, budget;
        if (mode)
            modeLabel = new Label("Shop Objects");
        else
            modeLabel = new Label("Collection Objects");
        budget = new Label("Budget: " + account.getBudget());
        budget.setFont(Font.font(Constants.PAGE_TITLE_FONT, FontWeight.EXTRA_BOLD, Constants.PAGE_TITLE_SIZE));
        budget.setTextFill(Color.DARKGREEN);
        budget.relocate(Constants.SELL_X, 2 * Constants.SELL_HEIGHT);
        object.setPrefWidth(Constants.FIELD_WIDTH);
        object.setPrefHeight(Constants.FIELD_HEIGHT);
        object.relocate(Constants.SELL_X + 0.1 * Constants.SELL_WIDTH + Constants.SELL_WIDTH / 2, 200);
        buy.getChildren().addAll(new ImageButton(buyView, Constants.SELL_WIDTH, Constants.SELL_HEIGHT, "BUY",
                Constants.SELL_TEXT_SIZE, Color.NAVY).getPane().getChildren());
        sell.getChildren().addAll(new ImageButton(sellView, Constants.SELL_WIDTH, Constants.SELL_HEIGHT, "SELL",
                Constants.SELL_TEXT_SIZE, Color.NAVY).getPane().getChildren());
        verticalList(Alignment.LEFT, Constants.SELL_PANE_X, Constants.CENTRE_Y,
                buyView.getFitWidth(), buyView.getFitHeight(), buy, sell);
        scrollPane(backView, next, prev, back);
        lightning(buy);
        lightning(sell);
        root.getChildren().addAll(backView, sell, buy, next, prev, back, object, modeLabel, budget);
        showCards(cards, items, modeLabel, page);
    }


    public void collectionMenu(String mode, TextField object, ArrayList<Card> cards, ArrayList<Item> items
            , AnchorPane createDeck, AnchorPane removeDeck, AnchorPane showDeck, AnchorPane back, AnchorPane collection
            , AnchorPane next, AnchorPane prev, AnchorPane mainDeck, AnchorPane setMainDeck, AnchorPane exportDeck
            , AnchorPane importDeck, int page) {
        root.getChildren().clear();
        Label modeLabel = new Label(mode + " Objects");
        ImageView backView = new ImageView(new Image("scenes/load/scene_load_background.jpg"));
        scrollPane(backView, next, prev, back);
        object.setPrefWidth(Constants.FIELD_WIDTH);
        object.setPrefHeight(Constants.FIELD_HEIGHT);
        object.relocate(Constants.COLLECTION_SEARCH_X, Constants.COLLECTION_SEARCH_Y);
        ImageView deckPane = new ImageView(new Image("card_backgrounds/deck_builder_prismatic_card_bg@2x.png"));
        createDeck.getChildren().addAll(new ImageButton(new ImageView(deckPane.getImage()), Constants.DECK_PANE_WIDTH,
                Constants.DECK_PANE_HEIGHT, "Create Deck", Constants.FONT_SIZE, Color.LIGHTBLUE)
                .getPane().getChildren());
        removeDeck.getChildren().addAll(new ImageButton(new ImageView(deckPane.getImage()), Constants.DECK_PANE_WIDTH,
                Constants.DECK_PANE_HEIGHT, "Remove Deck", Constants.FONT_SIZE, Color.LIGHTBLUE)
                .getPane().getChildren());
        mainDeck.getChildren().addAll(new ImageButton(new ImageView(deckPane.getImage()), Constants.DECK_PANE_WIDTH,
                Constants.DECK_PANE_HEIGHT, "Main Deck", Constants.FONT_SIZE, Color.LIGHTBLUE)
                .getPane().getChildren());
        setMainDeck.getChildren().addAll(new ImageButton(new ImageView(deckPane.getImage()), Constants.DECK_PANE_WIDTH,
                Constants.DECK_PANE_HEIGHT, "Set as MAIN", Constants.FONT_SIZE, Color.LIGHTBLUE)
                .getPane().getChildren());
        showDeck.getChildren().addAll(new ImageButton(new ImageView(deckPane.getImage()), Constants.DECK_PANE_WIDTH,
                Constants.DECK_PANE_HEIGHT, "Show Deck", Constants.FONT_SIZE, Color.LIGHTBLUE)
                .getPane().getChildren());
        collection.getChildren().addAll(new ImageButton(new ImageView(deckPane.getImage()), Constants.DECK_PANE_WIDTH,
                Constants.DECK_PANE_HEIGHT, "Collection", Constants.FONT_SIZE, Color.LIGHTBLUE)
                .getPane().getChildren());
        exportDeck.getChildren().addAll(new ImageButton(new ImageView(deckPane.getImage()), Constants.DECK_PANE_WIDTH,
                Constants.DECK_PANE_HEIGHT, "Export...", Constants.FONT_SIZE, Color.LIGHTBLUE)
                .getPane().getChildren());
        importDeck.getChildren().addAll(new ImageButton(new ImageView(deckPane.getImage()), Constants.DECK_PANE_WIDTH,
                Constants.DECK_PANE_HEIGHT, "Import...", Constants.FONT_SIZE, Color.LIGHTBLUE)
                .getPane().getChildren());
        verticalList(Alignment.LEFT, Constants.DECK_PANE_X, Constants.DECK_PANE_Y + Constants.DECK_PANE_HEIGHT
                , Constants.DECK_PANE_WIDTH, Constants.DECK_PANE_HEIGHT, showDeck, setMainDeck, mainDeck
                , createDeck, removeDeck, collection, importDeck, exportDeck);
        lightning(createDeck, removeDeck, mainDeck, setMainDeck, showDeck, collection, importDeck, exportDeck);
        root.getChildren().addAll(backView, modeLabel, next, prev, back, object, showDeck, setMainDeck, mainDeck,
                createDeck, removeDeck, collection, exportDeck, importDeck);
        showCards(cards, items, modeLabel, page);
    }

    private void scrollPane(ImageView backView, AnchorPane next, AnchorPane prev, AnchorPane back) {
        Image slide = new Image("ui/sliding_panel/sliding_panel_paging_button.png");
        Image arrow = new Image("ui/sliding_panel/sliding_panel_paging_button_text.png");
        ImageView leftArrow = new ImageView(), rightArrow = new ImageView();
        Image backArrow = new Image("ui/button_back_corner.png");
        leftArrow.setImage(arrow);
        rightArrow.setImage(arrow);
        rightArrow.setRotate(180);
        backView.setFitHeight(Constants.WINDOW_HEIGHT);
        backView.setFitWidth(Constants.WINDOW_WIDTH);
        backView.setOpacity(0.5);
        ImageView rightSlider = new ImageView(slide);
        ImageView leftSlider = new ImageView(slide);
        ImageView cornerImage = new ImageView(backArrow);
        setImageSize(Constants.SLIDE, cornerImage);
        setImageSize(Constants.SLIDE, rightSlider, leftSlider);
        setImageSize(Constants.ARROW, leftArrow, rightArrow);
        next.getChildren().addAll(rightSlider, rightArrow);
        prev.getChildren().addAll(leftSlider, leftArrow);
        back.getChildren().addAll(cornerImage);
        setPaneSize(rightSlider.getFitWidth(), rightSlider.getFitHeight(), next, prev);
        setPaneSize(cornerImage.getFitWidth(), cornerImage.getFitHeight(), back);
        horizantalList(Alignment.UP, 0, 0, back);
        horizantalList(Alignment.DOWN, Constants.SCROLLER_X, Constants.SCROLLER_Y, prev, next);
        lightning(back);
        lightning(prev);
        lightning(next);
    }

    private void showCards(ArrayList<Card> cards, ArrayList<Item> items, Label modeLabel, int page) {
        if (page <= (cards.size() + items.size() - 1) / Constants.CARD_PER_PAGE) {
            for (int i = 0; i < Constants.CARD_PER_COLUMN; i++) {
                for (int j = 0; j < Constants.CARD_PER_ROW; j++) {
                    try {
                        int index = page * Constants.CARD_PER_PAGE + i * Constants.CARD_PER_ROW + j;
                        AnchorPane anchorPane;
                        if (index < cards.size())
                            anchorPane = cards.get(index).getCardView().getPane();
                        else {
                            anchorPane = items.get(index - cards.size()).getCardView().getPane();
                        }
                        anchorPane.setLayoutX(Constants.CARD_X + j * (Constants.CARD_WIDTH + Constants.CARD_X_GAP));
                        anchorPane.setLayoutY(Constants.CARD_Y + i * (Constants.CARD_HEIGHT + Constants.CARD_Y_GAP));
                        lightning(anchorPane);
                        root.getChildren().add(anchorPane);
                    } catch (Exception e) {

                    }
                }
            }
        }
        modeLabel.setFont(Font.font(Constants.PAGE_TITLE_FONT, FontWeight.EXTRA_BOLD, Constants.PAGE_TITLE_SIZE));
        modeLabel.setTextFill(Color.NAVY);
        modeLabel.translateXProperty().bind(modeLabel.widthProperty().divide(2).negate());
        modeLabel.relocate(Constants.SCROLLER_X, Constants.PAGE_TITLE_Y);
    }

    private void stableLighning(Node... nodes) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.6);
        Glow glow = new Glow();
        glow.setLevel(0.9);
        for (int i = 0; i < nodes.length; i++) {
            int finalI = i;
//            nodes[i].addEventFilter(MouseEvent.MOUSE_ENTERED, e -> nodes[finalI].setEffect(colorAdjust));
            nodes[i].addEventFilter(MouseEvent.MOUSE_CLICKED, e -> nodes[finalI].setEffect(colorAdjust));
            nodes[i].addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                for (int j = 0; j < nodes.length; j++) {
                    if (finalI != j) {
                        int finalJ = j;
                        nodes[finalJ].addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, e -> nodes[finalJ].setEffect(null));
                    }
                }
            });
        }
    }

    private void lightning(Node... nodes) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        Glow glow = new Glow();
        glow.setLevel(0.9);
        for (Node singlePview : nodes) {
            singlePview.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> nodes[0].setEffect(colorAdjust));
            singlePview.removeEventFilter(MouseEvent.MOUSE_ENTERED, e -> nodes[0].setEffect(colorAdjust));
        }
    }

    private void lightning(AnchorPane... anchorPanes) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        Glow glow = new Glow();
        glow.setLevel(0.9);
        for (AnchorPane anchorPane : anchorPanes) {
            anchorPane.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> anchorPane.setEffect(colorAdjust));
            anchorPane.addEventFilter(MouseEvent.MOUSE_EXITED, e -> anchorPane.setEffect(null));
        }
    }

    public void setCardImage(String name) {
        Image image = new Image("generals/general_f4.jpg");
        switch (name) {
            case "WHITE_DIV":
                image = new Image("generals/general_f6third.jpg");
                break;
            case "SIMORGH":
                image = new Image("generals/general_f5alt.jpg");
                break;
            case "SEVEN_HEADED_DRAGON":
                image = new Image("generals/general_f5third.jpg");
                break;
            case "RAKHSH":
                image = new Image("generals/general_f3third.jpg");
                break;
            case "ZAHAK":
                image = new Image("generals/general_f2.jpg");
                break;
            case "KAVEH":
                image = new Image("generals/general_f3.jpg");
                break;
            case "ARASH":
                image = new Image("generals/general_f3alt.jpg");
                break;
            case "AFSANEH":
                image = new Image("generals/general_f4.jpg");
                break;
            case "ESFANDIAR":
                image = new Image("generals/general_f6.jpg");
                break;
            case "ROSTAM":
                image = new Image("generals/general_f1.jpg");
                break;
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(Constants.CARD_WIDTH);
        imageView.setFitHeight(Constants.CARD_HEIGHT);
    }

}



