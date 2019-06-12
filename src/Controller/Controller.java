package Controller;

import Model.*;
import View.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
    private Button[] buttons = new Button[Buttons.values().length];
    private TextField[] fields = new TextField[Texts.values().length];
    private static final Controller controller = new Controller();

    private Controller() {
        initializeGame();
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button();
        }
        for (int i = 0; i < fields.length; i++) {
            fields[i] = new TextField();
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

    public void handleCommands() {
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
    }

    public void main() {
        switch (menu.getStat()) {
            case MAIN:
                view.mainMenu(buttons[Buttons.LOGIN.ordinal()], buttons[Buttons.CREATE_ACCOUNT.ordinal()],
                        buttons[Buttons.EXIT.ordinal()], fields[Texts.USERNAME.ordinal()], fields[Texts.PASSWORD.ordinal()]);
        }
        handleButtons();
    }

    public void handleButtons(){
        buttons[Buttons.LOGIN.ordinal()].setOnMouseClicked(event -> System.exit(0));
    }

    private void AiFunctions() {
        if (battle.getGameType().equals(GameType.SINGLEPLAYER) && battle.getTurn() % 2 == 1) {
            System.out.println(battle.getAccounts()[1].getBudget());
            moveAI();
            if (battle.getMode().equals(BattleMode.COLLECTING)) {
                if (battle.checkForWin()) {
                    menu.setStat(MenuStat.GAME);
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

    private void selectUser(Request request) {
        if (request.checkSelectUserSyntax() && menu.getStat() == MenuStat.SELECT_USER) {
            String username = request.getAccountName(request.getCommand());
            view.playerAdded(Account.getAccountByName(username, game.getAccounts()));
            try {
                battle.setAccounts(account, Account.getAccountByName(username, game.getAccounts()));
                menu.setStat(MenuStat.BATTLE);
                battle.startBattle();
                view.endTurn(account);
            } catch (NullPointerException ignored) {
            }
        }
    }

    private void setProcess(Request request) {
        if (request.isProcess() && menu.getStat() == MenuStat.PROCESS) {
            battle.setProcess(request.getProcess(request.getCommand()));
            menu.setStat(MenuStat.BATTLE_MODE);
            view.chooseBattleMode();
            Account[] accounts = new Account[2];
            accounts[0] = account;
            for (int i = 0; i < game.getAccounts().size(); i++) {
                if (game.getAccounts().get(i).getName().equals("powerfulAI")) {
                    accounts[1] = game.getAccounts().get(i);
                }
            }
            battle.setAccounts(accounts);
        }
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
        if (battle.getAccounts()[1].getName().equals("powerfulAI") &&
                battle.getMode().equals(BattleMode.KILLENEMYHERO)) {
            battle.getAccounts()[1].getCollection().selectDeck("level1");
        }
        if (battle.getAccounts()[1].getName().equals("powerfulAI") &&
                battle.getMode().equals(BattleMode.FLAG)) {
            battle.getAccounts()[1].getCollection().selectDeck("level2");
        }
        if (battle.getAccounts()[1].getName().equals("powerfulAI") &&
                battle.getMode().equals(BattleMode.COLLECTING)) {
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

    private void createAccount(Request request) {
        if (request.checkAccountCreationSyntax() && menu.getStat() == MenuStat.MAIN) {
            String username = request.getAccountName(request.getCommand());
            view.passwordInsertion();
            String password = request.getNewCommand();
            Account account = new Account(request.getAccountName(username), request.getPassword(password));
            this.account = account;
            view.accountCreation(account.createAccount());
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

    private void login(Request request) {
        if (request.checkLoginSyntax() && menu.getStat() == MenuStat.MAIN) {
            String username = request.getAccountName(request.getCommand());
            view.passwordInsertion();
            String password = request.getNewCommand();
            view.login(Account.login(request.getAccountName(username), request.getPassword(password)));
            if (Account.login(request.getAccountName(username), request.getPassword(password)) == Message.SUCCESSFUL_LOGIN)
                this.account = game.getAccounts().get(Account.accountIndex(username));
        }
    }

    private void showLeaderBoard(Request request) {
        if (request.checkLeaderBoardSyntax() && menu.getStat() == MenuStat.ACCOUNT) {
            game.sortAccounts();
            view.printLeaderboard(game.getAccounts());
        }
    }

    private void save() {
        if (menu.getStat() == MenuStat.ACCOUNT) {
            game.save(account);
        }
    }

    private void logout() {
        if (menu.getStat() == MenuStat.ACCOUNT) {
            game.logout(account);
            this.account = null;
            view.logout();
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

    private void createDeck(Request request) {
        if (request.checkDeckSyntax() && menu.getStat() == MenuStat.COLLECTION) {
            view.createDeck(this.account.getCollection().createDeck(request.getDeckName(request.getCommand())));
        }
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

    private void buy(Request request) {
        if (request.checkBuySyntax() && menu.getStat() == MenuStat.SHOP) {
            view.printBuyMessages(shop.buy(request.getObjectName(request.getCommand()), this.account));
        }
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
            battle.endGame();
            view.endGame(battle);
        }
    }

    private void showMenu() {
        view.printOptions();
    }

}
