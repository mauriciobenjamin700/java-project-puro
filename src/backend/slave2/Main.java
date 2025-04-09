import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // Rotas do servidor
        server.createContext("/count", new CountHandler());
        server.createContext("/available", new AvailabilityHandler());
        
        server.setExecutor(null);
        System.out.println("Server running at http://localhost:8000/count");
        server.start();
    }

    static class CountHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }

            InputStream input = exchange.getRequestBody();
            String body = new String(input.readAllBytes());

            // Simples parsing de JSON (sem lib externa)
            String text = extractTextFromJson(body);

            int count = countDigits(text);

            String jsonResponse = "{\"digits\":" + count + "}";

            exchange.getResponseHeaders().add("Content-Type", "application/json");
            byte[] responseBytes = jsonResponse.getBytes();
            exchange.sendResponseHeaders(200, responseBytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(responseBytes);
            os.close();
        }

        private String extractTextFromJson(String json) {
            // assume json no formato: {"text": "alguma coisa"}
            int start = json.indexOf(":") + 1;
            String trimmed = json.substring(start).trim();
            if (trimmed.startsWith("\"") && trimmed.endsWith("}")) {
                trimmed = trimmed.substring(1, trimmed.length() - 2); // remove aspas e }
            }
            return trimmed;
        }
    }


    public static int countDigits(String str) {
        int count = 0;
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                count++;
            }
        }
        return count;
    }

    static class AvailabilityHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }

            String jsonResponse = "{\"available\": true}";

            exchange.getResponseHeaders().add("Content-Type", "application/json");
            byte[] responseBytes = jsonResponse.getBytes();
            exchange.sendResponseHeaders(200, responseBytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(responseBytes);
            os.close();
        }
    }
}
