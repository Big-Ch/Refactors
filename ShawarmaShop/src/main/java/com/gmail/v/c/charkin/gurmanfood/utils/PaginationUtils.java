package com.gmail.v.c.charkin.gurmanfood.utils;

import com.gmail.v.c.charkin.gurmanfood.dto.request.SearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Утилитный класс для работы с пагинацией.
 * Предоставляет методы для вычисления и добавления информации о пагинации в модель.
 */
@Component
public class PaginationUtils {

    private static final int MAX_PAGES_WITHOUT_ELLIPSIS = 7;

    /**
     * Добавляет информацию о пагинации в модель.
     *
     * @param model модель для добавления атрибутов
     * @param page страница с данными
     * @param <T> тип элементов на странице
     */
    public <T> void addPagination(Model model, Page<T> page) {
        model.addAttribute("pagination", computePagination(page));
        model.addAttribute("page", page);
    }

    /**
     * Добавляет информацию о пагинации и поисковом запросе в модель.
     *
     * @param searchRequest поисковый запрос
     * @param model модель для добавления атрибутов
     * @param page страница с данными
     * @param <T> тип элементов на странице
     */
    public <T> void addPagination(SearchRequest searchRequest, Model model, Page<T> page) {
        model.addAttribute("searchRequest", searchRequest);
        addPagination(model, page);
    }

    /**
     * Вычисляет массив номеров страниц для отображения пагинации.
     * Использует эллипсис (-1) для больших списков страниц.
     *
     * @param page страница с данными
     * @return массив номеров страниц для отображения
     */
    private int[] computePagination(Page<?> page) {
        Integer totalPages = page.getTotalPages();
        
        if (totalPages <= MAX_PAGES_WITHOUT_ELLIPSIS) {
            return IntStream.rangeClosed(1, totalPages).toArray();
        }

        Integer pageNumber = page.getNumber() + 1;
        Integer[] head = pageNumber > 4 ? new Integer[]{1, -1} : new Integer[]{1, 2, 3};
        Integer[] tail = pageNumber < (totalPages - 3) 
                ? new Integer[]{-1, totalPages} 
                : new Integer[]{totalPages - 2, totalPages - 1, totalPages};
        Integer[] bodyBefore = (pageNumber > 4 && pageNumber < (totalPages - 1)) 
                ? new Integer[]{pageNumber - 2, pageNumber - 1} 
                : new Integer[]{};
        Integer[] bodyAfter = (pageNumber > 2 && pageNumber < (totalPages - 3)) 
                ? new Integer[]{pageNumber + 1, pageNumber + 2} 
                : new Integer[]{};

        List<Integer> list = new ArrayList<>();
        Collections.addAll(list, head);
        Collections.addAll(list, bodyBefore);
        Collections.addAll(list, (pageNumber > 3 && pageNumber < totalPages - 2) 
                ? new Integer[]{pageNumber} 
                : new Integer[]{});
        Collections.addAll(list, bodyAfter);
        Collections.addAll(list, tail);
        
        Integer[] arr = list.toArray(new Integer[0]);
        return Arrays.stream(arr).mapToInt(Integer::intValue).toArray();
    }
}

