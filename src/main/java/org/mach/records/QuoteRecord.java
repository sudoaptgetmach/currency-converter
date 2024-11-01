package org.mach.records;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public record QuoteRecord(
        @SerializedName("base_code") String moedaBase,
        @SerializedName("conversion_rates") Map<String, Double> conversionRates
) {}