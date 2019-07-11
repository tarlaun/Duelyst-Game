package Controller.Request;

public class CheatRequest extends DirectRequest {
    private String code;
    private String userName;

    public CheatRequest(String... args){
        code = args[0];
        userName = args[1];
    }

    public String getCode() {
        return code;
    }

    public String getUserName() {
        return userName;
    }
}
