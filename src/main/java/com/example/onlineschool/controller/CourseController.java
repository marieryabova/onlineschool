package com.example.onlineschool.controller;

import com.example.onlineschool.model.Course;
import com.example.onlineschool.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CourseController {
    @Autowired
    private CourseService service;

    @GetMapping("/courses")
    public String courses(Model model, @Param("keyword") String keyword,
                          @Param("showStatistics") Boolean showStatistics) {
        List<Course> courseList = service.getAllCourses(keyword);
        model.addAttribute("courseList", courseList);
        model.addAttribute("keyword", keyword);

        model.addAttribute("courseCount", service.getCourseCount(keyword));

        model.addAttribute("minPrice", service.getMinPrice(keyword));
        model.addAttribute("maxPrice", service.getMaxPrice(keyword));
        model.addAttribute("avgPrice", service.getAvgPrice(keyword));
        model.addAttribute("sumPrice", service.getSumPrice(keyword));

        model.addAttribute("minDuration", service.getMinDuration(keyword));
        model.addAttribute("maxDuration", service.getMaxDuration(keyword));
        model.addAttribute("avgDuration", service.getAvgDuration(keyword));
        model.addAttribute("sumDuration", service.getSumDuration(keyword));

        model.addAttribute("minStudents", service.getMinStudents(keyword));
        model.addAttribute("maxStudents", service.getMaxStudents(keyword));
        model.addAttribute("avgStudents", service.getAvgStudents(keyword));
        model.addAttribute("sumStudents", service.getSumStudents(keyword));

        return "courses";
    }

    @RequestMapping("/new_course")
    public String newCourse(Model model) {
        model.addAttribute("course", new Course());
        return "new_course";
    }

    @PostMapping("/save_course")
    public String saveCourse(@ModelAttribute("course") Course course) {
        service.saveCourse(course);
        return "redirect:/courses";
    }

    @RequestMapping ("/edit_course/{id}")
    public ModelAndView editCourse(@PathVariable(name = "id") Integer id) {
        ModelAndView mav = new ModelAndView("edit_course");
        Course course = service.getCourse(id);
        mav.addObject("course", course);
        return mav;
    }

    @GetMapping("/delete_course/{id}")
    public String delete(@PathVariable Integer id) {
        service.deleteCourse(id);
        return "redirect:/courses";
    }
}
