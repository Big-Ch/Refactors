package com.gmail.v.c.charkin.gurmanfood.controller;

import com.gmail.v.c.charkin.gurmanfood.constants.Pages;
import com.gmail.v.c.charkin.gurmanfood.constants.PathConstants;
import com.gmail.v.c.charkin.gurmanfood.dto.request.ShawarmaRequest;
import com.gmail.v.c.charkin.gurmanfood.dto.request.SearchRequest;
import com.gmail.v.c.charkin.gurmanfood.dto.response.UserInfoResponse;
import com.gmail.v.c.charkin.gurmanfood.service.AdminService;
import com.gmail.v.c.charkin.gurmanfood.utils.ControllerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping(PathConstants.ADMIN)
@PreAuthorize("hasAuthority('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final ControllerUtils controllerUtils;

    @GetMapping("/shawarmas")
    public String getShawarmas(Pageable pageable, Model model) {
        controllerUtils.addPagination(model, adminService.getShawarmas(pageable));
        return Pages.ADMIN_SHAWARMAS;
    }

    @GetMapping("/shawarmas/search")
    public String searchShawarmas(SearchRequest request, Pageable pageable, Model model) {
        controllerUtils.addPagination(request, model, adminService.searchShawarmas(request, pageable));
        return Pages.ADMIN_SHAWARMAS;
    }

    @GetMapping("/users")
    public String getUsers(Pageable pageable, Model model) {
        controllerUtils.addPagination(model, adminService.getUsers(pageable));
        return Pages.ADMIN_ALL_USERS;
    }

    @GetMapping("/users/search")
    public String searchUsers(SearchRequest request, Pageable pageable, Model model) {
        controllerUtils.addPagination(request, model, adminService.searchUsers(request, pageable));
        return Pages.ADMIN_ALL_USERS;
    }

    @GetMapping("/order/{orderId}")
    public String getOrder(@PathVariable Long orderId, Model model) {
        model.addAttribute("order", adminService.getOrder(orderId));
        return Pages.ORDER;
    }

    @GetMapping("/orders")
    public String getOrders(Pageable pageable, Model model) {
        controllerUtils.addPagination(model, adminService.getOrders(pageable));
        return Pages.ORDERS;
    }

    @GetMapping("/orders/search")
    public String searchOrders(SearchRequest request, Pageable pageable, Model model) {
        controllerUtils.addPagination(request, model, adminService.searchOrders(request, pageable));
        return Pages.ORDERS;
    }

    @GetMapping("/shawarma/{shawarmaId}")
    public String getShawarma(@PathVariable Long shawarmaId, Model model) {
        model.addAttribute("shawarma", adminService.getShawarmaById(shawarmaId));
        return Pages.ADMIN_EDIT_SHAWARMA;
    }

    @PostMapping("/edit/shawarma")
    public String editShawarma(@Valid ShawarmaRequest shawarma, BindingResult bindingResult, Model model,
                              @RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
        if (controllerUtils.validateInputFields(bindingResult, model, "shawarma", shawarma)) {
            return Pages.ADMIN_EDIT_SHAWARMA;
        }
        return controllerUtils.setAlertFlashMessage(attributes, "/admin/shawarmas", adminService.editShawarma(shawarma, file));
    }

    @GetMapping("/add/shawarma")
    public String addShawarma() {
        return Pages.ADMIN_ADD_SHAWARMA;
    }

    @PostMapping("/add/shawarma")
    public String addShawarma(@Valid ShawarmaRequest shawarma, BindingResult bindingResult, Model model,
                             @RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
        if (controllerUtils.validateInputFields(bindingResult, model, "shawarma", shawarma)) {
            System.out.println(shawarma);
            return Pages.ADMIN_ADD_SHAWARMA;
        }
        return controllerUtils.setAlertFlashMessage(attributes, "/admin/shawarmas", adminService.addShawarma(shawarma, file));
    }

    @GetMapping("/user/{userId}")
    public String getUserById(@PathVariable Long userId, Model model, Pageable pageable) {
        UserInfoResponse userResponse = adminService.getUserById(userId, pageable);
        model.addAttribute("user", userResponse.getUser());
        controllerUtils.addPagination(model, userResponse.getOrders());
        return Pages.ADMIN_USER_DETAIL;
    }
}
