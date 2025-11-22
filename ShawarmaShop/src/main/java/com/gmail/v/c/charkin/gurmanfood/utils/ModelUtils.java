package com.gmail.v.c.charkin.gurmanfood.utils;

import com.gmail.v.c.charkin.gurmanfood.dto.response.MessageResponse;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Утилитный класс для работы с моделью представления.
 * Предоставляет методы для добавления сообщений в модель и RedirectAttributes.
 */
@Component
public class ModelUtils {

    /**
     * Устанавливает сообщение в модель и возвращает имя представления.
     *
     * @param model модель для добавления сообщения
     * @param page имя представления
     * @param messageResponse ответ с сообщением
     * @return имя представления
     */
    public String setAlertMessage(Model model, String page, MessageResponse messageResponse) {
        model.addAttribute("messageType", messageResponse.getResponse());
        model.addAttribute("message", messageResponse.getMessage());
        return page;
    }

    /**
     * Устанавливает flash-сообщение в RedirectAttributes и возвращает редирект.
     *
     * @param redirectAttributes атрибуты для редиректа
     * @param page путь для редиректа
     * @param messageResponse ответ с сообщением
     * @return строка редиректа
     */
    public String setAlertFlashMessage(RedirectAttributes redirectAttributes, String page, MessageResponse messageResponse) {
        redirectAttributes.addFlashAttribute("messageType", messageResponse.getResponse());
        redirectAttributes.addFlashAttribute("message", messageResponse.getMessage());
        return "redirect:" + page;
    }
}

