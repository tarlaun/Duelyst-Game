package Controller.Request;

public class SelectRequest extends DirectRequest {
    private int cardId ;

    public SelectRequest(String ... args) {
        cardId = Integer.parseInt(args[0]);
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }
}
