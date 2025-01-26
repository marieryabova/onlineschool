package com.example.onlineschool.service;

import com.example.onlineschool.model.Teacher;
import com.example.onlineschool.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository repo;

    // Вывод преподавателей (всех или по ключевому слову)
    public List<Teacher> getAllTeachers(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findAll();
        }
        return repo.search(keyword);
    }

    // Сохранение преподавателей
    public Teacher saveTeacher(Teacher teacher) {
        return repo.save(teacher);
    }

    // Вывод преподавателей по id
    public Teacher getTeacher(Integer id) {
        return repo.findById(id).get();
    }

    // Удаление преподавателя по id
    public void deleteTeacher(Integer id) {
        repo.deleteById(id);
    }

    // Поиск общего количества преподавателей
    public long getTeacherCount(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.count();
        }
        return repo.countByKey(keyword);
    }

    // Поиск минимума по опыту работы
    public Integer getMinExperience(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.getMinExperienceAll();
        }
        return repo.getMinExperience(keyword);
    }

    // Поиск максимума по опыту работы
    public Integer getMaxExperience(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.getMaxExperienceAll();
        }
        return repo.getMaxExperience(keyword);
    }

    // Поиск максимума по опыту работы
    public Double getAverageExperience(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.getAvgExperienceAll();
        }
        return repo.getAvgExperience(keyword);
    }
}
