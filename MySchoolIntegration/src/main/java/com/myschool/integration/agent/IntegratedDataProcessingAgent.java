package com.myschool.integration.agent;

import org.apache.camel.spring.Main;

/**
 * The Class IntegratedDataProcessingAgent.
 */
public class IntegratedDataProcessingAgent {

    private void loadAgent() throws Exception {
        Main main = new Main();
        main.setApplicationContextUri("config/spring/MySchoolIntegration.xml");
        main.enableHangupSupport();
        main.run();
    }

    /**
     * The main method.
     * 
     * @param args the arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
        IntegratedDataProcessingAgent integratedDataProcessingAgent = new IntegratedDataProcessingAgent();
        integratedDataProcessingAgent.loadAgent();
    }

}
