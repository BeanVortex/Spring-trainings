package ir.darkdeveloper.springbootconfig.controller;

import ir.darkdeveloper.springbootconfig.config.DBSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class GreetingController {

    @Value("${app.sad: default val}")
    private String greet;

    @Value("${app.list}")
    private List<String> list;

    @Value("#{${app.map}}")
    private Map<String, String> map;

    private final DBSettings dbSettings;

    @GetMapping("/greeting")
    public String greet(){
        return greet + " " + list + " " + map + " " + dbSettings;
    }
}
