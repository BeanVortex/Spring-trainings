package com.example.train5.Service;

import com.example.train5.Model.Student;
import com.example.train5.Repo.StudentRepo;
import javassist.tools.web.BadHttpRequest;
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

    public List<String> returnString() {
        return Arrays.asList("fads", "sadf", "adsf");
    }

    public void returnNothing() {
    }

    public void throwException() throws Exception {
        throw new Exception("Bad request exception 400");
    }

    public void afterAnnException() throws Exception {
        throw new Exception("Bad request exception 400");
    }

    public void afterAnnNothing() {

    }

    public void simpleThreadMethod() throws InterruptedException {
        System.out.println("proceeding");
        Thread.sleep(2500);
    }
}
