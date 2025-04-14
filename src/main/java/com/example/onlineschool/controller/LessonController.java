package com.example.onlineschool.controller;

import com.example.onlineschool.model.Module;
import com.example.onlineschool.model.Lesson;
import com.example.onlineschool.service.ModuleService;
import com.example.onlineschool.service.LessonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * Контроллер для обработки запросов, связанных с уроками.
 
 * Предоставляет полный набор операций CRUD для работы с уроками:
 * <ul>
 *   <li>Просмотр списка всех уроков с возможностью поиска</li>
 *   <li>Создание новых уроков</li>
 *   <li>Редактирование существующих уроков</li>
 *   <li>Удаление уроков</li>
 *   <li>Просмотр статистики по урокам</li>
 * </ul>
 * Все операции доступны только пользователям с ролью ADMIN.
 
 *
 * @see LessonService
 * @see ModuleService
 * @see Lesson
 * @see Module
 */
@Controller
public class LessonController {

    /**
     * Сервис для работы с уроками.
     
     * Обеспечивает доступ к данным уроков и выполнение операций CRUD.
     
     */
    @Autowired
    private LessonService lessonService;

    /**
     * Сервис для работы с модулями.
     
     * Используется для получения списка модулей при создании/редактировании уроков.
     
     */
    @Autowired
    private ModuleService moduleService;

    /**
     * Отображает страницу со списком всех уроков.
     
     * Включает:
     * <ul>
     *   <li>Список уроков с возможностью фильтрации по ключевому слову</li>
     *   <li>Общее количество уроков</li>
     *   <li>Данные для построения диаграммы распределения уроков по модулям</li>
     * </ul>
     
     *
     * @param model модель для передачи данных в представление
     * @param keyword ключевое слово для фильтрации уроков (может быть null)
     * @return имя представления "main/lessons"
     * @throws JsonProcessingException если возникает ошибка при преобразовании данных для диаграммы
     */
    @GetMapping("/main/lessons")
    public String showLessons(Model model, @Param("keyword") String keyword) throws JsonProcessingException {
        List<Lesson> lessonList = lessonService.getAllLessons(keyword);
        model.addAttribute("lessonList", lessonList);
        model.addAttribute("keyword", keyword);

        model.addAttribute("lessonCount", lessonService.getLessonCount(keyword));

        Map<String, Long> lessonsPerModule = lessonService.getLessonsPerModule(keyword);
        ObjectMapper objectMapper = new ObjectMapper();
        String lessonsPerModuleJson = objectMapper.writeValueAsString(lessonsPerModule);
        model.addAttribute("lessonsPerModuleJson", lessonsPerModuleJson);

        return "main/lessons";
    }

    /**
     * Отображает форму для создания нового урока.
     
     * Доступ разрешен только пользователям с ролью ADMIN.
     * В модель добавляется список всех модулей для выбора при создании урока.
     
     *
     * @param model модель для передачи данных в представление
     * @return имя представления "new/new_lesson"
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/new/new_lesson")
    public String newLesson(Model model) {
        model.addAttribute("lesson", new Lesson());
        model.addAttribute("modules", moduleService.getAllModules(null));
        return "new/new_lesson";
    }

    /**
     * Сохраняет новый урок в системе.
     
     * Доступ разрешен только пользователям с ролью ADMIN.
     * После сохранения перенаправляет на страницу со списком уроков.
     
     *
     * @param lesson объект урока для сохранения
     * @return строка перенаправления на "/main/lessons"
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save/save_lesson")
    public String saveLesson(@ModelAttribute("lesson") Lesson lesson) {
        Integer moduleId = lesson.getModule().getId();
        Module module = moduleService.getModule(moduleId);
        lesson.setModule(module);
        lessonService.saveLesson(lesson);
        return "redirect:/main/lessons";
    }

    /**
     * Отображает форму для редактирования урока.
     
     * Доступ разрешен только пользователям с ролью ADMIN.
     * В модель добавляется список всех модулей для выбора при редактировании урока.
     
     *
     * @param id идентификатор редактируемого урока
     * @return объект ModelAndView с данными урока для представления "edit/edit_lesson"
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/edit/edit_lesson/{id}")
    public ModelAndView editLesson(@PathVariable(name = "id") Integer id) {
        ModelAndView mav = new ModelAndView("edit/edit_lesson");
        Lesson lesson = lessonService.getLesson(id);
        mav.addObject("lesson", lesson);
        mav.addObject("modules", moduleService.getAllModules(null));
        return mav;
    }

    /**
     * Удаляет урок из системы.
     
     * Доступ разрешен только пользователям с ролью ADMIN.
     * После удаления перенаправляет на страницу со списком уроков.
     
     *
     * @param id идентификатор урока для удаления
     * @return строка перенаправления на "/main/lessons"
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/delete_lesson/{id}")
    public String deleteLesson(@PathVariable Integer id) {
        lessonService.deleteLesson(id);
        return "redirect:/main/lessons";
    }
}