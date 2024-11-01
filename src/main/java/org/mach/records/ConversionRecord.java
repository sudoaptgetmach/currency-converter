package org.mach.records;

import com.google.gson.annotations.SerializedName;

public record ConversionRecord(
        @SerializedName("base_code") String moedaBase,
        @SerializedName("target_code") String moedaConvertida,
        @SerializedName("conversion_result") double resultado,
        @SerializedName("conversion_rate") double taxa,
        String responseBody
) {}