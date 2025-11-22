package com.gmail.v.c.charkin.gurmanfood.exception;

import java.io.IOException;

/**
 * Исключение, выбрасываемое при ошибках загрузки или обработки файлов.
 * Расширяет IOException для совместимости с существующим кодом.
 */
public class FileUploadException extends IOException {

    public FileUploadException(String message) {
        super(message);
    }

    public FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}

