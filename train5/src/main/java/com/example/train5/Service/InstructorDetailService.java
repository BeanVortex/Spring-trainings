package com.example.train5.Service;

import com.example.train5.Model.InstructorDetailModel;
import com.example.train5.Model.InstructorModel;
import com.example.train5.Repo.InstructorDetailRepo;
import com.example.train5.Repo.InstructorRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InstructorDetailService {
    private final InstructorDetailRepo repo;

    public InstructorDetailModel save(InstructorDetailModel instructor) {
        return repo.save(instructor);
    }

    public InstructorDetailModel get(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
