package Controller;

import Model.*;
import View.*;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    private View view = View.getInstance();
    private Game game = Game.getInstance();
    private Menu menu = Menu.getInstance();
    private Shop shop = Shop.getInstance();
    private int turnCounter = 0;
    private Account account;
    private Battle battle = Battle.getInstance();
    private static final Controller controller = new Controller();

   /* public boolean checkForAIAccount(Account account){
        for (int i = 0; i < game.getAccounts().size() ; i++) {
            if(game.getAccounts().get(i).getName().equals("powerfulAI")){
                return true;
            }
        }
        return false;
    }*/

    private Controller() {

    }

    public static Controller getInstance() {
        return controller;
    }

    public void main() {
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
        Request request = new Request();

        while (true) {
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
                    moveAI();
                    moveToInBattle(request);
                    break;
                case ATTACK:
                    attackAI();
                    battleAttack(request);
                    break;
                case COMBO:
                    battleComboAttack(request);
                    break;
                case USE_SP:
                    specialPowerValidation();
                    if (battle.validSpecialPower() == null) {
                        useSpecialPower(request);
                    }
                    break;
                case SHOW_HAND:
                    showHand();
                    break;
                case INSERTION:
//                    if (insertAI()) break;
                    insertCard(request);
                    break;
                case END_TURN:
                    endTurn();
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
            } catch (NullPointerException e) {
            }
        }
    }

    private void setProcess(Request request) {
        if (request.isProcess() && menu.getStat() == MenuStat.PROCESS) {
            battle.setProcess(request.getProcess(request.getCommand()));
            menu.setStat(MenuStat.BATTLE_MODE);
            view.chooseBattleMode();
        }
    }

    private void setBattleMode(Request request) {
        if (request.isBattleMode() && menu.getStat() == MenuStat.BATTLE_MODE) {
            battle.setMode(request.getBattleMode(request.getCommand()));
            if (battle.getGameType() == GameType.MULTIPLAYER) {
                menu.setStat(MenuStat.SELECT_USER);
            } else {
                menu.setStat(MenuStat.BATTLE);
                battle.startBattle();
            }
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
        if (battle.getGameType().equals(GameType.SINGLEPLAYER) && turnCounter % 2 == 1) {
            for (int i = 0; i < battle.getFieldCards()[1].length; i++) {
                battle.moveTo(battle.setDestinationCoordinate(battle.getFieldCards()[1][i]));
                break;
            }
        }
    }

    private void attackAI() {
        if (battle.getGameType().equals(GameType.SINGLEPLAYER) && turnCounter % 2 == 1) {
            for (int i = 0; i < battle.getFieldCards()[1].length; i++) {
                battle.attack(opponentCardFinder(i).getId(), battle.getFieldCards()[1][i]);
                break;
            }
        }
    }

    private boolean insertAI() {
        if (battle.getGameType().equals(GameType.SINGLEPLAYER) && turnCounter % 2 == 1) {
            ArrayList<Card> cards = convertArrayToList(battle.getPlayerHands()[1]);
            battle.insertCard(battle.setCardCoordinates(battle.chooseCard(cards)), battle.chooseCard(cards).getName());
            return true;
        }
        return false;
    }

    public ArrayList<Card> convertArrayToList(Card[] cards) {
        ArrayList<Card> cards1 = new ArrayList<>();
        for (int i = 0; i < cards.length; i++) {
            cards1.add(cards[i]);
        }
        return cards1;
    }

    public Card opponentCardFinder(int i) {
        for (int j = 0; j < battle.getFieldCards()[0].length; j++) {
            if (battle.getFieldCards()[0][j].getCoordinate().getX() == battle.setTargetCoordiantes(battle.getFieldCards()[1][i]).getX() &&
                    battle.getFieldCards()[0][j].getCoordinate().getY() == battle.setTargetCoordiantes(battle.getFieldCards()[1][i]).getY()) {
                return battle.getFieldCards()[0][j];
            }
        }
        return null;
    }

    private void invalidCommand() {
        view.printInvalidCommand();
    }

    public void createAccount(Request request) {
        if (request.checkAccountCreationSyntax() && menu.getStat() == MenuStat.MAIN) {
            String username = request.getAccountName(request.getCommand());
            view.passwordInsertion();
            String password = request.getNewCommand();
            Account account = new Account(request.getAccountName(username), request.getPassword(password));
            this.account = account;
            view.accountCreation(account.createAccount());
        }
    }

    public void showMatchHistory(Request request) {
        if (request.checkMatchHistory() && menu.getStat() == MenuStat.ACCOUNT) {
            if (battle.getGameType() == GameType.SINGLEPLAYER) {
                view.showMatchHistory(account.getMatchHistory(), battle.getLevel());
            } else {
                view.showMatchHistory(account.getMatchHistory(), getOpponentName(account));
            }
        }
    }

    public String getOpponentName(Account account) {
        for (int i = 0; i < battle.getAccounts().length; i++) {
            if (!battle.getAccounts()[i].getName().equals(account.getName())) {
                return battle.getAccounts()[i].getName();
            }
        }
        return null;
    }

    public void login(Request request) {
        if (request.checkLoginSyntax() && menu.getStat() == MenuStat.MAIN) {
            String username = request.getAccountName(request.getCommand());
            view.passwordInsertion();
            String password = request.getNewCommand();
            view.login(Account.login(request.getAccountName(username), request.getPassword(password)));
            if (Account.login(request.getAccountName(username), request.getPassword(password)) == Message.SUCCESSFUL_LOGIN)
                this.account = game.getAccounts().get(Account.accountIndex(username));
        }
    }

    public void showLeaderBoard(Request request) {
        if (request.checkLeaderBoardSyntax() && menu.getStat() == MenuStat.ACCOUNT) {
            view.printLeaderboard();
        }
    }

    public void save() {
        if (menu.getStat() == MenuStat.ACCOUNT) {
            game.save(account);
        }
    }

    public void logout() {
        if (menu.getStat() == MenuStat.ACCOUNT) {
            game.logout(account);
            this.account = null;
            view.logout();
        }
    }

    public void help() {
        view.printHelp();
    }

    public void enter(Request request) {
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
            }
        }
    }

    public void exit() {
        menu.exitMenu();
    }

    public void showTheCollection() {
        if (menu.getStat() == MenuStat.SHOP) {
            view.printCollection(this.account.getCollection(), true);
        }
    }


    public void searchInCollection(Request request) {
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

    public void saveCollection() {

    }

    public void createDeck(Request request) {
        if (request.checkDeckSyntax() && menu.getStat() == MenuStat.COLLECTION) {
            view.createDeck(this.account.getCollection().createDeck(request.getDeckName(request.getCommand())));
        }
    }

    public void deleteDeck(Request request) {
        if (request.checkDeckSyntax() && menu.getStat() == MenuStat.COLLECTION) {
            view.deleteDeck(this.account.getCollection().deleteDeck(request.getDeckName(request.getCommand())));
        }
    }

    public void addToDeck(Request request) {
        if (request.checkToDeckAdditionSyntax() && menu.getStat() == MenuStat.COLLECTION) {
            view.addToCollection(this.account.getCollection().add(request.getDeckName(request.getCommand()), request.getObjectID(request.getCommand())));
        }
    }

    public void removeFromDeck(Request request) {
        if (request.checkFromDeckDeletionSyntax() && menu.getStat() == MenuStat.COLLECTION) {
            view.removeFromDeck(this.account.getCollection().remove(request.getDeckName(request.getCommand()), request.getObjectID(request.getCommand())));
        }
    }

    public void validateDeck(Request request) {
        if (request.checkValidationSyntax() && menu.getStat() == MenuStat.COLLECTION) {
            view.checkValidation(this.account.getCollection().validate(request.getDeckName(request.getCommand())));
        }
    }

    public void selectDeck(Request request) {
        if (request.checkDeckSelectionSyntax() && menu.getStat() == MenuStat.COLLECTION) {
            view.printDeckSelection(this.account.getCollection().selectDeck(request.getDeckName(request.getCommand())));
        }
    }

    public void showAllDecks(Request request) {
        if (request.checkShowAllDeckSyntax() && menu.getStat() == MenuStat.COLLECTION) {
            view.showAllDeck(this.account.getCollection().getDecks());
        }
    }

    public void showDeck(Request request) {
        if (request.checkShowDeckSyntax() && menu.getStat() == MenuStat.COLLECTION) {
            Collection collection = this.account.getCollection();
            try {
                view.printDeck(collection.getDecks().get(collection.deckExistance(request.getDeckName(request.getCommand()))));
            } catch (ArrayIndexOutOfBoundsException e) {
                view.printDeck(null);
            }
        }
    }

    public void search(Request request) {
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

    public void buy(Request request) {
        if (request.checkBuySyntax() && menu.getStat() == MenuStat.SHOP) {
            view.printBuyMessages(shop.buy(request.getObjectName(request.getCommand()), this.account));
        }
    }

    public void sell(Request request) {
        if (request.checkSellSyntax() && menu.getStat() == MenuStat.SHOP) {
            view.printSellMessages(shop.sell(request.getObjectID(request.getCommand()), this.account));
        }
    }

    public void showShop() {
        if (menu.getStat() == MenuStat.SHOP) {
            view.printCollection(new Collection(shop.getCards(), shop.getItems()), true);
        }
        if (menu.getStat() == MenuStat.COLLECTION) {
            view.printCollection(account.getCollection(), true);
        }
    }

    public void gameInfo() {
        if (menu.getStat() == MenuStat.BATTLE) {
            view.printGameInfo(battle);
        }
    }

    public void showMyMinions() {
        if (menu.getStat() == MenuStat.BATTLE) {
            view.printMinionsInfo(battle.getFieldCards()[battle.getTurnByAccount(this.account)]);
        }
    }

    public void showOppMinions() {
        if (menu.getStat() == MenuStat.BATTLE) {
            view.printMinionsInfo(battle.getFieldCards()[(battle.getTurnByAccount(this.account) + 1) % 2]);
        }
    }

    public void showCardInfo(Request request) {
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

    public void select(Request request) {
        if (request.checkCardSelectionSyntax() && menu.getStat() == MenuStat.BATTLE) {
            int turn = battle.getTurnByAccount(account);
            Card card = Card.getCardByID(request.getObjectID(request.getCommand()), battle.getFieldCards()[turn]);
            Item item = Item.getItemByID(request.getObjectID(request.getCommand()), battle.getCollectibles()[turn]);
            if (card != null)
                battle.selectCard(card.getId());
            else if (item != null)
                battle.selectItem(item.getId());
            else
                view.printUnsuccessfulSelection(battle.getCollectibles()[turn].length);
        }
    }

    public void moveToInBattle(Request request) {
        if (request.checkMoveSyntax() && menu.getStat() == MenuStat.BATTLE) {
            Coordinate coordinate = request.getCoordinate(request.getCommand());
            view.showMovement(battle.moveTo(coordinate));
        }
    }

    public void battleAttack(Request request) {
        if (request.checkAssaultSyntax() && menu.getStat() == MenuStat.BATTLE) {
            Card card = Card.getCardByID(request.getObjectID(request.getCommand()),
                    battle.getFieldCards()[(battle.getTurnByAccount(account) + 1) % 2]);
            view.showAttack(battle.attack(card.getId(), battle.getCurrentCard()));
        }
    }

    public void battleComboAttack(Request request) {
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

    public void useSpecialPower(Request request) {
        if (request.checkSPUsageSyntax() && menu.getStat() == MenuStat.BATTLE) {
            battle.setCurrentCoordinate(request.getCoordinate(request.getCommand()));

        }
    }

    public void showHand() {
        if (menu.getStat() == MenuStat.BATTLE) {
            view.printCards(battle.getPlayerHands()[battle.getTurnByAccount(account)]);
        }
    }

    public void insertCard(Request request) {
        if (request.checkCardInsertSyntax() && menu.getStat() == MenuStat.BATTLE) {
            view.printInsertionMessage(battle.insertCard(request.getCoordinate(request.getCommand()),
                    request.getInsertedName(request.getCommand())), battle);
        }
    }

    public void endTurn() {
        if (menu.getStat() == MenuStat.BATTLE) {
            battle.endTurn();
            view.endTurn();
        }
        turnCounter++;
    }

    public void showCollectables() {
        if (menu.getStat() == MenuStat.BATTLE) {
            view.printCollectables(battle.getCollectibles()[battle.getTurnByAccount(account)]);
        }
    }

    private void showCollectableInfo() {
        if (menu.getStat() == MenuStat.ITEM_SELECTION) {
            view.printItem(battle.getCurrentItem());
        }
    }

    public void useItem(Request request) {
        if (request.checkItemUseSyntax()) {
            if (menu.getStat() == MenuStat.BATTLE || menu.getStat() == MenuStat.ITEM_SELECTION) {
                battle.useItem(battle.getCurrentItem());
            }
        }
    }

    public void showNextCard() {
        if (menu.getStat() == MenuStat.BATTLE) {
            view.printCardInfo(account.getCollection().getMainDeck().getCards().get(0));
        }
    }

    public void specialPowerValidation() {
        if (menu.getStat() == MenuStat.BATTLE) {
            view.specialPowerValidation(battle.validSpecialPower());
        }
    }

    public void showCards() {
        if (menu.getStat() == MenuStat.GRAVEYARD) {
            view.printCards(battle.getGraveyard()[battle.getTurnByAccount(account)]);
        }
    }

    public void endGame() {
        if (menu.getStat() == MenuStat.BATTLE) {
            battle.endGame();
            view.endGame(battle);
        }
    }

    public void showMenu() {
        view.printOptions();
    }

/*    public void chooseBattleDetails(Request request) {
        GameType type;
        BattleMode mode;
        view.chooseMultiOrSingle();
        String multiOrSingle = request.getNewCommand();
        if (multiOrSingle.equals("Multiplayer")) {
            battle.setGameType(GameType.MULTI_PLAYER);
            Account[] accounts = new Account[2];
            if (battle.getAccounts().length == 1) {
                accounts[0] = battle.getAccounts()[0];
                accounts[1] = account;
                battle.setAccounts(accounts);
            }
            if (battle.getAccounts().length == 0) {
                accounts[0] = account;
                accounts[1] = null;
                battle.setAccounts(accounts);
            }
        } else if (multiOrSingle.equals("Singleplayer")) {
            Account[] accounts = new Account[2];
            accounts[0] = account;
            for (int i = 0; i < game.getAccounts().size(); i++) {
                if (game.getAccounts().get(i).getName().equals("powerfulAI")) {
                    accounts[1] = game.getAccounts().get(i);

                }
            }
            battle.setAccounts(accounts);
            battle.setGameType(GameType.SINGLE_PLAYER);
        } else {
            view.printInvalidCommand();
        }
        view.chooseBattleMode();
        String modeString = request.getNewCommand();
        switch (modeString) {
            case "KILLENEMYHERO":
                battle.setMode(BattleMode.KILLENEMYHERO);
                view.battleCreating();
                break;
            case "HOLD_FLAG":
                battle.setMode(BattleMode.HOLD_FLAG);
                view.battleCreating();
                break;
            case "COLLECT_FLAG":
                battle.setMode(BattleMode.COLLECT_FLAG);
                view.battleCreating();
                break;
            default:
                view.printInvalidCommand();
                break;
        }


    }*/
}
