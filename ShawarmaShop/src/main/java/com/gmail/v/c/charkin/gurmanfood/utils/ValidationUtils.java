package com.gmail.v.c.charkin.gurmanfood.utils;

import com.gmail.v.c.charkin.gurmanfood.dto.response.MessageResponse;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Утилитный класс для валидации данных.
 * Предоставляет методы для проверки и обработки ошибок валидации.
 */
@Component
public class ValidationUtils {

    /**
     * Проверяет результат валидации и добавляет ошибки в модель.
     *
     * @param bindingResult результат валидации
     * @param model модель для добавления ошибок
     * @param attributeKey ключ атрибута для сохранения исходного значения
     * @param attributeValue значение атрибута для сохранения
     * @return true, если есть ошибки валидации, false в противном случае
     */
    public boolean validateInputFields(BindingResult bindingResult, Model model, String attributeKey, Object attributeValue) {
        if (bindingResult.hasErrors()) {
            model.mergeAttributes(getErrors(bindingResult));
            model.addAttribute(attributeKey, attributeValue);
            return true;
        }
        return false;
    }

    /**
     * Проверяет результат операции и добавляет сообщение об ошибке в модель.
     *
     * @param model модель для добавления сообщения
     * @param messageResponse ответ с результатом операции
     * @param attributeKey ключ атрибута для сохранения исходного значения
     * @param attributeValue значение атрибута для сохранения
     * @return true, если операция не успешна, false в противном случае
     */
    public boolean validateInputField(Model model, MessageResponse messageResponse, String attributeKey, Object attributeValue) {
        if (!messageResponse.getResponse().contains("success")) {
            model.addAttribute(messageResponse.getResponse(), messageResponse.getMessage());
            model.addAttribute(attributeKey, attributeValue);
            return true;
        }
        return false;
    }

    /**
     * Преобразует ошибки валидации в Map для добавления в модель.
     *
     * @param bindingResult результат валидации
     * @return Map с ошибками, где ключ - имя поля с суффиксом "Error", значение - сообщение об ошибке
     */
    private Map<String, String> getErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField() + "Error",
                        FieldError::getDefaultMessage
                ));
    }
}

