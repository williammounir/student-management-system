package com.Daredevil.studentmanagment.service;

import com.Daredevil.studentmanagment.dto.CourseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {

    CourseDTO createCourse(CourseDTO courseDTO);

    boolean existsByCode(String code);

    boolean existsByCourseCodeAndIdNot(String courseCode, Long Id);

    public Page<CourseDTO> getCourses(int page, int size);

    CourseDTO getCourseById(Long ID);

    CourseDTO updateCourse(Long id, CourseDTO courseDTO);

    List<CourseDTO> getAllCourses();

}
