package Controller.Request;

import Model.Coordinate;

public class InsertionRequest extends DirectRequest {

    private String cardName;
    private Coordinate coordinate;
    public InsertionRequest(String ...args) {
        cardName = args[0];
        coordinate = new Coordinate(Integer.parseInt(args[1]),Integer.parseInt(args[2]));
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
