package com.example.onlineschool.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Сущность, представляющая пользователя системы.
 
 * Реализует интерфейс {@link UserDetails} для интеграции с Spring Security.
 * Содержит учетные данные пользователя и связь с профилями ученика/преподавателя.
 
 *
 * @see UserDetails
 * @see Entity
 * @see Table
 * @see Data
 */
@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {

    /**
     * Уникальный идентификатор пользователя.
     
     * Генерируется автоматически базой данных.
     * Используется как первичный ключ таблицы users.
     
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Уникальное имя пользователя (логин).
     
     * Используется для входа в систему.
     * Должно быть уникальным (проверяется на уровне БД).
     * Не может быть null.
     
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * Зашифрованный пароль пользователя.
     
     * Хранится в зашифрованном виде с использованием bcrypt.
     * Не может быть null.
     
     */
    @Column(nullable = false)
    private String password;

    /**
     * Роль пользователя в системе.
     
     * Определяет уровень доступа и функциональные возможности.
     * Хранится в виде строки в базе данных.
     
     */
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Связанный профиль ученика.
     
     * Заполняется только для пользователей с ролью STUDENT.
     * Связь один-к-одному с сущностью {@link Student}.
     
     */
    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    /**
     * Связанный профиль преподавателя.
     
     * Заполняется только для пользователей с ролью TEACHER.
     * Связь один-к-одному с сущностью {@link Teacher}.
     
     */
    @OneToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    /**
     * Возвращает список прав доступа пользователя.

     * Формируется на основе роли пользователя с префиксом "ROLE_".
     * Используется Spring Security для проверки прав доступа.

     *
     * @return список объектов {@link GrantedAuthority}
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    /**
     * Проверяет, не истек ли срок действия учетной записи.

     * В текущей реализации всегда возвращает true (срок не истек).

     *
     * @return true если учетная запись активна
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Проверяет, не заблокирована ли учетная запись.

     * В текущей реализации всегда возвращает true (не заблокирована).

     *
     * @return true если учетная запись не заблокирована
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Проверяет, не истек ли срок действия учетных данных.

     * В текущей реализации всегда возвращает true (срок не истек).

     *
     * @return true если учетные данные действительны
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Проверяет, активна ли учетная запись.
     
     * В текущей реализации всегда возвращает true (активна).
     
     *
     * @return true если учетная запись включена
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}