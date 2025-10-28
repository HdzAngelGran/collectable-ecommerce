package org.arkn37.utils;

import org.arkn37.dto.Filter;
import spark.Request;
import spark.QueryParamsMap;

import java.math.BigDecimal;
import java.util.Optional;

public class FilterMapper {
    private FilterMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static Filter toFilter(Request request) {
        final Filter filter = new Filter();
        final QueryParamsMap query = request.queryMap();

        getInteger(query, FilterKey.PAGE).ifPresent(filter::setPage);
        getInteger(query, FilterKey.SIZE).ifPresent(filter::setSize);
        getBigDecimal(query, FilterKey.MIN_PRICE).ifPresent(filter::setMinPrice);
        getBigDecimal(query, FilterKey.MAX_PRICE).ifPresent(filter::setMaxPrice);

        return filter;
    }

    private static Optional<Integer> getInteger(QueryParamsMap query, FilterKey key) {
        return getValue(query, key).map(QueryParamsMap::integerValue);
    }

    private static Optional<BigDecimal> getBigDecimal(QueryParamsMap query, FilterKey key) {
        return getValue(query, key).map(QueryParamsMap::value).map(BigDecimal::new);
    }

    private static Optional<QueryParamsMap> getValue(QueryParamsMap query, FilterKey key) {
        if (query.hasKey(key.toString())) {
            QueryParamsMap value = query.get(key.toString());
            if (value != null && value.value() != null && !value.value().isEmpty()) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }
}
