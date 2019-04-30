package Model;

public class Flag  {
    private Card flagHolder;
    private int turnCounter=0;
    private Coordinate coordinate;

    public Card getFlagHolder() {
        return flagHolder;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void setFlagHolder(Card flagHolder) {
        this.flagHolder = flagHolder;
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public void setTurnCounter(int turnCounter) {
        this.turnCounter = turnCounter;
    }
}
