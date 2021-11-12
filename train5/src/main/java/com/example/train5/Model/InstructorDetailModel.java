package com.example.train5.Model;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "instructor_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstructorDetailModel {

    @Id
    @GeneratedValue
    private Long id;
    private String youtubeChannel;
    private String hobby;

    @OneToOne(mappedBy = "instructorDetail", cascade = CascadeType.ALL)
    private InstructorModel instructor;
}
