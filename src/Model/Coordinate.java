package Model;

import static java.lang.Math.abs;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Coordinate coordinate) {
        return this.x == coordinate.x && this.y == coordinate.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static int getManhattanDistance(Coordinate c1, Coordinate c2) {
        return abs(c1.getX() - c2.getX()) + abs(c2.getY() - c1.getY());
    }

    public Coordinate sum(Coordinate coordinate) {
        return new Coordinate(this.x + coordinate.x, this.y + coordinate.y);
    }

/*
    public static Coordinate[] getPathDirections(Coordinate c1, Coordinate c2) {

    }
*/

}
