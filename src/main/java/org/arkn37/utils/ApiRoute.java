package org.arkn37.utils;

public enum ApiRoute {
    API_V1("/api/v1"),
    USERS("/users"),
    PARAM_UUID(":uuid"),
    ;

    private final String path;

    ApiRoute(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return this.path;
    }
}
