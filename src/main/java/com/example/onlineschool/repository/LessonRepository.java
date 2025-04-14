package com.example.onlineschool.repository;

import com.example.onlineschool.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Репозиторий для работы с сущностью {@link Lesson}.
 
 * Предоставляет методы для выполнения операций с уроками, включая:
 * <ul>
 *   <li>Базовые CRUD-операции (наследуются от {@link JpaRepository})</li>
 *   <li>Поиск уроков по различным критериям</li>
 *   <li>Получение статистики по распределению уроков</li>
 * </ul>
 
 */
public interface LessonRepository extends JpaRepository<Lesson, Integer> {

    /**
     * Ищет уроки, содержащие указанное ключевое слово.
     
     * Поиск выполняется по следующим полям:
     * <ul>
     *   <li>ID урока</li>
     *   <li>Название модуля (module.moduleTitle)</li>
     *   <li>Название урока</li>
     *   <li>Описание урока</li>
     * </ul>
     * Поиск не чувствителен к регистру и ищет частичные совпадения.
     
     *
     * @param keyword ключевое слово для поиска (не может быть null)
     * @return список найденных уроков или пустой список, если совпадений нет
     */
    @Query("SELECT p FROM Lesson p WHERE " +
            "CONCAT(p.id, p.module.moduleTitle, p.lessonTitle, p.lessonDescription) " +
            "LIKE %?1%")
    List<Lesson> search(String keyword);

    /**
     * Подсчитывает количество уроков, содержащих ключевое слово.
     
     * Использует те же критерии поиска, что и метод {@link #search(String)}.
     
     *
     * @param keyword ключевое слово для поиска (не может быть null)
     * @return количество найденных уроков (может быть 0)
     * @see #search(String)
     */
    @Query("SELECT COUNT(p) FROM Lesson p WHERE " +
            "CONCAT(p.id, p.module.moduleTitle, p.lessonTitle, p.lessonDescription) " +
            "LIKE %?1%")
    long countByKey(String keyword);

    /**
     * Получает статистику количества уроков по модулям.
     
     * Группирует уроки по названию модуля и возвращает для каждого модуля:
     * <ul>
     *   <li>Название модуля (String)</li>
     *   <li>Количество уроков в модуле (Long)</li>
     * </ul>
     * Результат возвращается в виде списка массивов Object[], где:
     * <ul>
     *   <li>Индекс 0: название модуля (String)</li>
     *   <li>Индекс 1: количество уроков (Long)</li>
     * </ul>
     
     *
     * @return список массивов объектов с данными о распределении уроков по модулям
     */
    @Query("SELECT c.moduleTitle, COUNT(m) FROM Lesson m JOIN m.module c GROUP BY c.moduleTitle")
    List<Object[]> countLessonsPerModule();

    /**
     * Получает статистику количества уроков по модулям с фильтрацией по ключевому слову.
     
     * Аналогичен {@link #countLessonsPerModule()}, но учитывает только уроки,
     * соответствующие ключевому слову. Критерии поиска совпадают с {@link #search(String)}.
     
     *
     * @param key ключевое слово для фильтрации (не может быть null)
     * @return список массивов объектов с данными о распределении уроков по модулям
     * @see #countLessonsPerModule()
     * @see #search(String)
     */
    @Query("SELECT c.moduleTitle, COUNT(m) FROM Lesson m JOIN m.module c WHERE CONCAT(m.id, m.module.moduleTitle, m.lessonTitle," +
            " m.lessonDescription) LIKE %?1% GROUP BY c.moduleTitle")
    List<Object[]> countLessonsPerModuleByKey(String key);
}