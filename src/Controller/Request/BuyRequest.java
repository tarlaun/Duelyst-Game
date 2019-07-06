package Controller.Request;

import Model.Account;

public class BuyRequest extends DirectRequest {
    private String name;
    private Account account;

    public BuyRequest(String... args){
        name = args[0];
        account = Account.fromJson(args[1]);
    }

    public String getName() {
        return name;
    }

    public Account getAccount() {
        return account;
    }
}
