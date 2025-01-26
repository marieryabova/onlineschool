package com.example.onlineschool.service;

import com.example.onlineschool.model.Course;
import com.example.onlineschool.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository repo;

    // Вывод курсов (всех или по ключевому слову)
    public List<Course> getAllCourses(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findAll();
        }
        return repo.search(keyword);
    }

    // Сохранение курсов
    public Course saveCourse(Course course) {
        return repo.save(course);
    }

    // Вывод курсов по id
    public Course getCourse(Integer id) {
        return repo.findById(id).get();
    }

    // Удаление курса по id
    public void deleteCourse(Integer id) {
        repo.deleteById(id);
    }

    public long getCourseCount(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.count();
        }
        return repo.countByKey(keyword);
    }

    // Методы для статистики по цене
    public Integer getMinPrice(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findMinPriceAll();
        }
        return repo.findMinPrice(keyword);
    }

    public Integer getMaxPrice(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findMaxPriceAll();
        }
        return repo.findMaxPrice(keyword);
    }

    public Double getAvgPrice(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findAvgPriceAll();
        }
        return repo.findAvgPrice(keyword);
    }

    public Integer getSumPrice(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findSumPriceAll();
        }
        return repo.findSumPrice(keyword);
    }

    // Методы для статистики по длительности
    public Integer getMinDuration(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findMinDurationAll();
        }
        return repo.findMinDuration(keyword);
    }

    public Integer getMaxDuration(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findMaxDurationAll();
        }
        return repo.findMaxDuration(keyword);
    }

    public Double getAvgDuration(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findAvgDurationAll();
        }
        return repo.findAvgDuration(keyword);
    }

    public Integer getSumDuration(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findSumDurationAll();
        }
        return repo.findSumDuration(keyword);
    }

    // Методы для статистики по количеству учеников
    public Integer getMinStudents(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findMinStudentsNumberAll();
        }
        return repo.findMinStudentsNumber(keyword);
    }

    public Integer getMaxStudents(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findMaxStudentsNumberAll();
        }
        return repo.findMaxStudentsNumber(keyword);
    }

    public Double getAvgStudents(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findAvgStudentsNumberAll();
        }
        return repo.findAvgStudentsNumber(keyword);
    }

    public Integer getSumStudents(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findSumStudentsNumberAll();
        }
        return repo.findSumStudentsNumber(keyword);
    }
}
