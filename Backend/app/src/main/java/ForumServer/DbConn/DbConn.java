package ForumServer.DbConn;

public interface DbConn {
    // returns True if the login details given by username and password are correct
    public boolean correctLogin(String username, String password);
    // enters the given details into the accounts table thus 'registering' the user
    // returns string "success" if success, returns appropriate error message as string otherwise
    public String registerUser(String email, String username, String password);
    // returns True if username/email are determined to be available, false otherwise
    public boolean userUsernameExists(String username);
    public boolean userEmailExists(String email);
    
    // will be used mainly for debugging purposes:
    public void dropAllRecords();
}
