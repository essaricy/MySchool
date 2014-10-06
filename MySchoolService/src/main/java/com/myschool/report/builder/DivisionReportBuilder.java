package com.myschool.report.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.branch.domain.DivisionManager;
import com.myschool.branch.dto.DivisionDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.util.MessageUtil;
import com.myschool.infra.report.builders.SimpleListingReportBuilder;
import com.myschool.infra.report.exception.ReportException;
import com.myschool.report.dto.ReportCriteria;

/**
 * The Class DivisionReportBuilder.
 */
@Component
public class DivisionReportBuilder extends SimpleListingReportBuilder {

    /** The number of columns. */
    protected static int numberOfColumns = 2;

    /** The division manager. */
    @Autowired
    private DivisionManager divisionManager;

    /** The message util. */
    @Autowired
    private MessageUtil messageUtil;

    /* (non-Javadoc)
     * @see com.myschool.infra.report.builders.SimpleListingReportBuilder#getListingHeaders()
     */
    @Override
    public String[] getListingHeaders() {
        int index = 0;
        String[] listingHeaders = new String[numberOfColumns];
        listingHeaders[index++] = "Division Code";
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
            List<DivisionDto> divisions = divisionManager.getAll();
            if (divisions != null && !divisions.isEmpty()) {
                reportData = new ArrayList<Object[]>();
                for (DivisionDto division : divisions) {
                    int index = 0;
                    Object[] rowData = new Object[numberOfColumns];
                    rowData[index++] = division.getDivisionCode();
                    rowData[index++] = division.getDescription();
                    reportData.add(rowData);
                }
            }
            return reportData;
        } catch (DataException dataException) {
            throw new ReportException(dataException.getMessage(), dataException);
        }
    }

}
