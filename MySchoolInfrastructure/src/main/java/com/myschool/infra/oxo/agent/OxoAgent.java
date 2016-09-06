package com.myschool.infra.oxo.agent;

import java.util.List;

import org.springframework.stereotype.Component;

import com.myschool.infra.agent.AbstractAgent;
import com.myschool.infra.filesystem.constants.FileSystemConstants;

/**
 * The Class OxoAgent.
 */
@Component
public abstract class OxoAgent extends AbstractAgent {

    /**
     * To xml.
     * 
     * @param object the object
     * @return the string
     */
    public abstract String toXml(Object object);

    /**
     * To object.
     *
     * @param xmlContent the xml content
     * @return the object
     */
    public abstract Object toObject(String xmlContent);

    /**
     * Gets the oxo mapping file name.
     *
     * @return the oxo mapping file name
     */
    public String getOxoMappingFileName() {
        return fileSystemProperties.getProperty(FileSystemConstants.CONFIG_OXO);
    }

    /**
     * To xml.
     * 
     * @param <T> the generic type
     * @param list the list
     * @param type the type
     * @return the string
     */
    public abstract <T> String toXml(List<T> list, Class<T> type);

    /**
     * To xml.
     * 
     * @param <T> the generic type
     * @param command the command
     * @param type the type
     * @return the string
     *//*
    public abstract <T> String toXml(CommandDto command, Class<T> type);*/

}
