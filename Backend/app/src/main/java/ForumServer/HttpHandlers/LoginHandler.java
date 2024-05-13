package ForumServer.HttpHandlers;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import ForumServer.DataClasses.LoginDetails;
import ForumServer.Services.UserService;

import org.apache.commons.io.IOUtils;
import com.google.gson.*;

public class LoginHandler implements HttpHandler {
    UserService userservice;
    public LoginHandler(UserService userservice) {
        this.userservice = userservice;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (HandlerHelpers.hasMethod(exchange, Arrays.asList("POST"))) {
            //System.out.println(exchange.getRequestMethod());
            String jsonstr = IOUtils.toString(exchange.getRequestBody(), "UTF-8");
            Gson gson = new Gson();
            LoginDetails logindetails = gson.fromJson(jsonstr, LoginDetails.class);
            if (userservice.checkLoginDetails(logindetails)) {
                // craft a response with message and token
                Map<String, String> responsedata = new HashMap<String,String>();
                responsedata.put("message", "correct login details");
                responsedata.put("token", userservice.generateToken(logindetails));
                HandlerHelpers.sendResponse(exchange, 200, responsedata);
            } else {
                Map<String, String> responsedata = new HashMap<String,String>();
                responsedata.put("error", "incorrect login details");
                HandlerHelpers.sendResponse(exchange, 403, responsedata);

            }
            
        } else {
            HandlerHelpers.sendResponse(exchange, 501, "invalid method");
        }
        // TODO Auto-generated method stub

    }
    
}
