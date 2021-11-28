package com.example.train4;

import com.example.train4.Annotations.CourseCode;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class Customer {

    @NotEmpty(message = "required")
    private String firstName;

    @NotEmpty(message = "is required")
    private String lastName;

    @NotNull(message = "can't be empty")
    @Min(value = 1, message = "number cant be less than 1 ")
    @Max(value = 10, message = "number cant be more than 10 ")
    private Integer freePass;

    @Pattern(regexp = "^[a-zA-Z0-9]{5}", message = "only 5 chars/digits")
    @NotEmpty(message = "is required")
    private String postalCode;

    @CourseCode
    private String courseCode;
}
