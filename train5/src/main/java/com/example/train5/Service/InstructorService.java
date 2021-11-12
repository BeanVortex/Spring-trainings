package com.example.train5.Service;

import com.example.train5.Model.InstructorModel;
import com.example.train5.Repo.InstructorRepo;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InstructorService {

    private final InstructorRepo repo;

    public InstructorModel save(InstructorModel instructor) {
        return repo.save(instructor);
    }

    public InstructorModel get(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
