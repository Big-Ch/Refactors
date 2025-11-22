package com.gmail.v.c.charkin.gurmanfood.controller;

import com.gmail.v.c.charkin.gurmanfood.constants.Pages;
import com.gmail.v.c.charkin.gurmanfood.constants.PathConstants;
import com.gmail.v.c.charkin.gurmanfood.dto.request.PasswordResetRequest;
import com.gmail.v.c.charkin.gurmanfood.dto.response.MessageResponse;
import com.gmail.v.c.charkin.gurmanfood.service.AuthenticationService;
import com.gmail.v.c.charkin.gurmanfood.utils.ModelUtils;
import com.gmail.v.c.charkin.gurmanfood.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(PathConstants.AUTH)
public class AuthenticationController {

    private final AuthenticationService authService;
    private final ValidationUtils validationUtils;
    private final ModelUtils modelUtils;

    @GetMapping("/forgot")
    public String forgotPassword() {
        return Pages.FORGOT_PASSWORD;
    }

    @PostMapping("/forgot")
    public String forgotPassword(@RequestParam String email, Model model) {
        return modelUtils.setAlertMessage(model, Pages.FORGOT_PASSWORD, authService.sendPasswordResetCode(email));
    }

    @GetMapping("/reset/{code}")
    public String resetPassword(@PathVariable String code, Model model) {
        model.addAttribute("email", authService.getEmailByPasswordResetCode(code));
        return Pages.RESET_PASSWORD;
    }

    @PostMapping("/reset")
    public String resetPassword(@Valid PasswordResetRequest request, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes, Model model) {
        if (validationUtils.validateInputFields(bindingResult, model, "email", request.getEmail())) {
            return Pages.RESET_PASSWORD;
        }
        MessageResponse messageResponse = authService.resetPassword(request);
        if (validationUtils.validateInputField(model, messageResponse, "email", request.getEmail())) {
            return Pages.RESET_PASSWORD;
        }
        return modelUtils.setAlertFlashMessage(redirectAttributes, PathConstants.LOGIN, messageResponse);
    }
}
