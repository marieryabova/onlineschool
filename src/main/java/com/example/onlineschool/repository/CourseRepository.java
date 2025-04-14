package com.example.onlineschool.repository;

import com.example.onlineschool.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Репозиторий для работы с сущностью {@link Course}.
 
 * Предоставляет методы для выполнения операций с курсами, включая:
 * <ul>
 *   <li>Базовые CRUD-операции (наследуются от {@link JpaRepository})</li>
 *   <li>Поиск курсов по различным критериям</li>
 *   <li>Получение статистических данных о курсах</li>
 * </ul>
 
 */
public interface CourseRepository extends JpaRepository<Course, Integer> {

    /**
     * Ищет курсы, содержащие указанное ключевое слово в любом из полей.
     
     * Поиск выполняется по следующим полям:
     * <ul>
     *   <li>id</li>
     *   <li>title</li>
     *   <li>description</li>
     *   <li>price</li>
     *   <li>category</li>
     *   <li>level</li>
     *   <li>duration</li>
     *   <li>studentsNumber</li>
     * </ul>
     
     *
     * @param keyword ключевое слово для поиска (не чувствительно к регистру)
     * @return список найденных курсов или пустой список, если ничего не найдено
     */
    @Query("SELECT p FROM Course p WHERE " +
            "CONCAT(p.id, p.title, p.description, p.price, p.category, p.level," +
            " p.duration, p.studentsNumber) " +
            "LIKE %?1%")
    List<Course> search(String keyword);

    /**
     * Подсчитывает количество курсов, содержащих указанное ключевое слово.
     *
     * @param keyword ключевое слово для поиска (не чувствительно к регистру)
     * @return количество найденных курсов
     * @see #search(String)
     */
    @Query("SELECT COUNT(p) FROM Course p WHERE " +
            "CONCAT(p.id, p.title, p.description, p.price, p.category, p.level," +
            " p.duration, p.studentsNumber) " +
            "LIKE %?1%")
    long countByKey(String keyword);

    /**
     * Находит минимальную цену среди курсов, содержащих ключевое слово.
     *
     * @param keyword ключевое слово для фильтрации
     * @return минимальная цена или null, если курсы не найдены
     */
    @Query("SELECT MIN(p.price) FROM Course p WHERE " +
            "CONCAT(p.id, p.title, p.description, p.price, p.category, p.level," +
            " p.duration, p.studentsNumber) " +
            "LIKE %?1%")
    Integer findMinPrice(String keyword);

    /**
     * Находит максимальную цену среди курсов, содержащих ключевое слово.
     *
     * @param keyword ключевое слово для фильтрации
     * @return максимальная цена или null, если курсы не найдены
     */
    @Query("SELECT MAX(p.price) FROM Course p WHERE " +
            "CONCAT(p.id, p.title, p.description, p.price, p.category, p.level," +
            " p.duration, p.studentsNumber) " +
            "LIKE %?1%")
    Integer findMaxPrice(String keyword);

    /**
     * Вычисляет среднюю цену курсов, содержащих ключевое слово.
     *
     * @param keyword ключевое слово для фильтрации
     * @return средняя цена или null, если курсы не найдены
     */
    @Query("SELECT AVG(p.price) FROM Course p WHERE " +
            "CONCAT(p.id, p.title, p.description, p.price, p.category, p.level," +
            " p.duration, p.studentsNumber) " +
            "LIKE %?1%")
    Double findAvgPrice(String keyword);

    /**
     * Вычисляет суммарную цену всех курсов, содержащих ключевое слово.
     *
     * @param keyword ключевое слово для фильтрации
     * @return сумма цен или 0, если курсы не найдены
     */
    @Query("SELECT SUM(p.price) FROM Course p WHERE " +
            "CONCAT(p.id, p.title, p.description, p.price, p.category, p.level," +
            " p.duration, p.studentsNumber) " +
            "LIKE %?1%")
    Integer findSumPrice(String keyword);

    /**
     * Находит минимальную длительность среди курсов, содержащих ключевое слово.
     *
     * @param keyword ключевое слово для фильтрации
     * @return минимальная длительность (в часах) или null, если курсы не найдены
     */
    @Query("SELECT MIN(p.duration) FROM Course p WHERE " +
            "CONCAT(p.id, p.title, p.description, p.price, p.category, p.level," +
            " p.duration, p.studentsNumber) " +
            "LIKE %?1%")
    Integer findMinDuration(String keyword);

    /**
     * Находит максимальную длительность среди курсов, содержащих ключевое слово.
     *
     * @param keyword ключевое слово для фильтрации
     * @return максимальная длительность (в часах) или null, если курсы не найдены
     */
    @Query("SELECT MAX(p.duration) FROM Course p WHERE " +
            "CONCAT(p.id, p.title, p.description, p.price, p.category, p.level," +
            " p.duration, p.studentsNumber) " +
            "LIKE %?1%")
    Integer findMaxDuration(String keyword);

    /**
     * Вычисляет среднюю длительность курсов, содержащих ключевое слово.
     *
     * @param keyword ключевое слово для фильтрации
     * @return средняя длительность (в часах) или null, если курсы не найдены
     */
    @Query("SELECT AVG(p.duration) FROM Course p WHERE " +
            "CONCAT(p.id, p.title, p.description, p.price, p.category, p.level," +
            " p.duration, p.studentsNumber) " +
            "LIKE %?1%")
    Double findAvgDuration(String keyword);

