package Model;

import View.Message;
import View.View;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Battle {
    private Card currentCard;
    private Card targetCard;
    private Coordinate currentCoordinate;
    private Item currentItem;
    private Account[] accounts = new Account[2];
    private Account currentPlayer;
    private Card[][] graveyard = new Card[2][];
    private Item[][] collectables = new Collectable[2][];
    private ArrayList<Item> battleCollectables = new ArrayList<>();
    private Card[][] playerHands = new Card[2][];
    private int turn;
    private Cell[][] field;
    private BattleMode mode;
    private GameType gameType;
    private Card[][] fieldCards = new Card[2][];
    private Menu menu = Menu.getInstance();
    private Shop shop = Shop.getInstance();
    private Random rand = new Random();
    private Match firstPlayerMatch = new Match();
    private Match secondPlayerMatch = new Match();
    private ArrayList<Flag> flagsOnTheGround = new ArrayList<>();
    private int flagsAppeared = 0;
    private Flag mainFlag = new Flag();

    public Battle(Account[] accounts, GameType gameType, BattleMode mode) {
        this.accounts = accounts;
        this.gameType = gameType;
        this.mode = mode;
    }

    public boolean checkForWin() {

        boolean firstPlayerWon = false;
        boolean secondPlayerWon = false;
        switch (mode) {
            case KILL_OPPONENT_HERO:
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < fieldCards[i].length; j++) {
                        if (fieldCards[i][j] instanceof Hero && fieldCards[i][j].getHealthPoint() <= 0) {
                            if (i == 0) secondPlayerWon = true;
                            if (i == 1) firstPlayerWon = true;
                        }
                    }
                }
                break;
            case HOLD_FLAG:
                if (mainFlag.getTurnCounter() >= Constants.TURNS_HOLDING_FLAG) {
                    if (mainFlag.getAccount().equals(accounts[0])) {
                        firstPlayerWon = true;
                    }
                    if (mainFlag.getAccount().equals(accounts[1])) {
                        secondPlayerWon = true;
                    }
                }

                break;
            case COLLECT_FLAG:
                for (int i = 0; i < 2; i++) {
                    if (accounts[i].getFlagsCollected() >= Constants.MAXIMUM_FLAGS / 2) {
                        if (i == 1) secondPlayerWon = true;
                        if (i == 0) firstPlayerWon = true;
                    }
                }
                break;
        }
        if (firstPlayerWon) {
            if (secondPlayerWon) {
                firstPlayerMatch.setResult(MatchResult.TIE);
                secondPlayerMatch.setResult(MatchResult.TIE);
                accounts[0].setBudget(accounts[0].getBudget() + 500);
                accounts[1].setBudget(accounts[0].getBudget() + 500);
                setMatchInfo();
                return true;
            }
            firstPlayerMatch.setResult(MatchResult.WON);
            secondPlayerMatch.setResult(MatchResult.LOST);
            accounts[0].setBudget(accounts[0].getBudget() + 1000);
            setMatchInfo();
            return true;
        } else if (secondPlayerWon) {
            firstPlayerMatch.setResult(MatchResult.LOST);
            secondPlayerMatch.setResult(MatchResult.WON);
            accounts[1].setBudget(accounts[0].getBudget() + 1000);
            setMatchInfo();
        }
        return false;
    }

    public void setMatchInfo() {
        firstPlayerMatch.setTime(LocalDateTime.now());
        secondPlayerMatch.setTime(LocalDateTime.now());
        firstPlayerMatch.setRival(accounts[1].getName());
        secondPlayerMatch.setRival(accounts[0].getName());
    }

    public void resign() {
        if ((turn % 2) == 0) {
            firstPlayerMatch.setResult(MatchResult.LOST);
            secondPlayerMatch.setResult(MatchResult.WON);
            accounts[1].setBudget(accounts[0].getBudget() + 1000);
            setMatchInfo();
        }
        if ((turn % 2) == 1) {
            firstPlayerMatch.setResult(MatchResult.WON);
            secondPlayerMatch.setResult(MatchResult.LOST);
            accounts[0].setBudget(accounts[0].getBudget() + 1000);
            setMatchInfo();
        }
    }

    public Coordinate getCurrentCoordinate() {
        return currentCoordinate;
    }

    private final int length = 9;
    private final int width = 5;

    public void setCurrentCoordinate(Coordinate currentCoordinate) {
        this.currentCoordinate = currentCoordinate;
    }

    public Cell getField(int x, int y) {
        return field[x][y];
    }

    public Card[][] getFieldCards() {
        return fieldCards;
    }

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
    public boolean isAttackable(Card currentCard, Card targetCard) {
        if (targetCard.getName().equals("GIV")) {
            return false;
        }
        if (targetCard.getName().equals("ASHKBOOS") && targetCard.getAssaultPower() > currentCard.getAssaultPower()) {
            return false;
        }
        return true;
    }

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
        checkAttackHistory(opponentCardId, currentCard);
        onAttackSpecialPower();
        currentCard.setAbleToAttack(false);
        targetCard.modifyHealth(-currentCard.getAssaultPower());
        if (isAttackable(currentCard, targetCard))
            targetCard.setHealthPoint(targetCard.getHealthPoint() - targetCard.getIsHoly());
        targetCard.modifyHealth(-currentCard.getAssaultPower());
        attack(currentCard.getId(), targetCard);
        killEnemy(targetCard);
        return null;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public Card getTargetCard() {
        return targetCard;
    }

    public Item getCurrentItem() {
        return currentItem;
    }

    public Account[] getAccounts() {
        return accounts;
    }

    public Account getCurrentPlayer() {
        return currentPlayer;
    }

    public Card[][] getGraveyard() {
        return graveyard;
    }

    public Collectable[][] getCollectables() {
        return collectables;
    }

    public ArrayList<Collectable> getBattleCollectables() {
        return battleCollectables;
    }

    public Card[][] getPlayerHands() {
        return playerHands;
    }

    public int getTurn() {
        return turn;
    }

    public Cell[][] getField() {
        return field;
    }

    public BattleMode getMode() {
        return mode;
    }

    public GameType getGameType() {
        return gameType;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setManaPoints() {
        if (turn <= 7) {
            accounts[0].setMana(turn + 1);
            accounts[1].setMana(turn + 2);
        } else {
            accounts[0].setMana(Constants.MAX_MANA);
            accounts[0].setMana(Constants.MAX_MANA);
        }
    }

    public boolean spendMana(int price) {
        if (accounts[turn % 2].getMana() < price) {
            return false;
        }
        accounts[turn % 2].setMana(accounts[turn % 2].getMana() - price);
        return true;
    }

    public boolean spellIsReady(Buff buff) {
        if (buff.getTurnCount() > 0) {
            return false;
        }
        return true;
    }


    private void checkAttackHistory(int opponentCardId, Card currentCard) {
        boolean newMinion = true;
        int emptyCell = -1;

        if (currentCard instanceof Minion) {
            for (int i = 0; i < 40; i++) {
                if (opponentCardId == ((Minion) currentCard).getAttackCount(opponentCardId)) {
                    ((Minion) currentCard).setAttackCount(i, 1, ((Minion) currentCard).getAttackCount(opponentCardId) + 1);
                    newMinion = false;
                    break;
                }
                if (((Minion) currentCard).getAttackCount(currentCard.getId()) == 0) {
                    emptyCell = i;
                    break;
                }
            }
            if (newMinion) {
                ((Minion) currentCard).setAttackCount(emptyCell, 0, opponentCardId);
                ((Minion) currentCard).setAttackCount(emptyCell, 1, 1);
            }
        }
    }

    private void killEnemy(Card targetCard) {
        if (targetCard.getHealthPoint() <= 0) {
            if (targetCard.getBuffs().size() == 1 && targetCard.getBuffs().get(0).getActivationType().equals(ActivationType.ON_DEATH)) {
                useSpecialPower(targetCard, targetCard.getBuffs().get(0));
            }
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

        if (!useSpecialPowerForCombo(cards)) {
            return Message.NOT_ABLE_TO_ATTACK;
        }
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
        if (currentCard.getRangeType().equals(RangeType.MELEE)) {
            if (Math.abs(currentCard.getCoordinate().getY() - targetCard.getCoordinate().getY()) > 1 ||
                    Math.abs(currentCard.getCoordinate().getX() - targetCard.getCoordinate().getX()) > 1) {
                return false;
            }
        }
        return true;
    }

    public Message holifyCell(Coordinate coordinate) {
        Cell cell = field[coordinate.getX()][coordinate.getY()];
        if (!cell.isHoly()) {
            cell.setHoly(true);
            cell.setHolyTurn(3);
            return null;
        } else {
            return Message.INVALID_TARGET;
        }
    }

    public boolean useSpecialPowerForCombo(Card... cards) {
        for (Card card : cards) {
            if (!(card.getName().equals("PERSIAN_COMMANDER")) && !(card.getName().equals("TURANIAN_PRINCE")) &&
                    !(card.getName().equals("SHAGHUL")) && !(card.getName().equals("ARZHANG"))) {
                return false;
            }
        }
        return true;
    }

    public Message validSpecialPower(Coordinate coordinate) {

        if (getField(coordinate.getX(), coordinate.getY()).getCardID() == 0)
            return Message.INVALID_TARGET;
        Card card = Card.getCardByID(getField(coordinate.getX(), coordinate.getY()).getCardID(), fieldCards[turn % 2]);
        if (card == null)
            return Message.OBJECT_NOT_FOUND;
        if (card.getBuffs().size() == 0) {
            return Message.NOT_ABLE_TO_ATTACK;
        }
        useSpecialPower(card, card.getBuffs().get(0));
        return null;
    }

    private void onAttackSpecialPower() {
        switch (currentCard.getName()) {
            case "PERSIAN_SWORDS_WOMAN":
                targetCard.setAbleToAttack(false);
                targetCard.setAbleToMove(false);
                targetCard.addToBuffs(currentCard.getBuffs().get(0));
                break;
            case "PERSIAN_CHAMPION":
                int multiply = ((Minion) currentCard).getAttackCount(targetCard.getId()) * 5;
                targetCard.modifyHealth(-multiply);
                break;
            case "TURANIAN_SPY":
                if (!targetCard.getName().equals("WILD_HOG")) {
                    targetCard.setAbleToAttack(false);
                    targetCard.addToBuffs(currentCard.getBuffs().get(0));
                }
                if (!targetCard.getName().equals("PIRAN")) {
                    targetCard.addToBuffs(currentCard.getBuffs().get(1));
                }
                break;
            case "VENOM_SNAKE":
                if (!targetCard.getName().equals("PIRAN")) {
                    targetCard.addToBuffs(currentCard.getBuffs().get(0));
                }
                break;
            case "LION":
                for (Buff buff : targetCard.getCastedBuffs()) {
                    if (buff.getType().equals(BuffType.HOLY) && buff.getPower() > 0) {
                        targetCard.modifyHealth(1);
                    }
                }
                break;
            case "WHITE_WOLF":
                targetCard.addToBuffs(currentCard.getBuffs().get(0));
                break;
            case "PALANG":
                targetCard.addToBuffs(currentCard.getBuffs().get(0));
                break;

            case "WOLF":
                targetCard.addToBuffs(currentCard.getBuffs().get(0));
                break;
            case "ZAHAK":
                targetCard.addToBuffs(currentCard.getBuffs().get(0));
                break;
            case "TWO_HEADED_GIANT":
                for (Buff buff : targetCard.getCastedBuffs()) {
                    if (buff.getType().equals(BuffType.HOLY) || buff.getType().equals(BuffType.POWER)) {
                        targetCard.removeFromBuffs(buff);
                    }
                }
                break;
        }
    }

    private void useSpecialPower(Card card, Buff buff) {
        int r = 0;
        switch (buff.getType()) {
            case HOLY:
                switch (card.getName()) {
                    case "FOOLADZEREH":
                        card.addToBuffs(buff);
                        break;
                    case "KAVEH":
                        if (spendMana(card.getManaPoint()) && spellIsReady(buff)) {
                            holifyCell(currentCoordinate);
                        }

                        break;
                    case "ESFANDIAR":
                        card.addToBuffs(buff);
                        break;
                }
                break;
            case STUN:
                switch (card.getName()) {
                    case "SIMORGH":
                        if (spendMana(card.getManaPoint()) && spellIsReady(buff)) {
                            for (Card enemy :
                                    fieldCards[(turn + 1) % 2]) {
                                enemy.addToBuffs(buff);
                                enemy.setAbleToMove(false);
                                enemy.setAbleToAttack(false);
                            }
                        }
                        break;
                    case "RAKHSH": //we need to choose a target here
                        if (spendMana(card.getManaPoint()) && spellIsReady(buff)) {
                            targetCard.addToBuffs(buff);
                            targetCard.setAbleToAttack(false);
                            targetCard.setAbleToMove(false);
                        }

                        break;
                    case "NANE_SARMA":
                        for (int i = -1; i < 2; i++) {
                            for (int j = -1; j < 2; j++) {
                                if (getField(card.getCoordinate().getX() + i, card.getCoordinate().getY() + j).getCardID() != 0) {
                                    Card target = Card.getCardByID(getField(card.getCoordinate().getX() + i, card.getCoordinate().getY() + j).getCardID(), fieldCards[(turn + 1) % 2]);
                                    if (target != null) {
                                        target.addToBuffs(card.getBuffs().get(0));
                                        target.setAbleToAttack(false);
                                        target.setAbleToMove(false);
                                    }
                                }
                            }
                        }

                        break;
                }
                break;
            case POWER:
                switch (card.getName()) {
                    case "WHITE_DIV":
                        if (spendMana(card.getManaPoint()) && spellIsReady(buff)) {
                            card.addToBuffs(buff);
                        }
                        break;
                    case "EAGLE":
                        card.addToBuffs(card.getBuffs().get(0));
                        break;
                    case "WITCH":
                    case "NANE_WITCH":
                        card.addToBuffs(card.getBuffs().get(0));
                        for (int i = -1; i < 2; i++) {
                            for (int j = -1; j < 2; j++) {
                                if (getField(card.getCoordinate().getX() + i, card.getCoordinate().getY() + j).getCardID() != 0) {
                                    Card target = Card.getCardByID(getField(card.getCoordinate().getX() + i, card.getCoordinate().getY() + j).getCardID(), fieldCards[turn % 2]);
                                    if (target != null) {
                                        target.addToBuffs(card.getBuffs().get(0));
                                        target.addToBuffs(card.getBuffs().get(1));
                                    }
                                }
                            }
                        }
                        break;
                    case "JEN":
                        break;
                }
                break;
            case DISARM:

                switch (card.getName()) {
                    case "SEVEN_HEADED_DRAGON":
                        if (spendMana(card.getManaPoint()) && spellIsReady(buff)) { // we need to choose a target here
                            targetCard.addToBuffs(buff);
                            targetCard.setAbleToAttack(false);
                        }
                        break;

                }
                break;
            case WEAKNESS:

                switch (card.getName()) {
                    case "ARASH":
                        if (spendMana(card.getManaPoint()) && spellIsReady(buff)) {
                            for (Card target :
                                    fieldCards[(turn + 1) % 2]) {
                                if (target.getCoordinate().getY() == card.getCoordinate().getY()) {
                                    if (!target.getName().equals("GIV")) {
                                        target.modifyHealth(-4);
                                    }
                                }
                            }
                        }
                        break;
                    case "CYCLOPS":
                        for (int i = -1; i < 2; i++) {
                            for (int j = -1; j < 2; j++) {
                                Card target = Card.getCardByID(getField(card.getCoordinate().getX() + i, card.getCoordinate().getY() + j).getCardID(), fieldCards[turn % 2]);
                                if (target instanceof Minion) {
                                    target.addToBuffs(card.getBuffs().get(0));
                                }
                            }
                        }
                        break;
                    case "GIANT_SNAKE":
                        for (int i = 0; i < 9; i++) {
                            for (int j = 0; j < 5; j++) {
                                if (Coordinate.getManhattanDistance(field[i][j].getCoordinate(), card.getCoordinate()) <= 2
                                        && Coordinate.getManhattanDistance(field[i][j].getCoordinate(), card.getCoordinate()) != 0
                                        && field[i][j].getCardID() != 0) {
                                    Card target = Card.getCardByID(field[i][j].getCardID(), fieldCards[turn % 2]);
                                    assert target != null;
                                    target.addToBuffs(card.getBuffs().get(0));

                                }
                            }
                        }
                        break;
                    case "BAHMAN":
                        if (fieldCards[(turn + 1) % 2].length >= 2) {
                            r = rand.nextInt(fieldCards[(turn + 1) % 2].length);
                            r += 1;
                            Card target = Card.getCardByID(r, fieldCards[(turn + 1) % 2]);
                            assert target != null;
                            target.addToBuffs(card.getBuffs().get(0));
                        }
                        break;
                    case "SIAVASH":
                        for (int i = 0; i < fieldCards[(turn + 1) % 2].length; i++) {
                            if (fieldCards[(turn + 1) % 2][i] instanceof Hero) {
                                fieldCards[(turn + 1) % 2][i].addToBuffs(buff);
                            }
                        }
                        break;

                }

                break;
            case POSITIVE_DISPEL:
                switch (card.getName()) {
                    case "AFSANEH": // we need to choose a target here
                        if (spendMana(card.getManaPoint()) && spellIsReady(buff)) {
                            for (Buff buffToDispel :
                                    targetCard.getCastedBuffs()) {
                                targetCard.getCastedBuffs().remove(buff);
                            }
                        }
                        break;

                }

                break;
        }

        useSpecialPower(card, card.getBuffs().get(1));
    }

    public Message insertCard(Coordinate coordinate, String cardName) {
        boolean validTarget = false;
        for (int i = 0; i < 5; i++) {
            if (playerHands[turn % 2][i].getName().equals(cardName)) {
                Card insert = Card.getCardByName(cardName, playerHands[turn % 2]);
                if (field[coordinate.getX()][coordinate.getY()].getCardID() != 0) {
                    return Message.INVALID_TARGET;
                }
                if (insert!=null && !spendMana(insert.getManaPoint())) {
                    return Message.INSUFFICIENT_MANA;
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
                field[coordinate.getX()][coordinate.getY()].setCardID(insert.getId());
                insert.setCoordinate(coordinate);
                playerHands[turn % 2] = Card.removeFromArray(playerHands[turn % 2], insert);
                fieldCards[turn % 2] = Card.addToArray(fieldCards[turn % 2], insert);
                return null;
            }

        }

        return Message.NOT_IN_HAND;
}

    public void showHand() {

    }

    public void endTurn() {
        for (Card card : fieldCards[0]) {
            for (Buff buff : card.getCastedBuffs()) {
                if (card.getName().equals("GIV") && (buff.getType().equals(BuffType.DISARM)
                        || buff.getType().equals(BuffType.WEAKNESS) || buff.getType().equals(BuffType.POISON)
                        || buff.getType().equals(BuffType.STUN))) {
                    card.removeFromBuffs(buff);
                    card.setAbleToMove(true);
                    card.setAbleToAttack(true);
                    card.setAssaultPower(card.getOriginalAssaultPower());

                }
                if (buff.getTurnCount() > 0) {
                    buff.setTurnCount(buff.getTurnCount() - 1);
                }
                if (buff.getType().equals(BuffType.STUN) && buff.getTurnCount() != 0) {
                    card.setAbleToAttack(false);
                    card.setAbleToMove(false);
                }
                if (buff.getType().equals(BuffType.STUN) && buff.getTurnCount() == 0) {
                    card.setAbleToMove(true);
                    card.setAbleToAttack(true);
                }
                if (buff.getType().equals(BuffType.POISON) && buff.getTurnCount() > 0 && buff.getTurnCount() % 2 == 0) {
                    card.modifyHealth(-buff.getPower());
                } else if (buff.getType().equals(BuffType.DISARM) && buff.getTurnCount() == 0) {
                    card.setAbleToAttack(true);
                }
                if (buff.getType().equals(BuffType.WHITE_WALKER_WOLF)) {
                    card.modifyHealth(buff.getPower());
                    buff.setPower(4);
                }
                if (buff.getType().equals(BuffType.WEAKNESS) && buff.getTargetType().equals("HEALTH") && buff.getTurnCount() == 1) {
                    targetCard.modifyHealth(buff.getPower());
                }
                if (buff.getActivationType().equals(ActivationType.PASSIVE)) {
                    if (buff.getTargetType().equals("HEALTH")) {
                        card.setHealthPoint(card.getHealthPoint() + card.getBuffs().get(0).getPower());
                    }
                }
                if (buff.getType().equals(BuffType.HOLY) && buff.getTurnCount() != 0) {
                    card.setIsHoly(buff.getPower());
                }
                if (buff.getType().equals(BuffType.HOLY) && buff.getTurnCount() == 0) {
                    card.setIsHoly(0);
                }
                if (buff.getType().equals(BuffType.POWER) && buff.getTurnCount() > 0 && buff.getTurnCount() % 2 == 0) {
                    card.setAssaultPower(card.getAssaultPower() + buff.getPower());
                }
                if (buff.getType().equals(BuffType.POWER) && buff.getTurnCount() == 0) {
                    card.setAssaultPower(card.getOriginalAssaultPower());
                }

                if (buff.getTurnCount() == 0) {
                    card.removeFromBuffs(buff);
                }
            }
        }
        for (int i = 0; i < 9; i++) { //deholify cells
            for (int j = 0; j < 5; j++) {
                if (field[i][j].isHoly()) {
                    field[i][j].setHolyTurn(field[i][j].getHolyTurn() - 1);
                    if (field[i][j].getHolyTurn() == 0) {
                        field[i][j].setHoly(false);
                    }
                }
            }

        }

        //random item appears on the battleField
        if ((turn % Constants.ITEM_APPEARANCE) == 1) {
            boolean ableToAddItem = true;
            while (ableToAddItem) {
                int randomX = rand.nextInt(9);
                int randomY = rand.nextInt(5);
                int randomCollectableItem = rand.nextInt(9);
                if (field[randomX][randomY].getCardID() == 0) {
                    ableToAddItem = false;
                    field[randomX][randomY].setCardID(chooseCollectableItems(shop.getItems()).get(randomCollectableItem).getId());
                    //effect item
                }
            }
        }

        if (mode.equals(BattleMode.COLLECT_FLAG) && (turn % Constants.ITEM_APPEARANCE) == 1) {
            flagAppearance();
        }
        turn++;
        currentCard = null;
        targetCard = null;


    }

    public boolean collectFlags() {
        for (int i = 0; i < flagsOnTheGround.size(); i++) {
            if (Coordinate.getManhattanDistance(flagsOnTheGround.get(i).getCoordinate(), currentCard.getCoordinate()) == 0) {
                accounts[turn % 2].setFlagsCollected(accounts[turn % 2].getFlagsCollected() + 1);
                flagsOnTheGround.remove(flagsOnTheGround.get(i));
                checkForWin();
                return true;
            }
        }
        return false;
    }


    public void flagAppearance() {
        boolean ableToAddFlag = true;
        while (ableToAddFlag) {
            int randomX = rand.nextInt(9);
            int randomY = rand.nextInt(5);
            if (field[randomX][randomY].getCardID() == 0 && flagsAppeared < Constants.MAXIMUM_FLAGS) {
                Flag flag = new Flag();
                flag.setCoordinate(new Coordinate(randomX, randomY));
                flagsOnTheGround.add(flag);
                ableToAddFlag = false;
                flagsAppeared++;
            }
        }
    }

    public void showCollectables() {

    }

    public void showInfo(int objectId) {
        if (menu.getStat() == MenuStat.ITEM_SELECTION) {
            Item.getItemByID(objectId, collectables[turn % 2]);
        }
    }

    public void showNextCard() {
        showCardInfo(accounts[turn % 2].getCollection().getMainDeck().getCards().get(0).getId());
    }

    public ArrayList<Item> chooseCollectableItems(ArrayList<Item> items) {
        ArrayList<Item> newItemList = new ArrayList<>();
        for (Item item : items) {
            if (item.getPrice() == 0) {
                newItemList.add(item);
            }
        }
        return newItemList;
    }

   /* public Message selectCollectableId(int collectableId) {
        for (Collectable collectable :
                collectables[turn % 2]) {
            if (collectable.getId() == collectableId) {
                menu.setStat(MenuStat.ITEM_SELECTION);
            }
        }

    }*/

    public boolean useItem(Coordinate coordinate) {
        if (menu.getStat() != MenuStat.ITEM_SELECTION)
            return false;
        return true;
    }

   /* public Message useSpecialPower(Coordinate coordinate) {

    }*/

    public void enterGraveyard() {
        menu.setStat(MenuStat.GRAVEYARD);
    }

   /* public Message showCardInfoInGraveyard(int cardId) {

    }*/

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

    public Coordinate setCardCoordinates(Card card) {
        if (getFieldCards().length == 0) {
            return new Coordinate(6, 2);
        } else {
            return new Coordinate(getFieldCards()[1][getFieldCards().length - 1].getCoordinate().getX(), (getField()[1][getFieldCards().length - 1].getCoordinate().getY() + 1) % 5);
        }
    }

    public Coordinate setTargetCoordiantes(Card card) {
        if (card instanceof Minion) {
            ArrayList<Card> closestEnemyCards = new ArrayList<>();
            switch (card.getAssaultType()) {
                case MELEE:
                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                            if (getField(card.getCoordinate().getX(), card.getCoordinate().getY() + 1).getCardID() != 0) {
                                addEnemy(closestEnemyCards, card.getCoordinate().getX() + i, card.getCoordinate().getY() + j);
                            }
                        }
                    }

                    int miratarin = getMiratarin(closestEnemyCards);
                    for (int i = 0; i < closestEnemyCards.size(); i++) {
                        if (closestEnemyCards.get(i) instanceof Hero) {
                            return closestEnemyCards.get(i).getCoordinate();
                        }
                    }
                    return closestEnemyCards.get(miratarin).getCoordinate();
                case RANGED:
                case HYBRID:
                    for (int i = -card.getMaxRange(); i <= card.getMaxRange(); i++) {
                        for (int j = -card.getMaxRange(); j <= card.getMaxRange(); j++) {
                            if ((i + j <= card.getMaxRange() && (card.getAssaultType().equals(AssaultType.HYBRID)))
                                    || (i + j <= card.getMaxRange() && (card.getAssaultType().equals(AssaultType.RANGED) && i + j != 1))) {
                                if (getField(card.getCoordinate().getX(), card.getCoordinate().getY() + 1).getCardID() != 0) {
                                    addEnemy(closestEnemyCards, card.getCoordinate().getX() + i, card.getCoordinate().getY() + j);
                                }
                            }
                        }
                    }

                    int miratarinn = getMiratarin(closestEnemyCards);
                    for (int i = 0; i < closestEnemyCards.size(); i++) {
                        if (closestEnemyCards.get(i) instanceof Hero) {
                            return closestEnemyCards.get(i).getCoordinate();
                        }
                    }
                    return closestEnemyCards.get(miratarinn).getCoordinate();

            }
        }
        return null;

    }

    private int getMiratarin(ArrayList<Card> closestEnemyCards) {
        int leastHp = 100;
        int miratarin = 0;
        for (int i = 0; i < closestEnemyCards.size(); i++) {
            if (closestEnemyCards.get(i).getHealthPoint() < leastHp) {
                miratarin = i;
            }
        }
        return miratarin;
    }

    private void addEnemy(ArrayList<Card> closestEnemyCards, int x, int y) {
        if (getField()[x][y].getCardID() != -1) {
            for (int i = 0; i < getFieldCards()[0].length; i++) {
                if (getFieldCards()[0][i] != null && getFieldCards()[0][i].getId() == getField()[x][y].getCardID() && getFieldCards()[0][i].getCardHolder() == 1) {
                    closestEnemyCards.add(getFieldCards()[0][i]);
                }
            }
        }
    }



}
