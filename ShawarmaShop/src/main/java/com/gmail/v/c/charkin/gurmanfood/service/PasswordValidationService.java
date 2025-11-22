package com.gmail.v.c.charkin.gurmanfood.service;

import com.gmail.v.c.charkin.gurmanfood.dto.response.MessageResponse;

/**
 * Сервис для валидации паролей.
 * Предоставляет централизованную логику проверки паролей.
 */
public interface PasswordValidationService {

    /**
     * Проверяет совпадение пароля и его подтверждения.
     *
     * @param password пароль
     * @param password2 подтверждение пароля
     * @return MessageResponse с ошибкой, если пароли не совпадают, или null, если валидация прошла успешно
     */
    MessageResponse validatePasswordMatch(String password, String password2);
}

