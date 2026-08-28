package com.Daredevil.studentmanagment.repository;

import com.Daredevil.studentmanagment.model.Students;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Students, Long> {

    boolean existsByEmailIgnoreCase(String email);

    Page<Students> findByActiveTrue(Pageable pageable);

    boolean existsByEmailAndIdNot(String email, Long id);

    List<Students> findByActiveTrue(Sort sort);

    @EntityGraph(attributePaths = {"enrollments", "enrollments.course"})
    @Query(value = """
            select distinct s
            from Students s
            join s.enrollments e
     """,
    countQuery = """
            select count(distinct s)
            from Students s
            join s.enrollments e
            """)
    Page<Students> findEnrolledStudents(Pageable pageable);

    @Query("""
            select s
            from Students s
            join fetch s.enrollments e
            join fetch e.course c
            where s.id = :id
            """)
    Optional<Students> findEnrolledStudentCourseDetails(@RequestParam("id") long id);
}
