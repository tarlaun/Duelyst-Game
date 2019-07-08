package Controller.Request;

import Model.Account;
import Model.Card;

public class DiscardAuction extends DirectRequest {
    private int id;
    private Account account;

    public DiscardAuction(String... args) {
        this.id = Integer.parseInt(args[0]);
        this.account = Account.fromJson(args[1]);
    }

    public int getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }
}
