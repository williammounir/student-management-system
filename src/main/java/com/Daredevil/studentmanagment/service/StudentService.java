package com.Daredevil.studentmanagment.service;

import com.Daredevil.studentmanagment.dto.CourseDTO;
import com.Daredevil.studentmanagment.dto.EnrollmentSummaryDTO;
import com.Daredevil.studentmanagment.dto.StudentDTO;
import com.Daredevil.studentmanagment.model.Students;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {

    boolean existsByEmailIgnoreCase(String email);

    StudentDTO createStudent(StudentDTO studentDTO);

    Page<StudentDTO> getStudents(int page, int size);

    StudentDTO getStudentById(Long ID);

    boolean existsByEmailAndIdNot(String email, Long id);

    StudentDTO updateStudent(Long id, StudentDTO studentDTO);

    List<StudentDTO> getAllStudents();


}
