package Controller;

import Model.*;
import Model.Menu;
import View.*;
import com.google.gson.Gson;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Controller {
    private transient View view = View.getInstance();
    private transient Game game = Game.getInstance();
    private transient Menu menu = Menu.getInstance();
    private transient Shop shop = Shop.getInstance();
    private Account account;
    private transient Battle battle = Battle.getInstance();
    private transient Button[] buttons = new Button[Buttons.values().length];
    private transient PasswordField passwordField = new PasswordField();
    private transient Label[] labels = new Label[Labels.values().length];
    private transient AnchorPane[] anchorPanes = new AnchorPane[Anchorpanes.values().length];
    private transient TextField[] fields = new TextField[Texts.values().length];
    private transient ImageView[] heroes = new ImageView[Constants.HEROES_COUNT];
    private transient javafx.scene.image.ImageView[] minions = new ImageView[Constants.MINIONS_COUNT];
    private transient ImageView[] spells = new ImageView[Constants.SPELLS_COUNT];
    private transient ImageView[] items = new ImageView[Constants.ITEMS_COUNT];
    private static final Controller controller = new Controller();
    private transient File file = new File("resources/music/music_mainmenu_lyonar.m4a");
    private transient Media media = new Media(file.toURI().toString());
    private transient MediaPlayer player = new MediaPlayer(media);
    private int collectionPage = 0, shopPage = 0, graveyardPage = 0;
    private ArrayList<Card> cardsInShop, cardsInCollection;
    private ArrayList<Item> itemsInShop, itemsInCollection;
    private boolean buyMode = true;
    private String deckName = "Collection";

    private Controller() {
        initializeGame();
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button();
        }
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new Label();
        }
        for (int i = 0; i < fields.length; i++) {
            fields[i] = new TextField();
        }
        for (int i = 0; i < anchorPanes.length; i++) {
            anchorPanes[i] = new AnchorPane();
        }
        menu.setStat(MenuStat.MAIN);
    }

    public static Controller getInstance() {
        return controller;
    }

    public void initializeGame() {
        try {
            game.initializeAccounts();
        } catch (IOException f) {
            System.out.println("Account initializing error!");
        }
        try {
            game.initializeHero();
        } catch (IOException f) {
            System.out.println("Hero initializing error!");
        }
        try {
            game.initializeMinion();
        } catch (IOException f) {
            System.out.println("Minion initializing error!");
        }
        try {
            game.initializeSpell();
        } catch (IOException f) {
            System.out.println("Spell initializing error!");
        }
        try {
            game.initializeItem();
        } catch (IOException f) {
            System.out.println("Item initializing error!");
        }
        game.setSrcs();
    }

    public void main() {

        player.stop();
        switch (menu.getStat()) {
            case MAIN:
                view.mainMenu(anchorPanes[Anchorpanes.LOGIN.ordinal()], anchorPanes[Anchorpanes.CREATE_ACCOUNT.ordinal()],
                        anchorPanes[Anchorpanes.EXIT.ordinal()], fields[Texts.USERNAME.ordinal()], passwordField);
                file = new File("resources/music/music_battlemap_vetruv.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                break;
            case ACCOUNT:
                view.accountMenu(anchorPanes[Anchorpanes.PLAY.ordinal()], anchorPanes[Anchorpanes.COLLECTION.ordinal()],
                        anchorPanes[Anchorpanes.SHOP.ordinal()], anchorPanes[Anchorpanes.MATCH_HISTORY.ordinal()],
                        anchorPanes[Anchorpanes.LEADER_BOARD.ordinal()],
                        anchorPanes[Anchorpanes.LOGOUT.ordinal()], anchorPanes[Anchorpanes.CUSTOM_CARD.ordinal()],
                        anchorPanes[Anchorpanes.CUSTOM_BUFF.ordinal()], anchorPanes[Anchorpanes.SAVE.ordinal()]);
                file = new File("resources/music/music_playmode.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                break;
            case SHOP:
                view.shopMenu(account, buyMode, fields[Texts.OBJECT.ordinal()], cardsInShop, itemsInShop,
                        anchorPanes[Anchorpanes.BACK.ordinal()], anchorPanes[Anchorpanes.NEXT.ordinal()],
                        anchorPanes[Anchorpanes.PREV.ordinal()], anchorPanes[Anchorpanes.SELL.ordinal()],
                        anchorPanes[Anchorpanes.BUY.ordinal()], shopPage);
                file = new File("resources/music/music_battlemap_morinkhur.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                handleInstances(cardsInShop, itemsInShop);
                break;
            case COLLECTION:
                view.collectionMenu(deckName, fields[Texts.OBJECT.ordinal()], cardsInCollection, itemsInCollection,
                        anchorPanes[Anchorpanes.CREATE.ordinal()], anchorPanes[Anchorpanes.REMOVE_DECK.ordinal()],
                        anchorPanes[Anchorpanes.SHOW_DECk.ordinal()], anchorPanes[Anchorpanes.BACK.ordinal()],
                        anchorPanes[Anchorpanes.WHOLE_COLLECTION.ordinal()], anchorPanes[Anchorpanes.NEXT.ordinal()],
                        anchorPanes[Anchorpanes.PREV.ordinal()], anchorPanes[Anchorpanes.MAIN_DECK.ordinal()],
                        anchorPanes[Anchorpanes.SET_MAIN_DECK.ordinal()], anchorPanes[Anchorpanes.EXPORT_DECK.ordinal()],
                        anchorPanes[Anchorpanes.IMPORT_DECK.ordinal()], collectionPage);
                file = new File("resources/music/music_battlemap_morinkhur.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                try {
                    handleCollection(cardsInCollection, itemsInCollection);
                } catch (Exception e) {

                }
                break;
            case CUSTOM_CARD:
                break;
            case CUSTOM_BUFF:
                break;
            case GAME_TYPE:
                view.gameTypeMenu(buttons[Buttons.SINGLE_PLAYER.ordinal()], buttons[Buttons.MULTI_PLAYER.ordinal()]);
                file = new File("resources/music/music_battlemap_firesofvictory.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                break;
            case BATTLE_MODE:
                view.battleMode(buttons[Buttons.KILL_ENEMY_HERO.ordinal()], buttons[Buttons.FLAG_COLLECTING.ordinal()],
                        buttons[Buttons.HOLD_FLAG.ordinal()]);
                file = new File("resources/music/music_battlemap_songhai.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                break;
            case BATTLE:
                view.battleMenu(battle.getAccounts());
                file = new File("resources/music/music_battlemap01.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                break;
            case SELECT_USER:
                view.selectUserMenu(game.getAccounts(), labels[Labels.STATUS.ordinal()], fields[Texts.USER_NAME.ordinal()]);
                file = new File("resources/music/music_battlemap_abyssian.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                break;
            case GRAVEYARD:
                view.graveYardMenu(battle.getGraveyard(), anchorPanes[Anchorpanes.NEXT.ordinal()], battle.getTurn(),
                        anchorPanes[Anchorpanes.PREV.ordinal()], anchorPanes[Anchorpanes.BACK.ordinal()], graveyardPage);
                break;
            case MATCH_HISTORY:
                Match match = new Match();
                match.setRival("FAKEMATCH");
                match.setResult(MatchResult.WON);
                match.setTime(LocalDateTime.now());
                Match match2 = new Match();
                match2.setRival("NO ONE");
                match2.setResult(MatchResult.TIE);
                match2.setTime(LocalDateTime.now());
                account.getMatchHistory().add(match);
                account.getMatchHistory().add(match2);
                view.showMatchHistory(account.getMatchHistory());
                break;

        }
        player.setAutoPlay(true);
        handleButtons();
        handleTextFields();
    }

    private void mainButtons() {
        anchorPanes[Anchorpanes.CREATE_ACCOUNT.ordinal()].setOnMouseClicked(event -> createAccount());
        anchorPanes[Anchorpanes.LOGIN.ordinal()].setOnMouseClicked(event -> login());
        anchorPanes[Anchorpanes.EXIT.ordinal()].setOnMouseClicked(event -> exit());
    }

    private void accountButtons() {
        anchorPanes[Anchorpanes.PLAY.ordinal()].setOnMouseClicked(event -> chooseBattleType());
        anchorPanes[Anchorpanes.LOGOUT.ordinal()].setOnMouseClicked(event -> logout());
        anchorPanes[Anchorpanes.LEADER_BOARD.ordinal()].setOnMouseClicked(event -> showLeaderBoard());
        anchorPanes[Anchorpanes.SHOP.ordinal()].setOnMouseClicked(event -> {
            cardsInShop = shop.getCards();
            itemsInShop = shop.getItems();
            menu.setStat(MenuStat.SHOP);
            main();
        });
        anchorPanes[Anchorpanes.COLLECTION.ordinal()].setOnMouseClicked(event -> {
            cardsInCollection = account.getCollection().getCards();
            itemsInCollection = account.getCollection().getItems();
            menu.setStat(MenuStat.COLLECTION);
            main();
        });
        anchorPanes[Anchorpanes.CUSTOM_CARD.ordinal()].setOnMouseClicked(event -> {
            menu.setStat(MenuStat.CUSTOM_CARD);
            main();
        });
        anchorPanes[Anchorpanes.CUSTOM_BUFF.ordinal()].setOnMouseClicked(event -> {
            menu.setStat(MenuStat.CUSTOM_BUFF);
            main();
        });
        anchorPanes[Anchorpanes.MATCH_HISTORY.ordinal()].setOnMouseClicked(event -> {
            menu.setStat(MenuStat.MATCH_HISTORY);
            main();
        });
        anchorPanes[Anchorpanes.SAVE.ordinal()].setOnMouseClicked(event -> {
            save();
            main();
        });
    }

    private void scrollerButtons() {
        anchorPanes[Anchorpanes.BACK.ordinal()].setOnMouseClicked(event -> exit());
        anchorPanes[Anchorpanes.PREV.ordinal()].setOnMouseClicked(event -> {
            if (menu.getStat() == MenuStat.COLLECTION && collectionPage > 0) {
                collectionPage--;
                main();
            }
            if (menu.getStat() == MenuStat.SHOP && shopPage > 0) {
                shopPage--;
                main();
            }
        });
        anchorPanes[Anchorpanes.NEXT.ordinal()].setOnMouseClicked(event -> {
            if (menu.getStat() == MenuStat.COLLECTION &&
                    collectionPage < (cardsInCollection.size() + itemsInCollection.size())
                            / Constants.CARD_PER_PAGE) {
                collectionPage++;
                main();
            }
            if (menu.getStat() == MenuStat.SHOP && shopPage < (cardsInShop.size() + itemsInShop.size())
                    / Constants.CARD_PER_PAGE) {
                shopPage++;
                main();
            }
        });
    }

    private void shopButtons() {
        anchorPanes[Anchorpanes.BUY.ordinal()].setOnMouseClicked(event -> {
            cardsInShop = shop.getCards();
            itemsInShop = shop.getItems();
            shopPage = 0;
            buyMode = true;
            main();
        });
        anchorPanes[Anchorpanes.SELL.ordinal()].setOnMouseClicked(event -> {
            cardsInShop = account.getCollection().getCards();
            itemsInShop = account.getCollection().getItems();
            shopPage = 0;
            buyMode = false;
            main();
        });
    }

    private void collectionButtons() {
        anchorPanes[Anchorpanes.SET_MAIN_DECK.ordinal()].setOnMouseClicked(event -> {
            deckLing(Constants.SELECT_MAIN_CONST);
            main();
        });
        anchorPanes[Anchorpanes.MAIN_DECK.ordinal()].setOnMouseClicked(event -> {
            deckName = account.getCollection().getMainDeck().getName();
            cardsInCollection = account.getCollection().getMainDeck().getCards();
            ArrayList<Item> itemList = new ArrayList<>();
            itemList.add(account.getCollection().findDeck(deckName).getItem());
            itemsInCollection = itemList;
            collectionPage = 0;
            main();
        });
        anchorPanes[Anchorpanes.WHOLE_COLLECTION.ordinal()].setOnMouseClicked(event -> {
            deckName = "Collection";
            cardsInCollection = account.getCollection().getCards();
            itemsInCollection = account.getCollection().getItems();
            main();
        });
        anchorPanes[Anchorpanes.SHOW_DECk.ordinal()].setOnMouseClicked(event -> {
            deckLing(Constants.SHOW_DECK_CONST);
            main();
        });
        anchorPanes[Anchorpanes.CREATE.ordinal()].setOnMouseClicked(event -> {
            createDeck();
            main();
        });
        anchorPanes[Anchorpanes.EXPORT_DECK.ordinal()].setOnMouseClicked(event -> {
            deckLing(Constants.EXPORT_DECK);
            main();
        });
        anchorPanes[Anchorpanes.IMPORT_DECK.ordinal()].setOnMouseClicked(event -> {
            importDeck();
            main();
        });
        anchorPanes[Anchorpanes.REMOVE_DECK.ordinal()].setOnMouseClicked(event -> {
            deckLing(Constants.REMOVE_DECK);
            main();
        });
    }

    private void handleButtons() {
        mainButtons();
        accountButtons();
        scrollerButtons();
        shopButtons();
        collectionButtons();
        anchorPanes[Anchorpanes.GRAVEYARD.ordinal()].setOnMouseClicked(event -> {
            menu.setStat(MenuStat.GRAVEYARD);
            main();
        });
        buttons[Buttons.SINGLE_PLAYER.ordinal()].setOnMouseClicked(event -> setBattleModeSingle());
        buttons[Buttons.MULTI_PLAYER.ordinal()].setOnMouseClicked(event -> setBattleModeMulti());
        buttons[Buttons.KILL_ENEMY_HERO.ordinal()].setOnMouseClicked(event -> setBattleMode(1));
        buttons[Buttons.FLAG_COLLECTING.ordinal()].setOnMouseClicked(event -> setBattleMode(2));
        buttons[Buttons.HOLD_FLAG.ordinal()].setOnMouseClicked(event -> setBattleMode(3));
    }

    private void handleTextFields() {
        fields[Texts.USER_NAME.ordinal()].setOnAction(event -> selectUser(fields[Texts.USER_NAME.ordinal()].getText()));
        fields[Texts.OBJECT.ordinal()].setOnKeyReleased(event -> {
            if (menu.getStat() == MenuStat.SHOP) {
                shopPage = 0;
                if (buyMode) {
                    cardsInShop = Card.matchSearch(fields[Texts.OBJECT.ordinal()].getCharacters().toString(), shop.getCards());
                    itemsInShop = Item.matchSearch(fields[Texts.OBJECT.ordinal()].getCharacters().toString(), shop.getItems());
                } else {
                    cardsInShop = Card.matchSearch(fields[Texts.OBJECT.ordinal()].getCharacters().toString(),
                            account.getCollection().getCards());
                    itemsInShop = Item.matchSearch(fields[Texts.OBJECT.ordinal()].getCharacters().toString(),
                            account.getCollection().getItems());
                }
            }
            if (menu.getStat() == MenuStat.COLLECTION) {
                try {
                    collectionPage = 0;
                    if (deckName.equals("Collection")) {
                        cardsInCollection = Card.matchSearch(fields[Texts.OBJECT.ordinal()].getCharacters().toString(),
                                account.getCollection().getCards());
                        itemsInCollection = Item.matchSearch(fields[Texts.OBJECT.ordinal()].getCharacters().toString(),
                                account.getCollection().getItems());
                    } else {
                        cardsInCollection = Card.matchSearch(fields[Texts.OBJECT.ordinal()].getCharacters().toString(),
                                account.getCollection().findDeck(deckName).getCards());
                        if (fields[Texts.OBJECT.ordinal()].getText().matches(account.getCollection().
                                findDeck(deckName).getItem().getName())) {
                            ArrayList<Item> itemList = new ArrayList<>();
                            itemList.add(account.getCollection().findDeck(deckName).getItem());
                            itemsInCollection = itemList;
                        } else {
                            itemsInCollection = new ArrayList<>();
                        }
                    }
                } catch (NullPointerException ignored) {

                }
            }
            main();
        });
    }

    public void handleCollection(ArrayList<Card> cards, ArrayList<Item> items) {
        for (int i = 0; i < cards.size(); i++) {
            int finalI = i;
            cards.get(i).getCardView().getPane().setOnMouseClicked(event -> {
                deckLing(cards.get(finalI).getId());
                main();
            });
        }
        for (int i = 0; i < items.size(); i++) {
            int finalI = i;
            items.get(i).getCardView().getPane().setOnMouseClicked(event -> {
                deckLing(items.get(finalI).getId());
                main();
            });
        }
    }

    private void deckLing(int id) {
        List<String> deckNames = new ArrayList<>();
        for (Deck deck : account.getCollection().getDecks()) {
            deckNames.add(deck.getName());
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>(account.getCollection().getMainDeck().getName(), deckNames);
        if (id == Constants.SELECT_MAIN_CONST) {
            dialog.setHeaderText("Set the Main");
            dialog.setContentText("Deck... ");
        } else if (id == Constants.SHOW_DECK_CONST) {
            dialog.setHeaderText("Show objects of");
            dialog.setContentText("Deck... ");
        } else if (id == Constants.EXPORT_DECK) {
            dialog.setHeaderText("Export");
            dialog.setContentText("Deck... ");
        } else if (id == Constants.REMOVE_DECK) {
            dialog.setHeaderText("Delete");
            dialog.setContentText("Deck... ");
        } else {
            if (deckName.equals("Collection")) {
                dialog.setHeaderText("Adding to");
                dialog.setContentText("Deck... ");
            } else {
                dialog.setHeaderText("Removeing from");
                dialog.setContentText("Deck... ");
            }
        }
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            if (id == Constants.SELECT_MAIN_CONST) {
                if (account.getCollection().selectDeck(name)) {
                    AlertMessage alert = new AlertMessage("Main Deck is " + name,
                            Alert.AlertType.INFORMATION, "OK");
                    alert.getResult();
                } else {
                    AlertMessage alert = new AlertMessage("The " + name + " is already the Main Deck",
                            Alert.AlertType.ERROR, "OK");
                    alert.getResult();
                }
            } else if (id == Constants.SHOW_DECK_CONST) {
                setObjectsOfDeck(name);
            } else if (id == Constants.EXPORT_DECK) {
                account.getCollection().exportDeck(name);
                AlertMessage alert = new AlertMessage("Deck exported!", Alert.AlertType.INFORMATION, "OK");
                alert.getResult();
            } else if (id == Constants.REMOVE_DECK) {
                deleteDeck(name);
                AlertMessage alert = new AlertMessage("Deck deleted!", Alert.AlertType.INFORMATION, "OK");
                alert.getResult();
            } else {
                if (deckName.equals("Collection")) {
                    Message message = addToDeck(name, id);
                    if (message == Message.OBJECT_ADDED) {
                        AlertMessage alert = new AlertMessage("Card added!", Alert.AlertType.INFORMATION, "OK");
                        alert.getResult();
                    } else {
                        AlertMessage alert = new AlertMessage(message.toString(), Alert.AlertType.ERROR, "OK");
                        alert.getResult();
                    }
                } else {
                    Message message = removeFromDeck(name, id);
                    if (message == Message.SUCCESSFUL_REMOVE) {
                        AlertMessage alert = new AlertMessage("Card removed!", Alert.AlertType.INFORMATION, "OK");
                        alert.getResult();
                    } else {
                        AlertMessage alert = new AlertMessage(message.toString(), Alert.AlertType.ERROR, "OK");
                        alert.getResult();
                    }
                }
            }
        });
    }

    private void setObjectsOfDeck(String name) {
        Deck deck = account.getCollection().findDeck(name);
        if (deck.getCards() != null)
            cardsInCollection = account.getCollection().findDeck(name).getCards();
        if (deck.getItem() != null) {
            ArrayList<Item> itemList = new ArrayList<>();
            itemList.add(account.getCollection().findDeck(name).getItem());
            itemsInCollection = itemList;
        }
        collectionPage = 0;
        deckName = name;
    }

    private void importDeck() {
        Stage stage = new Stage();
        stage.setTitle("Import Deck..");
        FileChooser chooser = new FileChooser();
        try {
            File file = chooser.showOpenDialog(stage);
            FileReader reader = new FileReader(file);
            Deck deck = new Gson().fromJson(reader, Deck.class);
            reader.close();
            for (Card card : deck.getCards()) {
                card.setCardView();
            }
            if (deck.getItem() != null) {
                deck.getItem().setCardView();
            }
            account.getCollection().getDecks().add(deck);
            setObjectsOfDeck(deck.getName());
            collectionPage = 0;
        } catch (Exception ignored) {

        }
    }

    private void createDeck() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Create Deck");
        dialog.setContentText("Name... ");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            if (account.getCollection().createDeck(name)) {
                AlertMessage alert = new AlertMessage("Deck created!", Alert.AlertType.INFORMATION, "OK");
                alert.getResult();
                cardsInCollection = new ArrayList<>();
                itemsInCollection = new ArrayList<>();
                collectionPage = 0;
                deckName = name;
            } else {
                AlertMessage alert = new AlertMessage(Message.EXISTING_DECK.toString(), Alert.AlertType.ERROR, "OK");
                alert.getResult();
            }
        });
    }

    private void handleInstances(ArrayList<Card> cards, ArrayList<Item> items) {
        for (int i = 0; i < cards.size(); i++) {
            int finalI = i;
            cards.get(i).getCardView().getPane().setOnMouseClicked(event -> {
                if (buyMode) {
                    handleBuy(account.getBudget(), cards.get(finalI).getName(), cards.get(finalI).getPrice());
                } else {
                    handleSell(cards.get(finalI).getId());
                }
                main();
            });
        }
        for (int i = 0; i < items.size(); i++) {
            int finalI = i;
            items.get(i).getCardView().getPane().setOnMouseClicked(event -> {
                if (buyMode) {
                    if (items.get(finalI).getPrice() == 0) {
                        AlertMessage alert;
                        alert = new AlertMessage("You cannot buy collectible item!", Alert.AlertType.ERROR, "OK");
                        alert.getResult();
                    } else {
                        handleBuy(account.getBudget(), items.get(finalI).getName(), items.get(finalI).getPrice());
                    }
                } else {
                    handleSell(items.get(finalI).getId());
                }
                main();
            });
        }
    }

    private void handleBuy(int budget, String name, int price) {
        AlertMessage alert;
        if (budget < price) {
            alert = new AlertMessage("Insufficient budget!", Alert.AlertType.ERROR, "OK");
            alert.getResult();
        } else {
            alert = new AlertMessage("It will cost " + price + " Drigs",
                    Alert.AlertType.CONFIRMATION, "OK", "Cancel");
            Optional<ButtonType> result = alert.getResult();
            if (result.isPresent()) {
                switch (result.get().getText()) {
                    case "OK":
                        buy(name);
                        break;
                }
            }
        }
    }

    private void handleSell(int id) {
        AlertMessage alert;
        alert = new AlertMessage("Are you sure to sell it?", Alert.AlertType.CONFIRMATION,
                "Yes", "No");
        Optional<ButtonType> result = alert.getResult();
        if (result.isPresent()) {
            switch (result.get().getText()) {
                case "Yes":
                    sell(id);
                    break;
            }
        }
    }

    public void setBattleMode(int a) {
        switch (a) {
            case 1:
                battle.setMode(BattleMode.KILLENEMYHERO);
                break;
            case 2:
                battle.setMode(BattleMode.COLLECTING);
                break;
            case 3:
                battle.setMode(BattleMode.FLAG);
                break;
        }
        if (battle.getGameType().equals(GameType.SINGLEPLAYER)) {
            menu.setStat(MenuStat.BATTLE);
            Account[] accounts = new Account[2];
            accounts[0] = account;
            for (int i = 0; i < game.getAccounts().size(); i++) {
                if (game.getAccounts().get(i).getName().equals("powerfulAI")) {
                    accounts[1] = game.getAccounts().get(i);
                }
            }
            battle.setAccounts(accounts);
            setMainDeckForAI();
            battle.startBattle();
        } else {
            menu.setStat(MenuStat.SELECT_USER);
        }
        main();
    }

    private void chooseBattleType() {
        menu.setStat(MenuStat.GAME_TYPE);
        main();
    }

    private void AiFunctions() {
        if (battle.getGameType().equals(GameType.SINGLEPLAYER) && battle.getTurn() % 2 == 1) {
            System.out.println(battle.getAccounts()[1].getBudget());
            moveAI();
            if (battle.getMode().equals(BattleMode.COLLECTING)) {
                if (battle.checkForWin()) {
//                    menu.setStat(MenuStat.GAME);
                    view.Success();
                }
            }
            insertAI();
            attackAI();
            endTurn();
            showMap();
        }
    }

    private void showMap() {
        if (menu.getStat() == MenuStat.BATTLE) {
            view.drawMap(battle);
        }
    }

    private void selectUser(String name) {
        Account accountt = Account.getAccountByName(name, game.getAccounts());
        if (accountt != null) {
            battle.setAccounts(account, accountt);
            menu.setStat(MenuStat.BATTLE);
            battle.startBattle();
        }
        main();
    }


    private void setBattleModeSingle() {
        battle.setGameType(GameType.SINGLEPLAYER);
        menu.setStat(MenuStat.BATTLE_MODE);
        main();
    }

    private void setBattleModeMulti() {
        battle.setGameType(GameType.MULTIPLAYER);
        menu.setStat(MenuStat.BATTLE_MODE);
        main();
    }

    private void setBattleMode(Request request) {
        if (request.isBattleMode() && menu.getStat() == MenuStat.BATTLE_MODE) {
            battle.setMode(request.getBattleMode(request.getCommand()));
            if (battle.getGameType().equals(GameType.SINGLEPLAYER)) {
                setMainDeckForAI();
            }
            if (battle.getGameType() == GameType.MULTIPLAYER) {
                menu.setStat(MenuStat.SELECT_USER);
            } else {
                menu.setStat(MenuStat.BATTLE);
                battle.startBattle();
            }
        }
    }

    private void setMainDeckForAI() {
        if (battle.getMode().equals(BattleMode.KILLENEMYHERO)) {
            battle.getAccounts()[1].getCollection().selectDeck("level1");
        }
        if (battle.getMode().equals(BattleMode.FLAG)) {
            battle.getAccounts()[1].getCollection().selectDeck("level2");
        }
        if (battle.getMode().equals(BattleMode.COLLECTING)) {
            battle.getAccounts()[1].getCollection().selectDeck("level3");
        }
    }

    private void setGameType(Request request) {
        if (request.isGameType() && menu.getStat() == MenuStat.GAME_TYPE) {
            battle.setGameType(request.getGameType(request.getCommand()));
            if (battle.getGameType() == GameType.SINGLEPLAYER) {
                menu.setStat(MenuStat.PROCESS);
                view.chooseProcess();
            } else {
                menu.setStat(MenuStat.BATTLE_MODE);
                view.chooseBattleMode();
            }
        }
    }

    private void moveAI() {
        if (battle.getGameType().equals(GameType.SINGLEPLAYER) && battle.getTurn() % 2 == 1) {
            for (int i = 0; i < battle.getFieldCards()[1].length; i++) {
                if (battle.getFieldCards()[1][i] != null) {
                    battle.setCurrentCard(battle.getFieldCards()[1][i]);
                    battle.moveTo(battle.setDestinationCoordinate(battle.getFieldCards()[1][i]));
                }
            }
        }
    }


    private void attackAI() {
        if (battle.getGameType().equals(GameType.SINGLEPLAYER) && battle.getTurn() % 2 == 1) {
            for (int i = 0; i < battle.getFieldCards()[1].length; i++) {
                for (int j = 0; j < battle.getFieldCards()[0].length; j++) {
                    if (battle.getFieldCards()[0][j] != null && battle.getFieldCards()[1][i] != null) {
                        battle.attack(battle.getFieldCards()[0][j].getId(), battle.getFieldCards()[1][i]);
                        if (battle.getFieldCards()[1][i].getType().equals("Hero")) {
                            if (battle.AIAssaultTypeBasedInsertion(i, j)) break;
                        }
                    }
                }
            }
            int counter = 0;
            for (int i = 0; i < battle.getFieldCards()[0].length; i++) {
                if (battle.getFieldCards()[0][i] != null) {
                    counter++;
                }
            }
            if (counter == 0) {
                view.showAttack(Message.BATTLE_FINISHED);
            }
        }
    }

    private void insertAI() {
        if (battle.getGameType().equals(GameType.SINGLEPLAYER) && battle.getTurn() % 2 == 1) {
            ArrayList<Card> cards = convertArrayToList(battle.getPlayerHands()[1]);
            //estefade az se tabe baraye piadesazi insertion ai
            battle.insertCard(battle.setCardCoordinates(), battle.chooseCard(cards).getName());
        }
    }

    private ArrayList<Card> convertArrayToList(Card[] cards) {
        return new ArrayList<>(Arrays.asList(cards));
    }

    private void invalidCommand() {
        view.printInvalidCommand();
    }

    private void createAccount() {
        String username = fields[Texts.USERNAME.ordinal()].getText();
        String password = passwordField.getText();
        if (Account.getAccountByName(username, game.getAccounts()) != null ) {
            AlertMessage message = new AlertMessage("An account with this name already exists!", Alert.AlertType.ERROR, "OK");
            message.getResult();


        } else if(username.equals("") || password.equals("")) {
            AlertMessage message = new AlertMessage("You should choose a valid username and passowrd!", Alert.AlertType.ERROR, "OK");
            message.getResult();
        }else{

            this.account = new Account(username, password);
            menu.setStat(MenuStat.ACCOUNT);
            AlertMessage message = new AlertMessage("Account successfully created!", Alert.AlertType.CONFIRMATION, "YAY!");
            message.getResult();
            main();
        }



    }

    private void showMatchHistory(Request request) {
        if (request.checkMatchHistory() && menu.getStat() == MenuStat.ACCOUNT) {
            if (battle.getGameType() == GameType.SINGLEPLAYER) {
                view.showMatchHistory(account.getMatchHistory(), battle.getLevel());
            } else {
                view.showMatchHistory(account.getMatchHistory(), getOpponentName(account));
            }
        }
    }

    private String getOpponentName(Account account) {
        for (int i = 0; i < battle.getAccounts().length; i++) {
            if (!battle.getAccounts()[i].getName().equals(account.getName())) {
                return battle.getAccounts()[i].getName();
            }
        }
        return null;
    }

    private void login() {
        String username = fields[Texts.USERNAME.ordinal()].getText();
        String password = passwordField.getText();
        Message message = Account.login(username, password);
        switch (message) {
            case INVALID_PASSWORD:
                AlertMessage alertMessage = new AlertMessage("Incorrect Password", Alert.AlertType.ERROR, "OK");
                alertMessage.getResult();
                break;

            case INVALID_ACCOUNT:
                alertMessage = new AlertMessage("An account with this username doesn't exist !", Alert.AlertType.ERROR, "OK");
                alertMessage.getResult();
                break;
            case SUCCESSFUL_LOGIN:
                break;

        }
        if (Account.login(username, password) == Message.SUCCESSFUL_LOGIN) {
            this.account = game.getAccounts().get(Account.accountIndex(username));
            menu.setStat(MenuStat.ACCOUNT);
            main();
        }


    }

    private void showLeaderBoard() {
        game.sortAccounts();
        view.printLeaderboard(game.getAccounts());
    }

    private void save() {
        game.save(account);
        AlertMessage alert = new AlertMessage("Saved successfully!", Alert.AlertType.INFORMATION, "OK");
        alert.getResult();
    }

    private void logout() {
        AlertMessage alert = new AlertMessage("Do you want to save your info?", Alert.AlertType.CONFIRMATION,
                "Yes", "No");
        Optional<ButtonType> result = alert.getResult();
        if (result.isPresent()) {
            switch (result.get().getText()) {
                case "Yes":
                    game.logout(account);
                default:
                    this.account = null;
                    menu.setStat(MenuStat.MAIN);
                    main();
            }
        }
    }

    private void help() {
        view.printHelp();
    }

    private void enter(Request request) {
        if (request.checkMenuEntrnaceSyntax()) {
            switch (request.getMenu(request.getCommand())) {
                case "Exit":
                    exit();
                    break;
                case "Help":
                    help();
                    break;
                case "Battle":
                    view.chooseMultiOrSingle();
                    menu.setStat(MenuStat.GAME_TYPE);
                    break;
                default:
                    menu.setStat(MenuStat.valueOf(request.getMenu(request.getCommand()).toUpperCase()));
                    view.showEntrance(request);
            }
        }
    }

    private void exit() {
        menu.exitMenu();
        main();
    }

    private void showTheCollection() {
        if (menu.getStat() == MenuStat.SHOP) {
            view.printCollection(this.account.getCollection(), true);
        }
    }


    private void searchInCollection(Request request) {
        if (request.checkSearchTheCollectionSyntax()) {
            if (menu.getStat() == MenuStat.COLLECTION) {
                String name = request.getObjectName(request.getCommand());
                view.printId(Card.getAllCardsId(name, account.getCollection().getCards()
                        .toArray(new Card[account.getCollection().getCards().size()]))
                        .toArray(new Card[Card.getAllCardsId(name, account.getCollection().getCards()
                                .toArray(new Card[account.getCollection().getCards().size()])).size()]));
                view.printId(Item.getAllItemsId(name, account.getCollection().getItems()
                        .toArray(new Item[account.getCollection().getItems().size()]))
                        .toArray(new Item[Item.getAllItemsId(name, account.getCollection().getItems()
                                .toArray(new Item[account.getCollection().getItems().size()])).size()]));
            }
        }
    }

    private void saveCollection() {

    }

    private void deleteDeck(String name) {
        this.account.getCollection().deleteDeck(name);
    }

    private Message addToDeck(String deckName, int id) {
        return this.account.getCollection().add(deckName, id);
    }

    private Message removeFromDeck(String name, int id) {
        return this.account.getCollection().remove(name, id);
    }

    private void validateDeck(Request request) {
        if (request.checkValidationSyntax() && menu.getStat() == MenuStat.COLLECTION) {
            view.checkValidation(this.account.getCollection().validate(request.getDeckName(request.getCommand())));
        }
    }

    private void selectDeck(Request request) {
        if (request.checkDeckSelectionSyntax() && menu.getStat() == MenuStat.COLLECTION) {
            view.printDeckSelection(this.account.getCollection().selectDeck(request.getDeckName(request.getCommand())));
        }
    }

    private void showAllDecks(Request request) {
        if (request.checkShowAllDeckSyntax() && menu.getStat() == MenuStat.COLLECTION) {
            view.showAllDeck(this.account.getCollection().getDecks());
        }
    }

    private void showDeck(Request request) {
        if (request.checkShowDeckSyntax() && menu.getStat() == MenuStat.COLLECTION) {
            Collection collection = this.account.getCollection();
            try {
                view.printDeck(collection.getDecks().get(collection.deckExistance(request.getDeckName(request.getCommand()))));
            } catch (ArrayIndexOutOfBoundsException e) {
                view.printDeck(null);
            }
        }
    }

    private void search(Request request) {
        if (request.checkSearchSyntax()) {
            if (menu.getStat() == MenuStat.COLLECTION) {
                searchInCollection(request);
            } else if (menu.getStat() == MenuStat.SHOP) {
                String name = request.getObjectName(request.getCommand());
                Card card = Card.getCardByName(name, shop.getCards().toArray(new Card[shop.getCards().size()]));
                Item item = Item.getItemByName(name, shop.getItems().toArray(new Item[shop.getItems().size()]));
                if (card != null)
                    view.printId(card);
                if (item != null)
                    view.printId(item);
            }
        }
    }

    private void buy(String name) {
        if (shop.getGame() == null)
            shop.setGame(this.game);
        shop.buy(name, this.account);
    }

    private void sell(int id) {
        shop.sell(id, account);
    }

    private void showShop() {
        if (menu.getStat() == MenuStat.SHOP) {
            view.printCollection(new Collection(shop.getCards(), shop.getItems()), true);
        }
        if (menu.getStat() == MenuStat.COLLECTION) {
            view.printCollection(account.getCollection(), true);
        }
    }

    private void gameInfo() {
        if (menu.getStat() == MenuStat.BATTLE) {
            view.printGameInfo(battle);
        }
    }

    private void showMyMinions() {
        if (menu.getStat() == MenuStat.BATTLE) {
            view.printMinionsInfo(battle.getFieldCards()[battle.getTurnByAccount(this.account)]);
        }
    }

    private void showOppMinions() {
        if (menu.getStat() == MenuStat.BATTLE) {
            view.printMinionsInfo(battle.getFieldCards()[(battle.getTurnByAccount(this.account) + 1) % 2]);
        }
    }

    private void showCardInfo(Request request) {
        if (menu.getStat() == MenuStat.BATTLE) {
            Card card = Card.getCardByID(request.getObjectID(request.getCommand()),
                    battle.getPlayerHands()[battle.getTurnByAccount(account)]);
            if (card != null) {
                view.printCardInfo(card);
                return;
            }
            card = Card.getCardByID(request.getObjectID(request.getCommand()),
                    battle.getFieldCards()[battle.getTurnByAccount(account)]);
            view.printCardInfo(card);
        }
    }

    private void select(Request request) {
        if (request.checkCardSelectionSyntax() && menu.getStat() == MenuStat.BATTLE) {
            int card = request.getObjectID(request.getCommand());
            int item = request.getObjectID(request.getCommand());
            view.printSelectionResult(battle.selectCard(card), battle.selectCollectibleId(item));
        }
    }

    private void moveToInBattle(Request request) {
        if (request.checkMoveSyntax() && menu.getStat() == MenuStat.BATTLE) {
            Coordinate coordinate = request.getCoordinate(request.getCommand());
            view.showMovement(battle.moveTo(coordinate), battle);
        }
    }

    private void battleAttack(Request request) {
        if (request.checkAssaultSyntax() && menu.getStat() == MenuStat.BATTLE) {
            Card card = Card.getCardByID(request.getObjectID(request.getCommand()),
                    battle.getFieldCards()[(battle.getTurnByAccount(account) + 1) % 2]);
            view.showAttack(battle.attack(card.getId(), battle.getCurrentCard()));
        }
    }

    private void battleComboAttack(Request request) {
        if (request.checkComboSyntax() && menu.getStat() == MenuStat.BATTLE) {
            int oppId = request.getOppIdInCombo(request.getCommand());
            int[] ids = request.getComboComradesId(request.getCommand());
            Card[] cards = new Card[ids.length];
            for (int i = 0; i < ids.length; i++) {
                cards[i] = Card.getCardByID(ids[i], battle.getFieldCards()[(battle.getTurnByAccount(account) + 1) % 2]);
            }
            view.comboErrors(battle.attackCombo(oppId, cards));
            view.showCombo(oppId, cards);
        }
    }

    private void useSpecialPower(Request request) {
        if (request.checkSPUsageSyntax() && menu.getStat() == MenuStat.BATTLE) {
            battle.setCurrentCoordinate(request.getCoordinate(request.getCommand()));

        }
    }

    private void showHand() {
        if (menu.getStat() == MenuStat.BATTLE) {
            view.printCards(battle.getPlayerHands()[battle.getTurnByAccount(account)]);
        }
    }

    private void insertCard(Request request) {
        if (request.checkCardInsertSyntax() && menu.getStat() == MenuStat.BATTLE) {
            view.printInsertionMessage(battle.insertCard(request.getCoordinate(request.getCommand()),
                    request.getInsertedName(request.getCommand())), battle);
        }
    }

    private void endTurn() {
        if (menu.getStat() == MenuStat.BATTLE) {
            battle.endTurn();
            this.account = battle.getCurrentPlayer();
            view.endTurn(account);
        }
    }

    private void showCollectables() {
        if (menu.getStat() == MenuStat.BATTLE) {
            view.printCollectables(battle.getCollectibles()[battle.getTurnByAccount(account)]);
        }
    }

    private void showCollectableInfo() {
        if (menu.getStat() == MenuStat.ITEM_SELECTION) {
            view.printItem(battle.getCurrentItem());
        }
    }

    private void useItem(Request request) {
        if (request.checkItemUseSyntax()) {
            if (menu.getStat() == MenuStat.BATTLE || menu.getStat() == MenuStat.ITEM_SELECTION) {
                battle.useItem(battle.getCurrentItem());
            }
        }
    }

    private void showNextCard() {
        if (menu.getStat() == MenuStat.BATTLE) {
            view.printCardInfo(account.getCollection().getMainDeck().getCards().get(0));
        }
    }

    private void specialPowerValidation() {
        if (menu.getStat() == MenuStat.BATTLE) {
            view.specialPowerValidation(battle.validSpecialPower());
        }
    }

    private void showCards() {
        if (menu.getStat() == MenuStat.GRAVEYARD) {
            view.printCards(battle.getGraveyard()[battle.getTurnByAccount(account)]);
        }
    }

    private void endGame() {
        if (menu.getStat() == MenuStat.BATTLE) {
            battle.resign();
            view.endGame(battle);
        }
    }

    private void showMenu() {
        view.printOptions();
    }

}