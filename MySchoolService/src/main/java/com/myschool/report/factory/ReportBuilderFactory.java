package com.myschool.report.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.infra.report.builders.AbstractReportBuilder;
import com.myschool.report.builder.BranchReportBuilder;
import com.myschool.report.builder.DesignationReportBuilder;
import com.myschool.report.builder.DivisionReportBuilder;
import com.myschool.report.builder.EmployeeReportBuilder;
import com.myschool.report.builder.ExamGradeReportBuilder;
import com.myschool.report.builder.HolidayReportBuilder;
import com.myschool.report.builder.MasterClassReportBuilder;
import com.myschool.report.builder.MasterDocumentReportBuilder;
import com.myschool.report.builder.RelationshipCodeReportBuilder;
import com.myschool.report.builder.SchoolReportBuilder;
import com.myschool.report.builder.StudentReportBuilder;
import com.myschool.report.constants.ReportKey;

/**
 * A factory for creating ReportBuilder objects.
 */
@Component
public class ReportBuilderFactory {

    /** The branch report builder. */
    @Autowired
    private BranchReportBuilder branchReportBuilder;

    /** The designation report builder. */
    @Autowired
    private DesignationReportBuilder designationReportBuilder;

    /** The division report builder. */
    @Autowired
    private DivisionReportBuilder divisionReportBuilder;

    /** The master document report builder. */
    @Autowired
    private MasterDocumentReportBuilder masterDocumentReportBuilder;

    /** The employee report builder. */
    @Autowired
    private EmployeeReportBuilder employeeReportBuilder;

    /** The exam grade report builder. */
    @Autowired
    private ExamGradeReportBuilder examGradeReportBuilder;

    /** The holiday report builder. */
    @Autowired
    private HolidayReportBuilder holidayReportBuilder;

    /** The master class report builder. */
    @Autowired
    private MasterClassReportBuilder masterClassReportBuilder;

    /** The relationship code report builder. */
    @Autowired
    private RelationshipCodeReportBuilder relationshipCodeReportBuilder;

    /** The school report builder. */
    @Autowired
    private SchoolReportBuilder schoolReportBuilder;

    /** The student report builder. */
    @Autowired
    private StudentReportBuilder studentReportBuilder;

    /**
     * Gets the report builder.
     * 
     * @param reportName the report name
     * @return the report builder
     */
    public AbstractReportBuilder getReportBuilder(ReportKey reportName) {
        if (reportName != null) {
            if (reportName == ReportKey.BRANCH_REPORT) {
                return branchReportBuilder;
            } else if (reportName == ReportKey.DESIGNATION_REPORT) {
                return designationReportBuilder;
            } else if (reportName == ReportKey.DIVISION_REPORT) {
                return divisionReportBuilder;
            } else if (reportName == ReportKey.EMPLOYEE_REPORT) {
                return employeeReportBuilder;
            } else if (reportName == ReportKey.EXAM_GRADE_REPORT) {
                return examGradeReportBuilder;
            } else if (reportName == ReportKey.HOLIDAY_REPORT) {
                return holidayReportBuilder;
            } else if (reportName == ReportKey.MASTER_CLASS_REPORT) {
                return masterClassReportBuilder;
            } else if (reportName == ReportKey.MASTER_DOCUMENT_REPORT) {
                return masterDocumentReportBuilder;
            } else if (reportName == ReportKey.RELATIONSHIP_CODE_REPORT) {
                return relationshipCodeReportBuilder;
            } else if (reportName == ReportKey.SCHOOL_REPORT) {
                return schoolReportBuilder;
            } else if (reportName == ReportKey.STUDENT_REPORT) {
                return studentReportBuilder;
            }
        }
        return null;
    }

}
