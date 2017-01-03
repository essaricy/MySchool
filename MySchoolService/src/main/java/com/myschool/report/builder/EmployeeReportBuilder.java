package com.myschool.report.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.employee.assembler.EmployeeDataAssembler;
import com.myschool.employee.domain.EmployeeManager;
import com.myschool.employee.dto.DesignationDto;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.employee.dto.EmployeeSearchCriteriaDto;
import com.myschool.infra.report.builders.SimpleListingReportBuilder;
import com.myschool.infra.report.exception.ReportException;
import com.myschool.report.dto.ReportCriteria;
import com.myschool.report.dto.ReportCriteriaToken;
import com.quasar.core.exception.DataException;

/**
 * The Class EmployeeReportBuilder.
 */
@Component
public class EmployeeReportBuilder extends SimpleListingReportBuilder {

    /** The number of columns. */
    protected static int numberOfColumns = 8;

    /** The employee manager. */
    @Autowired
    private EmployeeManager employeeManager;

    /* (non-Javadoc)
     * @see com.myschool.infra.report.builders.SimpleListingReportBuilder#getListingHeaders()
     */
    @Override
    public String[] getListingHeaders() {
        int index = 0;
        String[] listingHeaders = new String[numberOfColumns];
        listingHeaders[index++] = "Employee Number";
        listingHeaders[index++] = "Employee Name";
        listingHeaders[index++] = "Employed At";
        listingHeaders[index++] = "Designation";
        listingHeaders[index++] = "Status";
        listingHeaders[index++] = "Employment Start Data";
        listingHeaders[index++] = "Employment End Data";
        listingHeaders[index++] = "Reporting To";
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
            EmployeeSearchCriteriaDto employeeSearchCriteria = EmployeeDataAssembler.create(reportCriteriaValues);
            List<EmployeeDto> employees = employeeManager.getAll(employeeSearchCriteria);
            if (employees != null && !employees.isEmpty()) {
                reportData = new ArrayList<Object[]>();
                for (EmployeeDto employee : employees) {
                    int index = 0;
                    DesignationDto designation = employee.getDesignation();
                    EmployeeDto reportingTo = employee.getReportingTo();
                    Object[] rowData = new Object[numberOfColumns];
                    rowData[index++] = employee.getEmployeeNumber();
                    rowData[index++] = employee.getFirstName() + " " + employee.getMiddleName() + " " + employee.getLastName();
                    rowData[index++] = employee.getEmployedAtBranch().getBranchCode();
                    rowData[index++] = designation.getDesignationId() + " - " + designation.getDesignation();
                    rowData[index++] = employee.getEmploymentStatus().getDescription();
                    String date = employee.getEmploymentStartDate();
                    if (date == null) {
                        rowData[index++] = "";
                    } else {
                        rowData[index++] = date;
                    }
                    date = employee.getEmploymentEndDate();
                    if (date == null) {
                        rowData[index++] = "";
                    } else {
                        rowData[index++] = date;
                    }
                    if (reportingTo == null) {
                        rowData[index++] = "None";
                    } else {
                        rowData[index++] = employee.getEmployeeNumber() + " - " + employee.getFirstName() + " " + employee.getMiddleName() + " " + employee.getLastName();
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
