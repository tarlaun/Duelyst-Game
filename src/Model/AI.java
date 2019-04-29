package Model;

import java.util.ArrayList;

public class AI {
    private int level;
    private Battle battle = new Battle();

    public Coordinate setCardCoordinates(Card card) {

    }

    public Coordinate setTargetCoordiantes(Card card) {
        if (card instanceof Minion) {
            ArrayList<Card> closestEnemyCards = new ArrayList<>();
            switch (card.getAssaultType()) {
                case MELEE:
                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                            if (battle.getField(card.getCoordinate().getX(), card.getCoordinate().getY() + 1).getCardID() != 0) {
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
                break;
                case RANGED:
                case HYBRID:
                    for (int i = -card.getMaxRange(); i <= card.getMaxRange(); i++) {
                        for (int j = -card.getMaxRange(); j <= card.getMaxRange(); j++) {
                            if ((i + j <= card.getMaxRange() && (card.getAssaultType().equals(AssaultType.HYBRID)))
                                    || (i + j <= card.getMaxRange() && (card.getAssaultType().equals(AssaultType.RANGED) && i + j != 1))) {
                                if (battle.getField(card.getCoordinate().getX(), card.getCoordinate().getY() + 1).getCardID() != 0) {
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

                break;
            }
        }

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
        if (battle.getField()[x][y].getCardID() != -1) {
            for (int i = 0; i < battle.getFieldCards()[0].length; i++) {
                if (battle.getFieldCards()[0][i] != null && battle.getFieldCards()[0][i].getId() == battle.getField()[x][y].getCardID() && battle.getFieldCards()[0][i].getCardHolder() == 1) {
                    closestEnemyCards.add(battle.getFieldCards()[0][i]);
                }
            }
        }
    }

    public Coordinate setDestinationCoordinates(Card card) {
        if (card instanceof Minion) {
            switch (card.getAssaultType()) {
                case MELEE:
                    if (card.isAbleToAttack()) {
                        boolean enemyIsNear = false;
                        for (int k = -1; k < 2; k++) {
                            for (int j = -1; j < 2; j++) {
                                for (int i = 0; i < battle.getFieldCards()[0].length; i++) {
                                    if (battle.getFieldCards()[0][i].getCoordinate().equals(new Coordinate(card.getCoordinate().getX() + k, card.getCoordinate().getY() + j))) {
                                        if (battle.getFieldCards()[0][i] instanceof Hero) {
                                            return card.getCoordinate();
                                        }
                                        enemyIsNear = true;
                                    }
                                }
                            }
                        }
                        for (int i = 0; i < battle.getFieldCards()[0].length; i++) {
                            if (battle.getFieldCards()[0][i] instanceof Hero) {
                                if (Coordinate.getManhattanDistance(card.getCoordinate(), battle.getFieldCards()[0][i].getCoordinate()) < 4) {
                                    return new Coordinate((card.getCoordinate().getX() + battle.getFieldCards()[0][i].getCoordinate().getX()) / 2, (card.getCoordinate().getY() + battle.getFieldCards()[0][i].getCoordinate().getY()) / 2);
                                }
                            }
                        }
                        if (enemyIsNear) return card.getCoordinate();
                        return new Coordinate(card.getCoordinate().getX(), card.getCoordinate().getY());
                    }
                    break;
                case RANGED:
                case HYBRID:

                    break;
            }
            if (!card.isAbleToMove()) {
                return card.getCoordinate();
            }
            return new Coordinate(card.getCoordinate().getX(), card.getCoordinate().getY());
        }
    }

    public Card chooseCard(ArrayList<Card> cards) {

    }
}
