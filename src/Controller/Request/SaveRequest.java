package Controller.Request;

import Model.Account;

public class SaveRequest extends DirectRequest {
    private Account account;

    public SaveRequest(String... args) {
        account = Account.fromJson(args[0]);
    }

    public Account getAccount() {
        return account;
    }
}
