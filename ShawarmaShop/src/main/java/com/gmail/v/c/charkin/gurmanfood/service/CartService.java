package com.gmail.v.c.charkin.gurmanfood.service;

import com.gmail.v.c.charkin.gurmanfood.domain.Shawarma;

import java.util.List;

public interface CartService {

    List<Shawarma> getShawarmasInCart();

    void addShawarmaToCart(Long shawarmaId);

    void removeShawarmaFromCart(Long shawarmaId);
}
