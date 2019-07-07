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
        gameType = GameType.fromJson(args[2]);
    }

    public Account getAccount1() {
        return account1;
    }

    public void setAccount1(Account account1) {
        this.account1 = account1;
    }

    public Account getAccount2() {
        return account2;
    }

    public void setAccount2(Account account2) {
        this.account2 = account2;
    }

    public BattleMode getBattleMode() {
        return battleMode;
    }

    public void setBattleMode(BattleMode battleMode) {
        this.battleMode = battleMode;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }
}
