package org.arkn37.controller;

import org.arkn37.dto.Filter;
import org.arkn37.model.Item;
import org.arkn37.model.Offer;
import org.arkn37.service.ItemService;
import org.arkn37.service.OfferService;
import org.arkn37.utils.FilterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PageController {

    private static final Logger log = LoggerFactory.getLogger(PageController.class);
    private final ItemService itemService;
    private final OfferService offerService;

    public PageController(ItemService itemService, OfferService offerService) {
        this.itemService = itemService;
        this.offerService = offerService;
    }

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

    public ModelAndView itemOffers(Request req, Response res) {
        HashMap<String, Object> model = new HashMap<>();
        model.put("title", "Item Offers");
        model.put("headline", "Item Offers");

        UUID uuid = UUID.fromString(req.params("uuid"));
        Item item = itemService.findItemById(uuid);
        model.put("item", item);

        List<Offer> offers = offerService.findByItemUuid(item.getUuid());
        log.info("Item uuid: {}", item.getUuid());
        log.info("Offers size: {}", offers.size());
        model.put("offers", offers);

        return new ModelAndView(model, "item-offers.mustache");
    }
}
