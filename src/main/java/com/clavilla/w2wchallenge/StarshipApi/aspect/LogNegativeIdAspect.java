package com.clavilla.w2wchallenge.StarshipApi.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogNegativeIdAspect {

    @Pointcut("execution(* com.clavilla.w2wchallenge.StarshipApi.controller.StarshipController.getStarshipById(..))")
    public void getStarshipByIdPointcut() {}

    @Before("getStarshipByIdPointcut() && args(id,..)")
    public void logIfNegativeId(JoinPoint joinPoint, Long id) {
        if (id < 0) {
            log.warn("Attempted to access a starship with a negative ID: {}", id);
        }
    }
}
