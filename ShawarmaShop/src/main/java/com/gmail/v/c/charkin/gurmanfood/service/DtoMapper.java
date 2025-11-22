package com.gmail.v.c.charkin.gurmanfood.service;

import com.gmail.v.c.charkin.gurmanfood.domain.Order;
import com.gmail.v.c.charkin.gurmanfood.domain.Shawarma;
import com.gmail.v.c.charkin.gurmanfood.domain.User;
import com.gmail.v.c.charkin.gurmanfood.dto.request.OrderRequest;
import com.gmail.v.c.charkin.gurmanfood.dto.request.ShawarmaRequest;
import com.gmail.v.c.charkin.gurmanfood.dto.request.UserRequest;

/**
 * Сервис для маппинга DTO в сущности и обратно.
 * Централизует всю логику преобразования между слоями представления и домена.
 */
public interface DtoMapper {

    /**
     * Маппит UserRequest в User.
     *
     * @param userRequest DTO запроса пользователя
     * @return сущность User
     */
    User mapToUser(UserRequest userRequest);

    /**
     * Маппит ShawarmaRequest в Shawarma.
     *
     * @param shawarmaRequest DTO запроса шаурмы
     * @return сущность Shawarma
     */
    Shawarma mapToShawarma(ShawarmaRequest shawarmaRequest);

    /**
     * Маппит OrderRequest в Order.
     *
     * @param orderRequest DTO запроса заказа
     * @return сущность Order
     */
    Order mapToOrder(OrderRequest orderRequest);
}

