package com.example.onlineschool.repository;

import com.example.onlineschool.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Репозиторий для работы с сущностью {@link Module}.
 
 * Предоставляет методы для выполнения операций с учебными модулями, включая:
 * <ul>
 *   <li>Базовые CRUD-операции (наследуются от {@link JpaRepository})</li>
 *   <li>Поиск модулей по различным критериям</li>
 *   <li>Получение статистики по распределению модулей по курсам</li>
 * </ul>
 
 */
public interface ModuleRepository extends JpaRepository<Module, Integer> {

    /**
     * Ищет модули, содержащие указанное ключевое слово.
     
     * Поиск выполняется по следующим полям:
     * <ul>
     *   <li>ID модуля</li>
     *   <li>Название курса (course.title)</li>
     *   <li>Название модуля</li>
     *   <li>Описание модуля</li>
     * </ul>
     * Поиск не чувствителен к регистру и ищет частичные совпадения.
     
     *
     * @param keyword ключевое слово для поиска (не может быть null)
     * @return список найденных модулей или пустой список, если совпадений нет
     */
    @Query("SELECT p FROM Module p WHERE " +
            "CONCAT(p.id, p.course.title, p.moduleTitle, p.moduleDescription) " +
            "LIKE %?1%")
    List<Module> search(String keyword);

    /**
     * Подсчитывает количество модулей, содержащих ключевое слово.
     
     * Использует те же критерии поиска, что и метод {@link #search(String)}.
     
     *
     * @param keyword ключевое слово для поиска (не может быть null)
     * @return количество найденных модулей (может быть 0)
     * @see #search(String)
     */
    @Query("SELECT COUNT(p) FROM Module p WHERE " +
            "CONCAT(p.id, p.course.title, p.moduleTitle, p.moduleDescription) " +
            "LIKE %?1%")
    long countByKey(String keyword);

    /**
     * Получает статистику количества модулей по курсам.
     
     * Группирует модули по названию курса и возвращает для каждого курса:
     * <ul>
     *   <li>Название курса (String)</li>
     *   <li>Количество модулей в курсе (Long)</li>
     * </ul>
     * Результат возвращается в виде списка массивов Object[], где:
     * <ul>
     *   <li>Индекс 0: название курса (String)</li>
     *   <li>Индекс 1: количество модулей (Long)</li>
     * </ul>
     
     *
     * @return список массивов объектов с данными о распределении модулей по курсам
     */
    @Query("SELECT c.title, COUNT(m) FROM Module m JOIN m.course c GROUP BY c.title")
    List<Object[]> countModulesPerCourse();

    /**
     * Получает статистику количества модулей по курсам с фильтрацией по ключевому слову.
     
     * Аналогичен {@link #countModulesPerCourse()}, но учитывает только модули,
     * соответствующие ключевому слову. Критерии поиска совпадают с {@link #search(String)}.
     
     *
     * @param key ключевое слово для фильтрации (не может быть null)
     * @return список массивов объектов с данными о распределении модулей по курсам
     * @see #countModulesPerCourse()
     * @see #search(String)
     */
    @Query("SELECT c.title, COUNT(m) FROM Module m JOIN m.course c WHERE CONCAT(m.id, m.course.title, m.moduleTitle," +
            " m.moduleDescription) LIKE %?1% GROUP BY c.title")
    List<Object[]> countModulesPerCourseByKey(String key);
}