package com.gmail.v.c.charkin.gurmanfood.exception;

import com.gmail.v.c.charkin.gurmanfood.constants.Pages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

/**
 * Глобальный обработчик исключений для всего приложения.
 * Обеспечивает централизованную обработку всех исключений и единообразный формат ответов.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Обрабатывает ResponseStatusException - исключения с HTTP статусами.
     *
     * @param ex исключение
     * @param model модель для передачи данных в представление
     * @return имя представления для отображения ошибки
     */
    @ExceptionHandler(ResponseStatusException.class)
    public String handleResponseStatusException(ResponseStatusException ex, Model model) {
        logger.error("ResponseStatusException occurred: {}", ex.getMessage(), ex);
        
        HttpStatus status = ex.getStatus();
        String errorMessage = ex.getReason() != null ? ex.getReason() : ex.getMessage();
        
        model.addAttribute("errorMessage", errorMessage);
        
        if (status == HttpStatus.NOT_FOUND) {
            return Pages.ERROR_404;
        }
        
        return Pages.ERROR_500;
    }

    /**
     * Обрабатывает IOException - ошибки работы с файлами.
     *
     * @param ex исключение
     * @param model модель для передачи данных в представление
     * @return имя представления для отображения ошибки
     */
    @ExceptionHandler(IOException.class)
    public String handleIOException(IOException ex, Model model) {
        logger.error("IOException occurred: {}", ex.getMessage(), ex);
        model.addAttribute("errorMessage", "File operation error: " + ex.getMessage());
        return Pages.ERROR_500;
    }

    /**
     * Обрабатывает все остальные исключения (fallback).
     *
     * @param ex исключение
     * @param model модель для передачи данных в представление
     * @return имя представления для отображения ошибки
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        logger.error("Unexpected exception occurred: {}", ex.getMessage(), ex);
        model.addAttribute("errorMessage", "An unexpected error occurred. Please try again later.");
        return Pages.ERROR_500;
    }
}

