package com.example.train4.Annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CourseCodeConstraintValidator implements ConstraintValidator<CourseCode, String> {


    private String coursePrefix;

    @Override
    public void initialize(CourseCode courseCode) {
        coursePrefix = courseCode.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return value.startsWith(coursePrefix);
    }
}
