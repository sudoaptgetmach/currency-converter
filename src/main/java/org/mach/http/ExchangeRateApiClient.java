package org.mach.http;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.mach.records.CurrencyRecord;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ExchangeRateApiClient {

    private static final Gson gson = new Gson();

    public CurrencyRecord conversaoValores(double valor, String moedabase, String moedaconvertida) {
        String apiKey = System.getenv("EXCHANGERATE_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("API key not found in environment variables");
        }

        String endereco = String.format(
                "https://v6.exchangerate-api.com/v6/%s/pair/%s/%s/%f",
                apiKey, moedabase, moedaconvertida, valor
        );

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.out.println("Erro na API: Código HTTP " + response.statusCode());
                return new CurrencyRecord("N/A", "N/A", 0, 0);
            }

            String responseBody = response.body();
            System.out.println(responseBody);

            JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

            if (!jsonResponse.get("result").getAsString().equals("success")) {
                System.out.println("Erro na conversão: " + jsonResponse.get("error-type").getAsString());
                return new CurrencyRecord("N/A", "N/A", 0, 0);
            }
            return gson.fromJson(responseBody, CurrencyRecord.class);

        } catch (IOException | InterruptedException e) {
            System.out.println("Erro durante a requisição: " + e.getMessage());
            Thread.currentThread().interrupt();
        }

        return new CurrencyRecord("N/A", "N/A", 0, 0);
    }
}
