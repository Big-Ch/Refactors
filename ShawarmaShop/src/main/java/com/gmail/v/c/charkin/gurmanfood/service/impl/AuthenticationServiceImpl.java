package com.gmail.v.c.charkin.gurmanfood.service.impl;

import com.gmail.v.c.charkin.gurmanfood.constants.ErrorMessage;
import com.gmail.v.c.charkin.gurmanfood.constants.SuccessMessage;
import com.gmail.v.c.charkin.gurmanfood.domain.User;
import com.gmail.v.c.charkin.gurmanfood.dto.request.PasswordResetRequest;
import com.gmail.v.c.charkin.gurmanfood.dto.response.MessageResponse;
import com.gmail.v.c.charkin.gurmanfood.exception.EntityNotFoundException;
import com.gmail.v.c.charkin.gurmanfood.repository.UserRepository;
import com.gmail.v.c.charkin.gurmanfood.service.AuthenticationService;
import com.gmail.v.c.charkin.gurmanfood.service.PasswordValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final PasswordValidationService passwordValidationService;

    @Override
    @Transactional
    public MessageResponse sendPasswordResetCode(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return new MessageResponse("alert-danger",  ErrorMessage.EMAIL_NOT_FOUND);
        }
        user.setPasswordResetCode(UUID.randomUUID().toString());
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("firstName", user.getFirstName());
        attributes.put("resetCode", "/auth/reset/" + user.getPasswordResetCode());
        mailService.sendMessageHtml(user.getEmail(), "Password reset", "password-reset-template", attributes);
        return new MessageResponse("alert-success",  SuccessMessage.PASSWORD_CODE_SEND);
    }

    @Override
    public String getEmailByPasswordResetCode(String code) {
        return userRepository.getEmailByPasswordResetCode(code)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.INVALID_PASSWORD_CODE));
    }

    @Override
    @Transactional
    public MessageResponse resetPassword(PasswordResetRequest request) {
        MessageResponse passwordValidation = passwordValidationService.validatePasswordMatch(
                request.getPassword(), request.getPassword2());
        if (passwordValidation != null) {
            return passwordValidation;
        }
        User user = userRepository.findByEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPasswordResetCode(null);
        return new MessageResponse("alert-success", SuccessMessage.PASSWORD_CHANGED);
    }
}
