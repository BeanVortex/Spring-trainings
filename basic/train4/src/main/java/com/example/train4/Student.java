package com.example.train4;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

@Data
public class Student {
    private String firstName;
    private String lastName;
    private String country;
    private String favLang;
    private String[] os;
}
