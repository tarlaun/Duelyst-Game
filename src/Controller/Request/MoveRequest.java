package Controller.Request;

import Model.Coordinate;

public class MoveRequest extends DirectRequest {

    private Coordinate coordinate;

    public MoveRequest(String ... args){
        coordinate = new Coordinate(Integer.parseInt(args[0]),Integer.parseInt(args[1]));

    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
