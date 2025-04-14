package com.example.onlineschool.repository;

import com.example.onlineschool.model.CourseStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Репозиторий для работы с сущностью {@link CourseStudent}.
 
 * Предоставляет методы для выполнения операций со связями студентов и курсов, включая:
 * <ul>
 *   <li>Базовые CRUD-операции (наследуются от {@link JpaRepository})</li>
 *   <li>Поиск записей по различным критериям</li>
 *   <li>Получение статистических данных</li>
 * </ul>
 
 */
public interface CourseStudentRepository extends JpaRepository<CourseStudent, Integer> {

    /**
     * Ищет записи о студентах на курсах по указанному ключевому слову.
     
     * Поиск выполняется по следующим полям:
     * <ul>
     *   <li>ID записи</li>
     *   <li>Название курса (course.title)</li>
     *   <li>Имя студента (student.firstName)</li>
     *   <li>Фамилия студента (student.lastName)</li>
     *   <li>Статус оплаты</li>
     *   <li>Статус завершения курса</li>
     *   <li>Информация о сертификате</li>
     * </ul>
     * Поиск не чувствителен к регистру и ищет частичные совпадения.
     
     *
     * @param keyword ключевое слово для поиска (не может быть null)
     * @return список найденных записей или пустой список, если совпадений нет
     */
    @Query("SELECT cs FROM CourseStudent cs WHERE " +
            "CONCAT(cs.id, cs.course.title, cs.student.firstName, cs.student.lastName, cs.paymentStatus," +
            " cs.courseCompletionStatus, cs.certificateInfo) LIKE %?1%")
    List<CourseStudent> search(String keyword);

    /**
     * Подсчитывает количество записей о студентах на курсах, соответствующих ключевому слову.
     
     * Использует те же критерии поиска, что и метод {@link #search(String)}.
     
     *
     * @param keyword ключевое слово для поиска (не может быть null)
     * @return количество найденных записей (может быть 0)
     * @see #search(String)
     */
    @Query("SELECT COUNT(cs) FROM CourseStudent cs WHERE " +
            "CONCAT(cs.id, cs.course.title, cs.student.firstName, cs.student.lastName, cs.paymentStatus," +
            " cs.courseCompletionStatus, cs.certificateInfo) LIKE %?1%")
    long countByKey(String keyword);
}