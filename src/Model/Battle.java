package Model;

import View.Message;

import java.util.ArrayList;

public class Battle {
    private Card currentCard;
    private Card targetCard;
    private Item currentItem;
    private Account[] accounts = new Account[2];
    private Account currentPlayer;
    private Card[][] graveyard = new Card[2][];
    private Collectable[] collectables = new Collectable[2];
    private ArrayList<Collectable> battleCollectables = new ArrayList<>();
    private Card[][] playerHands = new Card[2][];
    private int turn;
    private int[][] field;
    private BattleMode mode;
    private GameType gameType;
    private Card[][] fieldCards = new Card[2][];

    public void gameInfo() {

    }

    public void showMyMininons() {

    }

    public void showOpponentMininos() {

    }

    public void showCardInfo(int cardId) {

    }

    public Message selectCard(int cardId) {

    }

    public Message moveTo(Coordinate coordinate) {

    }

    public Message attack(int opponentCardId) {

    }

    public Message attackCombo(int opponentCardId, int... myCardId) {

    }

    public Message useSpecialPower(Coordinate coordinate) {

    }

    public Message insertCard(Coordinate coordinate, String cardName) {

    }

    public void showHand() {

    }

    public void endTurn() {

    }

    public void showCollectables() {

    }

    public void showInfo() {

    }

    public void showNextCard() {

    }

    public Message selectCollectableId(int collectableId) {

    }

    public Message useItem(Coordinate coordinate) {

    }

    public void enterGraveyard() {

    }

    public Message showCardInfoInGraveyard(int cardId) {

    }

    public void showCard() {

    }

    public void endGame() {

    }

    public void exit() {

    }

}
