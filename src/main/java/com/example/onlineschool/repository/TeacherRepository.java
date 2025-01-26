package com.example.onlineschool.repository;

import com.example.onlineschool.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    @Query("SELECT p FROM Teacher p WHERE " +
            "CONCAT(p.ID, p.firstName, p.lastName, p.surname, p.email, p.phone, p.specialization, p.experience) " +
            "LIKE %?1%")
    List<Teacher> search(String keyword);

    @Query("SELECT COUNT(p) FROM Teacher p WHERE " +
            "CONCAT(p.ID, p.firstName, p.lastName, p.surname, p.email, p.phone, p.specialization, p.experience) " +
            "LIKE %?1%")
    long countByKey(String keyword);

    @Query("SELECT MIN(p.experience) FROM Teacher p WHERE " +
            "CONCAT(p.ID, p.firstName, p.lastName, p.surname, p.email, p.phone, p.specialization, p.experience) " +
            "LIKE %?1%")
    Integer getMinExperience(String keyword);

    @Query("SELECT MIN(t.experience) FROM Teacher t")
    Integer getMinExperienceAll();

    @Query("SELECT Max(p.experience) FROM Teacher p WHERE " +
            "CONCAT(p.ID, p.firstName, p.lastName, p.surname, p.email, p.phone, p.specialization, p.experience) " +
            "LIKE %?1%")
    Integer getMaxExperience(String keyword);

    @Query("SELECT MAX(t.experience) FROM Teacher t")
    Integer getMaxExperienceAll();

    @Query("SELECT AVG(p.experience) FROM Teacher p WHERE " +
            "CONCAT(p.ID, p.firstName, p.lastName, p.surname, p.email, p.phone, p.specialization, p.experience) " +
            "LIKE %?1%")
    Double getAvgExperience(String keyword);

    @Query("SELECT AVG(t.experience) FROM Teacher t")
    Double getAvgExperienceAll();
}
