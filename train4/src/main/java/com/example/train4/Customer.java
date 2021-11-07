package com.example.train4;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class Customer {
    @NotNull
    private String firstName;
    @NotEmpty(message = "is required")
    private String lastName;
    @Min(value = 1, message = "number cant be less than 1 digit")
    @Min(value = 10, message = "number cant be more than 10 digit")
    private Integer freePass;
}
