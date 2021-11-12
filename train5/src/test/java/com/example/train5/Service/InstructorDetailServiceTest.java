package com.example.train5.Service;

import static org.assertj.core.api.Assertions.assertThat;

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
class InstructorDetailServiceTest {

    private final InstructorDetailService service;

    @Autowired
    public InstructorDetailServiceTest(InstructorDetailService service) {
        this.service = service;
    }

    private static Long id;

    @Test
    @Order(1)
    void save() {
        var detail = new InstructorDetailModel();
        detail.setHobby("adsf");
        detail.setYoutubeChannel("https://youtube.com");
        var instructor = new InstructorModel();
        instructor.setEmail("dd@mail.com");
        instructor.setFirstName("first Name");
        instructor.setLastName("last Name");
        detail.setInstructor(instructor);
        var newDetail = service.save(detail);
        id = newDetail.getId();
        assertThat(newDetail).isNotNull();
    }

    @Test
    @Order(2)
    void get() {
        var fetchedInstructor = service.get(id);
        assertThat(fetchedInstructor).isNotNull();
    }
}