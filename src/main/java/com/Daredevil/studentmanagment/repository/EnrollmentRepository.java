package com.Daredevil.studentmanagment.repository;

import com.Daredevil.studentmanagment.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDateTime;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    @Query("""
            select count(distinct e.student.id) from
            Enrollment e
            where e.enrolledDate between :startDate and :endDate
            """)
    long CountDistinctStudentByEnrollDateBetween(@Param("startDate") LocalDateTime startDate,
                                                 @Param("endDate") LocalDateTime endDate);
}
