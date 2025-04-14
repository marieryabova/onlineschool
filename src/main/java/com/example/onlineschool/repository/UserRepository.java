package com.example.onlineschool.repository;

import com.example.onlineschool.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью {@link User}.
 
 * Предоставляет методы для выполнения операций с учетными записями пользователей, включая:
 * <ul>
 *   <li>Поиск пользователей по различным критериям</li>
 *   <li>Проверку существования пользователей</li>
 *   <li>Базовые CRUD-операции (наследуются от {@link JpaRepository})</li>
 * </ul>
 
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Находит пользователя по имени пользователя (логину).
     *
     * @param username имя пользователя для поиска (не чувствительно к регистру)
     * @return {@link Optional} с найденным пользователем или пустой {@link Optional}, если пользователь не найден
     */
    Optional<User> findByUsername(String username);

    /**
     * Проверяет существование пользователя, связанного с указанным студентом.
     *
     * @param studentId ID студента для проверки
     * @return true если существует пользователь, связанный с данным студентом, иначе false
     */
    boolean existsByStudentId(Integer studentId);

    /**
     * Проверяет существование пользователя, связанного с указанным преподавателем.
     *
     * @param teacherId ID преподавателя для проверки
     * @return true если существует пользователь, связанный с данным преподавателем, иначе false
     */
    boolean existsByTeacherId(Integer teacherId);

    /**
     * Проверяет существование пользователя с указанным именем пользователя (логином).
     *
     * @param username имя пользователя для проверки (не чувствительно к регистру)
     * @return true если пользователь с таким именем существует, иначе false
     */
    boolean existsByUsername(String username);
}