package com.gmail.v.c.charkin.gurmanfood.service.impl;

import com.gmail.v.c.charkin.gurmanfood.constants.ErrorMessage;
import com.gmail.v.c.charkin.gurmanfood.dto.response.MessageResponse;
import com.gmail.v.c.charkin.gurmanfood.service.PasswordValidationService;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса валидации паролей.
 */
@Service
public class PasswordValidationServiceImpl implements PasswordValidationService {

    @Override
    public MessageResponse validatePasswordMatch(String password, String password2) {
        if (password != null && !password.equals(password2)) {
            return new MessageResponse("passwordError", ErrorMessage.PASSWORDS_DO_NOT_MATCH);
        }
        return null;
    }
}

