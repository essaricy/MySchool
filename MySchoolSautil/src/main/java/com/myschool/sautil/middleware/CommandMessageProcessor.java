package com.myschool.sautil.middleware;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.myschool.infra.application.constants.CommandName;
import com.myschool.infra.application.dto.CommandDto;
import com.myschool.infra.application.dto.ConfigurationParameterDto;
import com.myschool.sautil.base.StandAloneUtility;

/**
 * The Class CommandMessageProcessor.
 */
public class CommandMessageProcessor extends MessageProcessor {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(CommandMessageProcessor.class);

    /* (non-Javadoc)
     * @see com.myschool.sautil.middleware.MessageProcessor#processObject(java.lang.Object)
     */
    @Override
    public void processObject(Object object) throws Exception {
        StandAloneUtility standAloneUtility = null;
        if (object instanceof CommandDto) {
            CommandDto command = (CommandDto) object;
            LOGGER.info("Command Received: " + command);

            CommandName commandName = command.getCommandName();
            standAloneUtility = standAloneUtilities.get(commandName);
            LOGGER.info("Running StandAloneUtility " + standAloneUtility);

            List<ConfigurationParameterDto> commandArguements = command.getCommandArguements();
            if (commandArguements != null && !commandArguements.isEmpty()) {
                Properties executionProperties = new Properties();
                for (ConfigurationParameterDto configurationParameter : commandArguements) {
                    executionProperties.setProperty(configurationParameter.getId(), configurationParameter.getValue());
                }
                standAloneUtility.setExecutionProperties(executionProperties);
            }
            standAloneUtility.validateParameters();
            standAloneUtility.startProcess();
            
        } else {
            LOGGER.fatal("Cannot execute the command. Unsupported object " + object);
        }
    }

}
