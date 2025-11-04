package org.arkn37.service;

import com.google.gson.JsonObject;
import org.arkn37.controller.NotificationWebSocketController;
import org.arkn37.dto.ItemRequest;
import org.arkn37.dto.OfferRequest;
import org.arkn37.model.Item;
import org.arkn37.model.Offer;
import org.arkn37.repository.OfferRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OfferService {

    private final OfferRepository offerRepository;
    private final ItemService itemService;

    public OfferService(OfferRepository offerRepository, ItemService itemService) {
        this.offerRepository = offerRepository;
        this.itemService = itemService;
    }

    public List<Offer> findByItemUuid(UUID itemUuid) {
        return offerRepository.findByItemUuid(itemUuid);
    }

    public Offer createOffer(UUID itemUuid, OfferRequest dto) {
        Offer newOffer = new Offer();
        newOffer.setItemUuid(itemUuid);
        newOffer.setUuid(UUID.randomUUID());
        newOffer.setName(dto.getName());
        newOffer.setAmount(dto.getAmount());
        newOffer.setEnabled(true);
        Offer offer = offerRepository.save(newOffer);
        ItemRequest itemReq = new ItemRequest();
        itemReq.setPrice(offer.getAmount());
        itemService.updateItem(offer.getItemUuid(), itemReq);

        JsonObject data = new JsonObject();
        data.addProperty("name", offer.getName());
        data.addProperty("price", offer.getAmount());
        data.addProperty("message", "New offer available!");
        NotificationWebSocketController.broadcastMessage(data.toString());

        return offer;
    }

    public Optional<Offer> findLastestByItemUuid(UUID itemUuid) {
        return offerRepository.findLastestByItemId(itemUuid);
    }

}
