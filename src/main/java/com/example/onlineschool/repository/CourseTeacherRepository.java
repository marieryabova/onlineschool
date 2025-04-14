package com.example.onlineschool.repository;

import com.example.onlineschool.model.CourseTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Репозиторий для работы с сущностью {@link CourseTeacher}.
 
 * Предоставляет методы для выполнения операций со связями курсов и преподавателей, включая:
 * <ul>
 *   <li>Базовые CRUD-операции (наследуются от {@link JpaRepository})</li>
 *   <li>Поиск связей по различным критериям</li>
 *   <li>Получение статистических данных</li>
 * </ul>
 
 */
public interface CourseTeacherRepository extends JpaRepository<CourseTeacher, Integer> {

    /**
     * Ищет связи курсов и преподавателей, содержащие указанное ключевое слово.
     
     * Поиск выполняется по следующим полям:
     * <ul>
     *   <li>ID связи</li>
     *   <li>Название курса (course.title)</li>
     *   <li>Имя преподавателя (teacher.firstName)</li>
     *   <li>Фамилия преподавателя (teacher.lastName)</li>
     *   <li>Роль преподавателя в курсе</li>
     * </ul>
     * Поиск не чувствителен к регистру.
     
     *
     * @param keyword ключевое слово для поиска
     * @return список найденных связей или пустой список, если ничего не найдено
     */
    @Query("SELECT cs FROM CourseTeacher cs WHERE " +
            "CONCAT(cs.id, cs.course.title, cs.teacher.firstName, cs.teacher.lastName, cs.role) LIKE %?1%")
    List<CourseTeacher> search(String keyword);

    /**
     * Подсчитывает количество связей курсов и преподавателей, содержащих ключевое слово.
     
     * Использует те же критерии поиска, что и метод {@link #search(String)}.
     
     *
     * @param keyword ключевое слово для поиска
     * @return количество найденных связей
     * @see #search(String)
     */
    @Query("SELECT COUNT(cs) FROM CourseTeacher cs WHERE " +
            "CONCAT(cs.id, cs.course.title, cs.teacher.firstName, cs.teacher.lastName, cs.role) LIKE %?1%")
    long countByKey(String keyword);
}