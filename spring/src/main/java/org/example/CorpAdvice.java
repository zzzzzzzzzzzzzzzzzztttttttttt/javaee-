package org.example;

import org.aspectj.lang.ProceedingJoinPoint;
public class CorpAdvice {
    public void aroundAddWorker(ProceedingJoinPoint joinPoint) throws Throwable {
        Corp targetCorp = (Corp) joinPoint.getTarget();
        System.out.println("xml环绕前的Corp实例：" + targetCorp);
        joinPoint.proceed();
        System.out.println("xml环绕后的Corp实例：" + targetCorp);
    }
    public void beforeAddWorker() {
        System.out.println("xml前置");
    }
}