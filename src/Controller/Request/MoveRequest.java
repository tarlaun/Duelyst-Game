package Controller.Request;

import Model.Coordinate;

public class MoveRequest extends DirectRequest {

    private Coordinate coordinate;

    public MoveRequest(String ... args){

    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
