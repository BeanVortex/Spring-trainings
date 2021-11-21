package com.example.train6.AOP;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ControllerLog {

    @Before("execution(* login())")
    public void logLoggingPage() {
        log.info("Reached /login");
    }
}
