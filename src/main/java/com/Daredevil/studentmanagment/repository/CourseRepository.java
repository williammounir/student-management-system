package com.Daredevil.studentmanagment.repository;

import com.Daredevil.studentmanagment.model.Courses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Courses, Long> {

    boolean existsByCourseCodeIgnoreCase(String code);
    boolean existsByCourseCodeIgnoreCaseAndIdNot(String courseCode, Long Id);

    Page<Courses> findByActiveTrue(Pageable pageable);

    List<Courses> findByActiveTrue(Sort sort);
}
