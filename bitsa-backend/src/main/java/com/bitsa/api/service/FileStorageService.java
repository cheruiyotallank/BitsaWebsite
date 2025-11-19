package com.bitsa.api.service;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileStorageService {
    String store(MultipartFile file);
    Path load(String filename);
}
