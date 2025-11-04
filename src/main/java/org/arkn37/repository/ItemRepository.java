package org.arkn37.repository;

import org.arkn37.dto.Filter;
import org.arkn37.exception.NotFoundException;
import org.arkn37.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

public class ItemRepository {

    private static final Logger log = LoggerFactory.getLogger(ItemRepository.class);
    private static final Map<UUID, Item> database = new HashMap<>();

    static {
        Item item1 = new Item();
        item1.setUuid(UUID.randomUUID());
        item1.setName("Gorra autografiada por Peso Pluma");
        item1.setDescription("Una gorra autografiada por el famoso Peso Pluma");
        item1.setPrice(new BigDecimal("621.3"));item1.setUuid(UUID.randomUUID());
        item1.setEnabled(true);

        Item item2 = new Item();
        item2.setUuid(UUID.fromString("b3a88fd4-a670-4f35-a2b4-3541966ffff6"));
        item2.setName("Casco autografiado por Rosalía");
        item2.setDescription("Un casco autografiado por la famosa cantante Rosalía, una verdadera MOTOMAMI!");
        item2.setPrice(new BigDecimal("734.57"));
        item2.setEnabled(true);

        Item item3 = new Item();
        item3.setUuid(UUID.randomUUID());
        item3.setName("Chamarra de Bad Bunny");
        item3.setDescription("Una chamarra de la marca favorita de Bad Bunny, autografiada por el propio artista");
        item3.setPrice(new BigDecimal("521.89"));
        item3.setEnabled(true);

        Item item4 = new Item();
        item4.setUuid(UUID.randomUUID());
        item4.setName("Guitarra de Fernando Delgadillo");
        item4.setDescription("Una guitarra acústica de alta calidad utilizada por el famoso cantautor Fernando Delgadillo");
        item4.setPrice(new BigDecimal("823.12"));
        item4.setEnabled(true);

        Item item5 = new Item();
        item5.setUuid(UUID.randomUUID());
        item5.setName("Jersey firmado por Snoop Dogg");
        item5.setDescription("Un jersey autografiado por el legendario rapero Snoop Dogg");
        item5.setPrice(new BigDecimal("355.67"));
        item5.setEnabled(true);

        List.of(item1, item2, item3, item4, item5)
            .forEach(i -> database.put(i.getUuid(), i));
    }

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
        if (page >= totalPages || page < 0 || size <= 0) return Collections.emptyList();

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
