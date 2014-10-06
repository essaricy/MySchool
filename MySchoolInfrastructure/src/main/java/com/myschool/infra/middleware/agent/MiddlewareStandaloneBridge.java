package com.myschool.infra.middleware.agent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.infra.application.constants.CommandName;
import com.myschool.infra.application.dto.CommandDto;
import com.myschool.infra.application.dto.ConfigurationParameterDto;
import com.myschool.infra.middleware.exception.MessageException;

/**
 * The Class MiddlewareStandaloneBridge.
 */
@Component
public class MiddlewareStandaloneBridge {

    /** The Constant PARAM_NAME_ACTION. */
    public static final String PARAM_NAME_ACTION = "--action";
    
    /** The Constant PARAM_NAME_EXT_DIR. */
    public static final String PARAM_NAME_EXT_DIR = "--ext-dir";
    
    /** The Constant PARAM_NAME_EXT_CFG. */
    public static final String PARAM_NAME_EXT_CFG = "--ext-cfg";
    
    /** The Constant PARAM_NAME_DATA_FORMAT. */
    public static final String PARAM_NAME_DATA_FORMAT = "--data-format";
    
    /** The Constant OPTION_TRACKER_ID. */
    public static final String OPTION_TRACKER_ID = "--tracker-id";

    /** The middleware message helper. */
    @Autowired
    private MiddlewareMessageHelper middlewareMessageHelper;

    /**
     * Send stand alone command.
     * 
     * @param commandName the command name
     * @param commandArguementsMap the command arguements map
     * @throws MessageException the message exception
     */
    public void sendStandAloneCommand(CommandName commandName,
            Map<String, Object> commandArguementsMap) throws MessageException {
        ConfigurationParameterDto param = null;
        List<ConfigurationParameterDto> commandArguements = null;
        if (commandName != null) {
            CommandDto command = new CommandDto();
            command.setCommandName(commandName);
            if (commandArguementsMap != null) {
                commandArguements = new ArrayList<ConfigurationParameterDto>();
                Set<String> keySet = commandArguementsMap.keySet();
                for (String key : keySet) {
                    param = new ConfigurationParameterDto();
                    param.setId(key);
                    Object object = commandArguementsMap.get(key);
                    if (object != null) {
                        param.setValue(object.toString());
                    }
                    commandArguements.add(param);
                }
                command.setCommandArguements(commandArguements);
            }
            middlewareMessageHelper.produceCommandMessage(command);
        }
    }

}
