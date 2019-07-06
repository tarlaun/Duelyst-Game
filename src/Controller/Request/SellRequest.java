package Controller.Request;

import Model.Card;

public class SellRequest extends DirectRequest{
    private Card card;

    public SellRequest(String... args) {
        this.card = Card.fromJson(args[0]);
    }

    public Card getCard() {
        return card;
    }
}
