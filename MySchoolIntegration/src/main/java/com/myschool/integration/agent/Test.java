package com.myschool.integration.agent;

import com.myschool.application.dto.ResourceDto;

public class Test {

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        //String xml="<Resources>Test</Resource>";
        String xml="<Resources>Test</Resources>";
        String className = ResourceDto.class.getSimpleName();
        if (className.endsWith("Dto")) {
            className = className.substring(0, className.indexOf("Dto"));
        }
        System.out.println("className " + className);
        System.out.println("Raw xml = " + xml);
        xml = xml.replaceAll("<" + className + "s", "<list")
                .replaceAll("</" + className + "s>", "</list>");
                /*.replaceAll("<" + className + "s\\\\/>", "<list\\\\/>")
                .replaceAll("<" + className + "s \\\\/>", "<list \\\\/>");*/
        System.out.println("Modified xml = " + xml);
    }

}
