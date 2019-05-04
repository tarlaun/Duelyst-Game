package Model;

import java.util.ArrayList;

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

    public static Coordinate getPathDirections(Coordinate c1, Coordinate c2, Cell[][] field) { // move from c1 to c2
        Coordinate answer = c1;
            if (c1.getX() > c2.getX() && field[c1.getX() - 1][c1.getY()].getCardID() == 0) {
                c1.setX(c1.getX() - 1);
                return answer;
            } else if (c1.getX() < c2.getX() && field[c1.getX() + 1][c1.getY()].getCardID() == 0) {
                c1.setX(c1.getX() + 1);
                return answer;
            }
            else if (c1.getY() > c2.getY() && field[c1.getX()][c1.getY() - 1].getCardID() == 0) {
                c2.setY(c2.getY() - 1);
                return answer;
            } else if (c1.getY() < c2.getY() && field[c1.getX()][c1.getY() + 1].getCardID() == 0) {
                c1.setY(c1.getY() + 1);
                return answer;
            }else {
                return answer; //IS UNABLE TO MOVE TO C2. WHAT SHOULD WE DO ??
            }


    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
