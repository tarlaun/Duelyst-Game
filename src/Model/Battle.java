package Model;

import View.Message;
import View.View;

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
    /*
        private Collectable[][] collectables = new Collectable[2][];
        private ArrayList<Collectable> battleCollectables = new ArrayList<>();
    */
    private Card[][] playerHands = new Card[2][];
    private int turn;
    private int[][] field;
    private BattleMode mode;
    private GameType gameType;
    private Card[][] fieldCards = new Card[2][];
    private Menu menu = new Menu();
    private View view = new View();
    private final int length = 9;
    private final int width = 5;


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

/*
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
*/

    public Message attack(int opponentCardId, Card currentCard) {
        targetCard = Card.getCardByID(opponentCardId, fieldCards[(turn + 1) % 2]);
        if (targetCard.equals(null))
            return Message.INVALID_TARGET;
        if (!isInRange(targetCard, currentCard)) {
            return Message.UNAVAILABLE;
        }
        if (!currentCard.isAbleToAttack()) {
            if (targetCard.isAbleToAttack()) {
                return Message.NOT_ABLE_TO_ATTACK;
            } else {
                return null;
            }
        }
        currentCard.setAbleToAttack(false);
        targetCard.modifyHealth(-currentCard.getAssaultPower());
        attack(currentCard.getId(), targetCard);
        killEnemy(targetCard);
        return null;
    }

    private void killEnemy(Card targetCard) {
        if (targetCard.getHealthPoint() <= 0) {
            ArrayList<Card> opponentFieldCards = new ArrayList<>(Arrays.asList(fieldCards[(turn + 1) % 2]));
            opponentFieldCards.remove(targetCard);
            for (int i = 0; i < opponentFieldCards.size(); i++) {
                fieldCards[(turn + 1) % 2][i] = opponentFieldCards.get(i);
            }
            fieldCards[(turn + 1) % 2][opponentFieldCards.size()] = null;
        }
    }

    public Message attackCombo(int opponentCardId, Card... cards) {
        targetCard = Card.getCardByID(opponentCardId, fieldCards[(turn + 1) % 2]);
        if (targetCard.equals(null))
            return Message.INVALID_TARGET;
        for (Card card : cards) {
            if (!isInRange(targetCard, card)) {
                return Message.UNAVAILABLE;
            }
        }
        useSpecialPowerForCombo(cards);
        for (Card card : cards) {
            attack(opponentCardId, card);
        }
        return null;
    }

    public boolean isInRange(Card targetCard, Card currentCard) {
        if (Coordinate.getManhattanDistance(targetCard.getCoordinate(), currentCard.getCoordinate())
                > currentCard.getMaxRange() ||
                Coordinate.getManhattanDistance(targetCard.getCoordinate(), currentCard.getCoordinate())
                        < currentCard.getMinRange())
            return false;
        return true;
    }

    public void useSpecialPowerForCombo(Card... cards) {

    }

/*
    public Message useSpecialPower(Coordinate coordinate) {

    }

    public Message insertCard(Coordinate coordinate, String cardName) {
        boolean validTarget = false;
        for (int i = 0; i < 5; i++) {
            if (playerHands[turn % 2][i].getName().equals(cardName)) {
                Card insert = Card.getCardByName(cardName, playerHands[turn % 2]);
                if (field[coordinate.getX()][coordinate.getY()] != 0) {
                    return Message.INVALID_TARGET;
                }
                for (Card card :
                        fieldCards[turn % 2]) {
                    if (Coordinate.getManhattanDistance(card.getCoordinate(), coordinate) == 1) {
                        validTarget = true;
                        break;
                    }
                }
                if (!validTarget) {
                    return Message.INVALID_TARGET;
                }
                field[coordinate.getX()][coordinate.getY()] = insert.getId();
                insert.setCoordinate(coordinate);
                playerHands[turn % 2] = Card.removeFromArray(playerHands[turn % 2], insert);
                fieldCards[turn % 2] = Card.addToArray(fieldCards[turn % 2], insert);
                return null;


            }
        }
        return Message.NOT_IN_HAND;
    }
*/

    public void showHand() {

    }

    public void endTurn() {
        turn++;
        for (Card card :
                fieldCards[0]) {
            card.setAbleToAttack(true);
            card.setAbleToMove(true);
        }
        currentCard = null;
        targetCard = null;


    }

    public void showCollectables() {

    }

/*
    public void showInfo(int objectId) {
        if (menu.getStat() == MenuStat.ITEM_SELECTION) {
            Item.getItemByID(objectId, collectables[turn % 2]);
        }
    }
*/

    public void showNextCard() {
        showCardInfo(accounts[turn % 2].getCollection().getMainDeck().getCards().get(0).getId());
    }

    /*
        public Message selectCollectableId(int collectableId) {
            for (Collectable collectable :
                    collectables[turn % 2]) {
                if (collectable.getId() == collectableId) {
                    menu.setStat(MenuStat.ITEM_SELECTION);
                }
            }

        }

    */
