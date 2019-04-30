package Controller;

import Model.*;
import View.*;

public class Controller {
    private View view = View.getInstance();
    private Game game = Game.getInstance();
    private Menu menu = Menu.getInstance();
    private Shop shop = Shop.getInstance();
    private Account account = new Account();
    private Battle battle = new Battle();
    private static final Controller controller = new Controller();

    private Controller() {

    }

    public static Controller getInstance() {
        return controller;
    }

    public void main() {
        Request request = new Request();
        request.getNewCommand();
        switch (request.getType()) {
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
                save();
                break;
            case LOGOUT:
                logout(request);
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
            case SHOW_COLLECTION_IN_SHOP:
                showCollectionShop(request);
                break;
            case SEARCH:
                searchInShop(request);
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
            case GAME_INFO:
                gameInfo();
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
                selectCardInBattle(request);
                break;
            case MOVE:
                moveToInBattle(request);
                break;
            case ATTACK:
                battleAttack(request);
                break;
        }
    }

    public void createAccount(Request request) {
        if (request.checkAccountCreationSyntax()) {
            String username = request.getCommand();
            view.passwordInsertion();
            String password = request.getNewCommand();
            Account account = new Account(request.getAccountName(username), request.getPassword(password));
            view.accountCreation(account.createAccount());
        }
    }

    public void login(Request request) {
        if (request.checkLoginSyntax()) {
            String username = request.getCommand();
            view.passwordInsertion();
            String password = request.getNewCommand();
            view.login(Account.login(request.getAccountName(username), request.getPassword(password)));
        }
    }

    public void showLeaderBoard(Request request) {
        if (request.checkLeaderBoardSyntax()) {
            view.printLeaderboard();
        }
    }

    public void save() {

    }

    public void logout(Request request) {
        if (request.checkLogoutSyntax()) {
            view.logout();
        }
    }

    public void help() {
        view.printHelp();
    }

    public void enter(Request request) {
        if (request.checkMenuEntrnaceSyntax()) {
            switch (request.getCommand()) {
                case "Exit":
                    exit();
                    break;
                case "Help":
                    help();
                    break;
                default:
                    menu.setStat(MenuStat.valueOf(request.getCommand().toUpperCase()));
            }
        }
    }

    public void exit() {
        menu.exitMenu();
    }

    public void showTheCollection() {
        view.printCollection(this.account.getCollection());
    }


    public void searchInCollection(Request request) {
        if (request.checkSearchSyntax()) {
            view.printId(Card.getAllCardsId(request.getObjectName(request.getCommand()),
                    this.account.getCollection().getCards().toArray(Card[]::new)).toArray(Card[]::new));
            view.printId(Item.getAllItemsId(request.getObjectName(request.getCommand()),
                    this.account.getCollection().getItems().toArray(Item[]::new)).toArray(Item[]::new));
        }
    }

    public void saveCollection() {

    }

    public void createDeck(Request request) {
        if (request.checkDeckSyntax()) {
            view.createDeck(this.account.getCollection().createDeck(request.getDeckName(request.getCommand())));
        }
    }

    public void deleteDeck(Request request) {
        if (request.checkDeckSyntax()) {
            view.deleteDeck(this.account.getCollection().deleteDeck(request.getDeckName(request.getCommand())));
        }
    }

    public void addToDeck(Request request) {
        if (request.checkToDeckAdditionSyntax()) {
            view.addToCollection(this.account.getCollection().add(request.getDeckName(request.getCommand()), request.getObjectID(request.getCommand())));
        }
    }

    public void removeFromDeck(Request request) {
        if (request.checkFromDeckDeletionSyntax()) {
            view.removeFromDeck(this.account.getCollection().remove(request.getDeckName(request.getCommand()), request.getObjectID(request.getCommand())));
        }
    }

    public void validateDeck(Request request) {
        if (request.checkValidationSyntax()) {
            view.checkValidation(this.account.getCollection().validate(request.getDeckName(request.getCommand())));
        }
    }

    public void selectDeck(Request request) {
        if (request.checkDeckSelectionSyntax()) {
            view.printDeckSelection(this.account.getCollection().selectDeck(request.getDeckName(request.getCommand())));
        }
    }

    public void showAllDecks(Request request) {
        if (request.checkShowAllDeckSyntax()) {
            view.showAllDeck(this.account.getCollection().getDecks());
        }
    }

    public void showDeck(Request request) {
        if (request.checkShowDeckSyntax()) {
            view.printDeck(this.account.getCollection().getDecks().get(
                    this.account.getCollection().deckExistance(request.getDeckName(request.getCommand()))));
        }
    }

    public void showCollectionShop(Request request) {
        view.printShopCollection(this.account.getCollection());
    }

    public void searchInShop(Request request) {
        if (request.checkSearchSyntax()) {
            Card card = Card.getCardByName(request.getObjectName(request.getCommand()),
                    shop.getCards().toArray(Card[]::new));
            Item item = Item.getItemByName(request.getObjectName(request.getCommand()),
                    shop.getItems().toArray(Item[]::new));
            if (card != null)
                view.printId(card);
            if (item != null)
                view.printId(item);
        }
    }

    public void buy(Request request) {
        if (request.checkBuySyntax()) {
            view.printBuyMessages(shop.buy(request.getObjectName(request.getCommand()), this.account));
        }
    }

    public void sell(Request request) {
        if (request.checkSellSyntax()) {
            view.printSellMessages(shop.sell(request.getObjectID(request.getCommand()), this.account));
        }
    }

    public void showShop() {
        view.printShopCollection(new Collection(shop.getCards(), shop.getItems()));
    }

    public void gameInfo() {

    }

    public void showMyMinions() {
        view.printMinionsInfo(battle.getFieldCards()[battle.getTurnByAccount(this.account)]);
    }

    public void showOppMinions() {
        view.printMinionsInfo(battle.getFieldCards()[(battle.getTurnByAccount(this.account) + 1) % 2]);
    }

    public void showCardInfo(Request request) {
        if (request.checkFetchInfoSyntax()) {
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

    public void selectCardInBattle(Request request) {
        if (request.checkCardSelectionSyntax()) {
            Card card = Card.getCardByID(request.getObjectID(request.getCommand()),
                    battle.getFieldCards()[battle.getTurnByAccount(account)]);
            view.printCardInfo(card);
        }
    }

    public void moveToInBattle(Request request) {
        if (request.checkMoveSyntax()) {
            Coordinate coordinate = request.getCoordinate(request.getCommand());
            view.showMovement(battle.moveTo(coordinate));
        }
    }

    public void battleAttack(Request request) {
        if (request.checkAssaultSyntax()) {
            Card card = Card.getCardByID(request.getObjectID(request.getCommand()),
                    battle.getFieldCards()[(battle.getTurnByAccount(account) + 1) % 2]);
            view.showAttack(card);
        }
    }

    public void battleComboAttack() {

    }

    public void useSpecialPower() {

    }

    public void showHand() {

    }

    public void insertCard() {

    }

    public void endTurn() {

    }

    public void showCollectables() {

    }

    public void selectCollectables() {

    }

    public void showBattleInfo() {

    }

    public void useItem() {

    }

    public void showNextCard() {

    }

    public void enterTheGraveyard() {

    }

    public void showCards() {

    }

    public void endGame() {

    }

    public void showMenu() {

    }
}
