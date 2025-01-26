package com.example.onlineschool.controller;

import com.example.onlineschool.model.Student;
import com.example.onlineschool.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class StudentController {
    @Autowired
    private StudentService service;

    @GetMapping("/students")
    public String students(Model model, @Param("keyword") String keyword) {
        List<Student> studentList = service.getAllStudents(keyword);
        model.addAttribute("studentList", studentList);
        model.addAttribute("keyword", keyword);

        model.addAttribute("studentCount", service.getStudentCount(keyword));

        model.addAttribute("minCourseNumber", service.getMinCourseNumber(keyword));
        model.addAttribute("maxCourseNumber", service.getMaxCourseNumber(keyword));
        model.addAttribute("avgCourseNumber", service.getAverageCourseNumber(keyword));

        return "students";
    }

    @RequestMapping("/new_student")
    public String newStudent(Model model) {
        model.addAttribute("student", new Student());
        return "new_student";
    }

    @PostMapping("/save_student")
    public String saveStudent(@ModelAttribute("student") Student student) {
        service.saveStudent(student);
        return "redirect:/students";
    }

    @RequestMapping ("/edit_student/{id}")
    public ModelAndView editStudent(@PathVariable(name = "id") Integer id) {
        ModelAndView mav = new ModelAndView("edit_student");
        Student student = service.getStudent(id);
        mav.addObject("student", student);
        return mav;
    }

    @GetMapping("/delete_student/{id}")
    public String delete(@PathVariable Integer id) {
        service.deleteStudent(id);
        return "redirect:/students";
    }
}
