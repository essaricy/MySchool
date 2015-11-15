package com.myschool.infra.report.builders.jasper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.component.ComponentBuilders;
import net.sf.dynamicreports.report.builder.component.FillerBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.ImageBuilder;
import net.sf.dynamicreports.report.builder.component.TextFieldBuilder;
import net.sf.dynamicreports.report.constant.ComponentPositionType;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.application.dto.OrganizationProfileDto;
import com.myschool.application.dto.ResourceDto;
import com.myschool.common.exception.FileSystemException;
import com.myschool.common.util.ConversionUtil;
import com.myschool.common.util.StringUtil;
import com.myschool.infra.filesystem.agent.TempFileSystem;
import com.myschool.infra.media.agent.MediaServerAgent;
import com.myschool.infra.media.exception.ResourceException;
import com.myschool.infra.report.builders.ReportBuilder;
import com.myschool.infra.report.constants.ReportStyles;
import com.myschool.infra.report.exception.ReportException;
import com.myschool.report.dto.ReportCriteria;
import com.myschool.report.dto.ReportCriteriaToken;
import com.myschool.report.dto.ReportDto;

/**
 * The Class JasperAbstractReportBuilder.
 */
@Component
public abstract class JasperAbstractReportBuilder implements ReportBuilder {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(JasperAbstractReportBuilder.class);

    /** The temp file system. */
    @Autowired
    private TempFileSystem tempFileSystem;

    /** The media server agent. */
    @Autowired
    private MediaServerAgent mediaServerAgent;

    /**
     * Gets the base report.
     * 
     * @param organizationProfile the organization profile
     * @param report the report
     * @param reportCriteria the report criteria
     * @return the base report
     * @throws ReportException the report exception
     */
    protected JasperReportBuilder getBaseReport(
            OrganizationProfileDto organizationProfile, ReportDto report,
            ReportCriteria reportCriteria) throws ReportException {
        JasperReportBuilder reportBuilder = null;
        if (reportCriteria == null) {
            throw new ReportException("No Report Criteria.");
        }
        //Hashtable<String, Object> reportParameters = report.getReportParameters();
        reportBuilder = DynamicReports.report();
        ComponentBuilders componentBuilders = DynamicReports.cmp;
        // Logo Builder
        ImageBuilder logo = componentBuilders.image(getLogo());
        logo.setFixedDimension(80, 80);
        logo.setPositionType(ComponentPositionType.FLOAT);

        // Organization Name Builder
        TextFieldBuilder<String> organizationNameBuilder = componentBuilders.text(
                organizationProfile.getOrganizationName());
        organizationNameBuilder.setStyle(ReportStyles.SERIF_12_BOLD_CENTER);
        // Address Builder
        TextFieldBuilder<String> addressBuilder = componentBuilders.text(
                organizationProfile.getAddress());
        addressBuilder.setStyle(ReportStyles.SERIF_8_REGULAR_CENTER);
        // Phone & Fax Builder
        String phoneNumber = organizationProfile.getPhoneNumber();
        String faxNumber = organizationProfile.getFaxNumber();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Phone: ").append((phoneNumber == null ? " " : phoneNumber));
        if (faxNumber != null) {
            stringBuffer.append(" Fax: ").append((faxNumber == null ? " " : faxNumber));
        }
        TextFieldBuilder<String> phoneFaxBuilder = componentBuilders.text(stringBuffer.toString());
        phoneFaxBuilder.setStyle(ReportStyles.SERIF_8_REGULAR_CENTER);
        // Report Name
        TextFieldBuilder<String> reportNameComponent = componentBuilders.text(
                "*** " + report.getReportName() + " ***");
        reportNameComponent.setStyle(ReportStyles.SERIF_8_BOLD_CENTER);
        // Page Header
        HorizontalListBuilder organizationDetailsBuilder = componentBuilders.horizontalList()
                .add(organizationNameBuilder)
                .newRow().add(addressBuilder)
                .newRow().add(phoneFaxBuilder)
                .newRow().add(componentBuilders.text(""))
                .newRow().add(reportNameComponent);
        organizationDetailsBuilder.setFixedWidth(575 - 140);

        TextFieldBuilder<String> reportDateTimeBuilder = componentBuilders.text(
                ConversionUtil.toReportDate(new Date()));
        reportDateTimeBuilder.setStyle(ReportStyles.SERIF_8_REGULAR_RIGHT);

        FillerBuilder horizontalRule = componentBuilders.filler();
        horizontalRule.setStyle(ReportStyles.STYLE_BUILDERS.style().setTopBorder(
                ReportStyles.STYLE_BUILDERS.pen1Point()));
        //horizontalRule.setFixedHeight(5);

        HorizontalListBuilder pageTitleBuilder = componentBuilders.horizontalList().add(logo)
                .add(organizationDetailsBuilder)
                .newRow().add(reportDateTimeBuilder);
        // Report Criteria
        HorizontalListBuilder reportCriteriaComponent = componentBuilders.horizontalList();
        Map<ReportCriteriaToken, String> reportCriteriaValues = reportCriteria.getReportCriteriaValues();
        TextFieldBuilder<String> reportCriteriaHeader = componentBuilders.text("Report Criteria: ");
        reportCriteriaHeader.setStyle(ReportStyles.SERIF_8_BOLD_CENTER)
                .setHorizontalAlignment(HorizontalAlignment.LEFT);
        reportCriteriaComponent.add(reportCriteriaHeader);
        if (reportCriteriaValues == null || reportCriteriaValues.isEmpty()) {
            reportCriteriaComponent.add(componentBuilders.text("None"));
        } else {
            int index = 0;
            Set<ReportCriteriaToken> keySet = reportCriteriaValues.keySet();
            for (Iterator<ReportCriteriaToken> iterator = keySet.iterator(); iterator.hasNext();) {
                ReportCriteriaToken reportCriteriaToken = iterator.next();
                String criteriaValue = reportCriteriaValues.get(reportCriteriaToken);
                if (reportCriteriaToken != null && criteriaValue != null && !criteriaValue.trim().equals("")) {
                    // Take line break
                    if (index++ % 4 == 0) {
                        reportCriteriaComponent.newRow();
                    }
                    reportCriteriaComponent.add(componentBuilders.text(reportCriteriaToken.getCriteriaName() + " =  " + criteriaValue));
                }
            }
        }
        reportCriteriaComponent.newRow().add(componentBuilders.text(""));

        reportBuilder.title(pageTitleBuilder, horizontalRule,
                reportCriteriaComponent);
        reportBuilder.pageFooter(componentBuilders.pageXofY().setStyle(
                ReportStyles.SERIF_8_REGULAR_CENTER));

        TextFieldBuilder<String> noDataComponentBuilder = componentBuilders.text("No Data Available for this report");
        noDataComponentBuilder.setStyle(ReportStyles.SERIF_8_REGULAR_CENTER);
        return reportBuilder.noData(pageTitleBuilder, horizontalRule, noDataComponentBuilder);
    }

