package org.arkn37.utils;

import org.arkn37.dto.ItemResponse;
import org.arkn37.model.Item;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Mapper {

    private Mapper() {
        throw new IllegalStateException("Utility class");
    }

    private static String toCurrency(BigDecimal amount) {
        Locale localeUS = Locale.US; // or new Locale("en", "US")
        NumberFormat currency = NumberFormat.getCurrencyInstance(localeUS);
        return currency.format(amount);
    }

    public static ItemResponse toResponse(Item item) {
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setUuid(item.getUuid());
        itemResponse.setName(item.getName());
        itemResponse.setPrice(toCurrency(item.getPrice()));
        itemResponse.setDescription(item.getDescription());
        return itemResponse;
    }
}
