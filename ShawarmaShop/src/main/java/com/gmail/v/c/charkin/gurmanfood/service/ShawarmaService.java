package com.gmail.v.c.charkin.gurmanfood.service;

import com.gmail.v.c.charkin.gurmanfood.domain.Shawarma;
import com.gmail.v.c.charkin.gurmanfood.dto.request.SearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Сервис для работы с шаурмой.
 * Предоставляет функциональность для получения и поиска шаурмы.
 */
public interface ShawarmaService {

    /**
     * Получает шаурму по идентификатору.
     *
     * @param shawarmaId идентификатор шаурмы
     * @return шаурма
     * @throws com.gmail.v.c.charkin.gurmanfood.exception.EntityNotFoundException если шаурма не найдена
     */
    Shawarma getShawarmaById(Long shawarmaId);

    /**
     * Получает список популярной шаурмы.
     *
     * @return список популярной шаурмы
     */
    List<Shawarma> getPopularShawarmas();

    /**
     * Получает шаурму с применением фильтров (категория, мораль, цена).
     *
     * @param searchRequest параметры фильтрации
     * @param pageable параметры пагинации
     * @return страница с отфильтрованной шаурмой
     */
    Page<Shawarma> getShawarmasByFilterParams(SearchRequest searchRequest, Pageable pageable);

    /**
     * Ищет шаурму по заданным критериям.
     *
     * @param searchRequest поисковый запрос
     * @param pageable параметры пагинации
     * @return страница с найденной шаурмой
     */
    Page<Shawarma> searchShawarmas(SearchRequest searchRequest, Pageable pageable);
}
