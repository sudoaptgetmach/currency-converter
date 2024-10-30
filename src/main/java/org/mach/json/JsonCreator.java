package org.mach.json;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.mach.records.CurrencyRecord;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonCreator {

    private static final Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .setPrettyPrinting()
            .create();

    private static final List<CurrencyRecord> lista = new ArrayList<>();

    public void createJson(CurrencyRecord c) {

        lista.add(c);

        try (FileWriter writer = new FileWriter("conversion.json")) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            System.out.println("Um erro aconteceu: ");
            System.out.println(e.getMessage());
        }

    }
}
