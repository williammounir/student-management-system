package com.Daredevil.studentmanagment.service.serviceImpl;

import com.Daredevil.studentmanagment.dto.EnrollmentSummaryDTO;
import com.Daredevil.studentmanagment.dto.StudentDTO;
import com.Daredevil.studentmanagment.model.Students;
import com.Daredevil.studentmanagment.repository.StudentRepository;
import com.Daredevil.studentmanagment.service.StudentService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper mapper;

    public StudentServiceImpl(StudentRepository studentRepository, ModelMapper mapper){
        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean existsByEmailIgnoreCase(String email) {
        return studentRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        Students students = mapper.map(studentDTO, Students.class);
        Students saved = studentRepository.save(students);

        return mapper.map(saved, StudentDTO.class);
    }

    @Override
    public Page<StudentDTO> getStudents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        return studentRepository.findByActiveTrue(pageable)
                .map(student -> mapper.map(student, StudentDTO.class));

    }

    @Override
    public StudentDTO getStudentById(Long ID) {
        Students student = studentRepository.findById(ID).orElseThrow(() -> new RuntimeException("Student with id: "+ID+" doesn't exist"));
        StudentDTO studentDTO = mapper.map(student,StudentDTO.class);

        return studentDTO;
    }

    @Override
    public boolean existsByEmailAndIdNot(String email, Long id) {
        return studentRepository.existsByEmailAndIdNot(email, id);
    }

    @Override
    public StudentDTO updateStudent(Long ID, StudentDTO studentDTO) {
        //gets the course and also checks whether it actually exists
        Students student = studentRepository.findById(ID).orElseThrow(() -> new RuntimeException("Student with id: "+ID +" doesn't exist"));



        mapper.map(studentDTO, student);

        Students updated = studentRepository.save(student);

        return mapper.map(updated, StudentDTO.class);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findByActiveTrue(Sort.by("firstName")).stream().map(student -> mapper.map(student, StudentDTO.class)).collect(Collectors.toList());
    }



}
