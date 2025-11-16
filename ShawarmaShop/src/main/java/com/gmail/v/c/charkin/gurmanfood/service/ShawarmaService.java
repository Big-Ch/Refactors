package com.gmail.v.c.charkin.gurmanfood.service;

import com.gmail.v.c.charkin.gurmanfood.domain.Shawarma;
import com.gmail.v.c.charkin.gurmanfood.dto.request.SearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShawarmaService {

    Shawarma getShawarmaById(Long shawarmaId);

    List<Shawarma> getPopularShawarmas();

    Page<Shawarma> getShawarmasByFilterParams(SearchRequest searchRequest, Pageable pageable);

    Page<Shawarma> searchShawarmas(SearchRequest searchRequest, Pageable pageable);
}
