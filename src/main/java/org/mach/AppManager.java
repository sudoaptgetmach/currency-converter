package org.mach;

import org.mach.http.ExchangeRateApiClient;
import org.mach.http.FixerApiClient;
import org.mach.json.JsonCreator;
import org.mach.records.CurrencyRecord;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class AppManager {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        JsonCreator jsonCreator = new JsonCreator();
        ExchangeRateApiClient exchangeRateApiClient = new ExchangeRateApiClient();
        String moedabase = "";

        while (!moedabase.equalsIgnoreCase("sair")) {
            try {
                System.out.println("Selecione a moeda base [digite lista para conferir a lista disponível]:");
                moedabase = scanner.nextLine();

                if (moedabase.equalsIgnoreCase("lista")) {
                    Set<String> moedas = FixerApiClient.listarMoedas();
                    System.out.println("Moedas disponíveis:");
                    for (String moeda : moedas) {
                        System.out.println(moeda);
                    }
                    continue;
                }

                if (moedabase.equalsIgnoreCase("sair")) {
                    break;
                }

                System.out.println("Selecione a moeda para conversão:");
                String moedaconvertida = scanner.nextLine();

                System.out.println("Digite o valor da conversão: ");
                double valor = scanner.nextDouble();
                scanner.nextLine();

                CurrencyRecord currency = exchangeRateApiClient.conversaoValores(valor, moedabase, moedaconvertida);
                jsonCreator.createJson(currency);

            } catch (IllegalArgumentException e) {
                System.out.println("Ocorreu um erro: " + e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Informe o argumento correto.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        }
    }
}
