package com.Daredevil.studentmanagment.service.serviceImpl;

import com.Daredevil.studentmanagment.dto.DashboardStatsDTO;
import com.Daredevil.studentmanagment.repository.CourseRepository;
import com.Daredevil.studentmanagment.repository.EnrollmentRepository;
import com.Daredevil.studentmanagment.repository.StudentRepository;
import com.Daredevil.studentmanagment.service.DashboardService;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;


    public DashboardServiceImpl(EnrollmentRepository enrollmentRepository, StudentRepository studentRepository, CourseRepository courseRepository){
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public DashboardStatsDTO getDashboardStats() {

        long totalStudents = studentRepository.count();
        long totalCourses = courseRepository.count();

        String topPerformingCourse = getTopPerformingCourse();

        YearMonth currentMonth = YearMonth.now();
        LocalDateTime startDate = currentMonth.atDay(1).atStartOfDay();
        LocalDateTime endDate = currentMonth.atEndOfMonth().atTime(LocalTime.MAX);

        long studentEnrolledThisMonth = enrollmentRepository.CountDistinctStudentByEnrollDateBetween(startDate, endDate);

        DashboardStatsDTO dashboardStatsDTO = new DashboardStatsDTO();
        dashboardStatsDTO.setTotalStudents(totalStudents);
        dashboardStatsDTO.setTotalCourses(totalCourses);
        dashboardStatsDTO.setTopPerformingCourse(topPerformingCourse);
        dashboardStatsDTO.setStudentsEnrolledThisMonth(studentEnrolledThisMonth);

        return dashboardStatsDTO;
    }

    private String getTopPerformingCourse(){

        return enrollmentRepository.findAll().stream()
                .collect(Collectors.groupingBy(e -> e.getCourse().getCourseCode(),Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }
}
