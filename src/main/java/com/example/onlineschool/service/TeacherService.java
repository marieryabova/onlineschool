package com.example.onlineschool.service;

import com.example.onlineschool.model.Teacher;
import com.example.onlineschool.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с сущностью {@link Teacher}.
 * Предоставляет методы для управления преподавателями, включая поиск, сохранение, удаление и статистику.
 */
@Service
public class TeacherService {

    @Autowired
    private TeacherRepository repo;

    /**
     * Возвращает список всех преподавателей или преподавателей, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return список преподавателей
     */
    public List<Teacher> getAllTeachers(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findAll();
        }
        return repo.search(keyword);
    }

    /**
     * Сохраняет преподавателя в базу данных.
     *
     * @param teacher преподаватель для сохранения
     * @return сохраненный преподаватель
     */
    public Teacher saveTeacher(Teacher teacher) {
        return repo.save(teacher);
    }

    /**
     * Возвращает преподавателя по его идентификатору.
     *
     * @param id идентификатор преподавателя
     * @return преподаватель
     */
    public Teacher getTeacher(Integer id) {
        return repo.findById(id).get();
    }

    /**
     * Удаляет преподавателя по его идентификатору.
     *
     * @param id идентификатор преподавателя
     */
    public void deleteTeacher(Integer id) {
        repo.deleteById(id);
    }

    /**
     * Возвращает количество преподавателей, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return количество преподавателей
     */
    public long getTeacherCount(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.count();
        }
        return repo.countByKey(keyword);
    }

    /**
     * Возвращает минимальный опыт работы преподавателей, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return минимальный опыт работы
     */
    public Integer getMinExperience(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.getMinExperienceAll();
        }
        return repo.getMinExperience(keyword);
    }

    /**
     * Возвращает максимальный опыт работы преподавателей, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return максимальный опыт работы
     */
    public Integer getMaxExperience(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.getMaxExperienceAll();
        }
        return repo.getMaxExperience(keyword);
    }

    /**
     * Возвращает средний опыт работы преподавателей, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return средний опыт работы
     */
    public Double getAverageExperience(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.getAvgExperienceAll();
        }
        return repo.getAvgExperience(keyword);
    }
}