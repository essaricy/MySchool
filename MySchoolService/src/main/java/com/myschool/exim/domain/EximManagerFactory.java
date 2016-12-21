package com.myschool.exim.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.exim.constants.EximPolicy;

/**
 * A factory for creating EximManager objects.
 */
@Component
public class EximManagerFactory {

    /** The attendance exim manager. */
    @Autowired
    private AttendanceEximManager attendanceEximManager;

    /** The branch exim manager. */
    @Autowired
    private BranchEximManager branchEximManager;

    /** The class exim manager. */
    @Autowired
    private ClassEximManager classEximManager;

    /** The designation exim manager. */
    @Autowired
    private DesignationEximManager designationEximManager;

    /** The division exim manager. */
    @Autowired
    private DivisionEximManager divisionEximManager;

    /** The document exim manager. */
    @Autowired
    private DocumentEximManager documentEximManager;

    /** The employee exim manager. */
    @Autowired
    private EmployeeEximManager employeeEximManager;

    /** The exam grade exim manager. */
    @Autowired
    private ExamGradeEximManager examGradeEximManager;

    /** The exam exim manager. */
    @Autowired
    private ExamEximManager examEximManager;

    /** The medium exim manager. */
    @Autowired
    private MediumEximManager mediumEximManager;

    /** The region exim manager. */
    @Autowired
    private RegionEximManager regionEximManager;

    /** The school exim manager. */
    @Autowired
    private SchoolEximManager schoolEximManager;

    /** The section exim manager. */
    @Autowired
    private SectionEximManager sectionEximManager;

    /** The student exim manager. */
    @Autowired
    private StudentEximManager studentEximManager;

    /** The subject exim manager. */
    @Autowired
    private SubjectEximManager subjectEximManager;

    /** The employment status exim manager. */
    @Autowired
    private EmploymentStatusEximManager employmentStatusEximManager;

    /** The holiday exim manager. */
    @Autowired
    private HolidayEximManager holidayEximManager;

    /** The admission status exim manager. */
    @Autowired
    private AdmissionStatusEximManager admissionStatusEximManager;

    /**
     * Gets the exim manager.
     * 
     * @param eximPolicy the exim policy
     * @return the exim manager
     */
    public AbstractEximManager getEximManager(EximPolicy eximPolicy) {
        AbstractEximManager abstractEximManager = null;
        if (eximPolicy == EximPolicy.BRANCHES) {
            abstractEximManager = branchEximManager;
        } else if (eximPolicy == EximPolicy.MASTER_CLASSES) {
            abstractEximManager = classEximManager;
        } else if (eximPolicy == EximPolicy.REGISTERED_CLASSES) {
            abstractEximManager = classEximManager;
        } else if (eximPolicy == EximPolicy.DESIGNATIONS) {
            abstractEximManager = designationEximManager;
        } else if (eximPolicy == EximPolicy.DIVISIONS) {
            abstractEximManager = divisionEximManager;
        } else if (eximPolicy == EximPolicy.DOCUMENTS) {
            abstractEximManager = documentEximManager;
        } else if (eximPolicy == EximPolicy.EMPLOYEES) {
            abstractEximManager = employeeEximManager;
        } else if (eximPolicy == EximPolicy.EXAM_GRADES) {
            abstractEximManager = examGradeEximManager;
        } else if (eximPolicy == EximPolicy.EXAMS) {
            abstractEximManager = examEximManager;
        } else if (eximPolicy == EximPolicy.MASTER_MEDIUMS) {
            abstractEximManager = mediumEximManager;
        } else if (eximPolicy == EximPolicy.REFERENCE_ATTENDANCE) {
            abstractEximManager = attendanceEximManager;
        } else if (eximPolicy == EximPolicy.REGIONS) {
            abstractEximManager = regionEximManager;
        } else if (eximPolicy == EximPolicy.SCHOOLS) {
            abstractEximManager = schoolEximManager;
        } else if (eximPolicy == EximPolicy.MASTER_SECTIONS) {
            abstractEximManager = sectionEximManager;
        } else if (eximPolicy == EximPolicy.STUDENT_ATTENDANCE) {
            abstractEximManager = attendanceEximManager;
        } else if (eximPolicy == EximPolicy.STUDENT_DOCUMENT) {
            abstractEximManager = studentEximManager;
        } else if (eximPolicy == EximPolicy.STUDENT_FAMILY) {
            abstractEximManager = studentEximManager;
        } else if (eximPolicy == EximPolicy.STUDENT_MARKS) {
            abstractEximManager = examEximManager;
        } else if (eximPolicy == EximPolicy.STUDENTS) {
            abstractEximManager = studentEximManager;
        } else if (eximPolicy == EximPolicy.MASTER_SUBJECTS) {
            abstractEximManager = subjectEximManager;
        } else if (eximPolicy == EximPolicy.REGISTERED_SUBJECTS) {
            abstractEximManager = subjectEximManager;
        } else if (eximPolicy == EximPolicy.SUBJECTS_IN_EXAMS) {
            abstractEximManager = examEximManager;
        } else if (eximPolicy == EximPolicy.EMPLOYMENT_STATUS) {
            abstractEximManager = employmentStatusEximManager;
        } else if (eximPolicy == EximPolicy.HOLIDAYS) {
            abstractEximManager = holidayEximManager;
        } else if (eximPolicy == EximPolicy.EMPLOYEE_CONTACT) {
            abstractEximManager = employeeEximManager;
        } else if (eximPolicy == EximPolicy.EMPLOYEE_EDUCATION) {
            abstractEximManager = employeeEximManager;
        } else if (eximPolicy == EximPolicy.EMPLOYEE_EXPERIENCE) {
            abstractEximManager = employeeEximManager;
        } else if (eximPolicy == EximPolicy.EMPLOYEE_DOCUMENT) {
            abstractEximManager = employeeEximManager;
        } else if (eximPolicy == EximPolicy.EMPLOYEE_PROMOTION) {
            abstractEximManager = employeeEximManager;
        } else if (eximPolicy == EximPolicy.EMPLOYEE_SUBJECT) {
            abstractEximManager = employeeEximManager;
        } else if (eximPolicy == EximPolicy.ADMISSION_STATUS) {
            abstractEximManager = admissionStatusEximManager;
        }
        return abstractEximManager;
    }

}
