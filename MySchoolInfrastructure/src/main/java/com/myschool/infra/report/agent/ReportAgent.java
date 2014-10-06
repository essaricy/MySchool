package com.myschool.infra.report.agent;

import org.springframework.stereotype.Component;

import com.myschool.infra.agent.AbstractAgent;
import com.myschool.infra.report.builders.ReportBuilder;

/**
 * The Class ReportAgent.
 */
@Component
public abstract class ReportAgent extends AbstractAgent {

    /**
     * Gets the report builder implementor.
     * 
     * @param reportBuilder the report builder
     * @return the report builder implementor
     */
    public abstract ReportBuilder getReportBuilderImplementor(ReportBuilder reportBuilder);

}
