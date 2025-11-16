package com.gmail.v.c.charkin.gurmanfood.controller;

import com.gmail.v.c.charkin.gurmanfood.constants.Pages;
import com.gmail.v.c.charkin.gurmanfood.constants.PathConstants;
import com.gmail.v.c.charkin.gurmanfood.dto.request.SearchRequest;
import com.gmail.v.c.charkin.gurmanfood.service.ShawarmaService;
import com.gmail.v.c.charkin.gurmanfood.utils.ControllerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(PathConstants.SHAWARMA)
public class ShawarmaController {

    private final ShawarmaService shawarmaService;
    private final ControllerUtils controllerUtils;

    @GetMapping("/{shawarmaId}")
    public String getShawarmaById(@PathVariable Long shawarmaId, Model model) {
        model.addAttribute("shawarma", shawarmaService.getShawarmaById(shawarmaId));
        return Pages.SHAWARMA;
    }

    @GetMapping
    public String getShawarmasByFilterParams(SearchRequest request, Model model, Pageable pageable) {
        controllerUtils.addPagination(request, model, shawarmaService.getShawarmasByFilterParams(request, pageable));
        return Pages.SHAWARMAS;
    }

    @GetMapping("/search")
    public String searchShawarmas(SearchRequest request, Model model, Pageable pageable) {
        controllerUtils.addPagination(request, model, shawarmaService.searchShawarmas(request, pageable));
        return Pages.SHAWARMAS;
    }
}
