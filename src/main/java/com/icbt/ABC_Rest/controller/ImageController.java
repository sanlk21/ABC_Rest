package com.icbt.ABC_Rest.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


    @RestController
    @RequestMapping("/api/uploads")
    @CrossOrigin(origins = "http://127.0.0.1:5501")
    public class ImageController {
        private final ResourceLoader resourceLoader;

        public ImageController(ResourceLoader resourceLoader) {
            this.resourceLoader = resourceLoader;
        }
        @GetMapping("/{filename:.+}")
        public ResponseEntity<Resource> getImage(@PathVariable String filename) {
            try {
                Path imagePath = Paths.get("C:\\projects\\images").resolve(filename).normalize();
                Resource resource = resourceLoader.getResource("file:" + imagePath.toString());

                if (resource.exists()) {
                    String contentType = Files.probeContentType(imagePath);
                    if (contentType == null) {
                        contentType = "application/octet-stream";
                    }

                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, contentType)
                            .body(resource);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

    }





