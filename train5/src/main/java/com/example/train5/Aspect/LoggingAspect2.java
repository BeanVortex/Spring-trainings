package com.example.train5.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
@Order(3)
public class LoggingAspect2 {

    @Pointcut("execution(public void doNothing(..))")
    public void forModelPackageSetters() {
    }

    @Before("forModelPackageSetters()")
    public void beforeModelPackageSetters(JoinPoint joinPoint) {
        var methodSignature = (MethodSignature) joinPoint.getSignature();
        var param = joinPoint.getArgs()[0];
        log.info("Order 3 : beforeModelPackageSetters " + param);
    }
}
