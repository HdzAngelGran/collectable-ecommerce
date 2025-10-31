package org.arkn37.service;

import org.arkn37.dto.OfferRequest;
import org.arkn37.model.Offer;
import org.arkn37.repository.OfferRepository;

import java.util.List;
import java.util.UUID;

public class OfferService {

    private final OfferRepository offerRepository;

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
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

        return offerRepository.save(newOffer);
    }

}
