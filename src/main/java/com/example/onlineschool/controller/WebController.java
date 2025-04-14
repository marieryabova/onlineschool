package com.example.onlineschool.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер для обработки web-запросов.
 
 * Аннотация {@link Controller} указывает на то, что класс является
 * контроллером Spring MVC и будет обрабатывать входящие запросы.
 * Обрабатывает запросы для основных страниц веб-приложения.
 
 */
@Controller
public class WebController {

    /**
     * Обрабатывает GET-запросы на URL "/index".
     
     * Добавляет в модель аутентификационные данные пользователя, если он авторизован.
     * Возвращает имя представления главной страницы.
     
     *
     * @param model объект модели для передачи данных в представление
     * @return имя представления "index"
     */
    @GetMapping("/index")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() != null) {
            model.addAttribute("authentication", authentication);
        }

        return "index";
    }

    /**
     * Обрабатывает GET-запросы на URL "/about".
     
     * Возвращает имя представления страницы "О нас".
     
     *
     * @return имя представления "about"
     */
    @GetMapping("/about")
    public String about() {
        return "about";
    }

    /**
     * Обрабатывает ошибки для всех HTTP-статусов.
     *
     * @param request HTTP-запрос
     * @param model модель для передачи данных в представление
     * @return имя представления страницы ошибки
     */
    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Получаем статус ошибки
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            // Добавляем сообщение в зависимости от типа ошибки
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("errorMessage", "Страница не найдена");
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                model.addAttribute("errorMessage", "Доступ запрещен");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("errorMessage", "Внутренняя ошибка сервера");
            } else {
                model.addAttribute("errorMessage", "Произошла ошибка: " + statusCode);
            }
        } else {
            model.addAttribute("errorMessage", "Что-то пошло не так");
        }

        return "error";
    }

    /**
     * Обрабатывает доступ запрещен (403 ошибка).
     *
     * @param model модель для передачи данных в представление
     * @return имя представления страницы ошибки
     */
    @GetMapping("/access-denied")
    public String accessDenied(Model model) {
        model.addAttribute("errorMessage", "У вас нет прав для доступа к этой странице");
        return "error";
    }
}