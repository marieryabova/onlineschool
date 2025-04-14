package com.example.onlineschool.service;

import com.example.onlineschool.model.CourseStudent;
import com.example.onlineschool.repository.CourseStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с сущностью {@link CourseStudent}.
 * Предоставляет методы для управления записями о курсах студентов, включая поиск, сохранение, удаление и подсчет.
 */
@Service
public class CourseStudentService {

    @Autowired
    private CourseStudentRepository repo;

    /**
     * Возвращает список всех записей о курсах студентов или записей, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return список записей о курсах студентов
     */
    public List<CourseStudent> getAllCourseStudents(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findAll();
        }
        return repo.search(keyword);
    }

    /**
     * Сохраняет запись о курсе студента в базу данных.
     *
     * @param courseStudent запись о курсе студента для сохранения
     */
    public void saveCourseStudent(CourseStudent courseStudent) {
        repo.save(courseStudent);
    }

    /**
     * Возвращает запись о курсе студента по её идентификатору.
     *
     * @param id идентификатор записи о курсе студента
     * @return запись о курсе студента
     * @throws java.util.NoSuchElementException если запись с указанным идентификатором не найдена
     */
    public CourseStudent getCourseStudentById(Integer id) {
        return repo.findById(id).get();
    }

    /**
     * Удаляет запись о курсе студента по её идентификатору.
     *
     * @param id идентификатор записи о курсе студента
     */
    public void deleteCourseStudent(Integer id) {
        repo.deleteById(id);
    }

    /**
     * Возвращает количество записей о курсах студентов, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return количество записей о курсах студентов
     */
    public long getCourseStudentsCount(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.count();
        }
        return repo.countByKey(keyword);
    }
}