package com.example.onlineschool.controller;

import com.example.onlineschool.model.Course;
import com.example.onlineschool.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Контроллер для обработки запросов, связанных с курсами.
 * Предоставляет полный набор операций CRUD для управления курсами:
 * <ul>
 *   <li>Просмотр списка курсов с возможностью поиска и статистики</li>
 *   <li>Создание новых курсов</li>
 *   <li>Редактирование существующих курсов</li>
 *   <li>Удаление курсов</li>
 * </ul>
 * Все операции изменения данных доступны только пользователям с ролью ADMIN.
 *
 * @see CourseService
 * @see Course
 */
@Controller
public class CourseController {

    /**
     * Сервис для работы с курсами.
     * Обеспечивает доступ к данным курсов и выполнение бизнес-логики.
     */
    @Autowired
    private CourseService service;

    /**
     * Отображает страницу со списком курсов.
     * Включает:
     * <ul>
     *   <li>Список курсов с возможностью поиска по ключевому слову</li>
     *   <li>Статистику по ценам курсов (мин, макс, средняя, сумма)</li>
     *   <li>Статистику по продолжительности курсов (мин, макс, средняя, сумма)</li>
     *   <li>Статистику по количеству студентов (мин, макс, среднее, сумма)</li>
     * </ul>
     *
     * @param model модель для передачи данных в представление
     * @param keyword ключевое слово для поиска курсов (может быть null)
     * @param showStatistics флаг отображения статистики (может быть null)
     * @return имя представления "main/courses"
     */
    @GetMapping("/main/courses")
    public String courses(Model model,
                          @Param("keyword") String keyword,
                          @Param("showStatistics") Boolean showStatistics) {
        List<Course> courseList = service.getAllCourses(keyword);
        model.addAttribute("courseList", courseList);
        model.addAttribute("keyword", keyword);

        // Статистика по ценам
        model.addAttribute("courseCount", service.getCourseCount(keyword));
        model.addAttribute("minPrice", service.getMinPrice(keyword));
        model.addAttribute("maxPrice", service.getMaxPrice(keyword));
        model.addAttribute("avgPrice", service.getAvgPrice(keyword));
        model.addAttribute("sumPrice", service.getSumPrice(keyword));

        // Статистика по продолжительности
        model.addAttribute("minDuration", service.getMinDuration(keyword));
        model.addAttribute("maxDuration", service.getMaxDuration(keyword));
        model.addAttribute("avgDuration", service.getAvgDuration(keyword));
        model.addAttribute("sumDuration", service.getSumDuration(keyword));

        // Статистика по студентам
        model.addAttribute("minStudents", service.getMinStudents(keyword));
        model.addAttribute("maxStudents", service.getMaxStudents(keyword));
        model.addAttribute("avgStudents", service.getAvgStudents(keyword));
        model.addAttribute("sumStudents", service.getSumStudents(keyword));

        return "main/courses";
    }

    /**
     * Отображает форму для создания нового курса.
     * Доступ разрешен только пользователям с ролью ADMIN.
     *
     * @param model модель для передачи данных в представление
     * @return имя представления "new/new_course"
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/new/new_course")
    public String newCourse(Model model) {
        model.addAttribute("course", new Course());
        return "new/new_course";
    }

    /**
     * Сохраняет новый курс в системе.
     * Доступ разрешен только пользователям с ролью ADMIN.
     * После сохранения перенаправляет на страницу со списком курсов.
     *
     * @param course объект курса для сохранения
     * @return строка перенаправления на "/main/courses"
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save/save_course")
    public String saveCourse(@ModelAttribute("course") Course course) {
        service.saveCourse(course);
        return "redirect:/main/courses";
    }

    /**
     * Отображает форму для редактирования курса.
     * Доступ разрешен только пользователям с ролью ADMIN.
     *
     * @param id идентификатор редактируемого курса
     * @return объект ModelAndView для представления "edit/edit_course"
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/edit/edit_course/{id}")
    public ModelAndView editCourse(@PathVariable(name = "id") Integer id) {
        ModelAndView mav = new ModelAndView("edit/edit_course");
        Course course = service.getCourse(id);
        mav.addObject("course", course);
        return mav;
    }

    /**
     * Удаляет курс из системы.
     * Доступ разрешен только пользователям с ролью ADMIN.
     * После удаления перенаправляет на страницу со списком курсов.
     *
     * @param id идентификатор удаляемого курса
     * @return строка перенаправления на "/main/courses"
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/delete_course/{id}")
    public String delete(@PathVariable Integer id) {
        service.deleteCourse(id);
        return "redirect:/main/courses";
    }
}