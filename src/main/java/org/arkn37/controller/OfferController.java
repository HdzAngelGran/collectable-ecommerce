package org.arkn37.controller;

import com.google.gson.Gson;
import org.arkn37.dto.OfferRequest;
import org.arkn37.exception.NotFoundException;
import org.arkn37.model.Offer;
import org.arkn37.service.OfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.UUID;

public class OfferController {

    private static final Logger log = LoggerFactory.getLogger(OfferController.class);
    private final Gson gson = new Gson();
    private final OfferService offerService;
    private static final String RES_TYPE = "application/json";

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    public List<Offer> getByItemUuid(Request req, Response res) {
        String itemUuid = req.params("uuid");
        List<Offer> offers = offerService.findByItemUuid(UUID.fromString(itemUuid));
        log.info("Offers size: {}", offers.size());

        res.type(RES_TYPE);
        res.status(200);
        return offers;
    }

    public Offer add(Request req, Response res) {
        String itemUuid = req.params("uuid");
        String jsonBody = req.body();

        OfferRequest offerRequest = gson.fromJson(jsonBody, OfferRequest.class);
        Offer newOffer = offerService.createOffer(UUID.fromString(itemUuid), offerRequest);

        res.type(RES_TYPE);
        res.status(201);
        return newOffer;
    }

    public Offer getLastestByItemUuid(Request req, Response res) {
        String itemUuid = req.params("uuid");

        res.type(RES_TYPE);
        res.status(200);
        return offerService.findLastestByItemUuid(UUID.fromString(itemUuid))
                .orElseThrow(() -> new NotFoundException("Offer not found"));
    }
}
