package com.Daredevil.studentmanagment.service;


import com.Daredevil.studentmanagment.dto.EnrollmentDTO;
import com.Daredevil.studentmanagment.dto.EnrollmentSummaryDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EnrollmentService {

    void enrollStudentToCourses(EnrollmentDTO enrollmentDTO);

    Page<EnrollmentSummaryDTO> getEnrolledStudents(int page, int size);

    EnrollmentSummaryDTO findEnrolledStudentCourseDetails(Long studentId);

    List<EnrollmentSummaryDTO> getRecentEnrolledStudents();
}
