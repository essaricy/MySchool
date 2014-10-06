package com.myschool.infra.scheduler.agent;

import java.text.MessageFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.AgentException;
import com.myschool.infra.scheduler.agent.SchedulerAgent;
import com.myschool.infra.scheduler.dto.SchedulerConfigDto;

/**
 * The Class QuartzSchedulerAgent.
 */
@Component
public class QuartzSchedulerAgent extends SchedulerAgent {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(QuartzSchedulerAgent.class);

    /** The Constant TRIGGER_IDENTITY. */
    private static final String TRIGGER_IDENTITY = "TRIGGER [{0}]";

    /** The Constant SCHEDULE_FORMAT. */
    private static final String SCHEDULE_FORMAT = "{0} {1} {2} {3} {4} {5} {6}";

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#validate()
     */
    @Override
    public void validate() throws AgentException {
        try {
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();
            String schedulerId = scheduler.getSchedulerInstanceId();
            LOGGER.info("Dummy scheduler created with id " + schedulerId);
        } catch (SchedulerException schedulerException) {
            throw new AgentException(schedulerException.getMessage(), schedulerException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.scheduler.agent.SchedulerAgent#schedule()
     */
    @Override
    public void schedule()
            throws com.myschool.infra.scheduler.exception.SchedulerException {
        String jobName = null;
        String className = null;
        String schedule = null;

        Class classInstance = null;
        JobDetail jobDetail = null;
        CronTrigger trigger = null;

        try {
            if (schedulerConfigs != null && !schedulerConfigs.isEmpty()) {
                SchedulerFactory schedulerFactory = new StdSchedulerFactory();
                Scheduler scheduler = schedulerFactory.getScheduler();
                String schedulerId = scheduler.getSchedulerInstanceId();

                for (SchedulerConfigDto schedulerConfig : schedulerConfigs) {
                    try {
                        jobName = schedulerConfig.getName();

                        LOGGER.info("Scheduling --> " + schedulerConfig);
                        className = schedulerConfig.getClassName();
                        classInstance = Class.forName(className);

                        jobDetail = JobBuilder.newJob(classInstance)
                                .withIdentity(MessageFormat.format(SCHEDULER_IDENTITY, jobName), schedulerId)
                                .requestRecovery()
                                .build();

                        schedule = getSchedule(schedulerConfig);
                        LOGGER.info("Schedule for Job [" + jobName + "] is '" + schedule + "'");
                        trigger = TriggerBuilder.newTrigger()
                                .withIdentity(MessageFormat.format(TRIGGER_IDENTITY, jobName), schedulerId)
                                .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.MINUTE))
                                .withSchedule(CronScheduleBuilder.cronSchedule(schedule))
                                .build();
                        scheduler.scheduleJob(jobDetail, trigger);

                        LOGGER.info("Job " + jobName + " has been scheduled successfully.");
                    } catch (ClassNotFoundException classNotFoundException) {
                        LOGGER.error("Job class " + className + " not found within the class path or does not exist at all.");
                    }
                }
                LOGGER.info("Starting the scheduler at " + Calendar.getInstance().getTime());
                scheduler.start();
            }
        } catch (SchedulerException schedulerException) {
            throw new com.myschool.infra.scheduler.exception.SchedulerException(schedulerException.getMessage(), schedulerException);
        }
    }

    /**
     * Gets the schedule.
     *
     * @param schedulerConfig the scheduler config
     * @return the schedule
     */
    private String getSchedule(SchedulerConfigDto schedulerConfig) {
        String year = schedulerConfig.getYear();
        String month = schedulerConfig.getMonth();
        String date = schedulerConfig.getDate();
        String days = schedulerConfig.getDays();
        String hour = schedulerConfig.getHour();
        String minute = schedulerConfig.getMinute();
        String second = schedulerConfig.getSecond();
        return MessageFormat.format(SCHEDULE_FORMAT, second, minute, hour, date, month, year, days);
    }

}