    /**
     * Вычисляет суммарную длительность всех курсов, содержащих ключевое слово.
     *
     * @param keyword ключевое слово для фильтрации
     * @return сумма длительностей (в часах) или 0, если курсы не найдены
     */
    @Query("SELECT SUM(p.duration) FROM Course p WHERE " +
            "CONCAT(p.id, p.title, p.description, p.price, p.category, p.level," +
            " p.duration, p.studentsNumber) " +
            "LIKE %?1%")
    Integer findSumDuration(String keyword);

    /**
     * Находит минимальное количество студентов среди курсов, содержащих ключевое слово.
     *
     * @param keyword ключевое слово для фильтрации
     * @return минимальное количество студентов или null, если курсы не найдены
     */
    @Query("SELECT MIN(p.studentsNumber) FROM Course p WHERE " +
            "CONCAT(p.id, p.title, p.description, p.price, p.category, p.level," +
            " p.duration, p.studentsNumber) " +
            "LIKE %?1%")
    Integer findMinStudentsNumber(String keyword);

    /**
     * Находит максимальное количество студентов среди курсов, содержащих ключевое слово.
     *
     * @param keyword ключевое слово для фильтрации
     * @return максимальное количество студентов или null, если курсы не найдены
     */
    @Query("SELECT MAX(p.studentsNumber) FROM Course p WHERE " +
            "CONCAT(p.id, p.title, p.description, p.price, p.category, p.level," +
            " p.duration, p.studentsNumber) " +
            "LIKE %?1%")
    Integer findMaxStudentsNumber(String keyword);

    /**
     * Вычисляет среднее количество студентов на курсах, содержащих ключевое слово.
     *
     * @param keyword ключевое слово для фильтрации
     * @return среднее количество студентов или null, если курсы не найдены
     */
    @Query("SELECT AVG(p.studentsNumber) FROM Course p WHERE " +
            "CONCAT(p.id, p.title, p.description, p.price, p.category, p.level," +
            " p.duration, p.studentsNumber) " +
            "LIKE %?1%")
    Double findAvgStudentsNumber(String keyword);

    /**
     * Вычисляет общее количество студентов на всех курсах, содержащих ключевое слово.
     *
     * @param keyword ключевое слово для фильтрации
     * @return суммарное количество студентов или 0, если курсы не найдены
     */
    @Query("SELECT SUM(p.studentsNumber) FROM Course p WHERE " +
            "CONCAT(p.id, p.title, p.description, p.price, p.category, p.level," +
            " p.duration, p.studentsNumber) " +
            "LIKE %?1%")
    Integer findSumStudentsNumber(String keyword);

    /**
     * Находит минимальную цену среди всех курсов.
     *
     * @return минимальная цена или null, если курсы отсутствуют
     */
    @Query("SELECT MIN(c.price) FROM Course c")
    Integer findMinPriceAll();

    /**
     * Находит максимальную цену среди всех курсов.
     *
     * @return максимальная цена или null, если курсы отсутствуют
     */
    @Query("SELECT MAX(c.price) FROM Course c")
    Integer findMaxPriceAll();

    /**
     * Вычисляет среднюю цену всех курсов.
     *
     * @return средняя цена или null, если курсы отсутствуют
     */
    @Query("SELECT AVG(c.price) FROM Course c")
    Double findAvgPriceAll();

    /**
     * Вычисляет суммарную цену всех курсов.
     *
     * @return сумма цен или 0, если курсы отсутствуют
     */
    @Query("SELECT SUM(c.price) FROM Course c")
    Integer findSumPriceAll();

    /**
     * Находит минимальную длительность среди всех курсов.
     *
     * @return минимальная длительность (в часах) или null, если курсы отсутствуют
     */
    @Query("SELECT MIN(c.duration) FROM Course c")
    Integer findMinDurationAll();

    /**
     * Находит максимальную длительность среди всех курсов.
     *
     * @return максимальная длительность (в часах) или null, если курсы отсутствуют
     */
    @Query("SELECT MAX(c.duration) FROM Course c")
    Integer findMaxDurationAll();

    /**
     * Вычисляет среднюю длительность всех курсов.
     *
     * @return средняя длительность (в часах) или null, если курсы отсутствуют
     */
    @Query("SELECT AVG(c.duration) FROM Course c")
    Double findAvgDurationAll();

    /**
     * Вычисляет суммарную длительность всех курсов.
     *
     * @return сумма длительностей (в часах) или 0, если курсы отсутствуют
     */
    @Query("SELECT SUM(c.duration) FROM Course c")
    Integer findSumDurationAll();

    /**
     * Находит минимальное количество студентов среди всех курсов.
     *
     * @return минимальное количество студентов или null, если курсы отсутствуют
     */
    @Query("SELECT MIN(c.studentsNumber) FROM Course c")
    Integer findMinStudentsNumberAll();

    /**
     * Находит максимальное количество студентов среди всех курсов.
     *
     * @return максимальное количество студентов или null, если курсы отсутствуют
     */
    @Query("SELECT MAX(c.studentsNumber) FROM Course c")
    Integer findMaxStudentsNumberAll();

    /**
     * Вычисляет среднее количество студентов на всех курсах.
     *
     * @return среднее количество студентов или null, если курсы отсутствуют
     */
    @Query("SELECT AVG(c.studentsNumber) FROM Course c")
    Double findAvgStudentsNumberAll();

    /**
     * Вычисляет общее количество студентов на всех курсах.
     *
     * @return суммарное количество студентов или 0, если курсы отсутствуют
     */
    @Query("SELECT SUM(c.studentsNumber) FROM Course c")
    Integer findSumStudentsNumberAll();
}