package Controller;

import Model.*;
import Model.Menu;
import View.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller {
    private View view = View.getInstance();
    private Game game = Game.getInstance();
    private Menu menu = Menu.getInstance();
    private Shop shop = Shop.getInstance();
    private Account account;
    private Battle battle = Battle.getInstance();
    private transient Button[] buttons = new Button[Buttons.values().length];
    private transient PasswordField passwordField = new PasswordField();
    private transient Label[] labels = new Label[Labels.values().length];
    private transient ImageView[] imageViews = new ImageView[ImageViews.values().length];
    private transient TextField[] fields = new TextField[Texts.values().length];
    private transient ImageView[] heroes = new ImageView[Constants.HEROES_COUNT];
    private transient javafx.scene.image.ImageView[] minions = new ImageView[Constants.MINIONS_COUNT];
    private transient ImageView[] spells = new ImageView[Constants.SPELLS_COUNT];
    private transient ImageView[] items = new ImageView[Constants.ITEMS_COUNT];
    private static final Controller controller = new Controller();
    private File file = new File("resources/music/music_mainmenu_lyonar.m4a");
    private Media media = new Media(file.toURI().toString());
    private MediaPlayer player = new MediaPlayer(media);

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
        for (int i = 0; i < imageViews.length; i++) {
            imageViews[i] = new ImageView();
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
    }

    /*public void handleCommands() {
        Request request = new Request();

//        while (true) {
        request.getNewCommand();
        switch (request.getType()) {
            case NULL:
                invalidCommand();
                break;
            case SHOW_MATCH_HISTORY:
                showMatchHistory(request);
            case CREATE_ACCOUNT:
                createAccount(request);
                break;
            case LOGIN:
                login(request);
                break;
            case LEADERBOARD:
                showLeaderBoard(request);
                break;
            case SAVE:
                try {
                    save();
                } catch (OutOfMemoryError h) {
                    System.out.println("Saving error!");
                }
                break;
            case LOGOUT:
                logout();
                break;
            case HELP:
                help();
                break;
            case EXIT:
                exit();
                break;
            case ENTRANCE:
                enter(request);
                break;
            case SHOW_COLLECTION:
                showTheCollection();
                break;
            case SEARCH_COLLECTION:
                searchInCollection(request);
                break;
            case SAVE_IN_COLLECTION:
                saveCollection();
                break;
            case CREATE_DECK:
                createDeck(request);
                break;
            case DELETE_DECK:
                deleteDeck(request);
                break;
            case ADD:
                addToDeck(request);
                break;
            case REMOVE:
                removeFromDeck(request);
                break;
            case VALIDATION:
                validateDeck(request);
                break;
            case SELECT_DECK:
                selectDeck(request);
                break;
            case SHOW_ALL_DECK:
                showAllDecks(request);
                break;
            case SHOW_DECK:
                showDeck(request);
                break;
            case SEARCH:
                search(request);
                break;
            case BUY:
                buy(request);
                break;
            case SELL:
                sell(request);
                break;
            case SHOW:
                showShop();
                break;
            case SINGLE_PLAYER:
                setGameType(request);
                break;
            case MULTI_PLAYER:
                setGameType(request);
                break;
            case STORY:
                setProcess(request);
                break;
            case CUSTOM:
                setProcess(request);
                break;
            case KILL_ENEMY_HERO:
                setBattleMode(request);
                break;
            case COLLECTING:
                setBattleMode(request);
                break;
            case FLAG:
                setBattleMode(request);
                break;
            case SELECT_USER:
                selectUser(request);
                break;
            case GAME_INFO:
                gameInfo();
                break;
            case SHOW_MAP:
                showMap();
                break;
            case SHOW_MY_MININOS:
                showMyMinions();
                break;
            case SHOW_OPP_MINIONS:
                showOppMinions();
                break;
            case SHOW_CARD_INFO:
                showCardInfo(request);
                break;
            case SELECTION:
                select(request);
                break;
            case MOVE:
                moveToInBattle(request);
                break;
            case ATTACK:
                battleAttack(request);
                break;
            case COMBO:
                battleComboAttack(request);
                break;
            case USE_SP:
                specialPowerValidation();
                if (battle.validSpecialPower() == Message.NULL) {
                    useSpecialPower(request);
                }
                break;
            case SHOW_HAND:
                showHand();
                break;
            case INSERTION:
                insertCard(request);
                break;
            case END_TURN:
                endTurn();
                AiFunctions();
                break;
            case SHOW_COLLECTABLES:
                showCollectables();
                break;
            case SHOW_COLLECTABLE_INFO:
                showCollectableInfo();
                break;
            case USE_ITEM:
                useItem(request);
                break;
            case NEXT_CARD:
                showNextCard();
                break;
            case SHOW_CARDS:
                showCards();
                break;
            case END_GAME:
                endGame();
                break;
            case SHOW_MENU:
                showMenu();
                break;
        }
//        }
    }*/

    public void main() {

        player.stop();
        switch (menu.getStat()) {
            case MAIN:
                view.mainMenu(buttons[Buttons.LOGIN.ordinal()], buttons[Buttons.CREATE_ACCOUNT.ordinal()],
                        buttons[Buttons.EXIT.ordinal()], fields[Texts.USERNAME.ordinal()], passwordField);
                file = new File("resources/music/music_battlemap_vetruv.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                break;
            case ACCOUNT:
                view.accountMenu(buttons[Buttons.PLAY.ordinal()], buttons[Buttons.COLLECTION.ordinal()],
                        buttons[Buttons.SHOP.ordinal()], buttons[Buttons.LEADER_BOARD.ordinal()],
                        buttons[Buttons.LOGOUT.ordinal()]);
                file = new File("resources/music/music_playmode.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                break;
            case SHOP:
                view.shopMenu(heroes, minions, spells, items, imageViews[ImageViews.BACK.ordinal()],
                        imageViews[ImageViews.NEXT.ordinal()], imageViews[ImageViews.PREV.ordinal()]);
                file = new File("resources/music/music_battlemap_morinkhur.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                break;
            case COLLECTION:
                view.collectionMenu(imageViews[ImageViews.CREATE.ordinal()], fields[Texts.DECKNAME.ordinal()],
                        imageViews[ImageViews.BACK.ordinal()], imageViews[ImageViews.NEXT.ordinal()], imageViews[ImageViews.PREV.ordinal()]);
                file = new File("resources/music/music_battlemap_morinkhur.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
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
                view.graveYardMenu();
                break;
        }
        player.setAutoPlay(true);

        handleButtons();
        handleTextFields();
    }

    public void handleButtons() {
        buttons[Buttons.CREATE_ACCOUNT.ordinal()].setOnMouseClicked(event -> createAccount());
        buttons[Buttons.LOGIN.ordinal()].setOnMouseClicked(event -> login());
        buttons[Buttons.EXIT.ordinal()].setOnMouseClicked(event -> exit());
        buttons[Buttons.PLAY.ordinal()].setOnMouseClicked(event -> chooseBattleType());
        buttons[Buttons.LOGOUT.ordinal()].setOnMouseClicked(event -> logout());
        buttons[Buttons.LEADER_BOARD.ordinal()].setOnMouseClicked(event -> showLeaderBoard());
        buttons[Buttons.SHOP.ordinal()].setOnMouseClicked(event -> {
            menu.setStat(MenuStat.SHOP);
            main();
        });
        buttons[Buttons.COLLECTION.ordinal()].setOnMouseClicked(event -> {
            menu.setStat(MenuStat.COLLECTION);
            main();
        });
        imageViews[ImageViews.BACK.ordinal()].setOnMouseClicked(event -> exit());
        buttons[Buttons.BUY.ordinal()].setOnMouseClicked(event -> buy());
        buttons[Buttons.SINGLE_PLAYER.ordinal()].setOnMouseClicked(event -> setBattleModeSingle());
        buttons[Buttons.MULTI_PLAYER.ordinal()].setOnMouseClicked(event -> setBattleModeMulti());
        buttons[Buttons.KILL_ENEMY_HERO.ordinal()].setOnMouseClicked(event -> setBattleMode(1));
        buttons[Buttons.FLAG_COLLECTING.ordinal()].setOnMouseClicked(event -> setBattleMode(2));
        buttons[Buttons.HOLD_FLAG.ordinal()].setOnMouseClicked(event -> setBattleMode(3));
        imageViews[ImageViews.CREATE.ordinal()].setOnMouseClicked(event -> createDeck(fields[Texts.DECKNAME.ordinal()].toString()));
    }

    public void handleTextFields() {
        fields[Texts.USER_NAME.ordinal()].setOnAction(event -> selectUser(fields[Texts.USER_NAME.ordinal()].getText()));
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
        this.account = new Account(username, password);
        menu.setStat(MenuStat.ACCOUNT);
        main();
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
        if (menu.getStat() == MenuStat.ACCOUNT) {
            game.save(account);
        }
    }

    private void logout() {
        game.logout(account);
        this.account = null;
        menu.setStat(MenuStat.MAIN);
        main();
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

    private void createDeck(String deckName) {
        /*if (request.checkDeckSyntax() && menu.getStat() == MenuStat.COLLECTION) {
            view.createDeck(this.account.getCollection().createDeck(request.getDeckName(request.getCommand())));
        }*/
        this.account.getCollection().createDeck(deckName);
    }

    private void deleteDeck(Request request) {
        if (request.checkDeckSyntax() && menu.getStat() == MenuStat.COLLECTION) {
            view.deleteDeck(this.account.getCollection().deleteDeck(request.getDeckName(request.getCommand())));
        }
    }

    private void addToDeck(Request request) {
        if (request.checkToDeckAdditionSyntax() && menu.getStat() == MenuStat.COLLECTION) {
            view.addToCollection(this.account.getCollection().add(request.getDeckName(request.getCommand()), request.getObjectID(request.getCommand())));
        }
    }

    private void removeFromDeck(Request request) {
        if (request.checkFromDeckDeletionSyntax() && menu.getStat() == MenuStat.COLLECTION) {
            view.removeFromDeck(this.account.getCollection().remove(request.getDeckName(request.getCommand()), request.getObjectID(request.getCommand())));
        }
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

    private void buy() {
        if (shop.getGame() == null)
            shop.setGame(this.game);
        shop.buy(fields[Texts.CARD.ordinal()].getText(), this.account);
    }

    private void sell(Request request) {
        if (request.checkSellSyntax() && menu.getStat() == MenuStat.SHOP) {
            view.printSellMessages(shop.sell(request.getObjectID(request.getCommand()), this.account));
        }
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
