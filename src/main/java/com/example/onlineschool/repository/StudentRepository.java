package com.example.onlineschool.repository;

import com.example.onlineschool.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query("SELECT p FROM Student p WHERE " +
            "CONCAT(p.ID, p.firstName, p.lastName, p.surname, p.email, p.phone, p.courseNumber) LIKE %?1%")
    List<Student> search(String keyword);

    @Query("SELECT COUNT(p) FROM Student p WHERE " +
            "CONCAT(p.ID, p.firstName, p.lastName, p.surname, p.email, p.phone, p.courseNumber) LIKE %?1%")
    long countByKey(String keyword);

    @Query("SELECT MIN(p.courseNumber) FROM Student p WHERE " +
            "CONCAT(p.ID, p.firstName, p.lastName, p.surname, p.email, p.phone, p.courseNumber) LIKE %?1%")
    Integer findMinCourseNumber(String keyword);

    @Query("SELECT MIN(p.courseNumber) from Student p")
    Integer findMinCourseNumberAll();

    @Query("SELECT Max(p.courseNumber) FROM Student p WHERE " +
            "CONCAT(p.ID, p.firstName, p.lastName, p.surname, p.email, p.phone, p.courseNumber) LIKE %?1%")
    Integer findMaxCourseNumber(String keyword);

    @Query("SELECT MAX(p.courseNumber) from Student p")
    Integer findMaxCourseNumberAll();

    @Query("SELECT AVG(p.courseNumber) FROM Student p WHERE " +
            "CONCAT(p.ID, p.firstName, p.lastName, p.surname, p.email, p.phone, p.courseNumber) LIKE %?1%")
    Double findAvgCourseNumber(String keyword);

    @Query("SELECT AVG(p.courseNumber) from Student p")
    Double findAvgCourseNumberAll();
}
