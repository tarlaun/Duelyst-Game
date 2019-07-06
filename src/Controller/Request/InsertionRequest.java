package Controller.Request;

import Model.Coordinate;

public class InsertionRequest {

    private int cardId;
    private Coordinate coordinate;
    public InsertionRequest(String ...args) {
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
