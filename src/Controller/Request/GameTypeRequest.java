package Controller.Request;

public class GameTypeRequest extends DirectRequest {
    private String battleType;

    public GameTypeRequest(String ...args) {
        battleType = args[0];
    }

    public String getBattleType() {
        return battleType;
    }

    public void setBattleType(String battleType) {
        this.battleType = battleType;
    }
}
