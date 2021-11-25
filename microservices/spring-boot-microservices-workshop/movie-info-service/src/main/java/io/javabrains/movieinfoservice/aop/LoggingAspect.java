package io.javabrains.movieinfoservice.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @AfterReturning("execution(* getMovieInfo(..))")
    private void logGetMovieInfo() {
        log.info("Get info");
    }
}
