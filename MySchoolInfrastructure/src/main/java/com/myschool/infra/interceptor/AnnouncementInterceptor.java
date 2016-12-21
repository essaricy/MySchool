package com.myschool.infra.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AnnouncementInterceptor {

    //private static final String CREATE_BRANCH = "A New Branch is opened at {0}, {1}";

    //private static final String CREATE_DIVISION = "Introducing a new Division {0}";

    @After("execution(* com.myschool.*.service.*ServiceImpl.create(..))")
    public void afterCreate(JoinPoint joinPoint) {/*
        String announcement = null;
        //System.out.println("*******************************AnnouncementInterceptor*************************************");
        //System.out.println("hijacked method is : " + joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length != 0) {
            Object object = args[0];
            if (object instanceof AcademicDto) {
                AcademicDto academic = (AcademicDto) object;
                announcement = MessageFormat.format(CREATE_BRANCH, academic.getAcademicYearName(), academic.getAcademicYearStartDate(), academic.getAcademicYearEndDate()); 
            } else if (object instanceof BranchDto) {
                BranchDto branch = (BranchDto) object;
                RegionDto region = branch.getRegion();
            } else if (object instanceof DivisionDto) {
                DivisionDto division = (DivisionDto) object;
                announcement = MessageFormat.format(CREATE_DIVISION, division.getDivisionCode()); 
            } else if (object instanceof SchoolDto) {
                //SchoolDto school = (SchoolDto) object;
                //announcement = MessageFormat.format(CREATE_DIVISION, school.get); 
            }
        }
        if (Boolean.TRUE != true) {
            System.out.println(announcement);
        }
        //System.out.println("announcement " + announcement);
        //System.out.println("*******************************AnnouncementInterceptor*************************************");
    */}

}
