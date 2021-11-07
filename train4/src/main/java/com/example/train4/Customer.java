package com.example.train4;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class Customer {

    private String firstName;

    @NotEmpty(message = "is required")
    private String lastName;

    @NotNull(message = "can't be empty")
    @Min(value = 1, message = "number cant be less than 1 ")
    @Max(value = 10, message = "number cant be more than 10 ")
    private Integer freePass;
}
