package com.example.onlineschool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Конфигурационный класс приложения.
 * Содержит основные бины, необходимые для работы приложения.
 */
@Configuration
public class ApplicationConfig {

    /**
     * Создает и настраивает кодировщик паролей.
     * Использует алгоритм BCrypt для хеширования паролей со следующими характеристиками:
     * <ul>
     *   <li>Автоматическая генерация соли</li>
     *   <li>Адаптивная сложность хеширования (strength = 10)</li>
     *   <li>Защита от атак перебора с использованием радужных таблиц</li>
     * </ul>
     *
     * @return экземпляр {@link BCryptPasswordEncoder} для хеширования паролей
     * @see BCryptPasswordEncoder
     * @see PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}