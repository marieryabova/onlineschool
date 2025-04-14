package com.example.onlineschool.service;

import com.example.onlineschool.model.CourseTeacher;
import com.example.onlineschool.repository.CourseTeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с сущностью {@link CourseTeacher}.
 * Предоставляет методы для управления записями о преподавателях курсов, включая поиск, сохранение, удаление и подсчет.
 */
@Service
public class CourseTeacherService {

    @Autowired
    private CourseTeacherRepository repo;

    /**
     * Возвращает список всех записей о преподавателях курсов или записей, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return список записей о преподавателях курсов
     */
    public List<CourseTeacher> getAllCourseTeachers(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findAll();
        }
        return repo.search(keyword);
    }

    /**
     * Сохраняет запись о преподавателе курса в базу данных.
     *
     * @param courseTeacher запись о преподавателе курса для сохранения
     */
    public void saveCourseTeacher(CourseTeacher courseTeacher) {
        repo.save(courseTeacher);
    }

    /**
     * Возвращает запись о преподавателе курса по её идентификатору.
     *
     * @param id идентификатор записи о преподавателе курса
     * @return запись о преподавателе курса
     */
    public CourseTeacher getCourseTeacherById(Integer id) {
        return repo.findById(id).get();
    }

    /**
     * Удаляет запись о преподавателе курса по её идентификатору.
     *
     * @param id идентификатор записи о преподавателе курса
     */
    public void deleteCourseTeacher(Integer id) {
        repo.deleteById(id);
    }

    /**
     * Возвращает количество записей о преподавателях курсов, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return количество записей о преподавателях курсов
     */
    public long getCourseTeachersCount(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.count();
        }
        return repo.countByKey(keyword);
    }
}