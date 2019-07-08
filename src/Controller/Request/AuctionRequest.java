package Controller.Request;

import Model.Account;

public class AuctionRequest extends DirectRequest {
    private Account account;
    private int id;
    private int initialPrice;

    public AuctionRequest(String... args) {
        this.id = Integer.parseInt(args[0]);
        this.account = Account.fromJson(args[1]);
        this.initialPrice = Integer.parseInt(args[2]);
    }

    public Account getAccount() {
        return account;
    }

    public int getId() {
        return id;
    }

    public int getInitialPrice() {
        return initialPrice;
    }
}
