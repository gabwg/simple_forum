package ForumServer.HttpHandlers;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import ForumServer.DataClasses.RegisterDetails;
import ForumServer.Services.UserService;

public class RegisterHandler implements HttpHandler {
    UserService userservice;
    public RegisterHandler(UserService userservice) {
        this.userservice = userservice;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // TODO Auto-generated method stub
        Gson gson = new Gson();
        String requestbody = IOUtils.toString(exchange.getRequestBody(), "UTF-8");
        RegisterDetails registerdetails = gson.fromJson(requestbody, RegisterDetails.class);
        // check if username or email have already been taken
        if (!userservice.userUsernameAvailable(registerdetails)) {
            HandlerHelpers.sendErrorResponse(exchange, 406, "username already in use");
        } else if (!userservice.userEmailAvailable(registerdetails)) {
            HandlerHelpers.sendErrorResponse(exchange, 406, "email already in use");

        } else { // good to register
            // register the user
            String registerstatus = userservice.registerUser(registerdetails);
            if (registerstatus == "success") {
                HandlerHelpers.sendMessageResponse(exchange, 406, registerstatus);
            } else{
                HandlerHelpers.sendErrorResponse(exchange, 406, registerstatus);
            }
        }
    }
    
}
