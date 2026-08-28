package com.Daredevil.studentmanagment.controller;

import com.Daredevil.studentmanagment.dto.CourseDTO;
import com.Daredevil.studentmanagment.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/courses/")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }
    @GetMapping("/list")
    public String showCoursesList(Model model, @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "3") int size, @RequestParam(value = "message", required = false) String message) {
        Page<CourseDTO> courses = courseService.getCourses(page, size);
        model.addAttribute("courses", courses);
        model.addAttribute("message",message);
        return "courses";
    }

    @GetMapping("/new")
    public String showCreateCourse(Model model) {
        model.addAttribute("courseDTO", new CourseDTO());
        return "add-course";
    }


    @PostMapping
    public String createCourse(@Valid @ModelAttribute("courseDTO") CourseDTO courseDTO,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            return "add-course";
        }

        if(courseService.existsByCode(courseDTO.getCourseCode())){
            bindingResult.rejectValue(
                    "courseCode",
                    "duplicate",
                    "Code must be unique"
            );

            return "add-course";
        }
        courseService.createCourse(courseDTO);

        redirectAttributes.addAttribute("message", "Course is created successfully");
        return "redirect:/courses/list";
    }

    @GetMapping("/{id}")
    public String getCourseById(@PathVariable Long id, Model model){

        CourseDTO course = courseService.getCourseById(id);
        model.addAttribute("course", course);

        return "view-course";
    }

    @GetMapping("/{id}/edit")
    public String editCourse(@PathVariable Long id, Model model){
        CourseDTO course = courseService.getCourseById(id);
        model.addAttribute("course", course);

        return "edit-course";
    }

    @PostMapping("/{id}/update")
    public String updateCourse(@PathVariable Long id, @Valid @ModelAttribute("courseDTO") CourseDTO courseDTO,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            return "edit-course";
        }

        if(courseService.existsByCourseCodeAndIdNot(courseDTO.getCourseCode(), id)){
            bindingResult.rejectValue(
                    "courseCode",
                    "duplicate",
                    "Code must be unique"
            );

            return "edit-course";
        }
        courseService.updateCourse(id,courseDTO);

        redirectAttributes.addAttribute("message", courseDTO.getCourseCode()+" Course is Updated Successfully!");
        return "redirect:/courses/list";

    }

}
