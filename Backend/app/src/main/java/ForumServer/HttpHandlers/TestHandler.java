package ForumServer.HttpHandlers;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class TestHandler implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        String response = "This is the response UWU";
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
