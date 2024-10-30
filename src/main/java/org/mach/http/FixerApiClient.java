package org.mach.http;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Set;

public class FixerApiClient {

    public static Set<String> listarMoedas() throws Exception {

        String apiKey = System.getenv("FIXER_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("API key not found in environment variables");
        }

        String url = "https://data.fixer.io/api/symbols?access_key=" + apiKey;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Erro ao acessar a API: " + response.body());
        }

        JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonObject symbols = jsonResponse.getAsJsonObject("symbols");

        return symbols.keySet();
    }
}
