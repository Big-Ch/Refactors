package com.gmail.v.c.charkin.gurmanfood.repository;

import com.gmail.v.c.charkin.gurmanfood.domain.Shawarma;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShawarmaRepository extends JpaRepository<Shawarma, Long> {

    List<Shawarma> findByIdIn(List<Long> shawarmasIds);

    Page<Shawarma> findAllByOrderByPriceAsc(Pageable pageable);

    @Query("SELECT shawarma FROM Shawarma shawarma WHERE " +
            "(CASE " +
            "   WHEN :searchType = 'shawarmaTitle' THEN UPPER(shawarma.shawarmaTitle) " +
            "   ELSE UPPER(shawarma.category) " +
            "END) " +
            "LIKE UPPER(CONCAT('%',:text,'%')) " +
            "ORDER BY shawarma.price ASC")
    Page<Shawarma> searchShawarmas(String searchType, String text, Pageable pageable);

    @Query("SELECT shawarma FROM Shawarma shawarma " +
            "WHERE (coalesce(:categorys, null) IS NULL OR shawarma.category IN :categorys) " +
            "AND (coalesce(:moralitys, null) IS NULL OR shawarma.morality IN :moralitys) " +
            "AND (coalesce(:priceStart, null) IS NULL OR shawarma.price BETWEEN :priceStart AND :priceEnd) " +
            "ORDER BY shawarma.price ASC")
    Page<Shawarma> getShawarmasByFilterParams(
            List<String> categorys,
            List<String> moralitys,
            Integer priceStart,
            Integer priceEnd,
            Pageable pageable);
}
