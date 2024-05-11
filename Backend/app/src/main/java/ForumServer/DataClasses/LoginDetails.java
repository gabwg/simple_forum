package ForumServer.DataClasses;

public class LoginDetails {
    private String username;
    private String password;
    public LoginDetails(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String toString() {
        return String.format("username: %s\npassword: %s", username, password);
    }
    
}
