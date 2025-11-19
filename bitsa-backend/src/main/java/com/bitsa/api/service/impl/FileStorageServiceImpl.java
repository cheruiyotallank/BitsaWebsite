package com.bitsa.api.service.impl;

import com.bitsa.api.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path rootLocation;

    public FileStorageServiceImpl(@Value("${file.upload-dir}") String uploadDir) throws IOException {
        this.rootLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(this.rootLocation);
    }

    @Override
    public String store(MultipartFile file) {
        String original = StringUtils.cleanPath(file.getOriginalFilename());
        String filename = UUID.randomUUID().toString() + "-" + original;
        try {
            if (original.contains("..")) throw new IOException("Invalid filename");
            Path target = this.rootLocation.resolve(filename);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (IOException ex) {
            throw new RuntimeException("Failed to store file", ex);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }
}
