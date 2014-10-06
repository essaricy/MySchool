package com.myschool.sautil.base;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.myschool.common.exception.ConfigurationException;
import com.myschool.common.util.DateUtil;
import com.myschool.common.util.PropertiesUtil;
import com.myschool.common.util.StringUtil;
import com.myschool.infra.application.ApplicationLoader;
import com.myschool.infra.filesystem.constants.FileSystemConstants;
import com.myschool.infra.filesystem.util.FileUtil;
import com.myschool.sautil.reader.CommandArguementsReader;

/**
 * The Class StandAloneUtility.
 */
public abstract class StandAloneUtility {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(StandAloneUtility.class);

    /** The Constant PADDING_WIDTH. */
    public static final String PADDING_WIDTH = "%-30s";

    /** The Constant UTILITY_NAME. */
    public static final String UTILITY_NAME = "--utility-name";

    /** The Constant OPTION_HELP. */
    public static final String OPTION_HELP = "--help";

    /** The Constant OPTION_EXT_DIR. */
    public static final String OPTION_EXT_DIR = "--ext-dir";

    /** The Constant OPTION_STAY. */
    public static final String OPTION_STAY = "--stay";

    /** The execution properties. */
    protected Properties executionProperties;

    /**
     * Validate parameters.
     *
     * @throws ConfigurationException the configuration exception
     */
    public abstract void validateParameters() throws ConfigurationException;

    /**
     * Start process.
     *
     * @throws Exception the exception
     */
    public abstract void startProcess() throws Exception;

    /**
     * Gets the usage text.
     *
     * @return the usage text
     */
    public abstract String getUsageText();

    /**
     * Run utility.
     *
     * @param args the args
     */
    public static void runUtility(String[] args) {
        long startTime = System.currentTimeMillis();
        String startMessage = "Standalone Utility Program started at '" + new Date() + "'";
        StandAloneUtility standAloneUtility = null;
        try {
            LOGGER.info(startMessage);
            CommandArguementsReader commandArguementsReader = new CommandArguementsReader();
            Properties executionProperties = commandArguementsReader.toProperties(args);
            String utilityName = executionProperties.getProperty(UTILITY_NAME);
            if (utilityName == null) {
                throw new ConfigurationException("You must specify " + UTILITY_NAME + " option");
            }
            // Check if FileSystem.properties file exists or not.
            String fileSystemProperties = System.getProperty("filesystem.properties");
            File fileSystemFile = FileUtil.checkFile(fileSystemProperties,
                    "Missing required system property filesystem.properties",
                    "Value for the system property filesystem.properties must point to a file.");
            System.setProperty(ApplicationLoader.FILE_SYSTEM_PROPERTIES, fileSystemFile.getAbsolutePath());
            // Load FileSystem.properties file. 
            Properties properties = PropertiesUtil.loadNestedProperties(fileSystemFile);
            FileSystemXmlApplicationContext fileSystemXmlApplicationContext = new FileSystemXmlApplicationContext(getSpringContextFileNames(properties));
            fileSystemXmlApplicationContext.start();


            Class<StandAloneUtility> utilityClass = (Class<StandAloneUtility>) Class.forName(utilityName);
            standAloneUtility = fileSystemXmlApplicationContext.getBean(utilityClass);

            standAloneUtility.setExecutionProperties(executionProperties);
            standAloneUtility.validateParameters();
            standAloneUtility.startProcess();
            long duration = System.currentTimeMillis() - startTime ;
            LOGGER.info("Standalone Utility Program completed at '" + new Date() + "' and has taken " + DateUtil.getReadableDuration(duration));
            System.out.println("Standalone Utility Program completed at '" + new Date() + "' and has taken " + DateUtil.getReadableDuration(duration));

            // Close the program if not requested for a stay alive
            String property = executionProperties.getProperty(OPTION_STAY);
            if (StringUtil.isNullOrBlank(property) || !Boolean.parseBoolean(property)) {
                System.exit(0);
            }
        } catch (Exception exception) {
            handleException(standAloneUtility, exception);
            long duration = System.currentTimeMillis() - startTime ;
            LOGGER.info("Standalone Utility Program completed at '" + new Date() + "' and has taken " + DateUtil.getReadableDuration(duration));
            System.exit(1);
        }
    }

    /**
     * Handle exception.
     *
     * @param standAloneUtility the stand alone utility
     * @param throwable the throwable
     */
    private static void handleException(
            StandAloneUtility standAloneUtility, Throwable throwable) {
        LOGGER.fatal(throwable.getMessage(), throwable);
        if (standAloneUtility != null) {
            standAloneUtility.printUsage();
        }
        throwable.printStackTrace();
        System.exit(1);
    }

    /**
     * Gets the spring context file names.
     * @param properties 
     *
     * @return the spring context file names
     * @throws ConfigurationException the configuration exception
     */
    private static String[] getSpringContextFileNames(Properties properties) throws ConfigurationException {
       List<String> springContextFileNames = new ArrayList<String>();

       springContextFileNames.add((String) properties.get(FileSystemConstants.CONFIG_SPRING_BASE_FILE));
       springContextFileNames.add((String) properties.get(FileSystemConstants.CONFIG_SPRING_INFRASTRUCTURE_FILE));
       springContextFileNames.add((String) properties.get(FileSystemConstants.CONFIG_SPRING_SERVICE_FILE));
       springContextFileNames.add((String) properties.get(FileSystemConstants.CONFIG_SPRING_SAUTIL_FILE));
       return springContextFileNames.toArray(new String[springContextFileNames.size()]);
    }

    /**
     * Prints the usage.
     */
    public void printUsage() {
       System.out.println(getUsageText());
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        runUtility(args);
    }

    /**
     * Gets the execution properties.
     *
     * @return the execution properties
     */
    public Properties getExecutionProperties() {
        return executionProperties;
    }

    /**
     * Sets the execution properties.
     *
     * @param executionProperties the new execution properties
     */
    public void setExecutionProperties(Properties executionProperties) {
        this.executionProperties = executionProperties;
    }

}
