package com.example.train5.Service;

import static org.assertj.core.api.Assertions.assertThat;

// import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.train5.Model.InstructorDetailModel;
import com.example.train5.Model.InstructorModel;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext
class InstructorServiceTest {

    private final InstructorService service;
    private final InstructorDetailService service2;

    @Autowired
    public InstructorServiceTest(InstructorService service, InstructorDetailService service2) {
        this.service = service;
        this.service2 = service2;
    }

    private static Long id;

    @Test
    @Order(1)
    void save() {
        var instructor = new InstructorModel();
        instructor.setEmail("dd@mail.com");
        instructor.setFirstName("first Name");
        instructor.setLastName("last Name");
        var detail = new InstructorDetailModel();
        detail.setHobby("adsf");
        detail.setYoutubeChannel("https://youtube.com");
        instructor.setInstructorDetail(detail);
        var newInstructor = service.save(instructor);
        id = newInstructor.getId();
        assertThat(newInstructor).isNotNull();
    }

    @Test
    @Order(2)
    void get() {
        var fetchedInstructor = service2.get(2L);
        System.out.println(fetchedInstructor.getInstructor().getFirstName());
        assertThat(fetchedInstructor).isNotNull();
    }

    @Test
    @Order(3)
    void delete() {
        service.delete(id);
    }
    @Test
    @Order(4)
    void delete2() {
        service2.delete(2L);
    }

}
