package org.arkn37.utils;

public enum FilterKey {
    PAGE("page"),
    SIZE("size"),
    MAX_PRICE("max_price"),
    MIN_PRICE("min_price"),
    ;

    private final String key;

    FilterKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return this.key;
    }
}
