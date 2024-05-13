package ForumServer.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import ForumServer.DataClasses.LoginDetails;
import ForumServer.DataClasses.RegisterDetails;
import ForumServer.DbConn.*;

public class UserService {
    DbConn dbconn = null;
    String secret;
    public UserService(DbConn dbconn, String secret) {
        this.dbconn = dbconn;
        this.secret = secret;
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
    public String generateToken(LoginDetails logindetails) {
        return generateToken(logindetails.getUsername(), logindetails.getPassword());
    }
    public String generateToken(String username, String password) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            String token = JWT.create()
            .withIssuer("auth0")
            .sign(algorithm);
            return token;
        } catch (Exception e) {
            return null;
        }
    }
}
