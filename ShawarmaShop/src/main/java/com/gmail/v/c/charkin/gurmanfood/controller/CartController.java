package com.gmail.v.c.charkin.gurmanfood.controller;

import com.gmail.v.c.charkin.gurmanfood.constants.Pages;
import com.gmail.v.c.charkin.gurmanfood.constants.PathConstants;
import com.gmail.v.c.charkin.gurmanfood.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping(PathConstants.CART)
public class CartController {

    private final CartService cartService;

    @GetMapping
    public String getCart(Model model) {
        model.addAttribute("shawarmas", cartService.getShawarmasInCart());
        return Pages.CART;
    }

    @PostMapping("/add")
    public String addShawarmaToCart(@RequestParam("shawarmaId") Long shawarmaId) {
        cartService.addShawarmaToCart(shawarmaId);
        return "redirect:" + PathConstants.CART;
    }

    @PostMapping("/remove")
    public String removeShawarmaFromCart(@RequestParam("shawarmaId") Long shawarmaId) {
        cartService.removeShawarmaFromCart(shawarmaId);
        return "redirect:" + PathConstants.CART;
    }
}
