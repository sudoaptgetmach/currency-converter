package org.mach.controller;

import com.google.gson.JsonObject;
import org.mach.http.ExchangeRateApiClient;
import org.mach.http.FixerApiClient;
import org.mach.json.JsonCreator;
import org.mach.records.ConversionRecord;
import org.mach.records.QuoteRecord;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Conversion {

    public void listCurrencies() throws IOException, InterruptedException {
        FixerApiClient fixerApiClient = new FixerApiClient();
        JsonObject currencies = fixerApiClient.getCurrencies();

        if (currencies != null && !currencies.entrySet().isEmpty()) {
            for (Map.Entry<String, ?> entry : currencies.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        } else {
            System.out.println("Erro: resposta da API está vazia ou nula.");
        }
    }

    public void quoteCurrency(String moedabase) throws Exception {
        ExchangeRateApiClient exchangeRateApiClient = new ExchangeRateApiClient();
        QuoteRecord record = exchangeRateApiClient.cotacaoValor(moedabase);
        Map<String, Double> conversionRates = record.conversionRates();

        if (conversionRates != null && !conversionRates.isEmpty()) {
            Map<String, Double> filteredRates = new HashMap<>();
            String[] majorCurrencies = {"EUR", "USD", "GBP"};
            for (String currency : majorCurrencies) {
                if (conversionRates.containsKey(currency)) {
                    filteredRates.put(currency, conversionRates.get(currency));
                }
            }

            QuoteRecord filteredRecord = new QuoteRecord(moedabase, filteredRates);
            JsonCreator jsonCreator = new JsonCreator();
            jsonCreator.createJson(filteredRecord);
        } else {
            System.out.println("Erro: resposta da API está vazia ou nula.");
        }
    }

    public void conversion(String moedabase) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Selecione a moeda para conversão:");
        String moedaconvertida = scanner.nextLine();

        System.out.println("Digite o valor da conversão: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        JsonCreator jsonCreator = new JsonCreator();
        ExchangeRateApiClient exchangeRateApiClient = new ExchangeRateApiClient();

        ConversionRecord record = exchangeRateApiClient.conversaoValores(valor, moedabase, moedaconvertida);
        if (record.taxa() != 0) {
            double conversionRate = record.taxa();

            ConversionRecord updatedRecord = new ConversionRecord(moedabase, moedaconvertida, valor, conversionRate, "");
            jsonCreator.createJson(updatedRecord);
        } else {
            System.out.println("Erro: resposta da API está vazia ou nula.");
        }
    }
}
