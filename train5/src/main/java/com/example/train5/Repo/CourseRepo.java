package com.example.train5.Repo;

import com.example.train5.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {

    @Modifying
    @Query("DELETE from Course c where c.id=?1")
    void deleteById(Long id);
}
