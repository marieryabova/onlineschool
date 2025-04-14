package com.example.onlineschool.dto;

import com.example.onlineschool.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO (Data Transfer Object) для запроса регистрации пользователя.

 * Содержит данные, необходимые для регистрации нового пользователя в системе,
 * включая общие обязательные поля и специфичные поля для студентов и преподавателей.

 *
 * @see Role
 * @see jakarta.validation.constraints.NotBlank
 * @see jakarta.validation.constraints.NotNull
 */
@Data
public class RegisterRequest {

    /**
     * Уникальное имя пользователя (логин).

     * Не может быть пустым или состоять только из пробельных символов.

     */
    @NotBlank
    private String username;

    /**
     * Пароль пользователя.

     * Не может быть пустым или состоять только из пробельных символов.
     * Рекомендуется использовать сложные пароли длиной не менее 8 символов.

     */
    @NotBlank
    private String password;

    /**
     * Роль пользователя в системе.

     * Определяет уровень доступа и функциональные возможности пользователя.
     * Не может быть null.

     */
    @NotNull
    private Role role;

    // ============ Студент ============

    /**
     * Имя студента.

     * Обязательно для заполнения при регистрации студента.

     */
    private String firstName;

    /**
     * Фамилия студента.

     * Обязательно для заполнения при регистрации студента.

     */
    private String lastName;

    /**
     * Отчество студента.
     */
    private String surname;

    /**
     * Электронная почта студента.

     * Должна быть в валидном формате email.

     */
    private String email;

    /**
     * Контактный телефон студента.
     */
    private String phone;

    // ============ Преподаватель ============

    /**
     * Имя преподавателя.

     * Обязательно для заполнения при регистрации преподавателя.

     */
    private String teacherFirstName;

    /**
     * Фамилия преподавателя.

     * Обязательно для заполнения при регистрации преподавателя.

     */
    private String teacherLastName;

    /**
     * Отчество преподавателя.
     */
    private String teacherSurname;

    /**
     * Электронная почта преподавателя.

     * Должна быть в валидном формате email.

     */
    private String teacherEmail;

    /**
     * Контактный телефон преподавателя.
     */
    private String teacherPhone;

    /**
     * Специализация преподавателя.

     * Определяет область знаний преподавателя.

     */
    private String specialization;

    /**
     * Опыт работы преподавателя (в годах).

     * Должен быть положительным числом.

     */
    private Integer experience;
}