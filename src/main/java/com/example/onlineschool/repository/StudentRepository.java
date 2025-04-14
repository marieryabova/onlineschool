package com.example.onlineschool.repository;

import com.example.onlineschool.model.Student;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Интерфейс репозитория для работы с сущностью {@link Student}.
 * Наследует {@link JpaRepository}, предоставляющий базовые CRUD-операции.
 */
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByUserUsername(String username);

    /**
     * Поиск студентов по ключевому слову.
     * Ищет совпадения в полях: ID, имя, фамилия, отчество, электронная почта, номер телефона.
     *
     * @param keyword ключевое слово для поиска
     * @param sort    параметр сортировки результатов
     * @return список студентов, соответствующих ключевому слову
     */
    @Query("SELECT p FROM Student p WHERE " +
            "CONCAT(p.id, p.firstName, p.lastName, p.surname, p.email, p.phone) LIKE %?1%")
    List<Student> search(String keyword, Sort sort);

    /**
     * Подсчет количества студентов, соответствующих ключевому слову.
     * Ищет совпадения в полях: ID, имя, фамилия, отчество, электронная почта, номер телефона.
     *
     * @param keyword ключевое слово для поиска
     * @return количество студентов, соответствующих ключевому слову
     */
    @Query("SELECT COUNT(p) FROM Student p WHERE " +
            "CONCAT(p.id, p.firstName, p.lastName, p.surname, p.email, p.phone) LIKE %?1%")
    long countByKey(String keyword);

    /**
     * Поиск минимального количества курсов у студентов, соответствующих ключевому слову.
     * Ищет совпадения в полях: ID, имя, фамилия, отчество, электронная почта, номер телефона, количество курсов.
     *
     * @param keyword ключевое слово для поиска
     * @return минимальное количество курсов у студентов, соответствующих ключевому слову
     */
    @Query("SELECT MIN(p.courseNumber) FROM Student p WHERE " +
            "CONCAT(p.id, p.firstName, p.lastName, p.surname, p.email, p.phone, p.courseNumber) LIKE %?1%")
    Integer findMinCourseNumber(String keyword);

    /**
     * Поиск максимального количества курсов у студентов, соответствующих ключевому слову.
     * Ищет совпадения в полях: ID, имя, фамилия, отчество, электронная почта, номер телефона, количество курсов.
     *
     * @param keyword ключевое слово для поиска
     * @return максимальное количество курсов у студентов, соответствующих ключевому слову
     */
    @Query("SELECT Max(p.courseNumber) FROM Student p WHERE " +
            "CONCAT(p.id, p.firstName, p.lastName, p.surname, p.email, p.phone, p.courseNumber) LIKE %?1%")
    Integer findMaxCourseNumber(String keyword);

    /**
     * Поиск среднего количества курсов у студентов, соответствующих ключевому слову.
     * Ищет совпадения в полях: ID, имя, фамилия, отчество, электронная почта, номер телефона, количество курсов.
     *
     * @param keyword ключевое слово для поиска
     * @return среднее количество курсов у студентов, соответствующих ключевому слову
     */
    @Query("SELECT AVG(p.courseNumber) FROM Student p WHERE " +
            "CONCAT(p.id, p.firstName, p.lastName, p.surname, p.email, p.phone, p.courseNumber) LIKE %?1%")
    Double findAvgCourseNumber(String keyword);

    /**
     * Поиск минимального количества курсов у всех студентов.
     *
     * @return минимальное количество курсов у всех студентов
     */
    @Query("SELECT MIN(p.courseNumber) from Student p")
    Integer findMinCourseNumberAll();

    /**
     * Поиск максимального количества курсов у всех студентов.
     *
     * @return максимальное количество курсов у всех студентов
     */
    @Query("SELECT MAX(p.courseNumber) from Student p")
    Integer findMaxCourseNumberAll();

    /**
     * Поиск среднего количества курсов у всех студентов.
     *
     * @return среднее количество курсов у всех студентов
     */
    @Query("SELECT AVG(p.courseNumber) from Student p")
    Double findAvgCourseNumberAll();
}