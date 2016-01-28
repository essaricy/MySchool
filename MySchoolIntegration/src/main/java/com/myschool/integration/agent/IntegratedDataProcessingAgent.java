package com.myschool.integration.agent;

import org.apache.camel.spring.Main;

/**
 * The Class IntegratedDataProcessingAgent.
 */
public class IntegratedDataProcessingAgent {

    /**
     * The main method.
     * 
     * @param args the arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
        /*String encode = URLEncoder.encode("move=history/${file:name.noext}_${date:now:yyyyMMddHHmmssSSS}.${file:name.ext}");
        System.out.println(encode);*/
        Main main = new Main();
        main.setApplicationContextUri("camelcontext-integration.xml");
        main.enableHangupSupport();
        main.run();
    }

}
