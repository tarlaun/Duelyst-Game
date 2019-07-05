package Controller.Request;

public class SaveRequest extends DirectRequest{
    private String userName;

    public SaveRequest(String... args) {
        userName = args[0];
    }

    public String getUserName() {
        return userName;
    }
}
