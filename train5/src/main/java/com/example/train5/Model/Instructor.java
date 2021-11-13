package com.example.train5.Model;

import javax.persistence.*;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "instructor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Instructor {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private InstructorDetail instructorDetail;

    @OneToMany(mappedBy = "instructor", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH})
    private List<Course> courses;

    public void addCourse(Course course) {
        if (courses == null)
            courses = new ArrayList<>();
        course.setInstructor(this);
        courses.add(course);
    }
}
