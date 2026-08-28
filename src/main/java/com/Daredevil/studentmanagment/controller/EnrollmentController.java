package com.Daredevil.studentmanagment.controller;

import com.Daredevil.studentmanagment.dto.EnrollmentDTO;
import com.Daredevil.studentmanagment.dto.EnrollmentSummaryDTO;
import com.Daredevil.studentmanagment.service.CourseService;
import com.Daredevil.studentmanagment.service.EnrollmentService;
import com.Daredevil.studentmanagment.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final CourseService courseService;
    private final StudentService studentService;
    private final EnrollmentService enrollmentService;

    public EnrollmentController(CourseService courseService, StudentService studentService, EnrollmentService enrollmentService){
        this.courseService = courseService;
        this.studentService = studentService;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/showEnroll")
    public String showEnrollment(Model model) {

        model.addAttribute("enrollmentDTO", new EnrollmentDTO());
        model.addAttribute("courseList", courseService.getAllCourses());
        model.addAttribute("studentList", studentService.getAllStudents());
        return "enroll-course";
    }

    @GetMapping("/enrollmentList")
    public String enrollmentList(Model model, @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "3") int size, @RequestParam(value = "message", required = false) String message) {

        Page<EnrollmentSummaryDTO> students = enrollmentService.getEnrolledStudents(page, size);
        model.addAttribute("students", students);
        model.addAttribute("message", message);

        return "enrolled-students";
    }

    @PostMapping("/enrollCourse")
    public String enrollCourse(@Valid @ModelAttribute("enrollmentDTO") EnrollmentDTO enrollmentDTO,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            model.addAttribute("courseList", courseService.getAllCourses());
            model.addAttribute("studentList", studentService.getAllStudents());
            return "enroll-course";
        }


        enrollmentService.enrollStudentToCourses(enrollmentDTO);

        redirectAttributes.addAttribute("message", "Enrollment happened successfully");
        return "redirect:/enrollments/enrollmentList";
    }

    @GetMapping("/getStudentEnrollmentDetails/{id}")
    public String getStudentEnrollmentDetails(@PathVariable Long id, Model model, @RequestParam(defaultValue = "enrollments") String source) {

        EnrollmentSummaryDTO enrollmentSummaryDTO = enrollmentService.findEnrolledStudentCourseDetails(id);
        model.addAttribute("enrollmentSummaryDTO", enrollmentSummaryDTO);
        model.addAttribute("source", source);
        return "enrolled-details";
    }

}
