package com.icbt.ABC_Rest.controller;

import com.icbt.ABC_Rest.dto.ItemDto;
import com.icbt.ABC_Rest.service.ImageService;
import com.icbt.ABC_Rest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
@CrossOrigin(origins = "http://127.0.0.1:5501")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ImageService imageService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping("/getItems")
    public ResponseEntity<List<ItemDto>> getItems() {
        List<ItemDto> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    @PostMapping("/saveItem")
    public ResponseEntity<?> saveItem(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("price") Double price,
            @RequestParam("quantity") Integer quantity,
            @RequestParam(value = "image", required = false) MultipartFile file) {
        try {
            ItemDto itemDto = new ItemDto();
            itemDto.setName(name);
            itemDto.setDescription(description);
            itemDto.setCategoryId(categoryId);
            itemDto.setPrice(price);
            itemDto.setQuantity(quantity);

            String s = imageService.uploadImage(file);
            itemDto.setImagePath(s);

            ItemDto createdItem = itemService.createItem(itemDto);
            System.out.println(createdItem.getImagePath());
            return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateItem/{id}")
    public ResponseEntity<?> updateItem(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("price") Double price,
            @RequestParam("quantity") Integer quantity,
            @RequestParam(value = "image", required = false) MultipartFile file) {
        try {
            ItemDto itemDto = new ItemDto();
            itemDto.setId(id);
            itemDto.setName(name);
            itemDto.setDescription(description);
            itemDto.setCategoryId(categoryId);
            itemDto.setPrice(price);
            itemDto.setQuantity(quantity);

            if (file != null && !file.isEmpty()) {
                String imagePath = saveImage(file);
                itemDto.setImagePath(imagePath);
            }

            ItemDto updatedItem = itemService.updateItem(itemDto);
            return ResponseEntity.ok(updatedItem);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteItem/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        boolean deleted = itemService.deleteItem(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    private String saveImage(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/" + fileName; // Adjust this path based on your server configuration
    }
}