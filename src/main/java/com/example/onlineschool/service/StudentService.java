package com.example.onlineschool.service;

import com.example.onlineschool.repository.StudentRepository;
import com.example.onlineschool.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с сущностью {@link Student}.
 * Предоставляет методы для управления студентами, включая поиск, сохранение, удаление и статистику.
 */
@Service
public class StudentService {

    @Autowired
    private StudentRepository repo;

    /**
     * Возвращает список всех студентов или студентов, соответствующих ключевому слову.
     * Результаты могут быть отсортированы по указанной колонке и в указанном порядке.
     *
     * @param keyword    ключевое слово для поиска (может быть null или пустым)
     * @param sortColumn колонка для сортировки (может быть null или пустой, по умолчанию "id")
     * @param sortOrder  порядок сортировки (может быть null или пустым, по умолчанию "asc")
     * @return список студентов
     */
    public List<Student> getAllStudents(String keyword, String sortColumn, String sortOrder) {
        if (sortColumn == null || sortColumn.isEmpty()) {
            sortColumn = "id";
        }

        if (sortOrder == null || sortOrder.isEmpty()) {
            sortOrder = "asc";
        }

        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC;

        Sort sort = Sort.by(direction, sortColumn);

        if (keyword == null || keyword.trim().equals("")) {
            return repo.findAll(sort);
        }
        return repo.search(keyword, sort);
    }

    /**
     * Сохраняет студента в базу данных.
     *
     * @param student студент для сохранения
     * @return сохраненный студент
     */
    public Student saveStudent(Student student) {
        return repo.save(student);
    }

    /**
     * Возвращает студента по его идентификатору.
     *
     * @param id идентификатор студента
     * @return студент
     */
    public Student getStudent(Integer id) {
        return repo.findById(id).get();
    }

    /**
     * Удаляет студента по его идентификатору.
     *
     * @param id идентификатор студента
     */
    public void deleteStudent(Integer id) {
        repo.deleteById(id);
    }

    /**
     * Возвращает количество студентов, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return количество студентов
     */
    public long getStudentCount(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.count();
        }
        return repo.countByKey(keyword);
    }

    /**
     * Возвращает минимальное количество курсов у студентов, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return минимальное количество курсов
     */
    public Integer getMinCourseNumber(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findMinCourseNumberAll();
        }
        return repo.findMinCourseNumber(keyword);
    }

    /**
     * Возвращает максимальное количество курсов у студентов, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return максимальное количество курсов
     */
    public Integer getMaxCourseNumber(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findMaxCourseNumberAll();
        }
        return repo.findMaxCourseNumber(keyword);
    }

    /**
     * Возвращает среднее количество курсов у студентов, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return среднее количество курсов
     */
    public Double getAverageCourseNumber(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findAvgCourseNumberAll();
        }
        return repo.findAvgCourseNumber(keyword);
    }
}