import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
            System.out.println("Content-Type: " + contentType);
            if (contentType == null || !contentType.startsWith("multipart/form-data")) {
                System.out.println("Invalid Content-Type: " + contentType);
                exchange.sendResponseHeaders(400, -1);
                return;
            }

            String boundary = "--" + contentType.split("boundary=")[1];
            InputStream input = exchange.getRequestBody();
            byte[] rawBody = input.readAllBytes();

            // ISO-8859-1 preserva bytes crus sem tentar converter como UTF-8
            String body = new String(rawBody, StandardCharsets.ISO_8859_1);
            System.out.println("Body received (" + body.length() + " chars)");

            String[] parts = body.split(boundary);
            String fileContent = null;

            for (String part : parts) {
                if (part.contains("Content-Disposition") && part.contains("filename=")) {
                    int start = part.indexOf("\r\n\r\n") + 4;
                    int end = part.lastIndexOf("\r\n");
                    fileContent = part.substring(start, end).trim();
                    break;
                }
            }

            if (fileContent == null) {
                System.out.println("Arquivo não encontrado no corpo da requisição.");
                exchange.sendResponseHeaders(400, -1);
                return;
            }

            System.out.println("Conteúdo do arquivo: " + fileContent);

            // Processamento com os dois slaves
            ExecutorService executor = Executors.newFixedThreadPool(2);
            final String finalFileContent = fileContent;
            int[] results = new int[2]; // letters, digits

            executor.submit(() -> {
                try {
                    results[0] = processSlave("http://java-slave1:8000", finalFileContent, "letters");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            executor.submit(() -> {
                try {
                    results[1] = processSlave("http://java-slave2:8000", finalFileContent, "digits");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            executor.shutdown();
            while (!executor.isTerminated()) {
            }

            String jsonResponse = String.format(
                    "{\"text\": \"%s\", \"letters\": %d, \"digits\": %d}",
                    escapeJson(fileContent), results[0], results[1]);

            exchange.getResponseHeaders().add("Content-Type", "application/json");
            byte[] responseBytes = jsonResponse.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, responseBytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(responseBytes);
            os.close();
        }

        private int processSlave(String slaveUrl, String fileContent, String key) throws IOException {
            // Verifica se o slave está disponível
            URL availableUrl = new URL(slaveUrl + "/available");
            HttpURLConnection conn = (HttpURLConnection) availableUrl.openConnection();
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() != 200)
                return 0;

            String availableResponse = new String(conn.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            if (!availableResponse.contains("\"available\": true"))
                return 0;

            // Envia para /count
            URL countUrl = new URL(slaveUrl + "/count");
            HttpURLConnection countConn = (HttpURLConnection) countUrl.openConnection();
            countConn.setRequestMethod("POST");
            countConn.setDoOutput(true);
            countConn.setRequestProperty("Content-Type", "application/json");

            String json = String.format("{\"text\": \"%s\"}", escapeJson(fileContent));
            try (OutputStream os = countConn.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
            }

            if (countConn.getResponseCode() != 200)
                return 0;

            String response = new String(countConn.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            int startIndex = response.indexOf(key) + key.length() + 2;
            int endIndex = response.indexOf("}", startIndex);
            return Integer.parseInt(response.substring(startIndex, endIndex));
        }

        private String escapeJson(String text) {
            return text.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r");
        }
    }
}
