package Controller.Request;

public class EndTurnRequest extends DirectRequest{
        private String end;

    public EndTurnRequest(String ... args) {
        end =args[0];
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
