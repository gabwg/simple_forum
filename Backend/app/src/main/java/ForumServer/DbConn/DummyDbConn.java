package ForumServer.DbConn;

import java.util.HashMap;
import java.util.Map;

public class DummyDbConn implements DbConn {
    Map<String, String> logindetails = new HashMap<String, String>();
    Map<String, String> useremails = new HashMap<String, String>();
    public DummyDbConn() {
        logindetails.put("dummy01", "pw");
        useremails.put("dummy01", "dummy01@email.com");
    }
    @Override
    public boolean correctLogin(String username, String password) {
        return logindetails.containsKey(username) && logindetails.get(username).equals(password);

    }

    @Override
    public String registerUser(String email, String username, String password) {
        if (logindetails.containsKey("username")) {
            return "username already exists";
        }
        logindetails.put(username, password);
        useremails.put(username, email);
        return "success";
    }
    @Override
    public boolean userEmailExists(String email) {
        return useremails.containsValue(email);
    }
    @Override
    public boolean userUsernameExists(String username) {
        return useremails.containsValue(username);
    }
    @Override
    public void dropAllRecords() {
        // TODO Auto-generated method stub
        logindetails.clear();
        useremails.clear();
    }
    
    
}
