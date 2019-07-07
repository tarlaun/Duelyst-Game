package Controller.Request;

import Model.Account;
import Model.BattleMode;
import Model.GameType;

public class BattleRequest extends DirectRequest{
        private String account1Name;
        private String account2Name;
        private String battleMode;
        private String gameType;

    public BattleRequest(String ... args) {
        account1Name =(args[0]);
        account2Name =(args[1]);
        battleMode = (args[3]);
        gameType = (args[2]);
    }

    public String getAccount1Name() {
        return account1Name;
    }

    public void setAccount1Name(String account1Name) {
        this.account1Name = account1Name;
    }

    public void setAccount2Name(String account2Name) {
        this.account2Name = account2Name;
    }

    public String getAccount2Name() {
        return account2Name;
    }


}
