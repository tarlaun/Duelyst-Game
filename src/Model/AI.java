package Model;

import java.util.ArrayList;

public class AI {
    private int level;
    Battle battle = new Battle();

    public Coordinate setCardCoordinates(Card card) {

    }

    public Coordinate setTargetCoordiantes(Card card) {
        if (card instanceof Minion) {
            ArrayList<Card> closestEnemyCards = new ArrayList<>();
            switch (card.getAssaultType()) {
                case MELEE:
                    addEnemy(closestEnemyCards, card.getCoordinate().getX() + 1, card.getCoordinate().getY() + 1);
                    if (battle.getField()[card.getCoordinate().getX()][card.getCoordinate().getY() + 1] != -1) {
                        addEnemy(closestEnemyCards, card.getCoordinate().getX(), card.getCoordinate().getY() + 1);
                    }
                    if (battle.getField()[card.getCoordinate().getX() + 1][card.getCoordinate().getY()] != -1) {
                        addEnemy(closestEnemyCards, card.getCoordinate().getX() + 1, card.getCoordinate().getY());
                    }
                    if (battle.getField()[card.getCoordinate().getX() - 1][card.getCoordinate().getY() - 1] != -1) {
                        addEnemy(closestEnemyCards, card.getCoordinate().getX() - 1, card.getCoordinate().getY() - 1);
                    }
                    if (battle.getField()[card.getCoordinate().getX() + 1][card.getCoordinate().getY() - 1] != -1) {
                        addEnemy(closestEnemyCards, card.getCoordinate().getX() + 1, card.getCoordinate().getY() - 1);
                    }
                    if (battle.getField()[card.getCoordinate().getX() - 1][card.getCoordinate().getY() + 1] != -1) {
                        addEnemy(closestEnemyCards, card.getCoordinate().getX() - 1, card.getCoordinate().getY() + 1);
                    }
                    if (battle.getField()[card.getCoordinate().getX()][card.getCoordinate().getY() - 1] != -1) {
                        addEnemy(closestEnemyCards, card.getCoordinate().getX(), card.getCoordinate().getY() - 1);
                    }
                    if (battle.getField()[card.getCoordinate().getX() - 1][card.getCoordinate().getY()] != -1) {
                        addEnemy(closestEnemyCards, card.getCoordinate().getX() - 1, card.getCoordinate().getY());
                    }
                    int leastHp =100;
                    int miratarin =0;
                    for (int i = 0; i < closestEnemyCards.size() ; i++) {
                        if(closestEnemyCards.get(i).getHealthPoint()<leastHp){
                            miratarin =i;
                        }
                    }
                    return closestEnemyCards.get(miratarin).getCoordinate();
                    break;
                case HYBRID:


                    break;
                case RANGED:


                    break;
            }
        }

    }

    private void addEnemy(ArrayList<Card> closestEnemyCards, int x, int y) {
        if (battle.getField()[x][y] != -1) {
            for (int i = 0; i < battle.getFieldCards()[0].length; i++) {
                if (battle.getFieldCards()[0][i] != null && battle.getFieldCards()[0][i].getId() == battle.getField()[x][y] && battle.getFieldCards()[0][i].getCardHolder() == 1) {
                    closestEnemyCards.add(battle.getFieldCards()[0][i]);
                }
            }
        }
    }

    public Coordinate setDestinationCoordinates() {

    }

    public Card chooseCard(ArrayList<Card> cards) {

    }
}
