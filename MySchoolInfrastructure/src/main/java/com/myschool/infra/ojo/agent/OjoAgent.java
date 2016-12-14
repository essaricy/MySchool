package com.myschool.infra.ojo.agent;

import org.springframework.stereotype.Component;

import com.myschool.infra.agent.AbstractAgent;

/**
 * The Class OjoAgent.
 */
@Component
public abstract class OjoAgent extends AbstractAgent {

    /** The object xml mapping reader. */
    //@Autowired
    //private OxoMappingReader objectXmlMappingReader;

    /**
     * To json.
     * 
     * @param object the object
     * @return the string
     */
    public abstract String toJson(Object object);

    /**
     * To object.
     *
     * @param xmlContent the xml content
     * @return the object
     */
    public abstract Object toObject(String xmlContent);

}
