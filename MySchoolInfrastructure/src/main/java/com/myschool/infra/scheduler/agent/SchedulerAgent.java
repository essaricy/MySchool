package com.myschool.infra.scheduler.agent;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.ConfigurationException;
import com.myschool.infra.agent.AbstractAgent;
import com.myschool.infra.scheduler.dto.SchedulerConfigDto;
import com.myschool.infra.scheduler.exception.SchedulerException;
import com.myschool.infra.scheduler.reader.SchedulerConfigReader;

/**
 * The Class SchedulerAgent.
 */
@Component
public abstract class SchedulerAgent extends AbstractAgent {

    /** The Constant JOB_IDENTITY. */
    protected static final String SCHEDULER_IDENTITY = "SCHEDULER [{0}]";

    /** The scheduler config reader. */
    @Autowired
    private SchedulerConfigReader schedulerConfigReader;

    /** The scheduler configs. */
    protected List<SchedulerConfigDto> schedulerConfigs;

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#loadConfiguration(java.io.File)
     */
    @Override
    public void loadConfiguration(File configFile)
            throws ConfigurationException {
        schedulerConfigs = schedulerConfigReader.getSchedulerConfig(configFile);
    }

    /**
     * Schedule.
     *
     * @throws SchedulerException 
     */
    public abstract void schedule() throws SchedulerException;

}
