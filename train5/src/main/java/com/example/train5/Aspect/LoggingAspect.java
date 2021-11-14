package com.example.train5.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    // Aspects will not work on entity classes because they won't get injected
    //Spring AOP only works for Spring managed beans. Hibernate entities are not Spring managed beans, so Spring AOP doesn't apply to them.

    @Pointcut("execution(public com.example.train5.Model.Instructor get(Long))")
    private void forGetInstructor() {
    }

    @Pointcut("execution(public com.example.train5.Model.Instructor save(*))")
    private void forSaveInstructor() {
    }


    @Before("forGetInstructor() || forSaveInstructor()")
    public void beforeGetInstructor() {
        log.error("RUNNING ASPECT: beforeGetOrSaveInstructor");
    }


    @Before("execution(* saveS*(*))")
    public void beforeEverySaveSMethod() {
        log.warn("RUNNING ASPECT: before save method");
    }

    @After("forGetInstructor()")
    public void AfterGetInstructor() {
        log.error("RUNNING ASPECT: afterGetInstructor");
    }

    @Before("execution(* delete(..))")
    public void beforeDeleteInstructor() {
        log.warn("RUNNING ASPECT: before delete method");
    }

    @Before("execution(public void callMethod())")
    public void beforeCallMethod() {
        log.warn("RUNNING ASPECT: before delete method");
    }

}
