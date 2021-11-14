package com.example.train5.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LoggingAspect2 {

    @Pointcut("execution(public void doNothing())")
    public void forModelPackageSetters(){}

    @Before("forModelPackageSetters()")
    public void beforeModelPackageSetters(){
        log.info("beforeModelPackageSetters");
    }
}
