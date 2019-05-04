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
                useSpecialPower(request);
                break;
            case SHOW_HAND:
                showHand();
                break;
            case INSERTION:
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

    public void createAccount(Request request) {
        if (request.checkAccountCreationSyntax() && menu.getStat() == MenuStat.MAIN) {
            String username = request.getCommand();
            view.passwordInsertion();
            String password = request.getNewCommand();
            Account account = new Account(request.getAccountName(username), request.getPassword(password));
            view.accountCreation(account.createAccount());
        }
    }

    public void login(Request request) {
        if (request.checkLoginSyntax() && menu.getStat() == MenuStat.MAIN) {
            String username = request.getCommand();
            view.passwordInsertion();
            String password = request.getNewCommand();
            view.login(Account.login(request.getAccountName(username), request.getPassword(password)));
        }
    }

    public void showLeaderBoard(Request request) {
        if (request.checkLeaderBoardSyntax() && menu.getStat() == MenuStat.ACCOUNT) {
            view.printLeaderboard();
        }
    }

    public void save() {
        if (menu.getStat() == MenuStat.ACCOUNT) {

        }
    }

    public void logout() {
        if (menu.getStat() == MenuStat.ACCOUNT) {
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
        if (menu.getStat() == MenuStat.COLLECTION && menu.getStat() == MenuStat.SHOP)
            view.printCollection(this.account.getCollection());
    }


    public void searchInCollection(Request request) {
        if (request.checkSearchSyntax()) {
            if (menu.getStat() == MenuStat.COLLECTION || menu.getStat() == MenuStat.SHOP) {
                view.printId(Card.getAllCardsId(request.getObjectName(request.getCommand()),
                        this.account.getCollection().getCards().toArray(Card[]::new)).toArray(Card[]::new));
                view.printId(Item.getAllItemsId(request.getObjectName(request.getCommand()),
                        this.account.getCollection().getItems().toArray(Item[]::new)).toArray(Item[]::new));
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
            view.printDeck(this.account.getCollection().getDecks().get(
                    this.account.getCollection().deckExistance(request.getDeckName(request.getCommand()))));
        }
    }

    public void search(Request request) {
        if (request.checkSearchSyntax()) {
            if (menu.getStat() == MenuStat.COLLECTION) {
                searchInCollection(request);
            } else if (menu.getStat() == MenuStat.SHOP) {
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
            view.printShopCollection(new Collection(shop.getCards(), shop.getItems()));
        }
    }

    public void gameInfo() {
        if (menu.getStat() == MenuStat.BATTLE) {

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
        if (request.checkFetchInfoSyntax() && menu.getStat() == MenuStat.BATTLE) {
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
            Item item = Item.getItemByID(request.getObjectID(request.getCommand()), battle.getCollectables()[turn]);
            if (card != null)
                battle.selectCard(card.getId());
            else if (item != null)
                battle.selectItem(item.getId());
            else
                view.printUnsuccessfulSelection(battle.getCollectables()[turn].length);
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
            view.showAttack(battle.attack(card.getId(),
                    battle.getCurrentCard()));
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
            view.showCombo(oppId, cards);
        }
    }

    public void useSpecialPower(Request request) {
        if (request.checkSPUsageSyntax() && menu.getStat() == MenuStat.BATTLE) {
            Coordinate target = request.getCoordinate(request.getCommand());

        }
    }

    public void showHand() {
        if (menu.getStat() == MenuStat.BATTLE) {
            view.printHand(battle.getPlayerHands()[battle.getTurnByAccount(account)]);
        }
    }

    public void insertCard(Request request) {
        if (request.checkCardInsertSyntax() && menu.getStat() == MenuStat.BATTLE) {
            view.printInsertionMessage(battle.insertCard(request.getCoordinate(request.getCommand()),
                    request.getInsertedName(request.getCommand())));
        }
    }

    public void endTurn() {

    }

    public void showCollectables() {

    }

    public void selectCollectables(Request request) {

    }

    private void showCollectableInfo() {

    }

    public void useItem(Request request) {

    }

    public void showNextCard() {

    }

    public void showCards() {

    }

    public void endGame() {

    }

    public void showMenu() {

    }
}
