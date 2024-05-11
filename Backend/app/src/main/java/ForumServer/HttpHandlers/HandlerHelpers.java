package ForumServer.HttpHandlers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.google.gson.*;

public class HandlerHelpers {
    // returns whether exchange request method is in list given
    public static boolean hasMethod(HttpExchange exchange, List<String> methods) {
        return methods.contains(exchange.getRequestMethod());
    }
    public static void sendResponse(HttpExchange exchange, int rCode, String string) throws IOException {
        byte[] response = string.getBytes();
        exchange.sendResponseHeaders(200, response.length);
        exchange.getResponseBody().write(response);
        exchange.getResponseBody().close();
    }
    public static void sendResponse(HttpExchange exchange, int rCode, Map<String, String> responsedata) throws IOException {
        sendResponse(exchange, rCode, (new Gson()).toJson(responsedata));
    }
    public static void sendErrorResponse(HttpExchange exchange, int rCode, String errorMessage) throws IOException {
        // structure of an error response body : {error: String}
        sendResponse(exchange, rCode, String.format("{'error': %s}", errorMessage));
    }
    public static void sendMessageResponse(HttpExchange exchange, int rCode, String message) throws IOException {
        // structure of an error response body : {error: String}
        sendResponse(exchange, rCode, String.format("{'message': %s}", message));
    }
}
