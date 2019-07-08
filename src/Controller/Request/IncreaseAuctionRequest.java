package Controller.Request;

import Model.Account;

public class IncreaseAuctionRequest extends DirectRequest {
    private int id;
    private int price;
    private Account account;

    public IncreaseAuctionRequest(String... args) {
        this.id = Integer.parseInt(args[0]);
        this.account = Account.fromJson(args[1]);
        this.price = Integer.parseInt(args[2]);
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public Account getAccount() {
        return account;
    }
}
