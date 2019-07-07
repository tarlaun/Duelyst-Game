package Controller.Request;

public class BattleModeRequest extends DirectRequest {
    private String battleMode;

    public BattleModeRequest(String ... args) {
        battleMode = args[0];
    }

    public String getBattleMode() {
        return battleMode;
    }

    public void setBattleMode(String battleMode) {
        this.battleMode = battleMode;
    }
}
