package com.example.onlineschool.service;

import com.example.onlineschool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Сервис для загрузки данных пользователя в Spring Security.
 
 * Реализует интерфейс {@link UserDetailsService} для интеграции с механизмом аутентификации Spring Security.
 * Используется в процессе аутентификации для получения данных пользователя по имени пользователя (логину).
 
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * Репозиторий для работы с пользователями.
     */
    private final UserRepository userRepository;

    /**
     * Загружает данные пользователя по имени пользователя.
     
     * Используется Spring Security в процессе аутентификации для получения:
     * <ul>
     *   <li>Учетных данных пользователя</li>
     *   <li>Роли пользователя</li>
     *   <li>Статуса аккаунта</li>
     * </ul>
     
     *
     * @param username имя пользователя (логин) для поиска
     * @return объект {@link UserDetails} с данными пользователя
     * @throws UsernameNotFoundException если пользователь с указанным именем не найден
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}