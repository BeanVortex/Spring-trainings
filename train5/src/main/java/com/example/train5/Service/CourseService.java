package com.example.train5.Service;

import com.example.train5.Model.Course;
import com.example.train5.Repo.CourseRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepo repo;


    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Course save(Course course) {
        return repo.save(course);
    }

}
