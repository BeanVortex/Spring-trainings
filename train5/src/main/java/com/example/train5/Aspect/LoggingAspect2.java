package com.example.train5.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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

//    @Before("forModelPackageSetters()")
    public void beforeDoNothing(JoinPoint joinPoint) {
        var methodSignature = (MethodSignature) joinPoint.getSignature();
        var param = joinPoint.getArgs()[0];
        System.out.println("getArgs: " + Arrays.asList(joinPoint.getArgs()));
        System.out.println("getSignature.getDeclaringTypeName: " + joinPoint.getSignature().getDeclaringTypeName());
        System.out.println("getSignature.getName: " + joinPoint.getSignature().getName());
        System.out.println("getSignature.getMethod: " + ((MethodSignature) joinPoint.getSignature()).getMethod());
        System.out.println("getSignature.getModifiers: " + joinPoint.getSignature().getModifiers());
        System.out.println("getKind: " + joinPoint.getKind());
        System.out.println("getTarget: " + joinPoint.getTarget());
        System.out.println("getSourceLocation: " + joinPoint.getSourceLocation());
        System.out.println("getStaticPart: " + joinPoint.getStaticPart());
        System.out.println("getThis: " + joinPoint.getThis());
        log.info("Order 3 : beforeModelPackageSetters " + param);
    }

    @AfterReturning(value = "execution(* returnNothing(..))", returning = "result")
    public void afterReturnNothing(JoinPoint joinPoint, List<String> result) {
        var method = joinPoint.getSignature().toShortString();
        log.info("After returning on method: " + method + "result: " + result);
    }
}
