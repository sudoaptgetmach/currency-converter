package org.mach.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.mach.records.QuoteRecord;
import org.mach.records.ConversionRecord;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonCreator {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void createJson(QuoteRecord record) throws IOException {
        JsonArray jsonArray = readExistingJson("quote.json");
        JsonElement newRecord = gson.toJsonTree(record);
        jsonArray.add(newRecord);
        writeJsonToFile(jsonArray, "quote.json");
    }

    public void createJson(ConversionRecord record) throws IOException {
        JsonArray jsonArray = readExistingJson("conversion.json");
        JsonElement newRecord = gson.toJsonTree(record);
        jsonArray.add(newRecord);
        writeJsonToFile(jsonArray, "conversion.json");
    }

    private JsonArray readExistingJson(String fileName) {
        try (FileReader reader = new FileReader(fileName)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            if (jsonElement.isJsonArray()) {
                return jsonElement.getAsJsonArray();
            } else {
                return new JsonArray();
            }
        } catch (IOException e) {
            return new JsonArray();
        }
    }

    private void writeJsonToFile(JsonArray jsonArray, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(jsonArray, writer);
        }
    }
}