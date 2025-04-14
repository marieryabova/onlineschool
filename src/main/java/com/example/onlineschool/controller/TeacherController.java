package com.example.onlineschool.controller;

import com.example.onlineschool.model.Teacher;
import com.example.onlineschool.model.User;
import com.example.onlineschool.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Контроллер для обработки запросов, связанных с преподавателями.
 
 * Предоставляет методы для CRUD-операций с преподавателями, включая:
 * <ul>
 *   <li>Просмотр списка преподавателей с возможностью поиска</li>
 *   <li>Добавление новых преподавателей</li>
 *   <li>Редактирование существующих преподавателей</li>
 *   <li>Удаление преподавателей</li>
 * </ul>
 * Доступ к некоторым операциям ограничен ролями ADMIN и TEACHER.
 
 *
 * @see TeacherService
 * @see Teacher
 */
@Controller
public class TeacherController {

    /**
     * Сервис для работы с данными преподавателей.
     
     * Используется для выполнения операций с преподавателями,
     * включая поиск, сохранение, обновление и удаление.
     
     */
    @Autowired
    private TeacherService service;

    /**
     * Отображает страницу со списком преподавателей.
     
     * Поддерживает поиск преподавателей по ключевому слову.
     * Добавляет в модель статистические данные о преподавателях:
     * общее количество, минимальный, максимальный и средний стаж.
     
     *
     * @param model модель для передачи данных в представление
     * @param keyword ключевое слово для фильтрации преподавателей (может быть null)
     * @return имя представления "main/teachers"
     */
    @GetMapping("/main/teachers")
    public String teachers(Model model, @Param("keyword") String keyword) {
        List<Teacher> teacherList = service.getAllTeachers(keyword);
        model.addAttribute("teacherList", teacherList);
        model.addAttribute("keyword", keyword);

        model.addAttribute("teacherCount", service.getTeacherCount(keyword));
        model.addAttribute("minExperience", service.getMinExperience(keyword));
        model.addAttribute("maxExperience", service.getMaxExperience(keyword));
        model.addAttribute("avgExperience", service.getAverageExperience(keyword));

        return "main/teachers";
    }

    /**
     * Отображает форму для создания нового преподавателя.
     
     * Доступ разрешен только пользователям с ролью ADMIN.
     
     *
     * @param model модель для передачи данных в представление
     * @return имя представления "new/new_teacher"
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/new/new_teacher")
    public String newTeacher(Model model) {
        model.addAttribute("teacher", new Teacher());
        return "new/new_teacher";
    }

    /**
     * Сохраняет нового преподавателя в системе.
     
     * Доступ разрешен пользователям с ролями ADMIN или TEACHER.
     * После сохранения перенаправляет:
     * <ul>
     *   <li>ADMIN - на страницу со списком преподавателей</li>
     *   <li>TEACHER - на главную страницу</li>
     * </ul>
     
     *
     * @param teacher объект преподавателя для сохранения
     * @return строка перенаправления в зависимости от роли пользователя
     */
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    @PostMapping("/save/save_teacher")
    public String saveTeacher(@ModelAttribute("teacher") Teacher teacher) {
        service.saveTeacher(teacher);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        currentUser.setTeacher(teacher);
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/main/teachers";
        } else {
            return "redirect:/index";
        }
    }

    /**
     * Отображает форму для редактирования преподавателя.
     
     * Доступ разрешен:
     * <ul>
     *   <li>ADMIN - может редактировать любого преподавателя</li>
     *   <li>TEACHER - может редактировать только свой профиль</li>
     * </ul>
     * В случае нарушения прав доступа выбрасывает AccessDeniedException.
     
     *
     * @param id идентификатор редактируемого преподавателя
     * @return объект ModelAndView с данными преподавателя для представления "edit/edit_teacher"
     * @throws AccessDeniedException если TEACHER пытается редактировать чужой профиль
     */
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    @RequestMapping("/edit/edit_teacher/{id}")
    public ModelAndView editTeacher(@PathVariable(name = "id") Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_TEACHER"))) {
            User currentUser = (User) auth.getPrincipal();
            if (!currentUser.getTeacher().getId().equals(id)) {
                throw new AccessDeniedException("Вы можете редактировать только свой профиль");
            }
        }
        ModelAndView mav = new ModelAndView("edit/edit_teacher");
        Teacher teacher = service.getTeacher(id);
        mav.addObject("teacher", teacher);
        return mav;
    }

    /**
     * Удаляет преподавателя с указанным идентификатором.
     
     * Доступ разрешен только пользователям с ролью ADMIN.
     * После удаления перенаправляет на страницу со списком преподавателей.
     
     *
     * @param id идентификатор преподавателя для удаления
     * @return строка перенаправления на страницу "/main/teachers"
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/delete_teacher/{id}")
    public String delete(@PathVariable Integer id) {
        service.deleteTeacher(id);
        return "redirect:/main/teachers";
    }
}