package org.arkn37.service;

import org.arkn37.dto.Filter;
import org.arkn37.dto.ItemRequest;
import org.arkn37.model.Item;
import org.arkn37.repository.ItemRepository;

import java.util.List;
import java.util.UUID;

public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item findItemById(UUID id) {
        return itemRepository.findById(id);
    }

    public List<Item> findItemByFilter(Filter filter) {
        return itemRepository.findByFilter(filter);
    }

    public Item createItem(ItemRequest dto) {
        Item newItem = new Item();
        newItem.setUuid(UUID.randomUUID());
        newItem.setName(dto.getName());
        newItem.setPrice(dto.getPrice());
        newItem.setDescription(dto.getDescription());

        return itemRepository.save(newItem);
    }
}
