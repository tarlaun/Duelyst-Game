package Controller.Request;

import Model.Coordinate;

public class UseSpRequest extends DirectRequest {
    private Coordinate coordinate;
    public UseSpRequest(String ... args) {
        coordinate = new Coordinate(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
