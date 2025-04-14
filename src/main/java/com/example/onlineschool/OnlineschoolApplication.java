package com.example.onlineschool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Основной класс приложения.
 * Аннотация {@link SpringBootApplication} объединяет:
 * <ul>
 *     <li>{@link org.springframework.context.annotation.Configuration} — указывает, что класс содержит конфигурацию Spring.</li>
 *     <li>{@link org.springframework.boot.autoconfigure.EnableAutoConfiguration} — включает автоматическую конфигурацию Spring Boot.</li>
 *     <li>{@link org.springframework.context.annotation.ComponentScan} — сканирует компоненты в текущем пакете и его подпакетах.</li>
 * </ul>
 * Этот класс является точкой входа для запуска Spring Boot приложения.
 */
@SpringBootApplication
public class OnlineschoolApplication {

    /**
     * Основной метод, который запускает Spring Boot приложение.
     *
     * @param args аргументы командной строки, переданные при запуске приложения
     */
    public static void main(String[] args) {
        SpringApplication.run(OnlineschoolApplication.class, args);
    }
}
