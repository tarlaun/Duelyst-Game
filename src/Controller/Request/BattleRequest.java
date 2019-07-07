package Controller.Request;

import Model.Account;
import Model.BattleMode;
import Model.GameType;

public class BattleRequest extends DirectRequest{
        private Account account1;
        private Account account2;
        private BattleMode battleMode;
        private GameType gameType;

    public BattleRequest(String ... args) {
        account1 = Account.fromJson(args[0]);
        account2 = Account.fromJson(args[1]);
        battleMode = BattleMode.fromJson(args[3]);
    }
}
