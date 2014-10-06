package com.myschool.infra.report.agent.jasper;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.AgentException;
import com.myschool.common.exception.ConfigurationException;
import com.myschool.infra.report.agent.ReportAgent;
import com.myschool.infra.report.builders.ReportBuilder;
import com.myschool.infra.report.factory.jasper.JasperReportBuilderFactory;

/**
 * The Class JasperDynamicReportAgent.
 */
@Component
public class JasperDynamicReportAgent extends ReportAgent {

    @Autowired
    private JasperReportBuilderFactory jasperReportBuilderFactory;

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#loadConfiguration(java.io.File)
     */
    @Override
    public void loadConfiguration(File configFile)
            throws ConfigurationException {
        // Load Jasper report configurations
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#validate()
     */
    @Override
    public void validate() throws AgentException {
        // No validation planned.
    }

    @Override
    public ReportBuilder getReportBuilderImplementor(
            ReportBuilder reportBuilder) {
        return jasperReportBuilderFactory.getReportBuilder(reportBuilder);
    }

}
