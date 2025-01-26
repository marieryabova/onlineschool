package com.example.onlineschool.controller;

import com.example.onlineschool.model.Teacher;
import com.example.onlineschool.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TeacherController {
    @Autowired
    private TeacherService service;

    @GetMapping("/teachers")
    public String teachers(Model model, @Param("keyword") String keyword) {
        List<Teacher> teacherList = service.getAllTeachers(keyword);
        model.addAttribute("teacherList", teacherList);
        model.addAttribute("keyword", keyword);

        model.addAttribute("teacherCount", service.getTeacherCount(keyword));

        model.addAttribute("minExperience", service.getMinExperience(keyword));
        model.addAttribute("maxExperience", service.getMaxExperience(keyword));
        model.addAttribute("avgExperience", service.getAverageExperience(keyword));

        return "teachers";
    }

    @RequestMapping("/new_teacher")
    public String newTeacher(Model model) {
        model.addAttribute("teacher", new Teacher());
        return "new_teacher";
    }

    @PostMapping("/save_teacher")
    public String saveTeacher(@ModelAttribute("teacher") Teacher teacher) {
        service.saveTeacher(teacher);
        return "redirect:/teachers";
    }

    @RequestMapping ("/edit_teacher/{id}")
    public ModelAndView editTeacher(@PathVariable(name = "id") Integer id) {
        ModelAndView mav = new ModelAndView("edit_teacher");
        Teacher teacher = service.getTeacher(id);
        mav.addObject("teacher", teacher);
        return mav;
    }

    @GetMapping("/delete_teacher/{id}")
    public String delete(@PathVariable Integer id) {
        service.deleteTeacher(id);
        return "redirect:/teachers";
    }
}
