package com.example.onlineschool.controller;

import com.example.onlineschool.model.CourseTeacher;
import com.example.onlineschool.service.CourseTeacherService;
import com.example.onlineschool.service.CourseService;
import com.example.onlineschool.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Контроллер для управления связями между курсами и преподавателями.
 * Предоставляет функциональность для:
 * <ul>
 *   <li>Просмотра списка всех связей курсов и преподавателей</li>
 *   <li>Создания новых связей</li>
 *   <li>Редактирования существующих связей</li>
 *   <li>Удаления связей</li>
 *   <li>Просмотра статистики по связям</li>
 * </ul>
 * Все операции доступны только пользователям с ролью ADMIN.
 *
 * @see CourseTeacherService
 * @see CourseService
 * @see TeacherService
 * @see CourseTeacher
 */
@Controller
public class CourseTeacherController {

    /**
     * Сервис для работы со связями курсов и преподавателей.
     * Обеспечивает основные CRUD-операции для связей.
     */
    @Autowired
    private CourseTeacherService courseTeacherService;

    /**
     * Сервис для работы с курсами.
     * Используется для получения списка курсов при создании/редактировании связей.
     */
    @Autowired
    private CourseService courseService;

    /**
     * Сервис для работы с преподавателями.
     * Используется для получения списка преподавателей при создании/редактировании связей.
     */
    @Autowired
    private TeacherService teacherService;

    /**
     * Отображает страницу со списком всех связей курсов и преподавателей.
     * Поддерживает фильтрацию по ключевому слову и отображает общее количество связей.
     
     *
     * @param model модель для передачи данных в представление
     * @param keyword ключевое слово для фильтрации (может быть null)
     * @return имя представления "main/course_teachers"
     */
    @GetMapping("/main/course_teachers")
    public String showCourseTeachers(Model model, @RequestParam(required = false) String keyword) {
        List<CourseTeacher> courseTeachersList = courseTeacherService.getAllCourseTeachers(keyword);
        model.addAttribute("courseTeachersList", courseTeachersList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("CourseTeachersCount", courseTeacherService.getCourseTeachersCount(keyword));
        return "main/course_teachers";
    }

    /**
     * Отображает форму для создания новой связи между курсом и преподавателем.
     
     * В модель добавляются списки всех курсов и преподавателей для выбора.
     
     *
     * @param model модель для передачи данных в представление
     * @return имя представления "new/new_course_teacher"
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/new/new_course_teacher")
    public String showNewCourseTeacherForm(Model model) {
        model.addAttribute("courseTeacher", new CourseTeacher());
        model.addAttribute("courses", courseService.getAllCourses(null));
        model.addAttribute("teachers", teacherService.getAllTeachers(null));
        return "new/new_course_teacher";
    }

    /**
     * Сохраняет новую связь между курсом и преподавателем.
     
     * После успешного сохранения перенаправляет на страницу со списком связей.
     
     *
     * @param courseTeacher объект связи для сохранения
     * @return строка перенаправления на "/main/course_teachers"
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save/save_course_teacher")
    public String saveCourseTeacher(@ModelAttribute("courseTeacher") CourseTeacher courseTeacher) {
        courseTeacherService.saveCourseTeacher(courseTeacher);
        return "redirect:/main/course_teachers";
    }

    /**
     * Отображает форму для редактирования существующей связи.
     
     * В модель добавляются списки всех курсов и преподавателей для выбора.
     
     *
     * @param id идентификатор редактируемой связи
     * @return объект ModelAndView с данными для представления "edit/edit_course_teacher"
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/edit_course_teacher/{id}")
    public ModelAndView showEditCourseTeacherForm(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("edit/edit_course_teacher");
        CourseTeacher courseTeacher = courseTeacherService.getCourseTeacherById(id);
        mav.addObject("courseTeacher", courseTeacher);
        mav.addObject("courses", courseService.getAllCourses(null));
        mav.addObject("teachers", teacherService.getAllTeachers(null));
        return mav;
    }

    /**
     * Удаляет связь между курсом и преподавателем.
     
     * После успешного удаления перенаправляет на страницу со списком связей.
     
     *
     * @param id идентификатор связи для удаления
     * @return строка перенаправления на "/main/course_teachers"
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/delete_course_teacher/{id}")
    public String deleteCourseTeacher(@PathVariable Integer id) {
        courseTeacherService.deleteCourseTeacher(id);
        return "redirect:/main/course_teachers";
    }
}