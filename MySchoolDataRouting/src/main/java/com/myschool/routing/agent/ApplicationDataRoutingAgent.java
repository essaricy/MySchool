package com.myschool.routing.agent;

import org.apache.camel.spring.Main;

public class ApplicationDataRoutingAgent {

    private void loadAgent() throws Exception {
        Main main = new Main();
        main.setApplicationContextUri("config/spring/MySchoolDataRouting.xml");
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
        ApplicationDataRoutingAgent applicationDataRoutingAgent = new ApplicationDataRoutingAgent();
        applicationDataRoutingAgent.loadAgent();
    }

}
