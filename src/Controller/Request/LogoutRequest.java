package Controller.Request;

public class LogoutRequest extends DirectRequest {
    private String userName;

    public LogoutRequest(String... args){
        userName = args[0];
    }

    public String getUserName() {
        return userName;
    }
}
