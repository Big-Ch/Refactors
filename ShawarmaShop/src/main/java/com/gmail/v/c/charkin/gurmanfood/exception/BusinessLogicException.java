package com.gmail.v.c.charkin.gurmanfood.exception;

/**
 * Базовое исключение для бизнес-логических ошибок приложения.
 * Используется для обработки ошибок, связанных с бизнес-правилами.
 */
public class BusinessLogicException extends RuntimeException {

    public BusinessLogicException(String message) {
        super(message);
    }

    public BusinessLogicException(String message, Throwable cause) {
        super(message, cause);
    }
}

