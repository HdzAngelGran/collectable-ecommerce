package org.arkn37.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.arkn37.dto.Filter;
import org.arkn37.dto.UserRequest;
import org.arkn37.utils.FilterMapper;
import org.arkn37.model.User;
import org.arkn37.service.UserService;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.UUID;

public class UserController {

    private static final String RES_TYPE = "application/json";
    private final Gson gson = new Gson();
    private final UserService userService = new UserService();

    public List<User> getByFilter(Request req, Response res) {
        Filter filter = FilterMapper.toFilter(req);
        List<User> users = userService.findUserByFilter(filter);

        res.type(RES_TYPE);
        res.status(HttpStatus.OK_200);
        return users;
    }

    public User getById(Request req, Response res) {
        String uuid = req.params("uuid");
        User user = userService.findUserById(UUID.fromString(uuid));

        res.type(RES_TYPE);
        res.status(HttpStatus.OK_200);
        return user;
    }

    public User add(Request req, Response res) {
        String jsonBody = req.body();
        UserRequest user = gson.fromJson(jsonBody, UserRequest.class);
        User newUser = userService.createUser(user);

        res.type(RES_TYPE);
        res.status(HttpStatus.CREATED_201);
        return newUser;
    }

    public JsonObject update(Request req, Response res) {
        String uuid = req.params("uuid");
        String jsonBody = req.body();
        UserRequest user = gson.fromJson(jsonBody, UserRequest.class);
        userService.updateUser(UUID.fromString(uuid), user);

        res.type(RES_TYPE);
        res.status(HttpStatus.OK_200);
        JsonObject jsonRes = new JsonObject();
        jsonRes.addProperty("message", "User updated successfully");
        return jsonRes;
    }

    public JsonObject delete(Request req, Response res) {
        String uuid = req.params("uuid");
        userService.deleteUser(UUID.fromString(uuid));

        res.type(RES_TYPE);
        res.status(HttpStatus.OK_200);
        JsonObject jsonRes = new JsonObject();
        jsonRes.addProperty("message", "User deleted successfully");
        return jsonRes;
    }

    public JsonObject exist(Request req, Response res) {
        String uuid = req.params("uuid");
        boolean exists = userService.userExist(UUID.fromString(uuid));

        res.type(RES_TYPE);
        res.status(HttpStatus.OK_200);
        JsonObject jsonRes = new JsonObject();
        jsonRes.addProperty("exists", exists);
        return jsonRes;
    }
}
