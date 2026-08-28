package com.Daredevil.studentmanagment.controller;

import com.Daredevil.studentmanagment.dto.StudentDTO;
import com.Daredevil.studentmanagment.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/new")
    public String showCreateStudent(Model model){

        model.addAttribute("studentDTO", new StudentDTO());

        return "add-student";
    }

    @GetMapping("/list")
    public String showStudents(Model model, @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "3") int size, @RequestParam(value = "message", required = false) String message){

        Page<StudentDTO> students = studentService.getStudents(page, size);
        model.addAttribute("students", students);
        model.addAttribute("message",message);

        return "students";
    }

    @PostMapping
    public String createStudent(@Valid @ModelAttribute("studentDTO") StudentDTO studentDTO,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()){
            return "add-student";
        }

        if(studentService.existsByEmailIgnoreCase(studentDTO.getEmail())){
            bindingResult.rejectValue(
                    "email",
                    "duplicate",
                    "Email must be unique"
            );

            return "add-student";
        }
        studentService.createStudent(studentDTO);
        //look at
        redirectAttributes.addAttribute("message", "Student is created successfully");
        return "redirect:/students/list";
    }

    @GetMapping("/{id}")
    public String getStudentById(@PathVariable Long id, Model model){

        StudentDTO student = studentService.getStudentById(id);
        model.addAttribute("student", student);

        return "view-student";
    }

    @GetMapping("/{id}/edit")
    public String editStudent(@PathVariable Long id, Model model){

        StudentDTO student = studentService.getStudentById(id);
        model.addAttribute("studentDTO", student);

        return "edit-student";
    }

    @PostMapping("/{id}/update")
    public String updateStudent(@PathVariable Long id, @Valid @ModelAttribute("studentDTO") StudentDTO studentDTO,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            return "edit-student";
        }

        if(studentService.existsByEmailAndIdNot(studentDTO.getEmail(), id)){
            bindingResult.rejectValue(
                    "email",
                    "duplicate",
                    "email must be unique"
            );

            return "edit-email";
        }
        studentService.updateStudent(id,studentDTO);
        //look at
        redirectAttributes.addAttribute("message",  "Student is Updated Successfully!");
        return "redirect:/students/list";

    }
}
