package com.example.onlineschool.controller;

import com.example.onlineschool.dto.LoginRequest;
import com.example.onlineschool.dto.RegisterRequest;
import com.example.onlineschool.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Контроллер для обработки запросов аутентификации и регистрации.
 * Предоставляет функциональность для:
 * <ul>
 *   <li>Перенаправления на страницу входа</li>
 *   <li>Входа в систему</li>
 *   <li>Отображения формы регистрации</li>
 *   <li>Обработки регистрации новых пользователей</li>
 * </ul>
 *
 * @see AuthService
 * @see RegisterRequest
 */
@Controller
@RequiredArgsConstructor
public class AuthController {

    /**
     * Сервис для обработки операций аутентификации и регистрации.
     */
    private final AuthService authService;

    /**
     * Перенаправляет корневой запрос на страницу входа.
     *
     * @return строка перенаправления на "/auth/login"
     */
    @GetMapping("/")
    public String root() {
        return "redirect:/auth/login";
    }

    /**
     * Отображает страницу входа в систему.
     * Обрабатывает параметры:
     * <ul>
     *   <li>error - отображает сообщение об ошибке при неудачной попытке входа</li>
     *   <li>logout - отображает сообщение об успешном выходе из системы</li>
     * </ul>
     *
     * @param error параметр ошибки входа (может быть null)
     * @param logout параметр выхода из системы (может быть null)
     * @param model модель для передачи данных в представление
     * @return имя представления "auth/login"
     */
    @GetMapping("/auth/login")
    public String showLoginForm(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {

        if (error != null) {
            model.addAttribute("error", "Неверное имя пользователя или пароль");
        }

        if (logout != null) {
            model.addAttribute("message", "Вы успешно вышли из системы");
        }

        model.addAttribute("loginRequest", new LoginRequest());
        return "auth/login";
    }

    /**
     * Обрабатывает POST-запрос с данными для входа в систему.
     * Фактическая аутентификация выполняется Spring Security,
     * данный метод лишь обрабатывает успешный вход и перенаправляет на главную страницу.
     *
     * @param loginRequest DTO с данными для входа (логин и пароль), привязанный из формы
     * @return строка перенаправления на главную страницу "/index"
     *
     * @see LoginRequest
     * @see org.springframework.security.authentication.AuthenticationManager
     */
    @PostMapping("/auth/login")
    public String processLogin(@ModelAttribute LoginRequest loginRequest) {
        return "redirect:/index";
    }

    /**
     * Отображает форму регистрации нового пользователя.
     * Добавляет в модель пустой объект RegisterRequest для заполнения.
     *
     * @param model модель для передачи данных в представление
     * @return имя представления "auth/register"
     */
    @GetMapping("/auth/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "auth/register";
    }

    /**
     * Обрабатывает запрос на регистрацию нового пользователя.
     * При успешной регистрации перенаправляет на страницу входа с параметром registered.
     * При ошибке возвращает на страницу регистрации с сообщением об ошибке.
     *
     * @param registerRequest DTO с данными для регистрации
     * @param model модель для передачи данных в представление
     * @return перенаправление на страницу входа или возврат на страницу регистрации с ошибкой
     */
    @PostMapping("/auth/register")
    public String registerUser(@ModelAttribute RegisterRequest registerRequest, Model model) {
        try {
            authService.register(registerRequest);
            return "redirect:/auth/login?registered";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }
}