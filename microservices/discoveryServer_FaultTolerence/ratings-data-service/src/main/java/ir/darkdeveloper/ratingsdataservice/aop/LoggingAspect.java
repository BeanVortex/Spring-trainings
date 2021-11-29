package ir.darkdeveloper.ratingsdataservice.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("execution(* getRatings(..))")
    private void logGetMovieInfo() {
        log.info("Getting ratings");
    }
}
