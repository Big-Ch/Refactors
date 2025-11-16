package com.gmail.v.c.charkin.gurmanfood.service.impl;

import com.gmail.v.c.charkin.gurmanfood.domain.Shawarma;
import com.gmail.v.c.charkin.gurmanfood.domain.User;
import com.gmail.v.c.charkin.gurmanfood.repository.ShawarmaRepository;
import com.gmail.v.c.charkin.gurmanfood.service.CartService;
import com.gmail.v.c.charkin.gurmanfood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Shawarma shawarma = shawarmaRepository.getOne(shawarmaId);
        user.getShawarmaList().add(shawarma);
    }

    @Override
    @Transactional
    public void removeShawarmaFromCart(Long shawarmaId) {
        User user = userService.getAuthenticatedUser();
        Shawarma shawarma = shawarmaRepository.getOne(shawarmaId);
        user.getShawarmaList().remove(shawarma);
    }
}
