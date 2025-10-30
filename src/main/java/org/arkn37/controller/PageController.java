package org.arkn37.controller;

import org.arkn37.dto.Filter;
import org.arkn37.model.Item;
import org.arkn37.service.ItemService;
import org.arkn37.utils.FilterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;

public class PageController {

    private static final Logger log = LoggerFactory.getLogger(PageController.class);
    private final ItemService itemService = new ItemService();

    public ModelAndView showItems(Request req, Response res) {
        HashMap<String, Object> model = new HashMap<>();

        Filter filter = FilterMapper.toFilter(req);
        List<Item> items = itemService.findItemByFilter(filter);
        model.put("title", "Home");
        model.put("headline", "Collectables");
        log.info("Item size: {}", items.size());
        model.put("items", items);

        return new ModelAndView(model, "item-list.mustache");
    }
}
