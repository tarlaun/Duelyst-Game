package Controller.Request;

public class SelectUserRequest extends DirectRequest {


    private String firstAccountName;
    private String secondAccountName;

    public SelectUserRequest(String ... args) {
        firstAccountName = args[0];
        secondAccountName = args[1];
    }

    public String getFirstAccountName() {
        return firstAccountName;
    }

    public void setFirstAccountName(String firstAccountName) {
        this.firstAccountName = firstAccountName;
    }

    public String getSecondAccountName() {
        return secondAccountName;
    }

    public void setSecondAccountName(String secondAccountName) {
        this.secondAccountName = secondAccountName;
    }
}
