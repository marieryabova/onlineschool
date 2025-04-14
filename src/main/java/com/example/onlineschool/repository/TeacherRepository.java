package com.example.onlineschool.repository;

import com.example.onlineschool.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Интерфейс репозитория для работы с сущностью {@link Teacher}.
 * Наследует {@link JpaRepository}, предоставляющий базовые CRUD-операции.
 */
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    /**
     * Поиск преподавателей по ключевому слову.
     * Ищет совпадения в полях: ID, имя, фамилия, отчество, электронная почта, номер телефона, специализация, опыт работы.
     *
     * @param keyword ключевое слово для поиска
     * @return список преподавателей, соответствующих ключевому слову
     */
    @Query("SELECT p FROM Teacher p WHERE " +
            "CONCAT(p.id, p.firstName, p.lastName, p.surname, p.email, p.phone, p.specialization, p.experience) " +
            "LIKE %?1%")
    List<Teacher> search(String keyword);

    /**
     * Подсчет количества преподавателей, соответствующих ключевому слову.
     * Ищет совпадения в полях: ID, имя, фамилия, отчество, электронная почта, номер телефона, специализация, опыт работы.
     *
     * @param keyword ключевое слово для поиска
     * @return количество преподавателей, соответствующих ключевому слову
     */
    @Query("SELECT COUNT(p) FROM Teacher p WHERE " +
            "CONCAT(p.id, p.firstName, p.lastName, p.surname, p.email, p.phone, p.specialization, p.experience) " +
            "LIKE %?1%")
    long countByKey(String keyword);

    /**
     * Поиск минимального опыта работы преподавателей, соответствующих ключевому слову.
     * Ищет совпадения в полях: ID, имя, фамилия, отчество, электронная почта, номер телефона, специализация, опыт работы.
     *
     * @param keyword ключевое слово для поиска
     * @return минимальный опыт работы преподавателей, соответствующих ключевому слову
     */
    @Query("SELECT MIN(p.experience) FROM Teacher p WHERE " +
            "CONCAT(p.id, p.firstName, p.lastName, p.surname, p.email, p.phone, p.specialization, p.experience) " +
            "LIKE %?1%")
    Integer getMinExperience(String keyword);

    /**
     * Поиск максимального опыта работы преподавателей, соответствующих ключевому слову.
     * Ищет совпадения в полях: ID, имя, фамилия, отчество, электронная почта, номер телефона, специализация, опыт работы.
     *
     * @param keyword ключевое слово для поиска
     * @return максимальный опыт работы преподавателей, соответствующих ключевому слову
     */
    @Query("SELECT Max(p.experience) FROM Teacher p WHERE " +
            "CONCAT(p.id, p.firstName, p.lastName, p.surname, p.email, p.phone, p.specialization, p.experience) " +
            "LIKE %?1%")
    Integer getMaxExperience(String keyword);

    /**
     * Поиск среднего опыта работы преподавателей, соответствующих ключевому слову.
     * Ищет совпадения в полях: ID, имя, фамилия, отчество, электронная почта, номер телефона, специализация, опыт работы.
     *
     * @param keyword ключевое слово для поиска
     * @return средний опыт работы преподавателей, соответствующих ключевому слову
     */
    @Query("SELECT AVG(p.experience) FROM Teacher p WHERE " +
            "CONCAT(p.id, p.firstName, p.lastName, p.surname, p.email, p.phone, p.specialization, p.experience) " +
            "LIKE %?1%")
    Double getAvgExperience(String keyword);

    /**
     * Поиск минимального опыта работы всех преподавателей.
     *
     * @return минимальный опыт работы всех преподавателей
     */
    @Query("SELECT MIN(t.experience) FROM Teacher t")
    Integer getMinExperienceAll();

    /**
     * Поиск максимального опыта работы всех преподавателей.
     *
     * @return максимальный опыт работы всех преподавателей
     */
    @Query("SELECT MAX(t.experience) FROM Teacher t")
    Integer getMaxExperienceAll();

    /**
     * Поиск среднего опыта работы всех преподавателей.
     *
     * @return средний опыт работы всех преподавателей
     */
    @Query("SELECT AVG(t.experience) FROM Teacher t")
    Double getAvgExperienceAll();
}