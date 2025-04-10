import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

public class UploadUtil {

    public static String enviarArquivo(File arquivo, String urlServidor) throws IOException {
        String boundary = Long.toHexString(System.currentTimeMillis());
        String CRLF = "\r\n";

        HttpURLConnection connection = (HttpURLConnection) new URL(urlServidor).openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        try (
            OutputStream output = connection.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, "UTF-8"), true);
        ) {
            writer.append("--").append(boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"")
                  .append(arquivo.getName()).append("\"").append(CRLF);
            writer.append("Content-Type: text/plain").append(CRLF);
            writer.append(CRLF).flush();
            Files.copy(arquivo.toPath(), output);
            output.flush();
            writer.append(CRLF).flush();
            writer.append("--").append(boundary).append("--").append(CRLF).flush();
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder resposta = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                resposta.append(line);
            }
            in.close();

            // Converte para objeto JSON
            JSONObject jsonObject = new JSONObject(resposta.toString());
            String respostaFormatada = jsonObject.toString(4); // 4 = identação bonita (opcional)

            // Imprime no terminal
            System.out.println("Resposta do servidor (JSON):\n" + respostaFormatada);

            return respostaFormatada;
        } else {
            throw new IOException("Erro na conexão: Código " + responseCode);
        }
    }
}

