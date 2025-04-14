package com.example.onlineschool.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Конфигурация безопасности приложения.
 * Определяет:
 * <ul>
 *   <li>Настройки аутентификации и авторизации</li>
 *   <li>Правила доступа к URL-адресам</li>
 *   <li>Параметры входа и выхода из системы</li>
 *   <li>Провайдер аутентификации</li>
 * </ul>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * Сервис для работы с данными пользователей.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Кодировщик паролей.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Настраивает цепочку фильтров безопасности.
     * Конфигурация включает:
     * <ul>
     *   <li>Отключение CSRF защиты</li>
     *   <li>Настройку правил авторизации для различных URL</li>
     *   <li>Конфигурацию формы входа</li>
     *   <li>Конфигурацию выхода из системы</li>
     *   <li>Обработку исключений аутентификации</li>
     * </ul>
     *
     * @param http объект для настройки безопасности
     * @return сконфигурированная цепочка фильтров безопасности
     * @throws Exception если возникает ошибка при настройке
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Публичные URL
                        .requestMatchers("/", "/auth/**", "/css/**", "/js/**", "/images/**").permitAll()
                        // URL для студентов
                        .requestMatchers("/edit/edit_student/**", "/save/save_student/**").hasAnyRole("STUDENT", "ADMIN")
                        // URL для преподавателей
                        .requestMatchers("/edit/edit_teacher/**", "/save/save_teacher/**").hasAnyRole("TEACHER", "ADMIN")
                        // Административные URL
                        .requestMatchers("/new/**", "/delete/**", "/save/**", "/edit/**").hasRole("ADMIN")
                        // URL для аутентифицированных пользователей
                        .requestMatchers("/index", "/about", "/main/**").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .defaultSuccessUrl("/index", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login?logout")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/auth/login"),
                                new AntPathRequestMatcher("/")
                        )
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.sendRedirect("/access-denied");
                        })
                );

        return http.build();
    }

    /**
     * Настраивает провайдер аутентификации.
     * Использует:
     * <ul>
     *   <li>UserDetailsService для загрузки данных пользователя</li>
     *   <li>PasswordEncoder для проверки паролей</li>
     * </ul>
     *
     * @return настроенный провайдер аутентификации
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }
}