package org.project.resourceservice.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicLong;

@Component
@Aspect
@Slf4j
public class ExecutionTimeAdvice {

    @Around("@annotation(org.project.resourceservice.annotation.ExecutionTime)")
    public Object executionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        AtomicLong startTime = new AtomicLong();
        AtomicLong endTime = new AtomicLong();
        Object proceed = joinPoint.proceed();
        if (proceed instanceof Mono) {
            Mono<?> mono = (Mono<?>) joinPoint.proceed();
            mono.doOnSubscribe(subscription -> startTime.set(System.currentTimeMillis())).doFinally(obj -> endTime.set(System.currentTimeMillis()));
        } else if (proceed instanceof Flux) {
            Flux<?> flux = (Flux<?>) joinPoint.proceed();
            flux.doOnSubscribe(subscription -> startTime.set(System.currentTimeMillis())).doFinally(obj -> endTime.set(System.currentTimeMillis()));
        }
        log.info("Method {} has taken {} ms.", joinPoint.getSignature().getName(), endTime.get() - startTime.get());
        return proceed;
    }
}
