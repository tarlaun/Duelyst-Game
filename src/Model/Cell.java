package Model;

public class Cell {
    private Coordinate coordinate;
    private int cardID;
    private boolean isHoly;
    private int holyTurn;

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
