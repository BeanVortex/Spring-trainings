package com.example.train5.Repo;

import com.example.train5.Model.InstructorDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorDetailRepo extends JpaRepository<InstructorDetail, Long> {

}
