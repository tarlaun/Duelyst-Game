package Controller.Request;

import Model.Account;

public class ObtainAuctionRequest extends DirectRequest{
    private int id;
    private Account account;

    public ObtainAuctionRequest(String... args){
        id = Integer.parseInt(args[0]);
        account = Account.fromJson(args[1]);
    }

    public int getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }
}
