package com.epam.esm.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class RepositoryAspect {
    private final Logger logger = LoggerFactory.getLogger(RepositoryAspect.class);

    @Before("execution(* com.epam.esm.repository.impl.*.*(..))")
    public void logAndHandleExceptions(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        logger.debug("Entering method: {} with argument(s): {}", methodName, Arrays.toString(args));
    }
}
