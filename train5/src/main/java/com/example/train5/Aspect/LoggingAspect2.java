package com.example.train5.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.mapping.Join;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;

@Aspect
@Slf4j
@Component
@Order(3)
public class LoggingAspect2 {

    @Pointcut("execution(public void doNothing(..))")
    public void forModelPackageSetters() {
    }

    @Before("forModelPackageSetters()")
    public void beforeDoNothing(JoinPoint joinPoint) {
        var methodSignature = (MethodSignature) joinPoint.getSignature();
        var param = joinPoint.getArgs()[0];
        System.out.println("getArgs: " + Arrays.asList(joinPoint.getArgs()));
        System.out.println("getSignature.getDeclaringTypeName: " + methodSignature.getDeclaringTypeName());
        System.out.println("getSignature.getName: " + methodSignature.getName());
        System.out.println("getSignature.getMethod: " + methodSignature.getMethod());
        System.out.println("getSignature.getModifiers: " + methodSignature.getModifiers());
        System.out.println("getKind: " + joinPoint.getKind());
        System.out.println("getTarget: " + joinPoint.getTarget());
        System.out.println("getSourceLocation: " + joinPoint.getSourceLocation());
        System.out.println("getStaticPart: " + joinPoint.getStaticPart());
        System.out.println("getThis: " + joinPoint.getThis());
        log.info("Order 3 : beforeModelPackageSetters " + param);
        System.out.println();
    }

    @AfterReturning(value = "execution(* returnString(..))", returning = "result")
    public void afterReturnString(JoinPoint joinPoint, List<String> result) {
        var method = joinPoint.getSignature().toShortString();
        log.info("After returning on method: " + method + ", result: " + result);
        // abstract list is immutable
        // every return types from methods are immutable
        if (!(result instanceof AbstractList))
            result.add("ghf");

    }

    @AfterReturning(pointcut = "execution(* returnNothing(..))", returning = "result")
    public void afterReturnNothing(JoinPoint joinPoint, Object result) {
        var method = joinPoint.getSignature().toShortString();
        log.info("After returning on method: " + method + "result: " + result);
    }

    @AfterThrowing(pointcut = "execution(* throwException(..))", throwing = "exception")
    public void afterThrowException(JoinPoint joinPoint, Throwable exception) {
        log.error("Exception on method: " + joinPoint.toShortString() + " \nException: " + exception.getMessage());
    }


    // whether method fails or not it will always run for matching methods
    // acts like finally block in try catch
    @After("execution(* afterAnn*(..))")
    public void after(JoinPoint joinPoint) {
        log.warn("ran method : " + joinPoint.getSignature().toShortString());
    }

    // use case for example:  Profiling code, checking how long does it take to execute
    // runs before and after method execution
    @Around("execution(* simpleThreadMethod(..))")
    public Object aroundPrintMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long begin = System.currentTimeMillis();
        System.out.println("before proceeding");
        var result = proceedingJoinPoint.proceed();
        System.out.println("after proceeding");
        long end = System.currentTimeMillis();
        long dur = end - begin;
        log.info("Took " + dur + "ms to execute " + proceedingJoinPoint.getSignature().toShortString());
        return result;
    }
}
