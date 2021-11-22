package com.example.train6.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
public class DemoController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/leaders")
    public String leaders() {
        return "leaders";
    }

    @GetMapping("/systems")
    public String systems() {
        return "systems";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "accessDenied";
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest req) {
        try {
            req.logout();
        } catch (ServletException e) {
            log.error(e.getMessage());
        }
    }

    @GetMapping("/ip")
    @ResponseBody
    public String getIp(HttpServletRequest request) {
        log.info("Remote adr: " + request.getRemoteAddr());
        log.info("Remote Host: " + request.getRemoteHost());
        log.info("Local adr: " + request.getLocalAddr());
        log.info("X-FORWARDED-FOR: " + request.getHeader("X-FORWARDED-FOR"));
        return request.getRemoteHost();
    }


}
