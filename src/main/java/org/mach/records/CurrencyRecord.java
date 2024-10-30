package org.mach.records;

public record CurrencyRecord(String base_code, String target_code, double conversion_rate, double conversion_result) {
}
