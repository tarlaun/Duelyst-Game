package Controller.Request;

import Model.Coordinate;

public class InsertionRequest extends DirectRequest {

    private String cardName;
    private Coordinate coordinate;
    private String accName;

    public InsertionRequest(String ...args) {
        cardName = args[0];
        coordinate = new Coordinate(Integer.parseInt(args[1]),Integer.parseInt(args[2]));
        accName = args[3];
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

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }
}
