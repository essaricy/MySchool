package com.myschool.report.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.employee.domain.DesignationManager;
import com.myschool.employee.dto.DesignationDto;
import com.myschool.infra.report.builders.SimpleListingReportBuilder;
import com.myschool.infra.report.exception.ReportException;
import com.myschool.report.dto.ReportCriteria;
import com.quasar.core.exception.DataException;

/**
 * The Class DesignationReportBuilder.
 */
@Component
public class DesignationReportBuilder extends SimpleListingReportBuilder {

    /** The number of columns. */
    protected static int numberOfColumns = 2;

    /** The designation manager. */
    @Autowired
    private DesignationManager designationManager;

    /* (non-Javadoc)
     * @see com.myschool.infra.report.builders.SimpleListingReportBuilder#getListingHeaders()
     */
    @Override
    public String[] getListingHeaders() {
        int index = 0;
        String[] listingHeaders = new String[numberOfColumns];
        listingHeaders[index++] = "Designation Code";
        listingHeaders[index++] = "Description";
        return listingHeaders;
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.report.builders.SimpleListingReportBuilder#getListingData(com.myschool.report.dto.ReportCriteria)
     */
    @Override
    public List<Object[]> getListingData(ReportCriteria reportCriteria) throws ReportException {
        List<Object[]> reportData = null;
        try {
            List<DesignationDto> designations = designationManager.getAll();
            if (designations != null && !designations.isEmpty()) {
                reportData = new ArrayList<Object[]>();
                for (DesignationDto designation : designations) {
                    int index = 0;
                    Object[] rowData = new Object[numberOfColumns];
                    rowData[index++] = String.valueOf(designation.getDesignationId());
                    rowData[index++] = designation.getDesignation();
                    reportData.add(rowData);
                }
            }
            return reportData;
        } catch (DataException dataException) {
            throw new ReportException(dataException.getMessage(), dataException);
        }
    }

}
