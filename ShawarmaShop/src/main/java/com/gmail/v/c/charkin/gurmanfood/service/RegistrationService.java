package com.gmail.v.c.charkin.gurmanfood.service;

import com.gmail.v.c.charkin.gurmanfood.dto.response.MessageResponse;
import com.gmail.v.c.charkin.gurmanfood.dto.request.UserRequest;

/**
 * Сервис для регистрации новых пользователей.
 * Предоставляет функциональность для регистрации и активации учетных записей.
 */
public interface RegistrationService {

    /**
     * Регистрирует нового пользователя.
     * Выполняет валидацию данных, проверку captcha и отправку письма с кодом активации.
     *
     * @param captchaResponse ответ от reCAPTCHA
     * @param user данные нового пользователя
     * @return ответ с результатом операции
     */
    MessageResponse registration(String captchaResponse, UserRequest user);

    /**
     * Активирует учетную запись пользователя по коду активации.
     *
     * @param code код активации из email
     * @return ответ с результатом операции
     */
    MessageResponse activateEmailCode(String code);
}
