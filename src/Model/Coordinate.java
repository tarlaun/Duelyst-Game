package Model;

import static java.lang.Math.abs;

public class Coordinate {
    private int x=-1;
    private int y=-1;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static int getManhattanDistance(Coordinate c1, Coordinate c2){
        return abs(c1.getX()-c2.getX())+abs(c2.getY()-c1.getY());
    }
    public static Coordinate[] getPathDirections(Coordinate c1, Coordinate c2){

    }

}
