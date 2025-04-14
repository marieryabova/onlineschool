package com.example.onlineschool.controller;

import com.example.onlineschool.model.CourseStudent;
import com.example.onlineschool.service.CourseStudentService;
import com.example.onlineschool.service.CourseService;
import com.example.onlineschool.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Контроллер для управления связями между курсами и студентами.
 * Предоставляет полный набор операций для работы с привязками студентов к курсам:
 * <ul>
 *   <li>Просмотр всех текущих привязок</li>
 *   <li>Добавление новых привязок</li>
 *   <li>Редактирование существующих привязок</li>
 *   <li>Удаление привязок</li>
 *   <li>Просмотр статистики по привязкам</li>
 * </ul>
 * Все операции доступны только пользователям с ролью ADMIN.
 *
 * @see CourseStudentService
 * @see CourseService
 * @see StudentService
 * @see CourseStudent
 */
@Controller
public class CourseStudentController {

    /**
     * Сервис для работы с привязками студентов к курсам.
     * Обеспечивает основные CRUD-операции для управления связями.
     */
    @Autowired
    private CourseStudentService courseStudentService;

    /**
     * Сервис для работы с курсами.
     * Используется для получения списка доступных курсов.
     */
    @Autowired
    private CourseService courseService;

    /**
     * Сервис для работы со студентами.
     * Используется для получения списка зарегистрированных студентов.
     */
    @Autowired
    private StudentService studentService;

    /**
     * Отображает страницу со списком всех привязок студентов к курсам.
     * Поддерживает:
     * <ul>
     *   <li>Фильтрацию по ключевому слову</li>
     *   <li>Отображение общего количества привязок</li>
     * </ul>
     *
     * @param model модель для передачи данных в представление
     * @param keyword ключевое слово для фильтрации (может быть null)
     * @return имя представления "main/course_students"
     */
    @GetMapping("/main/course_students")
    public String showCourseStudents(Model model, @RequestParam(required = false) String keyword) {
        List<CourseStudent> courseStudentsList = courseStudentService.getAllCourseStudents(keyword);
        model.addAttribute("courseStudentsList", courseStudentsList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("CourseStudentsCount", courseStudentService.getCourseStudentsCount(keyword));
        return "main/course_students";
    }

    /**
     * Отображает форму для создания новой привязки студента к курсу.
     * В модель добавляются:
     * <ul>
     *   <li>Пустой объект CourseStudent для заполнения</li>
     *   <li>Список всех доступных курсов</li>
     *   <li>Список всех зарегистрированных студентов</li>
     * </ul>
     *
     * @param model модель для передачи данных в представление
     * @return имя представления "new/new_course_student"
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/new/new_course_student")
    public String showNewCourseStudentForm(Model model) {
        model.addAttribute("courseStudent", new CourseStudent());
        model.addAttribute("courses", courseService.getAllCourses(null));
        model.addAttribute("students", studentService.getAllStudents(null, null, null));
        return "new/new_course_student";
    }

    /**
     * Сохраняет новую привязку студента к курсу.
     * После успешного сохранения перенаправляет на страницу со списком привязок.
     *
     * @param courseStudent объект привязки для сохранения
     * @return строка перенаправления на "/main/course_students"
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save/save_course_student")
    public String saveCourseStudent(@ModelAttribute("courseStudent") CourseStudent courseStudent) {
        courseStudentService.saveCourseStudent(courseStudent);
        return "redirect:/main/course_students";
    }

    /**
     * Отображает форму для редактирования существующей привязки.
     * В модель добавляются:
     * <ul>
     *   <li>Данные редактируемой привязки</li>
     *   <li>Список всех доступных курсов</li>
     *   <li>Список всех зарегистрированных студентов</li>
     * </ul>
     *
     * @param id идентификатор редактируемой привязки
     * @return объект ModelAndView с данными для представления "edit/edit_course_student"
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/edit_course_student/{id}")
    public ModelAndView showEditCourseStudentForm(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("edit/edit_course_student");
        CourseStudent courseStudent = courseStudentService.getCourseStudentById(id);
        mav.addObject("courseStudent", courseStudent);
        mav.addObject("courses", courseService.getAllCourses(null));
        mav.addObject("students", studentService.getAllStudents(null, null, null));
        return mav;
    }

    /**
     * Удаляет привязку студента к курсу.
     * После успешного удаления перенаправляет на страницу со списком привязок.
     *
     * @param id идентификатор привязки для удаления
     * @return строка перенаправления на "/main/course_students"
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/delete_course_student/{id}")
    public String deleteCourseStudent(@PathVariable Integer id) {
        courseStudentService.deleteCourseStudent(id);
        return "redirect:/main/course_students";
    }
}