package Controller.Request;

public class LoginRequest extends DirectRequest {
    private String userName;
    private String password;

    public LoginRequest(String... args) {
        userName = args[0];
        password = args[1];
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
