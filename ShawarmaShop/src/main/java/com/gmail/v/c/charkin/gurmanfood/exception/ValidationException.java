package com.gmail.v.c.charkin.gurmanfood.exception;

/**
 * Исключение, выбрасываемое при ошибках валидации данных.
 * Используется для обработки ошибок валидации входных данных.
 */
public class ValidationException extends BusinessLogicException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

