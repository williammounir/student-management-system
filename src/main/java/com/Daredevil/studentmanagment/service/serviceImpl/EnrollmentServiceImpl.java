package com.Daredevil.studentmanagment.service.serviceImpl;

import com.Daredevil.studentmanagment.dto.CourseDTO;
import com.Daredevil.studentmanagment.dto.EnrollmentDTO;
import com.Daredevil.studentmanagment.dto.EnrollmentSummaryDTO;
import com.Daredevil.studentmanagment.model.Courses;
import com.Daredevil.studentmanagment.model.Enrollment;
import com.Daredevil.studentmanagment.model.Students;
import com.Daredevil.studentmanagment.repository.CourseRepository;
import com.Daredevil.studentmanagment.repository.EnrollmentRepository;
import com.Daredevil.studentmanagment.repository.StudentRepository;
import com.Daredevil.studentmanagment.service.EnrollmentService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper mapper;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository, StudentRepository studentRepository, CourseRepository courseRepository, ModelMapper modelMapper){
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.mapper = modelMapper;
    }



    @Override
    public void enrollStudentToCourses(EnrollmentDTO enrollmentDTO) {

        Students student = studentRepository.findById(enrollmentDTO.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        for(Long courseId : enrollmentDTO.getCourseIds()){
            Courses course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course not found"));

            if(enrollmentRepository.existsByStudentIdAndCourseId(student.getId(), courseId)){
                continue;
            }
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);

            student.getEnrollments().add(enrollment);
            course.getEnrollments().add(enrollment);

            enrollmentRepository.save(enrollment);
        }
    }

    @Override
    public Page<EnrollmentSummaryDTO> getEnrolledStudents(int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return studentRepository.findEnrolledStudents(pageRequest)
                .map(student ->{
                        EnrollmentSummaryDTO dto = new EnrollmentSummaryDTO();
                        dto.setStudentId(student.getId());
                        dto.setStudentName(student.getFirstName() + " " + student.getLastName());
                        dto.setEmail(student.getEmail());
                        dto.setCourseCount(student.getEnrollments().size());
                        BigDecimal totalFee = student.getEnrollments().stream().map(enrollmentRepository -> enrollmentRepository.getCourse().getFee()).filter(fee -> fee != null).reduce(BigDecimal.ZERO, BigDecimal::add);
                        dto.setTotalFee(totalFee);

                        return dto;
                        }
                        );
    }

    @Override
    public EnrollmentSummaryDTO findEnrolledStudentCourseDetails(Long studentId) {


        return studentRepository.findEnrolledStudentCourseDetails(studentId)
                .map(student -> {
                    EnrollmentSummaryDTO dto = new EnrollmentSummaryDTO();
                    dto.setStudentId(student.getId());
                    dto.setStudentName(student.getFirstName() + " " + student.getLastName());
                    dto.setEmail(student.getEmail());
                    dto.setCourseCount(student.getEnrollments().size());
                    BigDecimal totalFee = student.getEnrollments().stream().map(enrollmentRepository -> enrollmentRepository.getCourse().getFee()).filter(fee -> fee != null).reduce(BigDecimal.ZERO, BigDecimal::add);
                    dto.setTotalFee(totalFee);

                    List<CourseDTO> courseList = student.getEnrollments().stream()
                            .map(enrollment -> enrollment.getCourse())
                            .map(course -> mapper.map(course, CourseDTO.class))
                            .collect(Collectors.toList());

                    dto.setCourseList(courseList);
                    return dto;
                })
                .orElseThrow(() -> new RuntimeException("Student Not Found"));
    }

    @Override
    public List<EnrollmentSummaryDTO> getRecentEnrolledStudents() {

        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));
        return studentRepository.findEnrolledStudents(pageRequest)
                .map(student ->{
                            EnrollmentSummaryDTO dto = new EnrollmentSummaryDTO();
                            dto.setStudentId(student.getId());
                            dto.setStudentName(student.getFirstName() + " " + student.getLastName());
                            dto.setEmail(student.getEmail());
                            dto.setCourseCount(student.getEnrollments().size());
                            BigDecimal totalFee = student.getEnrollments().stream().map(enrollmentRepository -> enrollmentRepository.getCourse().getFee()).filter(fee -> fee != null).reduce(BigDecimal.ZERO, BigDecimal::add);
                            dto.setTotalFee(totalFee);

                            return dto;
                        }
                ).getContent();
    }
}
