package com.example.hybridpos.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private final Path uploadPath =
            Paths.get("uploads/products");

    public FileStorageService() throws IOException {
        Files.createDirectories(uploadPath);
    }

    public String saveProductImage(MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            return null;
        }

        String originalName = file.getOriginalFilename();

        String extension = "";

        if (originalName != null && originalName.contains(".")) {
            extension =
                    originalName.substring(originalName.lastIndexOf("."));
        }

        String fileName =
                UUID.randomUUID() + extension;

        Path targetPath =
                uploadPath.resolve(fileName);

        Files.copy(
                file.getInputStream(),
                targetPath,
                StandardCopyOption.REPLACE_EXISTING
        );

        return "/uploads/products/" + fileName;
    }
}
