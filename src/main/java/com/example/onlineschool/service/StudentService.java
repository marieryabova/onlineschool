package com.example.onlineschool.service;

import com.example.onlineschool.repository.StudentRepository;
import com.example.onlineschool.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repo;

    // Вывод студентов (всех или по ключевому слову)
    public List<Student> getAllStudents(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findAll();
        }
        return repo.search(keyword);
    }

    // Сохранение студентов
    public Student saveStudent(Student student) {
        return repo.save(student);
    }

    // Вывод студентов по id
    public Student getStudent(Integer id) {
        return repo.findById(id).get();
    }

    // Удаление студента по id
    public void deleteStudent(Integer id) {
        repo.deleteById(id);
    }

    // Поиск количества студентов
    public long getStudentCount(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.count();
        }
        return repo.countByKey(keyword);
    }

    // Поиск минимума по количеству курсов
    public Integer getMinCourseNumber(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findMinCourseNumberAll();
        }
        return repo.findMinCourseNumber(keyword);
    }

    // Поиск максимума по количеству курсов
    public Integer getMaxCourseNumber(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findMaxCourseNumberAll();
        }
        return repo.findMaxCourseNumber(keyword);
    }

    // Поиск среднего по количеству курсов
    public Double getAverageCourseNumber(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findAvgCourseNumberAll();
        }
        return repo.findAvgCourseNumber(keyword);
    }
}
