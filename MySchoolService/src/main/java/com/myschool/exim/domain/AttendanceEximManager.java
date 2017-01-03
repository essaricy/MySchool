package com.myschool.exim.domain;

import org.springframework.stereotype.Component;

import com.myschool.common.dto.Rule;
import com.myschool.common.exception.ValidationException;
import com.myschool.exim.constants.EximPolicy;
import com.myschool.exim.dto.ImportRecordStatusDto;
import com.quasar.core.exception.DataException;

/**
 * The Class AttendanceEximManager.
 */
@Component
public class AttendanceEximManager extends AbstractEximManager {

    /** The month validator. *//*
    private MonthValidator monthValidator = new MonthValidator();

    *//** The day validator. *//*
    private DayValidator dayValidator = new DayValidator();*/

    @Override
    protected Object updateContent(EximPolicy eximPolicy, Object content, Rule rule,
            String fieldValue) throws DataException, ValidationException {
        return null;
    }

    @Override
    protected ImportRecordStatusDto validateRecord(EximPolicy eximPolicy,
            Object content) {
        return null;
    }

    @Override
    protected void processRecord(EximPolicy eximPolicy, ImportRecordStatusDto importRecordStatus) {
        
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.myschool.exim.domain.AbstractEximManager#updateContent(com.myschool
     * .exim.constants.EximPolicy, java.lang.Object,
     * com.myschool.common.dto.Rule, java.lang.String)
     
    @Override
    protected Object updateContent(EximPolicy eximPolicy, Object content, Rule rule,
            String fieldValue) throws DataException, ValidationException {
        if (eximPolicy == EximPolicy.REFERENCE_ATTENDANCE) {
            if (content == null) {
                content = new ReferenceAttendanceDto();
            }
            content = updateReferenceAttendance((ReferenceAttendanceDto) content, rule, fieldValue);
        } else if (eximPolicy == EximPolicy.STUDENT_ATTENDANCE) {
            if (content == null) {
                content = new StudentAttendanceDto();
            }
            content = updateStudentAttendance((StudentAttendanceDto) content, rule, fieldValue);
        }
        return content;
    }

     (non-Javadoc)
     * @see com.myschool.exim.domain.AbstractEximManager#validateRecord(com.myschool.exim.constants.EximPolicy, java.lang.Object)
     
