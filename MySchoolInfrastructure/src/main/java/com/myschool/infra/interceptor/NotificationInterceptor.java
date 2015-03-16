package com.myschool.infra.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.myschool.branch.dto.BranchDto;

@Component
@Aspect
public class NotificationInterceptor {

    @After("execution(* com.myschool.*.domain.*Manager.create(..))")
    public void afterCreate(JoinPoint joinPoint) {
    //@After("execution(* com.myschool.*.domain.*Manager.create(..)) && args (object)")
    //public void afterCreate(JoinPoint joinPoint, Object object) {
        //System.out.println("object ====> " + object);
        //System.out.println("*******************************NotificationInterceptor*************************************");
        //System.out.println("afterCreate() of NotificationInterceptor is called!!! " + branch);
        //System.out.println(dto + " is of type " + dto.getClass());
        //System.out.println("hijacked method is : " + joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length != 0) {
            Object object = args[0];
            if (object instanceof BranchDto) {
                
            }
            /*for (Object object : args) {
                System.out.println("arguement ===> " + object);
            }*/
        }
        //System.out.println("*******************************NotificationInterceptor*************************************");
    }

}
