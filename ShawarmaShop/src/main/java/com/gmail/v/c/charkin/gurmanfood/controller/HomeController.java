package com.gmail.v.c.charkin.gurmanfood.controller;

import com.gmail.v.c.charkin.gurmanfood.constants.Pages;
import com.gmail.v.c.charkin.gurmanfood.service.ShawarmaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ShawarmaService shawarmaService;

    @GetMapping
    public String home(Model model) {
        model.addAttribute("shawarmas", shawarmaService.getPopularShawarmas());
        return Pages.HOME;
    }
}
