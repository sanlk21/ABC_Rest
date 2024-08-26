package com.icbt.ABC_Rest.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {
    private final Path baseDirectory = Paths.get("C:\\projects\\images");

    public String uploadImage(MultipartFile image) {
        return saveImageToLocalDirectory(image);
    }

    private String saveImageToLocalDirectory(MultipartFile image) {
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path filePath = baseDirectory.resolve(fileName);

        try {
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, image.getBytes());
            System.out.println("File saved at: " + filePath.toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + fileName, e);
        }

        return "http://localhost:8080/api/uploads/" + fileName;
    }
}
