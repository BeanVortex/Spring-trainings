package com.example.train5.Service;

import com.example.train5.Model.InstructorDetail;
import com.example.train5.Repo.InstructorDetailRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InstructorDetailService {
    private final InstructorDetailRepo repo;

    public InstructorDetail save(InstructorDetail instructor) {
        return repo.save(instructor);
    }

    public InstructorDetail get(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
