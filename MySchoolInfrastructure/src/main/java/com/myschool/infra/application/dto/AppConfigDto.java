package com.myschool.infra.application.dto;

import java.io.Serializable;
import java.util.List;

/**
 * The Class AppConfigDto.
 */
public class AppConfigDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The agent dtos. */
    private List<AgentDto> agentDtos;

    /**
     * Gets the agent dtos.
     * 
     * @return the agent dtos
     */
    public List<AgentDto> getAgentDtos() {
        return agentDtos;
    }

    /**
     * Sets the agent dtos.
     * 
     * @param agentDtos the new agent dtos
     */
    public void setAgentDtos(List<AgentDto> agentDtos) {
        this.agentDtos = agentDtos;
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
        retValue.append("AppConfigDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("agentDtos = ").append(this.agentDtos).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
