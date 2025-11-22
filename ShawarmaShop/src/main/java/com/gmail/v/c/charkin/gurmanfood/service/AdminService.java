package com.gmail.v.c.charkin.gurmanfood.service;

import com.gmail.v.c.charkin.gurmanfood.domain.Order;
import com.gmail.v.c.charkin.gurmanfood.domain.Shawarma;
import com.gmail.v.c.charkin.gurmanfood.domain.User;
import com.gmail.v.c.charkin.gurmanfood.dto.request.ShawarmaRequest;
import com.gmail.v.c.charkin.gurmanfood.dto.request.SearchRequest;
import com.gmail.v.c.charkin.gurmanfood.dto.response.MessageResponse;
import com.gmail.v.c.charkin.gurmanfood.dto.response.UserInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Сервис для административных операций.
 * Предоставляет функциональность для управления шаурмой, пользователями и заказами.
 */
public interface AdminService {

    /**
     * Получает список всех шаурм с пагинацией, отсортированный по цене.
     *
     * @param pageable параметры пагинации
     * @return страница с шаурмой
     */
    Page<Shawarma> getShawarmas(Pageable pageable);

    /**
     * Ищет шаурму по заданным критериям.
     *
     * @param request поисковый запрос
     * @param pageable параметры пагинации
     * @return страница с найденной шаурмой
     */
    Page<Shawarma> searchShawarmas(SearchRequest request, Pageable pageable);

    /**
     * Получает список всех пользователей с пагинацией.
     *
     * @param pageable параметры пагинации
     * @return страница с пользователями
     */
    Page<User> getUsers(Pageable pageable);

    /**
     * Ищет пользователей по заданным критериям.
     *
     * @param request поисковый запрос
     * @param pageable параметры пагинации
     * @return страница с найденными пользователями
     */
    Page<User> searchUsers(SearchRequest request, Pageable pageable);

    /**
     * Получает заказ по идентификатору.
     *
     * @param orderId идентификатор заказа
     * @return заказ
     * @throws com.gmail.v.c.charkin.gurmanfood.exception.EntityNotFoundException если заказ не найден
     */
    Order getOrder(Long orderId);

    /**
     * Получает список всех заказов с пагинацией.
     *
     * @param pageable параметры пагинации
     * @return страница с заказами
     */
    Page<Order> getOrders(Pageable pageable);

    /**
     * Ищет заказы по заданным критериям.
     *
     * @param request поисковый запрос
     * @param pageable параметры пагинации
     * @return страница с найденными заказами
     */
    Page<Order> searchOrders(SearchRequest request, Pageable pageable);

    /**
     * Получает шаурму по идентификатору.
     *
     * @param shawarmaId идентификатор шаурмы
     * @return шаурма
     * @throws com.gmail.v.c.charkin.gurmanfood.exception.EntityNotFoundException если шаурма не найдена
     */
    Shawarma getShawarmaById(Long shawarmaId);

    /**
     * Редактирует существующую шаурму.
     *
     * @param shawarmaRequest данные для обновления шаурмы
     * @param file файл изображения (опционально)
     * @return ответ с результатом операции
     * @throws IOException при ошибке работы с файлом
     */
    MessageResponse editShawarma(ShawarmaRequest shawarmaRequest, MultipartFile file) throws IOException;

    /**
     * Добавляет новую шаурму.
     *
     * @param shawarmaRequest данные новой шаурмы
     * @param file файл изображения (опционально)
     * @return ответ с результатом операции
     * @throws IOException при ошибке работы с файлом
     */
    MessageResponse addShawarma(ShawarmaRequest shawarmaRequest, MultipartFile file) throws IOException;

    /**
     * Получает информацию о пользователе и его заказах.
     *
     * @param userId идентификатор пользователя
     * @param pageable параметры пагинации для заказов
     * @return информация о пользователе с заказами
     * @throws com.gmail.v.c.charkin.gurmanfood.exception.EntityNotFoundException если пользователь не найден
     */
    UserInfoResponse getUserById(Long userId, Pageable pageable);
}
