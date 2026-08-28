package com.Daredevil.studentmanagment.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.Daredevil.studentmanagment.controller.*.*(..))")
    private void forControllerPackage(){}

    @Pointcut("execution(* com.Daredevil.studentmanagment.service.serviceImpl.*.*(..))")
    private void forServiceImplPackage(){}

    @Around("forControllerPackage() || forServiceImplPackage()")
    public Object logMethod(ProceedingJoinPoint pjp) throws Throwable {


        String className = pjp.getSignature().getDeclaringTypeName();
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();

        log.info("Entering: {}.{}() | args={}", className, methodName, Arrays.toString(args));

        Object result = pjp.proceed();

        log.info("Exiting: {}.{}() | returned={}", className, methodName, result);

        return result;
    }
}
