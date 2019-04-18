package Model;

import static java.lang.Math.abs;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Coordinate c1, Coordinate c2) {
        return c1.x == c2.x && c1.y == c2.y;
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

    public static Coordinate[] getPathDirections(Coordinate c1, Coordinate c2) {

    }

}
