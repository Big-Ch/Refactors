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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
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
        log.info("Password reset code request for email: {}", email);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            log.warn("Password reset failed: email not found: {}", email);
            return new MessageResponse("alert-danger",  ErrorMessage.EMAIL_NOT_FOUND);
        }
        user.setPasswordResetCode(UUID.randomUUID().toString());
        log.info("Password reset code generated for user: {}", email);
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("firstName", user.getFirstName());
        attributes.put("resetCode", "/auth/reset/" + user.getPasswordResetCode());
        mailService.sendMessageHtml(user.getEmail(), "Password reset", "password-reset-template", attributes);
        log.debug("Password reset email sent to: {}", email);
        return new MessageResponse("alert-success",  SuccessMessage.PASSWORD_CODE_SEND);
    }

    @Override
    public String getEmailByPasswordResetCode(String code) {
        log.debug("Getting email by password reset code");
        return userRepository.getEmailByPasswordResetCode(code)
                .orElseThrow(() -> {
                    log.warn("Invalid password reset code: {}", code);
                    return new EntityNotFoundException(ErrorMessage.INVALID_PASSWORD_CODE);
                });
    }

    @Override
    @Transactional
    public MessageResponse resetPassword(PasswordResetRequest request) {
        log.info("Password reset attempt for email: {}", request.getEmail());
        MessageResponse passwordValidation = passwordValidationService.validatePasswordMatch(
                request.getPassword(), request.getPassword2());
        if (passwordValidation != null) {
            log.warn("Password reset failed: passwords do not match for email: {}", request.getEmail());
            return passwordValidation;
        }
        User user = userRepository.findByEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPasswordResetCode(null);
        log.info("Password reset successfully for email: {}", request.getEmail());
        return new MessageResponse("alert-success", SuccessMessage.PASSWORD_CHANGED);
    }
}
