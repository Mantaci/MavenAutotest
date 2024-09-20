package schema;

public class RegLogObj {

    public RegLogObj(String username, String password) {
        this.username = username;
        this.password = password;
    }

    String username;
    String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
