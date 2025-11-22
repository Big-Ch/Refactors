package com.gmail.v.c.charkin.gurmanfood.service;

import com.gmail.v.c.charkin.gurmanfood.domain.Order;
import com.gmail.v.c.charkin.gurmanfood.domain.Shawarma;
import com.gmail.v.c.charkin.gurmanfood.domain.User;
import com.gmail.v.c.charkin.gurmanfood.dto.request.OrderRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Сервис для работы с заказами.
 * Предоставляет функциональность для создания и управления заказами пользователей.
 */
public interface OrderService {

    /**
     * Получает заказ текущего пользователя по идентификатору.
     *
     * @param orderId идентификатор заказа
     * @return заказ
     * @throws com.gmail.v.c.charkin.gurmanfood.exception.EntityNotFoundException если заказ не найден или не принадлежит пользователю
     */
    Order getOrder(Long orderId);

    /**
     * Получает список шаурмы в корзине текущего пользователя для оформления заказа.
     *
     * @return список шаурмы в корзине
     */
    List<Shawarma> getOrdering();

    /**
     * Получает список заказов текущего пользователя с пагинацией.
     *
     * @param pageable параметры пагинации
     * @return страница с заказами пользователя
     */
    Page<Order> getUserOrdersList(Pageable pageable);

    /**
     * Создает новый заказ для пользователя.
     * После создания заказа корзина пользователя очищается.
     *
     * @param user пользователь, для которого создается заказ
     * @param orderRequest данные заказа
     * @return идентификатор созданного заказа
     */
    Long postOrder(User user, OrderRequest orderRequest);
}
