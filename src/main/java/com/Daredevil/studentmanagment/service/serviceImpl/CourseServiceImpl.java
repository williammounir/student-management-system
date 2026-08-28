package com.Daredevil.studentmanagment.service.serviceImpl;

import com.Daredevil.studentmanagment.dto.CourseDTO;
import com.Daredevil.studentmanagment.model.Courses;
import com.Daredevil.studentmanagment.repository.CourseRepository;
import com.Daredevil.studentmanagment.service.CourseService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper mapper;
    private static final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);

    CourseServiceImpl(CourseRepository courseRepository, ModelMapper mapper){
        this.courseRepository = courseRepository;
        this.mapper = mapper;
    }


    @Override
    public CourseDTO createCourse(CourseDTO courseDTO) {
        Courses courses = mapper.map(courseDTO, Courses.class);
        courseRepository.save(courses);
        //we used map rather than just returning the courseDTO directly because courseDTO is missing id
        return mapper.map(courses, CourseDTO.class);
    }

    @Override
    public boolean existsByCode(String code) {
        return courseRepository.existsByCourseCodeIgnoreCase(code);
    }

    @Override
    public boolean existsByCourseCodeAndIdNot(String courseCode, Long id) {
        return courseRepository.existsByCourseCodeIgnoreCaseAndIdNot(courseCode, id);
    }

    @Override
    public Page<CourseDTO> getCourses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));


        return courseRepository.findByActiveTrue(pageable)
                .map(course -> mapper.map(course, CourseDTO.class));

    }



    @Override
    public CourseDTO getCourseById(Long ID) {
        Courses course = courseRepository.findById(ID).orElseThrow(() -> new RuntimeException("Course with id: "+ID +" doesn't exist"));
        CourseDTO courseDTO = mapper.map(course,CourseDTO.class);

        return courseDTO;
    }

    @Override
    public CourseDTO updateCourse(Long ID, CourseDTO courseDTO) {
        //gets the course and also checks whether it actually exists
        Courses course = courseRepository.findById(ID).orElseThrow(() -> new RuntimeException("Course with id: "+ID +" doesn't exist"));



        mapper.map(courseDTO, course);

        Courses updated = courseRepository.save(course);

        return mapper.map(updated, CourseDTO.class);
    }


    @Override
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findByActiveTrue(Sort.by("courseName")).stream().map(course -> mapper.map(course,CourseDTO.class)).collect(Collectors.toList());
    }


}