/*
    public boolean useItem(Coordinate coordinate) {
        if (menu.getStat() != MenuStat.ITEM_SELECTION)
            return false;
    }
*/
    /*
    public Message useSpecialPower(Coordinate coordinate) {

    }

    public Message insertCard(Coordinate coordinate, String cardName) {
        boolean validTarget = false;
        for (int i = 0; i < 5; i++) {
            if (playerHands[turn % 2][i].getName().equals(cardName)) {
                Card insert = Card.getCardByName(cardName, playerHands[turn % 2]);
                if (field[coordinate.getX()][coordinate.getY()] != 0) {
                    return Message.INVALID_TARGET;
                }
                for (Card card :
                        fieldCards[turn % 2]) {
                    if (Coordinate.getManhattanDistance(card.getCoordinate(), coordinate) == 1) {
                        validTarget = true;
                        break;
                    }
                }
                if (!validTarget) {
                    return Message.INVALID_TARGET;
                }
                field[coordinate.getX()][coordinate.getY()] = insert.getId();
                insert.setCoordinate(coordinate);
                playerHands[turn % 2] = Card.removeFromArray(playerHands[turn % 2], insert);
                fieldCards[turn % 2] = Card.addToArray(fieldCards[turn % 2], insert);
                return null;


            }
        }
        return Message.NOT_IN_HAND;
    }



    }
*/
    public void enterGraveyard() {
        menu.setStat(MenuStat.GRAVEYARD);
    }

/*
    public Message showCardInfoInGraveyard(int cardId) {

    }
*/

    public void showCard() {

    }

    public void endGame() {

    }

    public void exit() {

    }

    public boolean useSpell(Spell spell, Coordinate target) {
        for (Buff buff : spell.getBuffs()) {
            switch (buff.getEffectArea().get(0).getX()) {
                case 0:
                    for (Coordinate coordinate : buff.getEffectArea()) {
                        switch (buff.getSide()) {
                            case COMRADE:
                                for (Card card : fieldCards[turn % 2]) {
                                    if (card.isClass(buff.getTargetType())
                                            || card.getCoordinate().equals(target.sum(coordinate)))
                                        continue;
                                    applyBuff(buff, card);
                                    return true;
                                }
                                break;
                            case ENEMY:
                                for (Card card : fieldCards[(turn + 1) % 2]) {
                                    if (card.isClass(buff.getTargetType())
                                            || card.getCoordinate().equals(target.sum(coordinate)))
                                        continue;
                                    applyBuff(buff, card);
                                    return true;
                                }
                                break;
                            default:
                                for (Card[] cards : fieldCards) {
                                    for (Card card : cards) {
                                        if (card.isClass(buff.getTargetType())
                                                || card.getCoordinate().equals(target.sum(coordinate)))
                                            continue;
                                        applyBuff(buff, card);
                                        return true;

                                    }
                                }
                        }
                    }
                    break;
                case Constants.ROW:
                    switch (buff.getSide()) {
                        case COMRADE:
                            for (Card card : fieldCards[turn % 2]) {
                                if (card.isClass(buff.getTargetType())
                                        || card.getCoordinate().getY() != target.getY())
                                    continue;
                                applyBuff(buff, card);
                                return true;
                            }
                            break;
                        case ENEMY:
                            for (Card card : fieldCards[(turn + 1) % 2]) {
                                if (card.isClass(buff.getTargetType())
                                        || card.getCoordinate().getY() != target.getY())
                                    continue;
                                applyBuff(buff, card);
                                return true;
                            }
                            break;
                        default:
                            for (Card[] cards : fieldCards) {
                                for (Card card : cards) {
                                    if (card.isClass(buff.getTargetType())
                                            || card.getCoordinate().getY() != target.getY())
                                        continue;
                                    applyBuff(buff, card);
                                    return true;

                                }
                            }

                    }
                    break;
                case Constants.COLUMN:
                    switch (buff.getSide()) {
                        case COMRADE:
                            for (Card card : fieldCards[turn % 2]) {
                                if (card.isClass(buff.getTargetType())
                                        || card.getCoordinate().getX() != target.getX())
                                    continue;
                                applyBuff(buff, card);
                                return true;
                            }
                            break;
                        case ENEMY:
                            for (Card card : fieldCards[(turn + 1) % 2]) {
                                if (card.isClass(buff.getTargetType())
                                        || card.getCoordinate().getX() != target.getX())
                                    continue;
                                applyBuff(buff, card);
                                return true;
                            }
                            break;
                        default:
                            for (Card[] cards : fieldCards) {
                                for (Card card : cards) {
                                    if (card.isClass(buff.getTargetType())
                                            || card.getCoordinate().getX() != target.getX())
                                        continue;
                                    applyBuff(buff, card);
                                    return true;

                                }
                            }
                    }
                    break;
                case Constants.ALL_FIELD:
                    switch (buff.getSide()) {
                        case COMRADE:
                            for (Card card : fieldCards[turn % 2]) {
                                if (card.isClass(buff.getTargetType()))
                                    continue;
                                applyBuff(buff, card);
                                return true;
                            }
                            break;
                        case ENEMY:
                            for (Card card : fieldCards[(turn + 1) % 2]) {
                                if (card.isClass(buff.getTargetType()))
                                    continue;
                                applyBuff(buff, card);
                                return true;
                            }
                            break;
                        default:
                            for (Card[] cards : fieldCards) {
                                for (Card card : cards) {
                                    if (card.isClass(buff.getTargetType()))
                                        continue;
                                    applyBuff(buff, card);
                                    return true;

                                }
                            }
                    }
                    break;
            }
        }
        return false;
    }

    public void applyBuff(Buff buff, Card card) {
        card.getCastedBuffs().add(buff);
        switch (buff.getType()) {
            case POISON:
                card.modifyHealth(buff.getPower());
            case STUN:
                card.setAbleToAttack(false);
                card.setAbleToMove(false);
                break;
            case DISARM:
                card.setAbleToCounter(false);
                break;
            case HIT_POWER:
                card.modifyHit(buff.getPower());
                break;
            case HEALTH_POWER:
                card.modifyHealth(buff.getPower());
                break;
            case HIT_WEAKNESS:
                card.modifyHit(buff.getPower());
                break;
            case HEALTH_WEAKNESS:
                card.modifyHealth(buff.getPower());
                break;
        }
    }

}
