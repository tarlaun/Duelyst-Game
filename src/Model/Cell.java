package Model;

public class Cell {
    private Coordinate coordinate;
    private int cardID;
    private boolean isHoly;
    private boolean isPoison;
    private boolean isFire;
    private int holyTurn;

    public int getPoisonTurn() {
        return poisonTurn;
    }

    public void setPoisonTurn(int poisonTurn) {
        this.poisonTurn = poisonTurn;
    }

    public int getFireTurn() {
        return fireTurn;
    }

    public void setFireTurn(int fireTurn) {
        this.fireTurn = fireTurn;
    }

    private int poisonTurn;
    private int fireTurn;


    public boolean isPoison() {
        return isPoison;
    }

    public void setPoison(boolean poison) {
        isPoison = poison;
    }

    public boolean isFire() {
        return isFire;
    }

    public void setFire(boolean fire) {
        isFire = fire;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public int getCardID() {
        return cardID;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public boolean isHoly() {
        return isHoly;
    }

    public void setHoly(boolean holy) {
        isHoly = holy;
    }

    public int getHolyTurn() {
        return holyTurn;
    }

    public void setHolyTurn(int holyTurn) {
        this.holyTurn = holyTurn;
    }
}
