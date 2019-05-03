package Model;

public class Flag  {
    private Card flagHolder;
    private int turnCounter=0;
    private Coordinate coordinate;
    private Account account;
    private boolean isHeld = false;

    public boolean isHeld() {
        return isHeld;
    }

    public void setHeld(boolean held) {
        isHeld = held;
    }

    public Card getFlagHolder() {
        return flagHolder;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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
