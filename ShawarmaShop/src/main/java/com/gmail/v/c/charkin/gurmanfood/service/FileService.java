package com.gmail.v.c.charkin.gurmanfood.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Service for handling file operations such as saving and deleting files.
 */
public interface FileService {

    /**
     * Saves a file to the upload directory.
     * Validates file size (max 10MB) and type (only images: jpg, png, jpeg).
     *
     * @param file the file to save
     * @return the generated filename
     * @throws IOException if file operations fail
     * @throws IllegalArgumentException if file validation fails
     */
    String saveFile(MultipartFile file) throws IOException;

    /**
     * Deletes a file from the upload directory.
     *
     * @param filename the name of the file to delete
     * @throws IOException if file deletion fails
     */
    void deleteFile(String filename) throws IOException;

    /**
     * Checks if a file exists in the upload directory.
     *
     * @param filename the name of the file to check
     * @return true if file exists, false otherwise
     */
    boolean fileExists(String filename);
}

