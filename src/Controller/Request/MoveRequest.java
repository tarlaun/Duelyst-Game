package Controller.Request;

import Model.Coordinate;

public class MoveRequest extends DirectRequest {

    private Coordinate coordinate;
    private String accName;

    public MoveRequest(String ... args){
        coordinate = new Coordinate(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
        accName = args[2];
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
}
