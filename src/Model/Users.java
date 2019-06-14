package Model;

public class Users {
    private String name;
    private String status;

    public Users(String name,String s) {
        this.name = name;
        this.status = s;
    }

    @Override
    public String toString() {
        return name+ " is "+status;
    }
}
