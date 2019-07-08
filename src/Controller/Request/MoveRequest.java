package Controller.Request;

import Model.Coordinate;

public class MoveRequest extends DirectRequest {

    private Coordinate coordinate;
    private String accName;
    private int cardId;

    public MoveRequest(String ... args){
        coordinate = new Coordinate(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
        accName = args[2];
        cardId = Integer.parseInt(args[3]);
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }
}
