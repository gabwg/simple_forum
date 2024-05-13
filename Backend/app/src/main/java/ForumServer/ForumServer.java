package ForumServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import ForumServer.DbConn.*;
import ForumServer.HttpHandlers.*;
import ForumServer.Services.*;

public class ForumServer {
    static int port = 8000;
    static String configlocation = "Backend/serverconfigs/config.json";
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        boolean debug = (args.length == 1 && args[0].equals("debug"));
        if (debug) {
            System.out.println("SERVER IN DEBUG MODE. DATA WILL NOT BE SAVED");
        }       
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> configs = gson.fromJson(readConfigFile(), type);
        // create dbconn
        //DbConn dbconn = new DummyDbConn();
        String postgresurl = configs.get("postgres_url");
        if (debug) {
            postgresurl = configs.get("postgres_debug_url");
        }
        DbConn dbconn = new PostgresDbConn(postgresurl, 
                                            configs.get("postgres_username"), 
                                            configs.get("postgres_password"));
        if (debug) {
            dbconn.dropAllRecords();
        }
        // create services
        UserService userservice = new UserService(dbconn, configs.get("jwt_secret"));
        
        // create contexts
        // for a test route. can be removed
        server.createContext("/test", new TestHandler());
        //
        server.createContext("/login", new LoginHandler(userservice));
        server.createContext("/register", new RegisterHandler(userservice));


        // start server
        server.setExecutor(null);
        server.start();
    }
    private static String readConfigFile() throws FileNotFoundException {
        File file = new File(configlocation);
        try {
            Scanner s = new Scanner(file);
            return s.useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            System.out.println("error with loading config.json");
            throw e;
        }
        
    }
}