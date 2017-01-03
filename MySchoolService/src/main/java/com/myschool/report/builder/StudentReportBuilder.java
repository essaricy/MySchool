package com.myschool.report.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.clazz.dto.ClassDto;
import com.myschool.clazz.dto.MediumDto;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.clazz.dto.SectionDto;
import com.myschool.common.dto.PersonalDetailsDto;
import com.myschool.infra.report.builders.SimpleListingReportBuilder;
import com.myschool.infra.report.exception.ReportException;
import com.myschool.report.dto.ReportCriteria;
import com.myschool.report.dto.ReportCriteriaToken;
import com.myschool.student.assembler.StudentDataAssembler;
import com.myschool.student.domain.StudentManager;
import com.myschool.student.dto.StudentDto;
import com.myschool.student.dto.StudentSearchCriteriaDto;
import com.quasar.core.exception.DataException;

/**
 * The Class StudentReportBuilder.
 */
@Component
public class StudentReportBuilder extends SimpleListingReportBuilder {

    /** The number of columns. */
    protected static int numberOfColumns = 5;

    /** The student manager. */
    @Autowired
    private StudentManager studentManager;

    /* (non-Javadoc)
     * @see com.myschool.infra.report.builders.SimpleListingReportBuilder#getListingHeaders()
     */
    @Override
    public String[] getListingHeaders() {
        int index = 0;
        String[] listingHeaders = new String[5];
        listingHeaders[index++] = "Admission Number";
        listingHeaders[index++] = "Student Name";
        listingHeaders[index++] = "Class";
        listingHeaders[index++] = "Date Of Joining";
        listingHeaders[index++] = "Status";
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
            StudentSearchCriteriaDto studentSearchCriteria = StudentDataAssembler.create(reportCriteriaValues);
            List<StudentDto> students = studentManager.search(studentSearchCriteria);
            if (students != null && !students.isEmpty()) {
                reportData = new ArrayList<Object[]>();
                for (StudentDto student : students) {
                    int index = 0;
                    PersonalDetailsDto personalDetails = student.getPersonalDetails();
                    RegisteredClassDto registeredClass = student.getRegisteredClassDto();
                    ClassDto classDto = registeredClass.getClassDto();
                    MediumDto medium = registeredClass.getMedium();
                    SectionDto section = registeredClass.getSection();
                    Object[] rowData = new Object[5];
                    rowData[index++] = student.getAdmissionNumber();
                    rowData[index++] = personalDetails.getFirstName() + " " + personalDetails.getMiddleName() + " " + personalDetails.getLastName();
                    rowData[index++] = classDto.getClassName() + ", " + medium.getDescription() + ", " + section.getSectionName();
                    rowData[index++] = student.getDateOfJoining();
                    rowData[index++] = student.getAdmissionStatus().getDescription();
                    reportData.add(rowData);
                }
            }
            return reportData;
        } catch (DataException dataException) {
            throw new ReportException(dataException.getMessage(), dataException);
        }
    }

}
