package org.arkn37.repository;

import org.arkn37.dto.Filter;
import org.arkn37.exception.NotFoundException;
import org.arkn37.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ItemRepository {

    private static final Logger log = LoggerFactory.getLogger(ItemRepository.class);
    private final Map<UUID, Item> database = new HashMap<>();

    public List<Item> findByFilter(Filter filter) {
        List<Item> itemsFilter = database.values().stream()
                .filter(i -> filter.getMinPrice() == null || i.getPrice().compareTo(filter.getMinPrice()) >= 0)
                .filter(i -> filter.getMaxPrice() == null || i.getPrice().compareTo(filter.getMaxPrice()) <= 0)
                .toList();

        int page = filter.getPage();
        int size = filter.getSize();
        int totalElements = itemsFilter.size();

        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, totalElements);
        int totalPages = (int) Math.ceil((double) totalElements / size);
        if (page > totalPages || page <= 0 || size <= 0) return Collections.emptyList();


        return itemsFilter.subList(startIndex, endIndex);
    }

    public Item findById(UUID id) {
        Item item = database.get(id);
        if (item == null) throw new NotFoundException("Item not found");

        return item;
    }

    public Item save(Item item) {
        database.put(item.getUuid(), item);
        log.info("Item saved to 'database': {}", item.getName());
        return item;
    }
}
