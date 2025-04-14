package com.example.onlineschool.service;

import com.example.onlineschool.model.Course;
import com.example.onlineschool.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с сущностью {@link Course}.
 * Предоставляет методы для управления курсами, включая поиск, сохранение, удаление и статистику.
 */
@Service
public class CourseService {

    @Autowired
    private CourseRepository repo;

    /**
     * Возвращает список всех курсов или курсов, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return список курсов
     */
    public List<Course> getAllCourses(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findAll();
        }
        return repo.search(keyword);
    }

    /**
     * Сохраняет курс в базу данных.
     *
     * @param course курс для сохранения
     * @return сохраненный курс
     */
    public Course saveCourse(Course course) {
        return repo.save(course);
    }

    /**
     * Возвращает курс по его идентификатору.
     *
     * @param id идентификатор курса
     * @return курс
     * @throws java.util.NoSuchElementException если курс с указанным идентификатором не найден
     */
    public Course getCourse(Integer id) {
        return repo.findById(id).get();
    }

    /**
     * Удаляет курс по его идентификатору.
     *
     * @param id идентификатор курса
     */
    public void deleteCourse(Integer id) {
        repo.deleteById(id);
    }

    /**
     * Возвращает количество курсов, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return количество курсов
     */
    public long getCourseCount(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.count();
        }
        return repo.countByKey(keyword);
    }

    /**
     * Возвращает минимальную цену курсов, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return минимальная цена курсов
     */
    public Integer getMinPrice(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findMinPriceAll();
        }
        return repo.findMinPrice(keyword);
    }

    /**
     * Возвращает максимальную цену курсов, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return максимальная цена курсов
     */
    public Integer getMaxPrice(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findMaxPriceAll();
        }
        return repo.findMaxPrice(keyword);
    }

    /**
     * Возвращает среднюю цену курсов, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return средняя цена курсов
     */
    public Double getAvgPrice(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findAvgPriceAll();
        }
        return repo.findAvgPrice(keyword);
    }

    /**
     * Возвращает сумму цен курсов, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return сумма цен курсов
     */
    public Integer getSumPrice(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findSumPriceAll();
        }
        return repo.findSumPrice(keyword);
    }

    /**
     * Возвращает минимальную длительность курсов, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return минимальная длительность курсов
     */
    public Integer getMinDuration(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findMinDurationAll();
        }
        return repo.findMinDuration(keyword);
    }

    /**
     * Возвращает максимальную длительность курсов, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return максимальная длительность курсов
     */
    public Integer getMaxDuration(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findMaxDurationAll();
        }
        return repo.findMaxDuration(keyword);
    }

    /**
     * Возвращает среднюю длительность курсов, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return средняя длительность курсов
     */
    public Double getAvgDuration(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findAvgDurationAll();
        }
        return repo.findAvgDuration(keyword);
    }

    /**
     * Возвращает сумму длительности курсов, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return сумма длительности курсов
     */
    public Integer getSumDuration(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findSumDurationAll();
        }
        return repo.findSumDuration(keyword);
    }

    /**
     * Возвращает минимальное количество студентов на курсах, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return минимальное количество студентов
     */
    public Integer getMinStudents(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findMinStudentsNumberAll();
        }
        return repo.findMinStudentsNumber(keyword);
    }

    /**
     * Возвращает максимальное количество студентов на курсах, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return максимальное количество студентов
     */
    public Integer getMaxStudents(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findMaxStudentsNumberAll();
        }
        return repo.findMaxStudentsNumber(keyword);
    }

    /**
     * Возвращает среднее количество студентов на курсах, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return среднее количество студентов
     */
    public Double getAvgStudents(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findAvgStudentsNumberAll();
        }
        return repo.findAvgStudentsNumber(keyword);
    }

    /**
     * Возвращает сумму количества студентов на курсах, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return сумма количества студентов
     */
    public Integer getSumStudents(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findSumStudentsNumberAll();
        }
        return repo.findSumStudentsNumber(keyword);
    }
}