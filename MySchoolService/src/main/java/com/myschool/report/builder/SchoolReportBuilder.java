package com.myschool.report.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.infra.report.builders.SimpleListingReportBuilder;
import com.myschool.infra.report.exception.ReportException;
import com.myschool.report.dto.ReportCriteria;
import com.myschool.report.dto.ReportCriteriaToken;
import com.myschool.school.assembler.SchoolDataAssembler;
import com.myschool.school.domain.SchoolManager;
import com.myschool.school.dto.SchoolDto;
import com.quasar.core.exception.DataException;

/**
 * The Class SchoolReportBuilder.
 */
@Component
public class SchoolReportBuilder extends SimpleListingReportBuilder {

    /** The number of columns. */
    protected static int numberOfColumns = 9;

    /** The school manager. */
    @Autowired
    private SchoolManager schoolManager;

    /* (non-Javadoc)
     * @see com.myschool.infra.report.builders.SimpleListingReportBuilder#getListingHeaders()
     */
    @Override
    public String[] getListingHeaders() {
        int index = 0;
        String[] listingHeaders = new String[numberOfColumns];
        listingHeaders[index++] = "Branch";
        listingHeaders[index++] = "Division";
        listingHeaders[index++] = "School Name";
        listingHeaders[index++] = "Address";
        listingHeaders[index++] = "Primary Phone";
        listingHeaders[index++] = "Secondary Phone";
        listingHeaders[index++] = "Mobile Number";
        listingHeaders[index++] = "Fax Number";
        listingHeaders[index++] = "Email ID";
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
            SchoolDto schoolDto = SchoolDataAssembler.create(reportCriteriaValues);
            List<SchoolDto> schools = schoolManager.getAll(schoolDto);
            if (schools != null && !schools.isEmpty()) {
                reportData = new ArrayList<Object[]>();
                for (SchoolDto school : schools) {
                    int index = 0;
                    Object[] rowData = new Object[numberOfColumns];
                    rowData[index++] = school.getBranch().getBranchCode();
                    rowData[index++] = school.getDivision().getDivisionCode();
                    rowData[index++] = school.getSchoolName();
                    rowData[index++] = school.getAddress();
                    rowData[index++] = school.getPrimaryPhoneNumber();
                    rowData[index++] = school.getSecondaryPhoneNumber();
                    rowData[index++] = school.getMobileNumber();
                    rowData[index++] = school.getFaxNumber();
                    rowData[index++] = school.getEmailId();
                    reportData.add(rowData);
                }
            }
            return reportData;
        } catch (DataException dataException) {
            throw new ReportException(dataException.getMessage(), dataException);
        }
    }

}
