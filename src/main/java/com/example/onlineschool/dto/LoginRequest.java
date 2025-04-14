package com.example.onlineschool.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO для запроса входа в систему.
 * Содержит:
 * <ul>
 *   <li>Логин пользователя</li>
 *   <li>Пароль пользователя</li>
 * </ul>
 */
@Data
public class LoginRequest {

    @NotBlank(message = "Логин не может быть пустым")
    private String username;

    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}