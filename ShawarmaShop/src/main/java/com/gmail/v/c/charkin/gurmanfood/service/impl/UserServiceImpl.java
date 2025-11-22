package com.gmail.v.c.charkin.gurmanfood.service.impl;

import com.gmail.v.c.charkin.gurmanfood.constants.SuccessMessage;
import com.gmail.v.c.charkin.gurmanfood.domain.Order;
import com.gmail.v.c.charkin.gurmanfood.domain.User;
import com.gmail.v.c.charkin.gurmanfood.dto.request.ChangePasswordRequest;
import com.gmail.v.c.charkin.gurmanfood.dto.request.EditUserRequest;
import com.gmail.v.c.charkin.gurmanfood.dto.request.SearchRequest;
import com.gmail.v.c.charkin.gurmanfood.dto.response.MessageResponse;
import com.gmail.v.c.charkin.gurmanfood.repository.OrderRepository;
import com.gmail.v.c.charkin.gurmanfood.repository.UserRepository;
import com.gmail.v.c.charkin.gurmanfood.security.UserPrincipal;
import com.gmail.v.c.charkin.gurmanfood.service.PasswordValidationService;
import com.gmail.v.c.charkin.gurmanfood.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordValidationService passwordValidationService;

    @Override
    public User getAuthenticatedUser() {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmailWithCart(principal.getUsername());
    }

    @Override
    public Page<Order> searchUserOrders(SearchRequest request, Pageable pageable) {
        User user = getAuthenticatedUser();
        return orderRepository.searchUserOrders(user.getId(), request.getSearchType(), request.getText(), pageable);
    }

    @Override
    @Transactional
    public MessageResponse editUserInfo(EditUserRequest request) {
        User user = getAuthenticatedUser();
        log.info("Updating user info for user: {}", user.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setCity(request.getCity());
        user.setAddress(request.getAddress());
        user.setPhoneNumber(request.getPhoneNumber());
        log.info("User info updated successfully for user: {}", user.getEmail());
        return new MessageResponse("alert-success", SuccessMessage.USER_UPDATED);
    }

    @Override
    @Transactional
    public MessageResponse changePassword(ChangePasswordRequest request) {
        User user = getAuthenticatedUser();
        log.info("Password change attempt for user: {}", user.getEmail());
        MessageResponse passwordValidation = passwordValidationService.validatePasswordMatch(
                request.getPassword(), request.getPassword2());
        if (passwordValidation != null) {
            log.warn("Password change failed: passwords do not match for user: {}", user.getEmail());
            return passwordValidation;
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        log.info("Password changed successfully for user: {}", user.getEmail());
        return new MessageResponse("alert-success", SuccessMessage.PASSWORD_CHANGED);
    }
}
