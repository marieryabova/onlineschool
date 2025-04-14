package com.example.onlineschool.service;

import com.example.onlineschool.model.Module;
import com.example.onlineschool.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Сервис для работы с сущностью {@link Module}.
 * Предоставляет методы для управления модулями, включая поиск, сохранение, удаление и статистику.
 */
@Service
public class ModuleService {

    @Autowired
    private ModuleRepository repo;

    /**
     * Возвращает список всех модулей или модулей, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return список модулей
     */
    public List<Module> getAllModules(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.findAll();
        }
        return repo.search(keyword);
    }

    /**
     * Сохраняет модуль в базу данных.
     *
     * @param module модуль для сохранения
     * @return сохраненный модуль
     */
    public Module saveModule(Module module) {
        return repo.save(module);
    }

    /**
     * Возвращает модуль по его идентификатору.
     *
     * @param id идентификатор модуля
     * @return модуль
     */
    public Module getModule(Integer id) {
        return repo.findById(id).get();
    }

    /**
     * Удаляет модуль по его идентификатору.
     *
     * @param id идентификатор модуля
     */
    public void deleteModule(Integer id) {
        repo.deleteById(id);
    }

    /**
     * Возвращает количество модулей, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return количество модулей
     */
    public long getModuleCount(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.count();
        }
        return repo.countByKey(keyword);
    }

    /**
     * Возвращает количество модулей для каждого курса.
     * Если ключевое слово не указано, возвращает статистику для всех курсов.
     * Если ключевое слово указано, возвращает статистику для курсов, соответствующих ключевому слову.
     *
     * @param keyword ключевое слово для поиска (может быть null или пустым)
     * @return карта, где ключ — название курса, а значение — количество модулей в этом курсе
     */
    public Map<String, Long> getModulesPerCourse(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return repo.countModulesPerCourse().stream()
                    .collect(Collectors.toMap(
                            result -> (String) result[0], // Название курса
                            result -> (Long) result[1]   // Количество модулей
                    ));
        }
        return repo.countModulesPerCourseByKey(keyword).stream()
                .collect(Collectors.toMap(
                        result -> (String) result[0], // Название курса
                        result -> (Long) result[1]   // Количество модулей
                ));
    }
}