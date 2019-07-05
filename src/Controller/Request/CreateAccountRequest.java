package Controller.Request;

public class CreateAccountRequest extends DirectRequest {
    private String userName;
    private String password;

    public CreateAccountRequest(String... args) {
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
