package com.gmail.v.c.charkin.gurmanfood.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Исключение, выбрасываемое когда запрашиваемая сущность не найдена в базе данных.
 * Автоматически преобразуется в HTTP 404 статус.
 */
public class EntityNotFoundException extends BusinessLogicException {

    private final HttpStatus status;

    public EntityNotFoundException(String message) {
        super(message);
        this.status = HttpStatus.NOT_FOUND;
    }

    /**
     * Преобразует исключение в ResponseStatusException для совместимости с существующим кодом.
     *
     * @return ResponseStatusException с HTTP 404 статусом
     */
    public ResponseStatusException toResponseStatusException() {
        return new ResponseStatusException(status, getMessage());
    }

    public HttpStatus getStatus() {
        return status;
    }
}

