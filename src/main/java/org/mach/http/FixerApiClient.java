package org.mach.http;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FixerApiClient {
    public JsonObject getCurrencies() throws IOException, InterruptedException {
        String apiKey = System.getenv("FIXER_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("API key not found in environment variables");
        }

        HttpClient client = HttpClient.newHttpClient();

        String endpoint = String.format(
                "http://data.fixer.io/api/symbols?access_key=%s",
                apiKey
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();

        if (response.statusCode() != 200) {
            System.out.println("Erro na API: Código HTTP " + response.statusCode());
            return null;
        }

        if (responseBody == null || responseBody.isEmpty()) {
            System.out.println("Erro: resposta da API está vazia ou nula.");
            return null;
        }

        JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
        return jsonResponse.getAsJsonObject("symbols");
    }
}