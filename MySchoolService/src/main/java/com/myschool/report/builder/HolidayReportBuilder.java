package com.myschool.report.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.academic.assembler.HolidayDataAssembler;
import com.myschool.academic.domain.HolidayManager;
import com.myschool.academic.dto.HolidayDto;
import com.myschool.academic.dto.HolidaySearchCriteria;
import com.myschool.common.exception.DataException;
import com.myschool.infra.report.builders.SimpleListingReportBuilder;
import com.myschool.infra.report.exception.ReportException;
import com.myschool.report.dto.ReportCriteria;
import com.myschool.report.dto.ReportCriteriaToken;

/**
 * The Class HolidayReportBuilder.
 */
@Component
public class HolidayReportBuilder extends SimpleListingReportBuilder {

    /** The number of columns. */
    protected static int numberOfColumns = 3;

    /** The holiday manager. */
    @Autowired
    private HolidayManager holidayManager;

    /* (non-Javadoc)
     * @see com.myschool.infra.report.builders.SimpleListingReportBuilder#getListingHeaders()
     */
    @Override
    public String[] getListingHeaders() {
        int index = 0;
        String[] listingHeaders = new String[numberOfColumns];
        listingHeaders[index++] = "Holiday";
        listingHeaders[index++] = "Start Date";
        listingHeaders[index++] = "End Date";
        return listingHeaders;
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.report.builders.SimpleListingReportBuilder#getListingData(com.myschool.report.dto.ReportCriteria)
     */
    @Override
    public List<Object[]> getListingData(ReportCriteria reportCriteria) throws ReportException {
        List<Object[]> reportData = null;
        try {
            Map<ReportCriteriaToken, String> reportCriteriaValues = reportCriteria.getReportCriteriaValues();
            HolidaySearchCriteria holidaySearchCriteria = HolidayDataAssembler.create(reportCriteriaValues);
            List<HolidayDto> holidays = holidayManager.getAll(holidaySearchCriteria);
            if (holidays != null && !holidays.isEmpty()) {
                reportData = new ArrayList<Object[]>();
                for (HolidayDto holiday : holidays) {
                    int index = 0;
                    Object[] rowData = new Object[numberOfColumns];
                    rowData[index++] = holiday.getHolidayName();
                    String date = holiday.getStartDate();
                    if (date == null) {
                        rowData[index++] = "";
                    } else {
                        rowData[index++] = date;
                    }
                    date = holiday.getEndDate();
                    if (date == null) {
                        rowData[index++] = "";
                    } else {
                        rowData[index++] = date;
                    }
                    reportData.add(rowData);
                }
            }
            return reportData;
        } catch (DataException dataException) {
            throw new ReportException(dataException.getMessage(), dataException);
        }
    }

}
