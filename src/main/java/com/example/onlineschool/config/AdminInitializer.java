package com.example.onlineschool.config;

import com.example.onlineschool.model.Role;
import com.example.onlineschool.model.User;
import com.example.onlineschool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Компонент для инициализации администратора при запуске приложения.
 * Реализует интерфейс {@link CommandLineRunner} для выполнения кода после старта приложения.
 * Создает учетную запись администратора по умолчанию, если она не существует.
 */
@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    /**
     * Репозиторий для работы с пользователями в базе данных.
     */
    private final UserRepository userRepository;

    /**
     * Кодировщик паролей для безопасного хранения паролей.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Метод, выполняемый после запуска приложения.
     * Проверяет наличие пользователя с именем "root" и создает его, если он не существует.
     * Учетная запись администратора создается со следующими параметрами:
     * <ul>
     *   <li>Логин: root</li>
     *   <li>Пароль: 123 (кодируется перед сохранением)</li>
     *   <li>Роль: ADMIN</li>
     * </ul>
     *
     * @param args аргументы командной строки (не используются)
     */
    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("root").isEmpty()) {
            User admin = new User();
            admin.setUsername("root");
            admin.setPassword(passwordEncoder.encode("123"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }
    }
}