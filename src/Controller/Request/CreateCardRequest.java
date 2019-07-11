package Controller.Request;

import Model.Card;

public class CreateCardRequest extends DirectRequest {
    private Card card;

    public CreateCardRequest(String... args){
        card = Card.fromJson(args[0]);
    }

    public Card getCard() {
        return card;
    }
}
