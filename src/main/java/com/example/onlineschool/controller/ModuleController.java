package com.example.onlineschool.controller;

import com.example.onlineschool.model.Course;
import com.example.onlineschool.model.Module;
import com.example.onlineschool.service.CourseService;
import com.example.onlineschool.service.ModuleService;
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
 * Контроллер для обработки запросов, связанных с модулями курсов.
 
 * Предоставляет функциональность для:
 * <ul>
 *   <li>Просмотра списка модулей с возможностью поиска</li>
 *   <li>Создания новых модулей</li>
 *   <li>Редактирования существующих модулей</li>
 *   <li>Удаления модулей</li>
 *   <li>Просмотра статистики по модулям</li>
 * </ul>
 * Все операции доступны только для пользователей с ролью ADMIN.
 
 *
 * @see ModuleService
 * @see CourseService
 * @see Module
 * @see Course
 */
@Controller
public class ModuleController {

    /**
     * Сервис для работы с модулями курсов.
     
     * Обеспечивает доступ к данным модулей и выполнение операций CRUD.
     
     */
    @Autowired
    private ModuleService moduleService;

    /**
     * Сервис для работы с курсами.
     
     * Используется для получения списка курсов при создании/редактировании модулей.
     
     */
    @Autowired
    private CourseService courseService;

    /**
     * Отображает страницу со списком всех модулей.
     
     * Включает:
     * <ul>
     *   <li>Список модулей с возможностью фильтрации по ключевому слову</li>
     *   <li>Статистику по количеству модулей</li>
     *   <li>Данные для построения диаграммы распределения модулей по курсам</li>
     * </ul>
     
     *
     * @param model модель для передачи данных в представление
     * @param keyword ключевое слово для фильтрации модулей (может быть null)
     * @return имя представления "main/modules"
     * @throws JsonProcessingException если возникает ошибка при преобразовании данных для диаграммы
     */
    @GetMapping("/main/modules")
    public String showModules(Model model, @Param("keyword") String keyword) throws JsonProcessingException {
        List<Module> moduleList = moduleService.getAllModules(keyword);
        model.addAttribute("moduleList", moduleList);
        model.addAttribute("keyword", keyword);

        model.addAttribute("moduleCount", moduleService.getModuleCount(keyword));

        Map<String, Long> modulesPerCourse = moduleService.getModulesPerCourse(keyword);
        ObjectMapper objectMapper = new ObjectMapper();
        String modulesPerCourseJson = objectMapper.writeValueAsString(modulesPerCourse);
        model.addAttribute("modulesPerCourseJson", modulesPerCourseJson);

        return "main/modules";
    }

    /**
     * Отображает форму для создания нового модуля.
     
     * Доступ разрешен только пользователям с ролью ADMIN.
     * В модель добавляется список всех курсов для выбора при создании модуля.
     
     *
     * @param model модель для передачи данных в представление
     * @return имя представления "new/new_module"
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/new/new_module")
    public String newModule(Model model) {
        model.addAttribute("module", new Module());
        model.addAttribute("courses", courseService.getAllCourses(null));
        return "new/new_module";
    }

    /**
     * Сохраняет новый модуль в системе.
     
     * Доступ разрешен только пользователям с ролью ADMIN.
     * После сохранения перенаправляет на страницу со списком модулей.
     
     *
     * @param module объект модуля для сохранения
     * @return строка перенаправления на "/main/modules"
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save/save_module")
    public String saveModule(@ModelAttribute("module") Module module) {
        Integer courseId = module.getCourse().getId();
        Course course = courseService.getCourse(courseId);
        module.setCourse(course);
        moduleService.saveModule(module);
        return "redirect:/main/modules";
    }

    /**
     * Отображает форму для редактирования модуля.
     
     * Доступ разрешен только пользователям с ролью ADMIN.
     * В модель добавляется список всех курсов для выбора при редактировании модуля.
     
     *
     * @param id идентификатор редактируемого модуля
     * @return объект ModelAndView с данными модуля для представления "edit/edit_module"
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/edit/edit_module/{id}")
    public ModelAndView editModule(@PathVariable(name = "id") Integer id) {
        ModelAndView mav = new ModelAndView("edit/edit_module");
        Module module = moduleService.getModule(id);
        mav.addObject("module", module);
        mav.addObject("courses", courseService.getAllCourses(null));
        return mav;
    }

    /**
     * Удаляет модуль из системы.
     
     * Доступ разрешен только пользователям с ролью ADMIN.
     * После удаления перенаправляет на страницу со списком модулей.
     
     *
     * @param id идентификатор модуля для удаления
     * @return строка перенаправления на "/main/modules"
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/delete_module/{id}")
    public String deleteModule(@PathVariable Integer id) {
        moduleService.deleteModule(id);
        return "redirect:/main/modules";
    }
}