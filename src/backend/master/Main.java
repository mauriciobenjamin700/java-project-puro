import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/upload", new FileUploadHandler());
        server.setExecutor(null);
        System.out.println("Server running at http://localhost:8000/upload");
        server.start();
    }

    static class FileUploadHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }

            String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
            if (contentType == null || !contentType.startsWith("multipart/form-data")) {
                exchange.sendResponseHeaders(400, -1); // Bad Request
                return;
            }

            String boundary = contentType.split("boundary=")[1];
            InputStream input = exchange.getRequestBody();
            String body = new String(input.readAllBytes(), StandardCharsets.UTF_8);

            // Extract the file content
            String[] parts = body.split("--" + boundary);
            String fileContent = null;

            for (String part : parts) {
                if (part.contains("Content-Disposition") && part.contains("filename=")) {
                    int startIndex = part.indexOf("\r\n\r\n") + 4; // Skip headers
                    int endIndex = part.lastIndexOf("\r\n");
                    fileContent = part.substring(startIndex, endIndex).trim();
                    break;
                }
            }

            if (fileContent == null) {
                exchange.sendResponseHeaders(400, -1); // Bad Request
                return;
            }

            String jsonResponse = "{\"content\":\"" + escapeJson(fileContent) + "\"}";

            exchange.getResponseHeaders().add("Content-Type", "application/json");
            byte[] responseBytes = jsonResponse.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, responseBytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(responseBytes);
            os.close();
        }

        private String escapeJson(String text) {
            return text.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
        }
    }
}