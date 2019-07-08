package Controller.Request;

public class KillRequest extends DirectRequest {
    private String accName;
    private int cardId;

    public KillRequest(String ... args) {
        accName = args[0];
        cardId = Integer.parseInt(args[1]);
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }
}