    @Override
    protected ImportRecordStatusDto validateRecord(EximPolicy eximPolicy,
            Object content) {
        ImportRecordStatusDto importRecordStatus = new ImportRecordStatusDto();
        try {
            if (eximPolicy == EximPolicy.REFERENCE_ATTENDANCE) {
                ReferenceAttendanceDto referenceAttendance = (ReferenceAttendanceDto) content;
                referenceAttendance.setRegisteredClass(validateRegisteredClass(referenceAttendance.getRegisteredClass()));
            } else if (eximPolicy == EximPolicy.STUDENT_ATTENDANCE) {
                StudentAttendanceDto studentAttendanceDto = (StudentAttendanceDto) content;
                StudentDto student = studentAttendanceDto.getStudent();
                studentAttendanceDto.setStudent(studentManager.getStudent(student.getAdmissionNumber()));
            }
        } catch (Exception exception) {
            importRecordStatus.setActionCode(ImportRecordStatusDto.ACTION_CODE_SKIP);
            importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_INVALID_DATA);
            importRecordStatus.setStatusDescription(exception.getMessage());
        }
        return importRecordStatus;
    }

     (non-Javadoc)
     * @see com.myschool.exim.domain.AbstractEximManager#processRecord(com.myschool.exim.constants.EximPolicy, com.myschool.exim.dto.ImportRecordStatusDto)
     
    @Override
    protected void processRecord(EximPolicy eximPolicy, ImportRecordStatusDto importRecordStatus) {
        try {
            if (eximPolicy == EximPolicy.REFERENCE_ATTENDANCE) {
                handleReferenceAttendanceData(importRecordStatus);
            } else if (eximPolicy == EximPolicy.STUDENT_ATTENDANCE) {
                handleStudentAttendanceData(importRecordStatus);
            }
        } catch (DaoException daoException) {
            importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
            importRecordStatus.setStatusDescription(daoException.getMessage());
        }
    }

    *//**
     * Update reference attendance.
     *
     * @param referenceAttendance the reference attendance
     * @param rule the rule
     * @param fieldValue the field value
     * @return the reference attendance dto
     * @throws DataException the data exception
     * @throws ValidationException the validation exception
     *//*
    private ReferenceAttendanceDto updateReferenceAttendance(ReferenceAttendanceDto referenceAttendance,
            Rule rule, String fieldValue) throws DataException, ValidationException {
        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();

        RegisteredClassDto registeredClass = referenceAttendance.getRegisteredClass();
        if (registeredClass == null) {
            registeredClass = new RegisteredClassDto();
        }

        List<AttendanceDay> dayAttendances = referenceAttendance.getDayAttendances();
        if (dayAttendances == null) {
            dayAttendances = new ArrayList<AttendanceDay>();
        }

        SchoolDto school = registeredClass.getSchool();
        if (school == null) {
            school = new SchoolDto();
        }

        if (fieldName.equals(ReferenceAttendanceFieldNames.BRANCH_CODE)) {
            BranchDto branch = new BranchDto();
            branch.setBranchCode(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            school.setBranch(branch);
        } else if (fieldName.equals(ReferenceAttendanceFieldNames.DIVISION_CODE)) {
            DivisionDto division = new DivisionDto();
            division.setDivisionCode(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            school.setDivision(division);
        } else if (fieldName.equals(ReferenceAttendanceFieldNames.SCHOOL_NAME)) {
            school.setSchoolName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        } else if (fieldName.equals(ReferenceAttendanceFieldNames.CLASS_NAME)) {
            ClassDto classDto = new ClassDto();
            classDto.setClassName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            registeredClass.setClassDto(classDto);
        } else if (fieldName.equals(ReferenceAttendanceFieldNames.MEDIUM_NAME)) {
            MediumDto medium = new MediumDto();
            medium.setDescription(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            registeredClass.setMedium(medium);
        } else if (fieldName.equals(ReferenceAttendanceFieldNames.SECTION_NAME)) {
            SectionDto section = new SectionDto();
            section.setSectionName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            registeredClass.setSection(section);
        } else if (fieldName.equals(ReferenceAttendanceFieldNames.YEAR)) {
            referenceAttendance.setYear(Integer.parseInt(DataTypeValidator.validate(fieldValue, dataType, fieldName)));
        } else if (fieldName.equals(ReferenceAttendanceFieldNames.MONTH)) {
            int monthNumber = Integer.parseInt(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            monthValidator.validateNumber(monthNumber);
            referenceAttendance.setMonth(monthNumber);
        } else if (fieldName.startsWith(ReferenceAttendanceFieldNames.DAY_PREFIX)) {
            int dateNumber = Integer.parseInt(fieldName.substring(fieldName.indexOf(ReferenceAttendanceFieldNames.DAY_PREFIX)
                    + ReferenceAttendanceFieldNames.DAY_PREFIX.length()));
            dayValidator.validateDate(dateNumber);
            //boolean present = ConversionUtil.toBoolean(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            AttendanceDay dayAttendance = new AttendanceDay();
            Day day = new Day();
            day.setDate(dateNumber);
            dayAttendance.setDay(day);
            //dayAttendance.setPresent(present);
            dayAttendances.add(dayAttendance);
        }

        registeredClass.setSchool(school);
        referenceAttendance.setRegisteredClass(registeredClass);
        referenceAttendance.setDayAttendances(dayAttendances);
        return referenceAttendance;
    }

    *//**
     * Update student attendance.
     *
     * @param studentAttendance the student attendance
     * @param rule the rule
     * @param fieldValue the field value
     * @return the object
     * @throws DataException the data exception
     * @throws ValidationException the validation exception
     *//*
    private Object updateStudentAttendance(StudentAttendanceDto studentAttendance,
            Rule rule, String fieldValue) throws DataException, ValidationException {

        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();

        AttendanceMonth monthAttendance = (AttendanceMonth) studentAttendance.getAttendance();
        StudentDto student = studentAttendance.getStudent();

        if (student == null) {
            student = new StudentDto();
        }

        if (monthAttendance == null) {
            monthAttendance = new AttendanceMonth();
        }
        List<AttendanceDay> dayAttendances = monthAttendance.getDayAttendances();
        if (dayAttendances == null) {
            dayAttendances = new ArrayList<AttendanceDay>();
        }

        if (fieldName.equals(StudentAttendanceFieldNames.YEAR)) {
            monthAttendance.setAttendanceYear(Integer.parseInt(DataTypeValidator.validate(fieldValue, dataType, fieldName)));
        } else if (fieldName.equals(StudentAttendanceFieldNames.MONTH)) {
            int monthNumber = Integer.parseInt(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            monthValidator.validateNumber(monthNumber);
            Month month = new Month();
            month.setNumber(monthNumber);
            monthAttendance.setMonth(month);
        } else  if (fieldName.equals(StudentAttendanceFieldNames.ADMISSION_NUMBER)) {
            student.setAdmissionNumber(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            studentAttendance.setStudent(student);
        } else if (fieldName.startsWith(StudentAttendanceFieldNames.DAY_PREFIX)) {
            int dateNumber = Integer.parseInt(fieldName.substring(fieldName.indexOf(StudentAttendanceFieldNames.DAY_PREFIX)
                    + StudentAttendanceFieldNames.DAY_PREFIX.length()));
            dayValidator.validateDate(dateNumber);
            //boolean present = ConversionUtil.toBoolean(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            AttendanceDay dayAttendance = new AttendanceDay();
            Day day = new Day();
            day.setDate(dateNumber);
            dayAttendance.setDay(day);
            //dayAttendance.setPresent(present);
            dayAttendances.add(dayAttendance);
        }
        monthAttendance.setDayAttendances(dayAttendances);
        studentAttendance.setStudent(student);
        studentAttendance.setAttendance(monthAttendance);
        return studentAttendance;
    }

    *//**
     * Handle reference attendance data.
     *
     * @param importRecordStatus the import record status
     * @throws DaoException the dao exception
     *//*
    private void handleReferenceAttendanceData(
            ImportRecordStatusDto importRecordStatus) throws DaoException {
        ReferenceAttendanceDto referenceAttendance = (ReferenceAttendanceDto) importRecordStatus.getContent();
        int classId = referenceAttendance.getRegisteredClass().getClassId();
        int year = referenceAttendance.getYear();
        int month = referenceAttendance.getMonth();

        ReferenceAttendanceDto existingReferenceAttendance = attendanceDao.getReferenceAttendance(classId, year, month);
        if (existingReferenceAttendance == null) {
            if (attendanceDao.createReferenceAttendance(referenceAttendance) > 0) {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                importRecordStatus.setStatusDescription("Reference Attendance (year: " + year + ", month: " + month + ") added successfully.");
            } else {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                importRecordStatus.setStatusDescription("System encountered problems while creating reference attendance (year: " + year + ", month: " + month + ").");
            }
        } else {
            // Update the existing student.
            if (attendanceDao.updateReferenceAttendance(existingReferenceAttendance.getReferenceAttendanceId(), referenceAttendance)) {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_UPDATED);
                importRecordStatus.setStatusDescription("Reference Attendance (year: " + year + ", month: " + month + ") updated successfully.");
            } else {
                importRecordStatus.setStatusDescription("System encountered problems while updating reference attendance (year: " + year + ", month: " + month + ").");
            }
        }
    }

    *//**
     * Handle student attendance data.
     *
     * @param importRecordStatus the import record status
     * @throws DaoException the dao exception
     *//*
    private void handleStudentAttendanceData(
            ImportRecordStatusDto importRecordStatus) throws DaoException {
        
        StudentAttendanceDto studentAttendance = (StudentAttendanceDto) importRecordStatus.getContent();
        StudentDto student = studentAttendance.getStudent();
        int studentId = student.getStudentId();
        String admissionNumber = student.getAdmissionNumber();
        RegisteredClassDto registeredClassDto = student.getRegisteredClassDto();
        int classId = registeredClassDto.getClassId();
        AttendanceMonth monthAttendance = (AttendanceMonth) studentAttendance.getAttendance();
        int year = monthAttendance.getAttendanceYear();
        int month = monthAttendance.getMonth().getNumber();

        ReferenceAttendanceDto existingReferenceAttendance = attendanceDao.getReferenceAttendance(classId, year, month);
        if (existingReferenceAttendance == null) {
            importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
            importRecordStatus.setStatusDescription("Reference attendance must be added before you add student attendance for the year " + year + " and month " + month);
        } else {
            AttendanceMonth existingStudentAttendance = (AttendanceMonth) attendanceDao.getStudentAttendance(studentId, year, month);
            if (existingStudentAttendance == null) {
                if (attendanceDao.createStudentAttendance(existingReferenceAttendance, studentAttendance) > 0) {
                    importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                    importRecordStatus.setStatusDescription("Student Attendance (Admission Number: " + admissionNumber + ", year: " + year + ", month: " + month + ") added successfully.");
                } else {
                    importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                    importRecordStatus.setStatusDescription("System encountered problems while creating reference attendance (year: " + year + ", month: " + month + ").");
                }
            } else {
                int monthAttendanceId = existingStudentAttendance.getMonthAttendanceId();
                // Update the existing student.
                if (attendanceDao.updateStudentAttendance(monthAttendanceId, existingReferenceAttendance, studentAttendance)) {
                    importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_UPDATED);
                    importRecordStatus.setStatusDescription("Reference Attendance (Admission Number: " + admissionNumber + ", year: " + year + ", month: " + month + ") updated successfully.");
                } else {
                    importRecordStatus.setStatusDescription("System encountered problems while updating reference attendance (year: " + year + ", month: " + month + ").");
                }
            }
        }
    }*/

}
