package Controller.Request;

public class KillRequest extends DirectRequest {
    private String accName;
    private String cardName;

    public KillRequest(String ... args) {
        accName = args[0];
        cardName = args[1];
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
}
