package org.arkn37.service;

import org.arkn37.dto.Filter;
import org.arkn37.dto.ItemRequest;
import org.arkn37.dto.ItemResponse;
import org.arkn37.model.Item;
import org.arkn37.repository.ItemRepository;
import org.arkn37.utils.Mapper;

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

    public ItemResponse findItemResponseById(UUID id) {
        return Mapper.toResponse(
                itemRepository.findById(id)
        );
    }

    public List<ItemResponse> findItemByFilter(Filter filter) {
        return itemRepository.findByFilter(filter)
                .stream().map(Mapper::toResponse).toList();
    }

    public Item createItem(ItemRequest dto) {
        Item newItem = new Item();
        newItem.setUuid(UUID.randomUUID());
        newItem.setName(dto.getName());
        newItem.setPrice(dto.getPrice());
        newItem.setDescription(dto.getDescription());

        return itemRepository.save(newItem);
    }

    public void updateItem(UUID itemUuid, ItemRequest itemRequest) {
        Item item = findItemById(itemUuid);
        if (itemRequest.getPrice() != null)
            item.setPrice(itemRequest.getPrice());

        itemRepository.save(item);
    }
}
