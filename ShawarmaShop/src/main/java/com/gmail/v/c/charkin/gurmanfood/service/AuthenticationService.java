package com.gmail.v.c.charkin.gurmanfood.service;

import com.gmail.v.c.charkin.gurmanfood.dto.request.PasswordResetRequest;
import com.gmail.v.c.charkin.gurmanfood.dto.response.MessageResponse;

/**
 * Сервис для аутентификации и восстановления пароля.
 * Предоставляет функциональность для сброса пароля пользователя.
 */
public interface AuthenticationService {

    /**
     * Отправляет код сброса пароля на указанный email.
     *
     * @param email email пользователя
     * @return ответ с результатом операции
     */
    MessageResponse sendPasswordResetCode(String email);

    /**
     * Получает email пользователя по коду сброса пароля.
     *
     * @param code код сброса пароля
     * @return email пользователя
     * @throws com.gmail.v.c.charkin.gurmanfood.exception.EntityNotFoundException если код не найден
     */
    String getEmailByPasswordResetCode(String code);

    /**
     * Сбрасывает пароль пользователя.
     *
     * @param request данные для сброса пароля (email, новый пароль)
     * @return ответ с результатом операции
     */
    MessageResponse resetPassword(PasswordResetRequest request);
}
