package org.arkn37.controller;

import com.google.gson.Gson;
import org.arkn37.dto.Filter;
import org.arkn37.dto.ItemRequest;
import org.arkn37.utils.FilterMapper;
import org.arkn37.model.Item;
import org.arkn37.service.ItemService;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.UUID;

public class ItemController {

    private final Gson gson = new Gson();
    private final ItemService itemService = new ItemService();
    private static final String RES_TYPE = "application/json";

    public List<Item> getByFilter(Request req, Response res) {
        Filter filter = FilterMapper.toFilter(req);
        List<Item> items = itemService.findItemByFilter(filter);

        res.type(RES_TYPE);
        res.status(200);
        return items;
    }

    public Item getById(Request req, Response res) {
        String uuid = req.params("uuid");
        Item item = itemService.findItemById(UUID.fromString(uuid));

        res.type(RES_TYPE);
        res.status(200);
        return item;
    }

    public Item add(Request req, Response res) {
        String jsonBody = req.body();

        ItemRequest itemRequest = gson.fromJson(jsonBody, ItemRequest.class);
        Item newItem = itemService.createItem(itemRequest);

        res.type(RES_TYPE);
        res.status(201);
        return newItem;
    }

}
