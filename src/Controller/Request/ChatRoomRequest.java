package Controller.Request;

import Model.Account;

public class ChatRoomRequest extends DirectRequest {
    private Account account;

    public ChatRoomRequest(String... args){
        this.account = Account.fromJson(args[0]);
    }

    public Account getAccount() {
        return account;
    }
}
