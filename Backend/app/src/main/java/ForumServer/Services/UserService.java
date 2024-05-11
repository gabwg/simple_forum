package ForumServer.Services;

import ForumServer.DataClasses.LoginDetails;
import ForumServer.DataClasses.RegisterDetails;
import ForumServer.DbConn.*;

public class UserService {
    DbConn dbconn = null;
    public UserService(DbConn dbconn) {
        this.dbconn = dbconn;
    }
    // returns True if correct details
    public boolean checkLoginDetails(String username, String password) {
        return dbconn.correctLogin(username, password);
    }
    public boolean checkLoginDetails(LoginDetails logindetails) {
        return dbconn.correctLogin(logindetails.getUsername(), logindetails.getPassword());
    }
    // returns True if given registration details are available
    public boolean userDetailsAvailable(String email, String username) {
        return dbconn.userEmailExists(email) && dbconn.userUsernameExists(username);
    }
    public boolean userEmailAvailable(String email) {
        return !dbconn.userEmailExists(email);
    }
    public boolean userUsernameAvailable(String username) {
        return !dbconn.userUsernameExists(username);
    }
    public boolean userEmailAvailable(RegisterDetails registerdetails) {
        return !dbconn.userEmailExists(registerdetails.getEmail());
    }
    public boolean userUsernameAvailable(RegisterDetails registerdetails) {
        return !dbconn.userUsernameExists(registerdetails.getUsername());
    }
    public boolean userDetailsAvailable(RegisterDetails registerdetails) {
        return userDetailsAvailable(registerdetails.getEmail(), registerdetails.getUsername());
    }
    // returns  "success" if registration is successful, returns error message otherwise
    public String registerUser(RegisterDetails registerdetails) {
        return dbconn.registerUser(registerdetails.getEmail(), registerdetails.getUsername(), registerdetails.getPassword());
    }
}
