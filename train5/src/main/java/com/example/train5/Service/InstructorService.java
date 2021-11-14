package com.example.train5.Service;

import com.example.train5.Model.Instructor;
import com.example.train5.Repo.InstructorRepo;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InstructorService {

    private final InstructorRepo repo;

    public Instructor save(Instructor instructor) {
        return repo.save(instructor);
    }

    public Instructor get(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public void callMethod(Long l) {
        System.out.println("Call method");
    }
}
