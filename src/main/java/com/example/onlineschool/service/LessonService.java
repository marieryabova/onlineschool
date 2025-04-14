package com.example.onlineschool.service;

import com.example.onlineschool.model.Lesson;
import com.example.onlineschool.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Сервис для работы с сущностью {@link Lesson}.
 * Предоставляет методы для управления уроками, включая поиск, сохранение, удаление и статистику.
 */
@Service
public class LessonService {

    @Autowired
    private LessonRepository repo;

    /**
     * Возвращает список всех уроков или уроков, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return список уроков
     */
    public List<Lesson> getAllLessons(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findAll();
        }
        return repo.search(keyword);
    }

    /**
     * Сохраняет урок в базу данных.
     *
     * @param lesson урок для сохранения
     * @return сохраненный урок
     */
    public Lesson saveLesson(Lesson lesson) {
        return repo.save(lesson);
    }

    /**
     * Возвращает урок по его идентификатору.
     *
     * @param id идентификатор урока
     * @return урок
     */
    public Lesson getLesson(Integer id) {
        return repo.findById(id).get();
    }

    /**
     * Удаляет урок по его идентификатору.
     *
     * @param id идентификатор урока
     */
    public void deleteLesson(Integer id) {
        repo.deleteById(id);
    }

    /**
     * Возвращает количество уроков, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return количество уроков
     */
    public long getLessonCount(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.count();
        }
        return repo.countByKey(keyword);
    }

    /**
     * Возвращает количество уроков для каждого модуля.
     * Если ключевое слово не указано, возвращает статистику для всех модулей.
     * Если ключевое слово указано, возвращает статистику для модулей, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return карта, где ключ — название модуля, а значение — количество уроков в этом модуле
     */
    public Map<String, Long> getLessonsPerModule(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.countLessonsPerModule().stream()
                    .collect(Collectors.toMap(
                            result -> (String) result[0],
                            result -> (Long) result[1]
                    ));
        }
        return repo.countLessonsPerModuleByKey(keyword).stream()
                .collect(Collectors.toMap(
                        result -> (String) result[0],
                        result -> (Long) result[1]
                ));
    }
}