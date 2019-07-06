package Controller.Request;

public class AttackRequest extends DirectRequest {
    private int opponentCardId ;
    private int cardId;
    private int turn;
    public AttackRequest(String ... args) {
        opponentCardId = Integer.parseInt(args[0]);
        cardId = Integer.parseInt(args[1]);
        turn = Integer.parseInt(args[2]);

    }

    public int getOpponentCardId() {
        return opponentCardId;
    }

    public void setOpponentCardId(int opponentCardId) {
        this.opponentCardId = opponentCardId;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
}
