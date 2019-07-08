package Controller.Request;

import Model.Account;
import Model.Card;
import Model.Item;

public class SellRequest extends DirectRequest {
    private int id;

    private Account account;

    public SellRequest(String... args) {
        this.account = Account.fromJson(args[1]);
        try {
            this.id = Card.fromJson(args[0]).getId();
        } catch (Exception isItem) {
            this.id = Item.fromJson(args[0]).getId();
        }
    }

    public int getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }
}
