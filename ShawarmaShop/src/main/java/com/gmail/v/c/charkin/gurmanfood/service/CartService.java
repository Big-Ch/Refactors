package com.gmail.v.c.charkin.gurmanfood.service;

import com.gmail.v.c.charkin.gurmanfood.domain.Shawarma;

import java.util.List;

/**
 * Сервис для работы с корзиной покупок.
 * Предоставляет функциональность для управления товарами в корзине пользователя.
 */
public interface CartService {

    /**
     * Получает список шаурмы в корзине текущего пользователя.
     *
     * @return список шаурмы в корзине
     */
    List<Shawarma> getShawarmasInCart();

    /**
     * Добавляет шаурму в корзину текущего пользователя.
     *
     * @param shawarmaId идентификатор шаурмы
     * @throws com.gmail.v.c.charkin.gurmanfood.exception.EntityNotFoundException если шаурма не найдена
     */
    void addShawarmaToCart(Long shawarmaId);

    /**
     * Удаляет шаурму из корзины текущего пользователя.
     *
     * @param shawarmaId идентификатор шаурмы
     * @throws com.gmail.v.c.charkin.gurmanfood.exception.EntityNotFoundException если шаурма не найдена
     */
    void removeShawarmaFromCart(Long shawarmaId);
}
