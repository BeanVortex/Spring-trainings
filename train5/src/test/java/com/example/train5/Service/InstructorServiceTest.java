package com.example.train5.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

// import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.train5.Model.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext
class InstructorServiceTest {

    private final InstructorService service;
    private final InstructorDetailService detailService;
    private final CourseService courseService;
    private final StudentService studentService;

    private static Long courseId;

    @Autowired
    public InstructorServiceTest(InstructorService service, InstructorDetailService detailService,
                                 CourseService courseService, StudentService studentService) {
        this.service = service;
        this.detailService = detailService;
        this.courseService = courseService;
        this.studentService = studentService;
    }

    private static Long id;

    @Test
    @Order(1)
    void saveInstructorAndDetailCascading() {
        var instructor = new Instructor();
        instructor.setEmail("dd@mail.com");
        instructor.setFirstName("first Name");
        instructor.setLastName("last Name");

        var detail = new InstructorDetail();
        detail.setHobby("adsf");
        detail.setYoutubeChannel("https://youtube.com");
        instructor.setInstructorDetail(detail);

        service.save(instructor);
        id = instructor.getId();
    }

    @Test
    @Order(2)
    void saveCourseAndReviewCascading() {
        var course = new Course();
        course.setTitle("fasd");
        var instructor = new Instructor();
        instructor.setId(id);
        course.setInstructor(instructor);
        var review = new Review();
        review.setReview("review for a course");
        course.addReview(review);
        courseService.save(course);
        courseId = course.getId();
    }

    @Test
    @Order(3)
    void saveStudentForCourse() {
        var student = new Student();
        student.setFirstName("Dark");
        student.setLastName("Developer");
        student.setEmail("dark_developer@outlook.com");
        var tmpCourse = new Course();
        tmpCourse.setId(courseId);
        student.addCourse(tmpCourse);
        studentService.saveStudent(student);
    }

    @Test
    @Order(4)
    void getDetail() {
        var fetchedInstructor = detailService.get(2L);
        System.out.println(fetchedInstructor);
        assertThat(fetchedInstructor).isNotNull();
    }

    @Test
    @Order(5)
    void getInstructor() {
        var fetchedInstructor = service.get(id);
        assertThat(fetchedInstructor).isNotNull();
        fetchedInstructor.doNothing();
    }

    @Test
    @Order(6)
    void getCourses() {
        var fetchedInstructor = service.get(id);
        fetchedInstructor.getCourses().forEach(System.out::println);
        courseId = fetchedInstructor.getCourses().get(0).getId();
        assertThat(fetchedInstructor).isNotNull();
    }

    @Test
    @Order(7)
    @Disabled
    void delete() {
        service.delete(id);
    }

    @Test
    @Order(8)
    @Disabled
    void deleteDetail() {
        detailService.delete(2L);
    }


    @Test
    @Order(9)
    @Disabled
    void deleteCourse() {
        courseService.delete(courseId);
    }


    @Test
    @Order(10)
    void aopTest() {
        service.callMethod(5L);
        studentService.doNothing(56L);
        var instructor = new Instructor();
        instructor.doNothing();
        instructor.setId(8L);
        studentService.returnNothing();
    }

}
