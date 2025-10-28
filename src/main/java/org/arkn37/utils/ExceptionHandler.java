package org.arkn37.utils;

import com.google.gson.JsonObject;
import org.arkn37.exception.NotFoundException;
import spark.Request;
import spark.Response;

public class ExceptionHandler {

    private static final String RES_TYPE = "application/json";
    private static final String[] ERROR_KEYS = {"code", "message"};

    private ExceptionHandler() {
        throw new IllegalStateException("Utility class");
    }

    public static String resourceNotFound(Request req, Response res) {
        res.type(RES_TYPE);
        JsonObject jsonRes = new JsonObject();
        jsonRes.addProperty(ERROR_KEYS[0], 404);
        jsonRes.addProperty(ERROR_KEYS[1], "Resource not found");
        return jsonRes.toString();
    }

    public static String internalServerError(Request req, Response res) {
        res.type(RES_TYPE);
        JsonObject jsonRes = new JsonObject();
        jsonRes.addProperty(ERROR_KEYS[0], 500);
        jsonRes.addProperty(ERROR_KEYS[1], "Internal server error");
        return jsonRes.toString();
    }

    public static void dataNotFound(NotFoundException exception, Request req, Response res) {
        res.type(RES_TYPE);
        res.status(404);
        JsonObject jsonRes = new JsonObject();
        jsonRes.addProperty(ERROR_KEYS[0], 404);
        jsonRes.addProperty(ERROR_KEYS[1], exception.getMessage());
        res.body(jsonRes.toString());
    }

    public static void badRequest(Exception exception, Request req, Response res) {
        res.type(RES_TYPE);
        res.status(400);
        JsonObject jsonRes = new JsonObject();
        jsonRes.addProperty(ERROR_KEYS[0], 400);
        jsonRes.addProperty(ERROR_KEYS[1], exception.getMessage());
        res.body(jsonRes.toString());
    }

    public static void exception(Exception exception, Request req, Response res) {
        res.type(RES_TYPE);
        res.status(500);
        JsonObject jsonRes = new JsonObject();
        jsonRes.addProperty(ERROR_KEYS[0], 500);
        jsonRes.addProperty(ERROR_KEYS[1], exception.getMessage());
        res.body(jsonRes.toString());
    }

}
