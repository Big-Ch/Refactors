package com.gmail.v.c.charkin.gurmanfood.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Сервис для отправки email сообщений.
 * Использует ExecutorService для асинхронной отправки писем.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private static final int THREAD_POOL_SIZE = 10;
    private static final long SHUTDOWN_TIMEOUT_SECONDS = 30;

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine thymeleafTemplateEngine;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${hostname}")
    private String hostname;

    private ExecutorService executorService;

    /**
     * Инициализирует ExecutorService при создании бина.
     */
    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        log.info("MailService initialized with thread pool size: {}", THREAD_POOL_SIZE);
    }

    /**
     * Корректно завершает ExecutorService при остановке приложения.
     */
    @PreDestroy
    public void destroy() {
        if (executorService != null) {
            log.info("Shutting down MailService ExecutorService...");
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(SHUTDOWN_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
                    log.warn("ExecutorService did not terminate in {} seconds, forcing shutdown", SHUTDOWN_TIMEOUT_SECONDS);
                    executorService.shutdownNow();
                    if (!executorService.awaitTermination(SHUTDOWN_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
                        log.error("ExecutorService did not terminate after forced shutdown");
                    }
                }
            } catch (InterruptedException e) {
                log.error("Interrupted while waiting for ExecutorService termination", e);
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }
            log.info("MailService ExecutorService shutdown completed");
        }
    }

    /**
     * Отправляет HTML email сообщение асинхронно.
     *
     * @param to адрес получателя
     * @param subject тема письма
     * @param template имя шаблона (без расширения)
     * @param attributes атрибуты для шаблона
     */
    public void sendMessageHtml(String to, String subject, String template, Map<String, Object> attributes) {
        executorService.execute(() -> {
            try {
                attributes.put("url", "http://" + hostname);
                Context thymeleafContext = new Context();
                thymeleafContext.setVariables(attributes);
                String htmlBody = thymeleafTemplateEngine.process("email/" + template, thymeleafContext);
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                helper.setFrom(username);
                helper.setTo(to);
                helper.setSubject(subject);
                helper.setText(htmlBody, true);
                mailSender.send(message);
                log.debug("Email sent successfully to: {}", to);
            } catch (MessagingException e) {
                log.error("Failed to send email to: {}", to, e);
                throw new RuntimeException("Failed to send email", e);
            }
        });
    }
}
