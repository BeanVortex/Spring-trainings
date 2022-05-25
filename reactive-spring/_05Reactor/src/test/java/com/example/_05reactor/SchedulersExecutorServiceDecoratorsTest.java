package com.example._05reactor;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class SchedulersExecutorServiceDecoratorsTest {

    private final AtomicInteger methodInvocationCounts = new AtomicInteger();

    private String rsb = "rsb";

    @BeforeEach
    void before() {
        Schedulers.resetFactory();
        Schedulers.addExecutorServiceDecorator(rsb,
                (scheduler, scheduledExecutorService) -> decorate(scheduledExecutorService));
    }


    @Test
    void onScheduleHook() {
        var counter = new AtomicInteger();
        Schedulers.onScheduleHook("my hook", runnable -> () -> {
            var threadName = Thread.currentThread().getName();
            counter.incrementAndGet();
            log.info("before execution: " + threadName);
            runnable.run();
            log.info("after execution: " + threadName);
        });
        Flux<Integer> integerFlux = Flux.just(1, 2, 3)
                .delayElements(Duration.ofMillis(1)).subscribeOn(Schedulers.immediate());
        StepVerifier.create(integerFlux).expectNext(1, 2, 3).verifyComplete();
        Assertions.assertEquals(counter.get(), 3);
    }

    private ScheduledExecutorService decorate(ScheduledExecutorService executorService) {
        try {
            var pfb = new ProxyFactoryBean();
            pfb.setProxyInterfaces(new Class[]{ScheduledExecutorService.class});
            pfb.addAdvice((MethodInterceptor) methodInvocation -> {
                var methodName = methodInvocation.getMethod().getName().toLowerCase();
                methodInvocationCounts.incrementAndGet();
                log.info("methodName: (" + methodName + ") incrementing...");
                return methodInvocation.proceed();
            });
            pfb.setSingleton(true);
            pfb.setTarget(executorService);
            return (ScheduledExecutorService) pfb.getObject();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }


}
