package com.example.onlineschool.repository;

import com.example.onlineschool.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query("SELECT p FROM Course p WHERE " +
            "CONCAT(p.ID, p.title, p.description, p.price, p.category, p.level," +
            " p.duration, p.studentsNumber) " +
            "LIKE %?1%")
    List<Course> search(String keyword);

    @Query("SELECT COUNT(p) FROM Course p WHERE " +
            "CONCAT(p.ID, p.title, p.description, p.price, p.category, p.level," +
            " p.duration, p.studentsNumber) " +
            "LIKE %?1%")
    long countByKey(String keyword);

    // Методы для статистики по цене с учетом ключевого слова
    @Query("SELECT MIN(p.price) FROM Course p WHERE " +
            "CONCAT(p.ID, p.title, p.description, p.price, p.category, p.level," +
            " p.duration, p.studentsNumber) " +
            "LIKE %?1%")
    Integer findMinPrice(String keyword);

    @Query("SELECT MAX(p.price) FROM Course p WHERE " +
            "CONCAT(p.ID, p.title, p.description, p.price, p.category, p.level," +
            " p.duration, p.studentsNumber) " +
            "LIKE %?1%")
    Integer findMaxPrice(String keyword);

    @Query("SELECT AVG(p.price) FROM Course p WHERE " +
            "CONCAT(p.ID, p.title, p.description, p.price, p.category, p.level," +
            " p.duration, p.studentsNumber) " +
            "LIKE %?1%")
    Double findAvgPrice(String keyword);

    @Query("SELECT SUM(p.price) FROM Course p WHERE " +
            "CONCAT(p.ID, p.title, p.description, p.price, p.category, p.level," +
            " p.duration, p.studentsNumber) " +
            "LIKE %?1%")
    Integer findSumPrice(String keyword);

    // Методы для статистики по длительности с учетом ключевого слова
    @Query("SELECT MIN(p.duration) FROM Course p WHERE " +
            "CONCAT(p.ID, p.title, p.description, p.price, p.category, p.level," +
            " p.duration, p.studentsNumber) " +
            "LIKE %?1%")
    Integer findMinDuration(String keyword);

    @Query("SELECT MAX(p.duration) FROM Course p WHERE " +
            "CONCAT(p.ID, p.title, p.description, p.price, p.category, p.level," +
            " p.duration, p.studentsNumber) " +
            "LIKE %?1%")
    Integer findMaxDuration(String keyword);

    @Query("SELECT AVG(p.duration) FROM Course p WHERE " +
            "CONCAT(p.ID, p.title, p.description, p.price, p.category, p.level," +
            " p.duration, p.studentsNumber) " +
            "LIKE %?1%")
    Double findAvgDuration(String keyword);

    @Query("SELECT SUM(p.duration) FROM Course p WHERE " +
            "CONCAT(p.ID, p.title, p.description, p.price, p.category, p.level," +
            " p.duration, p.studentsNumber) " +
            "LIKE %?1%")
    Integer findSumDuration(String keyword);

    // Методы для статистики по количеству учеников с учетом ключевого слова
    @Query("SELECT MIN(p.studentsNumber) FROM Course p WHERE " +
            "CONCAT(p.ID, p.title, p.description, p.price, p.category, p.level," +
            " p.duration, p.studentsNumber) " +
            "LIKE %?1%")
    Integer findMinStudentsNumber(String keyword);

    @Query("SELECT MAX(p.studentsNumber) FROM Course p WHERE " +
            "CONCAT(p.ID, p.title, p.description, p.price, p.category, p.level," +
            " p.duration, p.studentsNumber) " +
            "LIKE %?1%")
    Integer findMaxStudentsNumber(String keyword);

    @Query("SELECT AVG(p.studentsNumber) FROM Course p WHERE " +
            "CONCAT(p.ID, p.title, p.description, p.price, p.category, p.level," +
            " p.duration, p.studentsNumber) " +
            "LIKE %?1%")
    Double findAvgStudentsNumber(String keyword);

    @Query("SELECT SUM(p.studentsNumber) FROM Course p WHERE " +
            "CONCAT(p.ID, p.title, p.description, p.price, p.category, p.level," +
            " p.duration, p.studentsNumber) " +
            "LIKE %?1%")
    Integer findSumStudentsNumber(String keyword);

    // Методы для статистики по всей таблице
    @Query("SELECT MIN(c.price) FROM Course c")
    Integer findMinPriceAll();

    @Query("SELECT MAX(c.price) FROM Course c")
    Integer findMaxPriceAll();

    @Query("SELECT AVG(c.price) FROM Course c")
    Double findAvgPriceAll();

    @Query("SELECT SUM(c.price) FROM Course c")
    Integer findSumPriceAll();

    @Query("SELECT MIN(c.duration) FROM Course c")
    Integer findMinDurationAll();

    @Query("SELECT MAX(c.duration) FROM Course c")
    Integer findMaxDurationAll();

    @Query("SELECT AVG(c.duration) FROM Course c")
    Double findAvgDurationAll();

    @Query("SELECT SUM(c.duration) FROM Course c")
    Integer findSumDurationAll();

    @Query("SELECT MIN(c.studentsNumber) FROM Course c")
    Integer findMinStudentsNumberAll();

    @Query("SELECT MAX(c.studentsNumber) FROM Course c")
    Integer findMaxStudentsNumberAll();

    @Query("SELECT AVG(c.studentsNumber) FROM Course c")
    Double findAvgStudentsNumberAll();

    @Query("SELECT SUM(c.studentsNumber) FROM Course c")
    Integer findSumStudentsNumberAll();
}
