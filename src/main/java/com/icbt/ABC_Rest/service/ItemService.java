package com.icbt.ABC_Rest.service;

import com.icbt.ABC_Rest.dto.ItemDto;
import com.icbt.ABC_Rest.entity.Item;
import com.icbt.ABC_Rest.entity.Category;
import com.icbt.ABC_Rest.repo.ItemRepo;
import com.icbt.ABC_Rest.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ItemService {

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    public ItemDto createItem(ItemDto itemDto) {
        try {
            validateItemDto(itemDto);
            Item item = convertToEntity(itemDto);
            Item savedItem = itemRepo.save(item);
            return convertToDto(savedItem);
        } catch (Exception e) {
            throw new RuntimeException("Error creating item: " + e.getMessage(), e);
        }
    }

    public List<ItemDto> getAllItems() {
        List<Item> itemList = itemRepo.findAll();
        return itemList.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public ItemDto updateItem(ItemDto itemDto) {
        if (itemDto.getId() == null || !itemRepo.existsById(itemDto.getId())) {
            throw new RuntimeException("Item not found with id: " + itemDto.getId());
        }
        Item item = convertToEntity(itemDto);
        Item updatedItem = itemRepo.save(item);
        return convertToDto(updatedItem);
    }

    public boolean deleteItem(Long itemId) {
        if (itemId == null || !itemRepo.existsById(itemId)) {
            return false;
        }
        itemRepo.deleteById(itemId);
        return true;
    }

    private Item convertToEntity(ItemDto itemDto) {
        Item item = new Item();
        item.setId(itemDto.getId());
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setPrice(itemDto.getPrice());
        item.setQuantity(itemDto.getQuantity());
        item.setImagePath(itemDto.getImagePath());

        if (itemDto.getCategoryId() != null) {
            Category category = categoryRepo.findById(itemDto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + itemDto.getCategoryId()));
            item.setCategory(category);
        }
        return item;
    }

    private ItemDto convertToDto(Item item) {
        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setPrice(item.getPrice());
        dto.setQuantity(item.getQuantity());
        dto.setImagePath(item.getImagePath());
        if (item.getCategory() != null) {
            dto.setCategoryId(item.getCategory().getId());
        }
        return dto;
    }

    private void validateItemDto(ItemDto itemDto) {
        if (itemDto.getName() == null || itemDto.getName().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be null or empty");
        }
        if (itemDto.getDescription() == null || itemDto.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Item description cannot be null or empty");
        }
        if (itemDto.getPrice() <= 0) {
            throw new IllegalArgumentException("Item price must be greater than 0");
        }
        if (itemDto.getQuantity() < 0) {
            throw new IllegalArgumentException("Item quantity cannot be negative");
        }
        if (itemDto.getCategoryId() == null) {
            throw new IllegalArgumentException("Item category cannot be null");
        }
    }
}
