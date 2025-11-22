package com.gmail.v.c.charkin.gurmanfood.service.impl;

import com.gmail.v.c.charkin.gurmanfood.constants.ErrorMessage;
import com.gmail.v.c.charkin.gurmanfood.domain.Shawarma;
import com.gmail.v.c.charkin.gurmanfood.domain.User;
import com.gmail.v.c.charkin.gurmanfood.exception.EntityNotFoundException;
import com.gmail.v.c.charkin.gurmanfood.repository.ShawarmaRepository;
import com.gmail.v.c.charkin.gurmanfood.service.CartService;
import com.gmail.v.c.charkin.gurmanfood.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final UserService userService;
    private final ShawarmaRepository shawarmaRepository;

    @Override
    public List<Shawarma> getShawarmasInCart() {
        User user = userService.getAuthenticatedUser();
        return user.getShawarmaList();
    }

    @Override
    @Transactional
    public void addShawarmaToCart(Long shawarmaId) {
        User user = userService.getAuthenticatedUser();
        log.debug("Adding shawarma to cart. Shawarma ID: {}, User: {}", shawarmaId, user.getEmail());
        Shawarma shawarma = shawarmaRepository.findById(shawarmaId)
                .orElseThrow(() -> {
                    log.warn("Failed to add shawarma to cart: shawarma not found. ID: {}, User: {}", 
                            shawarmaId, user.getEmail());
                    return new EntityNotFoundException(ErrorMessage.SHAWARMA_NOT_FOUND);
                });
        user.getShawarmaList().add(shawarma);
        log.info("Shawarma added to cart successfully. Shawarma ID: {}, User: {}", shawarmaId, user.getEmail());
    }

    @Override
    @Transactional
    public void removeShawarmaFromCart(Long shawarmaId) {
        User user = userService.getAuthenticatedUser();
        log.debug("Removing shawarma from cart. Shawarma ID: {}, User: {}", shawarmaId, user.getEmail());
        Shawarma shawarma = shawarmaRepository.findById(shawarmaId)
                .orElseThrow(() -> {
                    log.warn("Failed to remove shawarma from cart: shawarma not found. ID: {}, User: {}", 
                            shawarmaId, user.getEmail());
                    return new EntityNotFoundException(ErrorMessage.SHAWARMA_NOT_FOUND);
                });
        user.getShawarmaList().remove(shawarma);
        log.info("Shawarma removed from cart successfully. Shawarma ID: {}, User: {}", shawarmaId, user.getEmail());
    }
}
