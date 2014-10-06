package com.myschool.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * The Class LoggingInterceptor.
 */
@Component
@Aspect
public class LoggingInterceptor {

    /** The Constant LOGGER. */
    //private static final Logger LOGGER = Logger.getLogger(LoggingInterceptor.class);

    /**
     * After service exception.
     * 
     * @param joinPoint the join point
     * @param error the error
     */
    @AfterThrowing(pointcut="execution(* com.myschool.*.service.*ServiceImpl.*(..))", throwing= "error")
    public void afterServiceException(JoinPoint joinPoint, Throwable error) {
        //System.out.println("*******************************LoggingInterceptor*************************************");
        //System.out.println("hijacked method is : " + joinPoint.getSignature().getName());
        //error.printStackTrace();
        //System.out.println("*******************************LoggingInterceptor*************************************");
        //LOGGER.error(error.getMessage(), error);
    }

}
