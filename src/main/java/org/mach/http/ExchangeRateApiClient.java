package org.mach.http;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.mach.records.ConversionRecord;
import org.mach.records.QuoteRecord;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ExchangeRateApiClient {

    private final Gson gson = new Gson();

    public QuoteRecord cotacaoValor(String moedabase) throws IOException, InterruptedException {
        String apiKey = System.getenv("EXCHANGERATE_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("API key not found in environment variables");
        }

        HttpClient client = HttpClient.newHttpClient();

        String endereco = String.format(
                "https://v6.exchangerate-api.com/v6/%s/latest/%s",
                apiKey, moedabase
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();

        if (response.statusCode() != 200) {
            System.out.println("Erro na API: Código HTTP " + response.statusCode());
            return new QuoteRecord(moedabase, null);
        }

        if (responseBody == null || responseBody.isEmpty()) {
            System.out.println("Erro: resposta da API está vazia ou nula.");
            return new QuoteRecord(moedabase, null);
        }

        JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

        return gson.fromJson(jsonResponse, QuoteRecord.class);
    }

    public ConversionRecord conversaoValores(double valor, String moedabase, String moedaconvertida) throws IOException, InterruptedException {
        String apiKey = System.getenv("EXCHANGERATE_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("API key not found in environment variables");
        }

        HttpClient client = HttpClient.newHttpClient();

        String endereco = String.format(
                "https://v6.exchangerate-api.com/v6/%s/pair/%s/%s/%f",
                apiKey, moedabase, moedaconvertida, valor
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();

        if (response.statusCode() != 200) {
            System.out.println("Erro na API: Código HTTP " + response.statusCode());
            return new ConversionRecord(moedabase, moedaconvertida, valor, 0, response.body());
        }

        if (responseBody == null || responseBody.isEmpty()) {
            System.out.println("Erro: resposta da API está vazia ou nula.");
            return new ConversionRecord(moedabase, moedaconvertida, valor, 0, "");
        }

        JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

        return gson.fromJson(jsonResponse, ConversionRecord.class);
    }
}