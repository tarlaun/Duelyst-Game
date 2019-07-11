package Controller.Request;

import Model.Account;

public class EnterChatRequest extends DirectRequest {
    private Account account;

    public EnterChatRequest(String... args){
        account = Account.fromJson(args[0]);
    }

    public Account getAccount() {
        return account;
    }
}
