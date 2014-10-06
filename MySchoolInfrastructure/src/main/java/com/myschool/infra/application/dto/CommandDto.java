package com.myschool.infra.application.dto;

import java.io.Serializable;
import java.util.List;

import com.myschool.infra.application.constants.CommandName;

/**
 * The Class CommandDto.
 */
public class CommandDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The command name. */
    private CommandName commandName;

    /** The command arguements. */
    private List<ConfigurationParameterDto> commandArguements;

    /**
     * Gets the command name.
     * 
     * @return the command name
     */
    public CommandName getCommandName() {
        return commandName;
    }

    /**
     * Sets the command name.
     * 
     * @param commandName the new command name
     */
    public void setCommandName(CommandName commandName) {
        this.commandName = commandName;
    }

    /**
     * Gets the command arguements.
     * 
     * @return the command arguements
     */
    public List<ConfigurationParameterDto> getCommandArguements() {
        return commandArguements;
    }

    /**
     * Sets the command arguements.
     * 
     * @param commandArguements the new command arguements
     */
    public void setCommandArguements(
            List<ConfigurationParameterDto> commandArguements) {
        this.commandArguements = commandArguements;
    }

    /**
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation 
     * of this object.
     */
    public String toString() {
        final String SEPARATOR = ", ";
        StringBuilder retValue = new StringBuilder();
        retValue.append("CommandDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("commandName = ").append(this.commandName).append(SEPARATOR)
            .append("commandArguements = ").append(this.commandArguements).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
