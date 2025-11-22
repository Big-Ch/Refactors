package com.gmail.v.c.charkin.gurmanfood.service;

import com.gmail.v.c.charkin.gurmanfood.domain.Order;
import com.gmail.v.c.charkin.gurmanfood.domain.User;
import com.gmail.v.c.charkin.gurmanfood.dto.request.ChangePasswordRequest;
import com.gmail.v.c.charkin.gurmanfood.dto.request.EditUserRequest;
import com.gmail.v.c.charkin.gurmanfood.dto.request.SearchRequest;
import com.gmail.v.c.charkin.gurmanfood.dto.response.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Сервис для работы с пользователями.
 * Предоставляет функциональность для управления профилем пользователя и его заказами.
 */
public interface UserService {

    /**
     * Получает текущего аутентифицированного пользователя с загруженной корзиной.
     *
     * @return текущий пользователь
     */
    User getAuthenticatedUser();

    /**
     * Ищет заказы текущего пользователя по заданным критериям.
     *
     * @param request поисковый запрос
     * @param pageable параметры пагинации
     * @return страница с найденными заказами пользователя
     */
    Page<Order> searchUserOrders(SearchRequest request, Pageable pageable);

    /**
     * Обновляет информацию о текущем пользователе.
     *
     * @param request данные для обновления
     * @return ответ с результатом операции
     */
    MessageResponse editUserInfo(EditUserRequest request);

    /**
     * Изменяет пароль текущего пользователя.
     *
     * @param request данные для смены пароля
     * @return ответ с результатом операции
     */
    MessageResponse changePassword(ChangePasswordRequest request);
}
