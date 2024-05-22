package com.clavilla.w2wchallenge.StarshipApi.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogNegativeIdAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogNegativeIdAspect.class);

    @Pointcut("execution(* com.clavilla.w2wchallenge.StarshipApi.controller.StarshipController.getStarshipById(..))")
    public void getStarshipByIdPointcut() {}

    @Before("getStarshipByIdPointcut() && args(id,..)")
    public void logIfNegativeId(JoinPoint joinPoint, Long id) {
        if (id < 0) {
            logger.warn("Attempted to access a starship with a negative ID: {}", id);
        }
    }
}
