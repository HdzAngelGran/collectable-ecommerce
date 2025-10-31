package org.arkn37.repository;

import org.arkn37.dto.Filter;
import org.arkn37.exception.NotFoundException;
import org.arkn37.model.Offer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class OfferRepository {

    private static final Logger log = LoggerFactory.getLogger(OfferRepository.class);
    Map<UUID, Offer> database = new HashMap<>();

    public List<Offer> findByFilter(Filter filter) {
        List<Offer> offerFilter = database.values().stream()
                .filter(i -> true)
                .toList();

        int page = filter.getPage();
        int size = filter.getSize();
        int totalElements = offerFilter.size();

        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, totalElements);
        int totalPages = (int) Math.ceil((double) totalElements / size);
        if (page >= totalPages || page < 0 || size <= 0) return Collections.emptyList();

        return offerFilter.subList(startIndex, endIndex);
    }

    public Offer findById(UUID uuid) {
        Offer offer = database.get(uuid);
        if (offer == null) throw new NotFoundException("Offer not found");
        return offer;
    }

    public List<Offer> findByItemUuid(UUID itemUuid) {
         return database.values().stream()
                .filter(o -> o.getItemUuid().equals(itemUuid))
                .toList();
    }

    public Offer save(Offer offer) {
        database.put(offer.getUuid(), offer);
        log.info("Offer saved to 'database': {}", offer.getName());
        return offer;
    }
}
