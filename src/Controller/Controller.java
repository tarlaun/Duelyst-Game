package Controller;

import Model.Account;
import Model.Battle;
import Model.Game;
import Model.Menu;
import View.*;

public class Controller {
    private View view = View.getInstance();
    private Game game = Game.getInstance();
    private Menu menu = Menu.getInstance();
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
        if(request.checkLeaderBoardSyntax()){
            view.printLeaderboard();
        }
    }

    public void save() {

    }

    public void logout(Request request) {
        if(request.checkLogoutSyntax()){
            view.logout();
        }
    }

    public void help() {
        switch (menu.getStat()){
            case MAIN:
                view.printMainMenu();
                break;
            case BATTLE:
                view.printBattleHelp();
                break;
            case ACCOUNT:
                view.accountHelp();
                break;
            case GRAVEYARD:
                view.printGraveyardMenu();
                break;
            case SHOP:
                view.printShopHelp();
                break;

        }
    }

    public void enter(Request request) {

    }


    public void exitBattle() {

    }

    public void exitColletion() {

    }

    public void showTheCollection() {

    }

    public void searchInCollection() {

    }

    public void saveCollection() {

    }

    public void createDeck() {

    }

    public void deleteDeck() {

    }

    public void addToCollection() {

    }

    public void removeFromCollection() {

    }

    public void validateDeck() {

    }

    public void selectDeck() {

    }

    public void showAllDecks() {

    }

    public void showDeck() {

    }

    public void helpCollection() {

    }

    public void exitShop() {

    }

    public void showCollectionShop() {

    }

    public void searchInShop() {

    }

    public void buy() {

    }

    public void sell() {

    }

    public void showShop() {

    }

    public void helpShop() {

    }

    public void gameInfo() {

    }

    public void showMyMinions() {

    }

    public void showOppMinions() {

    }

    public void selectCardInBattle() {

    }

    public void moveToInBattle() {

    }

    public void battleAttack() {

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

    public void showCardInfo() {

    }

    public void showCards() {

    }

    public void battleHelp() {

    }

    public void endGame() {

    }

    public void showMenu() {

    }
}
