package com.example.train5.Model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    private String review;
}
