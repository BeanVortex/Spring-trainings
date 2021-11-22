package com.example.train7.service;

import javax.annotation.PostConstruct;

import com.example.train7.model.Student;
import com.example.train7.repo.StudentRepo;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepo repo;

    @PostConstruct
    public void saveStudent() {
        var st = new Student();
        st.setAge(645);
        st.setName("DarkDeveloper");
        repo.save(st);
    }

}
