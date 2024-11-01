package org.mach;

import org.mach.controller.Conversion;

import java.util.Scanner;

public class AppManager {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Conversion conversion = new Conversion();
        String input;

        do {
            System.out.println("Digite '1' para cotação de moeda, '2' para conversão de valores, '3' para listar moedas ou 'sair' para encerrar:");
            input = scanner.nextLine();

            try {
                switch (input) {
                    case "1" -> {
                        System.out.println("Digite a moeda base:");
                        String moedabase = scanner.nextLine();
                        conversion.quoteCurrency(moedabase);
                    }
                    case "2" -> {
                        System.out.println("Digite a moeda base:");
                        String moedabase = scanner.nextLine();
                        conversion.conversion(moedabase);
                    }
                    case "3" -> conversion.listCurrencies();
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (!input.equalsIgnoreCase("sair"));

        System.out.println("Aplicação encerrada.");
    }
}