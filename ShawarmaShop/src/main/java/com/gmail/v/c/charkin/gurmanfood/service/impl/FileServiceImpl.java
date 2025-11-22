package com.gmail.v.c.charkin.gurmanfood.service.impl;

import com.gmail.v.c.charkin.gurmanfood.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of FileService for handling file operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png");
    private static final String PATH_SEPARATOR = "/";

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public String saveFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be null or empty");
        }

        validateFile(file);

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdirs();
            if (!created) {
                log.error("Failed to create upload directory: {}", uploadPath);
                throw new IOException("Failed to create upload directory: " + uploadPath);
            }
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new IllegalArgumentException("Original filename cannot be null or empty");
        }

        String extension = getFileExtension(originalFilename);
        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + extension;

        Path destinationPath = Paths.get(uploadPath + PATH_SEPARATOR + resultFilename);
        file.transferTo(destinationPath.toFile());

        log.debug("File saved successfully: {}", resultFilename);
        return resultFilename;
    }

    @Override
    public void deleteFile(String filename) throws IOException {
        if (filename == null || filename.isEmpty()) {
            log.warn("Attempted to delete file with null or empty filename");
            return;
        }

        Path filePath = Paths.get(uploadPath + PATH_SEPARATOR + filename);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
            log.debug("File deleted successfully: {}", filename);
        } else {
            log.warn("File not found for deletion: {}", filename);
        }
    }

    @Override
    public boolean fileExists(String filename) {
        if (filename == null || filename.isEmpty()) {
            return false;
        }

        Path filePath = Paths.get(uploadPath + PATH_SEPARATOR + filename);
        return Files.exists(filePath);
    }

    /**
     * Validates the uploaded file.
     *
     * @param file the file to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateFile(MultipartFile file) {
        // Validate file size
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException(
                    String.format("File size exceeds maximum allowed size of %d MB", MAX_FILE_SIZE / (1024 * 1024))
            );
        }

        // Validate file type
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new IllegalArgumentException("File must have a filename");
        }

        String extension = getFileExtension(originalFilename).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException(
                    String.format("File type not allowed. Allowed types: %s", String.join(", ", ALLOWED_EXTENSIONS))
            );
        }

        // Validate content type
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("File must be an image");
        }
    }

    /**
     * Extracts file extension from filename.
     *
     * @param filename the filename
     * @return the file extension
     */
    private String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == filename.length() - 1) {
            throw new IllegalArgumentException("File must have a valid extension");
        }
        return filename.substring(lastDotIndex + 1);
    }
}

