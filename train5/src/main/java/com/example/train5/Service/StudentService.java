package com.example.train5.Service;

import com.example.train5.Model.Student;
import com.example.train5.Repo.StudentRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {
    private final StudentRepo repo;

    public Student saveStudent(Student student) {
        return repo.save(student);
    }


    public void doNothing(long dd) {
        System.out.println(dd);
    }

    public List<String> returnNothing(){
        return Arrays.asList("fads","sadf","adsf");
    }
}
