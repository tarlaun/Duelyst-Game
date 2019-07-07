package Controller.Request;

public class SelectRequest extends DirectRequest {
    private int cardId ;
    private String accName;

    public SelectRequest(String ... args) {
        cardId = Integer.parseInt(args[0]);
        accName = args[1];
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }
}
