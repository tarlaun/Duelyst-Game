package Controller.Request;

public class RivalRequest extends DirectRequest{
    private String name;

    public RivalRequest(String... args){
        this.name = args[0];
    }

    public String getName() {
        return name;
    }
}
