package com.example.train5.Repo;

import com.example.train5.Model.InstructorDetailModel;
import com.example.train5.Model.InstructorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorDetailRepo extends JpaRepository<InstructorDetailModel, Long> {

}
