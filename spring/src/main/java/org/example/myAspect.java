package org.example;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class myAspect {
    @Autowired
    private Corp corp;
    @Pointcut("execution(* org.example.Corp.addWorker(..))")
    private void addWorker() {}

    @Around("addWorker()")
    public void addWorker(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("注解环绕前的Corp实例：" +corp);
        joinPoint.proceed();
        System.out.println("注解环绕前的Corp实例：" +corp);
    }

    @Before("addWorker()")
    public void addWorker2(){
        System.out.println("注解before");
    }

}
