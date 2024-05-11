package ForumServer.DbConn;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class PostgresDbConn implements DbConn {
    Connection conn;
    public PostgresDbConn(String url, String username, String password) throws SQLException {
        this.conn = DriverManager.getConnection(url, username, password);
    }

    @Override
    public boolean correctLogin(String username, String password) {
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM accounts WHERE username = ? AND password = ?");
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery() ;
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public String registerUser(String email, String username, String password) {
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO accounts (email, username, password) VALUES (?,?,?)");
            st.setString(1, email);
            st.setString(2, username);
            st.setString(3, password);
            st.executeUpdate(); 
        } catch (SQLException e) {
            return "error registering new user";
        }
        return null;
    }

    @Override
    public boolean userEmailExists(String email) {
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM accounts WHERE email = ?");
            st.setString(1, email);
            ResultSet rs = st.executeQuery() ;
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean userUsernameExists(String username) {
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM accounts WHERE username = ?");
            st.setString(1, username);
            ResultSet rs = st.executeQuery() ;
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void dropAllRecords() {
        try {
            List<String> tablenames = Arrays.asList();
            for (String tablename: tablenames) {
                PreparedStatement st = conn.prepareStatement("DELETE FROM ?");
                st.setString(1, tablename);
                st.executeUpdate();
            }
        } catch (SQLException e) {
            return;
        }
    }
    
}