    /**
     * Generate pdf file.
     * 
     * @param jasperReportBuilder the jasper report builder
     * @return the file
     * @throws ReportException the report exception
     */
    protected File generatePdfFile(JasperReportBuilder jasperReportBuilder) throws ReportException {
        File reportFile = null;
        FileOutputStream fileOutputStream = null;
        try {
            if (jasperReportBuilder != null) {
                reportFile = tempFileSystem.createPdfReportFile();
                fileOutputStream = new FileOutputStream(reportFile);
                jasperReportBuilder.toPdf(fileOutputStream);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            throw new ReportException(fileNotFoundException.getMessage(), fileNotFoundException);
        } catch (FileSystemException fileSystemException) {
            throw new ReportException(fileSystemException.getMessage(), fileSystemException);
        } catch (DRException drException) {
            throw new ReportException(drException.getMessage(), drException);
        }
        return reportFile;
    }

    /**
     * Gets the logo.
     * 
     * @return the logo
     */
    private String getLogo() {
        String thumbnailUrl = null;
        try {
            ResourceDto logo = mediaServerAgent.getLogo();
            if (logo == null || logo.getThumbnailUrl() == null) {
                throw new ResourceException("LOGO resource is null");
            }
            thumbnailUrl = logo.getThumbnailUrl();
            if (StringUtil.isNullOrBlank(thumbnailUrl)) {
                throw new ResourceException("LOGO/thumbnail is null");
            }
        } catch (ResourceException resourceException) {
            LOGGER.warn("Unable to load the logo into the report. " + resourceException.getMessage());
        }
        return thumbnailUrl;
    }
}
