package com.example.onlineschool.controller;

import com.example.onlineschool.model.Student;
import com.example.onlineschool.model.User;
import com.example.onlineschool.service.StudentService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Контроллер для обработки запросов, связанных со студентами.
 * Предоставляет методы для CRUD-операций со студентами, включая:
 * <ul>
 *   <li>Просмотр списка студентов с возможностью поиска и сортировки</li>
 *   <li>Добавление новых студентов</li>
 *   <li>Редактирование данных студентов</li>
 *   <li>Удаление студентов</li>
 * </ul>
 * Доступ к операциям ограничен в соответствии с ролями пользователей (ADMIN, STUDENT).
 *
 * @see StudentService
 * @see Student
 */
@Controller
public class StudentController {

    /**
     * Сервис для работы с данными студентов.
     * Обеспечивает взаимодействие с базой данных и бизнес-логикой приложения
     * для операций со студентами.
     */
    @Autowired
    private StudentService service;

    /**
     * Отображает страницу со списком студентов.
     * Поддерживает:
     * <ul>
     *   <li>Поиск студентов по ключевому слову</li>
     *   <li>Сортировку по различным полям</li>
     *   <li>Отображение статистики по номерам курсов</li>
     * </ul>
     *
     * @param model модель для передачи данных в представление
     * @param keyword ключевое слово для фильтрации студентов (может быть null)
     * @param sortColumn название столбца для сортировки (может быть null)
     * @param sortOrder порядок сортировки (asc/desc, может быть null)
     * @return имя представления "main/students"
     */
    @GetMapping("/main/students")
    public String students(Model model,
                           @Param("keyword") String keyword,
                           @Param("sortColumn") String sortColumn,
                           @Param("sortOrder") String sortOrder) {
        List<Student> studentList = service.getAllStudents(keyword, sortColumn, sortOrder);

        model.addAttribute("studentList", studentList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortColumn", sortColumn);
        model.addAttribute("sortOrder", sortOrder);

        model.addAttribute("studentCount", service.getStudentCount(keyword));
        model.addAttribute("minCourseNumber", service.getMinCourseNumber(keyword));
        model.addAttribute("maxCourseNumber", service.getMaxCourseNumber(keyword));
        model.addAttribute("avgCourseNumber", service.getAverageCourseNumber(keyword));

        return "main/students";
    }

    /**
     * Отображает форму для создания нового студента.
     * Доступ разрешен только пользователям с ролью ADMIN.
     *
     * @param model модель для передачи данных в представление
     * @return имя представления "new/new_student"
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/new/new_student")
    public String newStudent(Model model) {
        model.addAttribute("student", new Student());
        return "new/new_student";
    }

    /**
     * Сохраняет данные студента в системе.
     * В зависимости от роли пользователя:
     * <ul>
     *   <li>ADMIN - перенаправляет на страницу списка студентов</li>
     *   <li>STUDENT - обновляет профиль и перенаправляет на главную страницу</li>
     * </ul>
     *
     * @param student объект студента для сохранения
     * @return строка перенаправления в зависимости от роли пользователя
     */
    @PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
    @PostMapping("/save/save_student")
    public String saveStudent(@ModelAttribute("student") Student student) {
        service.saveStudent(student);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/main/students";
        } else {
            User currentUser = (User) auth.getPrincipal();
            currentUser.setStudent(student);
            return "redirect:/index";
        }
    }

    /**
     * Отображает форму для редактирования данных студента.
     * Правила доступа:
     * <ul>
     *   <li>ADMIN - может редактировать любого студента</li>
     *   <li>STUDENT - может редактировать только свой профиль</li>
     * </ul>
     * В случае нарушения прав доступа выбрасывает AccessDeniedException.
     *
     * @param id идентификатор студента
     * @return объект ModelAndView с данными студента
     * @throws AccessDeniedException если STUDENT пытается редактировать чужой профиль
     */
    @PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
    @RequestMapping("/edit/edit_student/{id}")
    public ModelAndView editStudent(@PathVariable(name = "id") Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"))) {
            User currentUser = (User) auth.getPrincipal();
            if (!currentUser.getStudent().getId().equals(id)) {
                throw new AccessDeniedException("Вы можете редактировать только свой профиль");
            }
        }

        ModelAndView mav = new ModelAndView("edit/edit_student");
        Student student = service.getStudent(id);
        mav.addObject("student", student);
        return mav;
    }

    /**
     * Удаляет студента из системы.
     * Доступ разрешен только пользователям с ролью ADMIN.
     * После удаления перенаправляет на страницу со списком студентов.
     *
     * @param id идентификатор студента для удаления
     * @return строка перенаправления на "/main/students"
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/delete_student/{id}")
    public String delete(@PathVariable Integer id) {
        service.deleteStudent(id);
        return "redirect:/main/students";
    }
}