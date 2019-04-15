package Model;

import View.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public boolean selectCard(int cardId) {
        Card card = Card.getCardByID(cardId, fieldCards[turn % 2]);
        if (card.equals(null)) {
            return false;
        }
        currentCard = card;
        return true;

    }

    public boolean moveTo(Coordinate coordinate) {
        if (currentCard.getCoordinate() == coordinate) {
            return true;
        }
        if (Coordinate.getManhattanDistance(currentCard.getCoordinate(), coordinate) > currentCard.getMaxPossibleMoving()) {
            return false;
        }
        if (Coordinate.getPathDirections(coordinate, currentCard.getCoordinate()).length == 0) {
            return false;
        }
        field[currentCard.getCoordinate().getX()][currentCard.getCoordinate().getY()] = 0;
        currentCard.setCoordinate(Coordinate.getPathDirections(coordinate, currentCard.getCoordinate())[0]);
        field[currentCard.getCoordinate().getX()][currentCard.getCoordinate().getY()] = currentCard.getId();
        moveTo(coordinate);
        return true;

    }

    public Message attack(int opponentCardId, Card currentCard) {
        targetCard = Card.getCardByID(opponentCardId, fieldCards[(turn  + 1)%2]);
        if (targetCard.equals(null))
            return Message.INVALID_TARGET;
        if (!isInRange(targetCard,currentCard)) {
            return Message.UNAVAILABLE;
        }
        if (!currentCard.isAbleToAttack()) {
            if (targetCard.isAbleToAttack()) {
                return Message.NOT_ABLE_TO_ATTACK;
            }else {
                return null;
            }
        }
        currentCard.setAbleToAttack(false);
        targetCard.decreaseHealth(currentCard.getAssaultPower());
        attack(currentCard.getId() , targetCard);
        killEnemy(targetCard);
        return null ;
    }

    private void killEnemy(Card targetCard ){
        if(targetCard.getHealthPoint()<=0){
            ArrayList<Card> opponentFieldCards= new ArrayList<>(Arrays.asList(fieldCards[(turn+1)%2]));
            opponentFieldCards.remove(targetCard);
            for (int i=0; i< opponentFieldCards.size() ; i++){
                fieldCards[(turn+1)%2][i] = opponentFieldCards.get(i);
            }
            fieldCards[(turn+1)%2][opponentFieldCards.size()]=null;
        }
    }

    public Message attackCombo(int opponentCardId, Card... cards) {
        targetCard = Card.getCardByID(opponentCardId, fieldCards[(turn + 1)%2]);
        if (targetCard.equals(null))
            return Message.INVALID_TARGET;
        for (Card card: cards) {
            if (!isInRange(targetCard,card)) {
                return Message.UNAVAILABLE;
            }
        }
       useSpecialPowerForCombo(cards);
        for (Card card:cards) {
            attack(opponentCardId,card);
        }
        return null;
    }

    public boolean isInRange(Card targetCard , Card currentCard){
        if (Coordinate.getManhattanDistance(targetCard.getCoordinate(), currentCard.getCoordinate())
                > currentCard.getMaxRange() ||
                Coordinate.getManhattanDistance(targetCard.getCoordinate(), currentCard.getCoordinate())
                        < currentCard.getMinRange())
            return false;
        return true;
    }

    public void useSpecialPowerForCombo(Card ... cards){

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
