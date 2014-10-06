package com.myschool.infra.webserver.constants;


/**
 * The Enum ServerType.
 */
public enum ServerType {

    /** The JBOSS. */
    JBOSS("org.jboss.Main"),
    
    /** The STAND_ALONE. */
    STAND_ALONE(null),
    
    /** The TOMCAT. */
    TOMCAT("org.apache.catalina.startup.Bootstrap");

    /** The Constant SUN_JAVA_COMMAND. */
    private static final String SUN_JAVA_COMMAND = "sun.java.command";

    /** The startup command. */
    private String startupCommand;

    /**
     * Instantiates a new server type.
     *
     * @param startupCommand the startup command
     */
    private ServerType(String startupCommand) {
        this.startupCommand = startupCommand;
    }

    /**
     * Gets the startup command.
     *
     * @return the startup command
     */
    public String getStartupCommand() {
        return startupCommand;
    }

    /**
     * Gets the server type.
     *
     * @param startupCommandValue the startup command value
     * @return the server type
     */
    public static ServerType getServerType(String startupCommandValue) {
        ServerType returnServerType = null;
        if (startupCommandValue != null) {
            for (ServerType serverType : values()) {
                String startupCommand = serverType.getStartupCommand();
                if (startupCommand != null && startupCommandValue.indexOf(startupCommand) !=-1) {
                    returnServerType = serverType;
                    break;
                }
            }
            if (returnServerType == null) {
                returnServerType = ServerType.STAND_ALONE;
            }
        }
        return returnServerType;
    }

    /**
     * Gets the server type.
     *
     * @return the server type
     */
    public static ServerType getServerType() {
        /*Properties properties = System.getProperties();
        Enumeration<Object> keys = properties.keys();
        while (keys.hasMoreElements()) {
            Object nextElement = keys.nextElement();
        }*/
        String startupCommand = System.getProperty(SUN_JAVA_COMMAND);
        return ServerType.getServerType(startupCommand);
    }

}
