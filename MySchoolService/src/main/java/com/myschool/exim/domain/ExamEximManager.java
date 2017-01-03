package com.myschool.exim.domain;

import java.text.MessageFormat;

import org.springframework.stereotype.Component;

import com.myschool.branch.dto.BranchDto;
import com.myschool.branch.dto.DivisionDto;
import com.myschool.clazz.dto.ClassDto;
import com.myschool.clazz.dto.MediumDto;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.clazz.dto.RegisteredSubjectDto;
import com.myschool.clazz.dto.SectionDto;
import com.myschool.clazz.dto.SubjectDto;
import com.myschool.common.dto.Rule;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.exam.dto.ExamDto;
import com.myschool.exam.dto.StudentExamDto;
import com.myschool.exam.dto.SubjectExamDto;
import com.myschool.exam.fields.ExamFieldNames;
import com.myschool.exam.fields.StudentExamFieldNames;
import com.myschool.exam.fields.SubjectExamFieldNames;
import com.myschool.exim.constants.EximPolicy;
import com.myschool.exim.dto.ImportRecordStatusDto;
import com.myschool.infra.cache.exception.CacheException;
import com.myschool.school.dto.SchoolDto;
import com.myschool.student.dto.StudentDto;
import com.quasar.core.exception.DataException;
import com.quasar.core.util.ConversionUtil;

/**
 * The Class ExamEximManager.
 */
@Component
public class ExamEximManager extends AbstractEximManager {

    /** The Constant EXAM_ADD_SUCCESS. */
    private static final String EXAM_ADD_SUCCESS = "Exam ({0}) added successfully";

    /** The Constant EXAM_UPDATE_FAIL. */
    private static final String EXAM_UPDATE_FAIL = "Exam ({0}) has already been defined in the system";

    /** The Constant EXAM_ADD_FAILED. */
    private static final String EXAM_ADD_FAILED = "System encountered problems while creating Exam ({0})";

    /** The Constant SUBJECT_EXAM_ADD_SUCCESS. */
    private static final String SUBJECT_EXAM_ADD_SUCCESS = "Subject ({0}) in class ({1}) added to Exam ({2})";

    /** The Constant SUBJECT_EXAM_ADD_FAIL. */
    private static final String SUBJECT_EXAM_ADD_FAIL = "System encountered a problem while adding Subject ({0}) in class ({1}) to Exam ({2})";

    /** The Constant SUBJECT_EXAM_UPDATE_SUCCESS. */
    private static final String SUBJECT_EXAM_UPDATE_SUCCESS = "Subject ({0}) in class ({1}) updated in Exam ({2})";

    /** The Constant SUBJECT_EXAM_UPDATE_FAIL. */
    private static final String SUBJECT_EXAM_UPDATE_FAIL = "System encountered a problem while updating Subject ({0}) in class ({1}) to Exam ({2})";

    /** The Constant SUBJECT_EXAM_FAIL_NO_EXAM. */
    private static final String SUBJECT_EXAM_FAIL_NO_EXAM = "Exam ({0}) must be assigned to class ({1}) to add subjects";

    /** The Constant STUDENT_EXAM_ADD_SUCCESS. */
    private static final String STUDENT_EXAM_ADD_SUCCESS = "Student ({0}) marks added for Exam ({1})";

    /** The Constant STUDENT_EXAM_ADD_FAIL. */
    private static final String STUDENT_EXAM_ADD_FAIL = "System encountered problems while adding Student ({0}) marks to Exam ({1})";

    /** The Constant STUDENT_EXAM_UPDATE_SUCCESS. */
    private static final String STUDENT_EXAM_UPDATE_SUCCESS = "Student ({0}) marks updated for Exam ({1})";

    /** The Constant STUDENT_EXAM_UPDATE_FAIL. */
    private static final String STUDENT_EXAM_UPDATE_FAIL = "System encountered problems while updating Student ({0}) marks to Exam ({1})";

    /** The Constant STUDENT_EXAM_ADD_NO_SUBJECT_IN_EXAM. */
    private static final String STUDENT_EXAM_ADD_NO_SUBJECT_IN_EXAM = "Subject ({0}) not found in Exam ({1}) or in Class ({1})";

    /** The Constant STUDENT_EXAM_ADD_NO_STUDENT. */
    private static final String STUDENT_EXAM_ADD_NO_STUDENT = "Student ({0}) not found.";

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.myschool.exim.domain.AbstractEximManager#updateContent(com.myschool
     * .exim.constants.EximPolicy, java.lang.Object,
     * com.myschool.common.dto.Rule, java.lang.String)
     */
    @Override
    protected Object updateContent(EximPolicy eximPolicy, Object content, Rule rule,
            String fieldValue) throws DataException, ValidationException {
        if (eximPolicy == EximPolicy.EXAMS) {
            if (content == null) {
                content = new ExamDto();
            }
            content = updateExam((ExamDto) content, rule, fieldValue);
        } else if (eximPolicy == EximPolicy.SUBJECTS_IN_EXAMS) {
            if (content == null) {
                content = new SubjectExamDto();
            }
            content = updateSubjectExam((SubjectExamDto) content, rule, fieldValue);
        } else if (eximPolicy == EximPolicy.STUDENT_MARKS) {
            if (content == null) {
                content = new StudentExamDto();
            }
            content = updateStudentExam((StudentExamDto) content, rule, fieldValue);
        }
        return content;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.myschool.exim.domain.AbstractEximManager#validateRecord(com.myschool
     * .exim.constants.EximPolicy, java.lang.Object)
     */
    @Override
    protected ImportRecordStatusDto validateRecord(EximPolicy eximPolicy,
            Object content) {
        ImportRecordStatusDto importRecordStatus = new ImportRecordStatusDto();

        try {
            if (eximPolicy == EximPolicy.EXAMS) {
                ExamDto exam = (ExamDto) content;
                exam.setRegisteredClass(validateRegisteredClass(exam.getRegisteredClass()));
            } else if (eximPolicy == EximPolicy.SUBJECTS_IN_EXAMS) {
                validateSubjectExam((SubjectExamDto) content);
            } else if (eximPolicy == EximPolicy.STUDENT_MARKS) {
                validateStudentExam((StudentExamDto) content);
            }
        } catch (Exception exception) {
            importRecordStatus.setActionCode(ImportRecordStatusDto.ACTION_CODE_SKIP);
            importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_INVALID_DATA);
            importRecordStatus.setStatusDescription(exception.getMessage());
        }
        return importRecordStatus;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.myschool.exim.domain.AbstractEximManager#processRecord(com.myschool
     * .exim.constants.EximPolicy, com.myschool.exim.dto.ImportRecordStatusDto)
     */
    @Override
    protected void processRecord(EximPolicy eximPolicy, ImportRecordStatusDto importRecordStatus) {
        try {
            if (eximPolicy == EximPolicy.EXAMS) {
                handleExamsData(importRecordStatus);
            } else if (eximPolicy == EximPolicy.SUBJECTS_IN_EXAMS) {
                handleSubjectsInExamsData(importRecordStatus);
            } else if (eximPolicy == EximPolicy.STUDENT_MARKS) {
                handleStudentMarksData(importRecordStatus);
            }
        } catch (Exception exception) {
            importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
            importRecordStatus.setStatusDescription(exception.getMessage());
        }
    }

    /**
     * Handle exams data.
     *
     * @param importRecordStatus the import record status
     * @throws DaoException the dao exception
     */
    private void handleExamsData(ImportRecordStatusDto importRecordStatus)
            throws DaoException {
        ExamDto exam = (ExamDto) importRecordStatus.getContent();
        String examName = exam.getExamName();
        String examDate = exam.getExamDate();
        RegisteredClassDto registeredClass = exam.getRegisteredClass();
        if (examDao.get(registeredClass.getClassId(), examName, examDate) == null) {
            if (examDao.create(exam) > 0) {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                importRecordStatus.setStatusDescription(
                        MessageFormat.format(EXAM_ADD_SUCCESS, examName));
            } else {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                importRecordStatus.setStatusDescription(
                        MessageFormat.format(EXAM_ADD_FAILED, examName));
            }
        } else {
            importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_UNPROCESSED);
            importRecordStatus.setStatusDescription(
                    MessageFormat.format(EXAM_UPDATE_FAIL, examName));
        }
    }

    /**
     * Handle exam in schools data.
     * 
     * @param importRecordStatus the import record status
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    private void handleSubjectsInExamsData(
            ImportRecordStatusDto importRecordStatus) throws DaoException,
            CacheException {
        SubjectExamDto subjectExam = (SubjectExamDto) importRecordStatus.getContent();
        ExamDto exam = subjectExam.getExam();
        RegisteredSubjectDto registeredSubject = subjectExam.getRegisteredSubject();
        SubjectDto subject = registeredSubject.getSubject();
        RegisteredClassDto registeredClass = registeredSubject.getRegisteredClass();
        ClassDto classDto = registeredClass.getClassDto();
        String className = classDto.getClassName();
        String subjectName = subject.getSubjectName();
        String examName = exam.getExamName();

        SubjectExamDto gotSubjectExam = getSubjectExam(exam.getExamId(), registeredSubject.getSubjectId());
        if (gotSubjectExam == null) {
            int create = subjectExamDao.create(exam.getExamId(),
                    registeredSubject.getSubjectId(), subjectExam.getMaximumMarks());
            if (create > 0) {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                importRecordStatus.setStatusDescription(
                        MessageFormat.format(SUBJECT_EXAM_ADD_SUCCESS, subjectName, className, examName));
            } else {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                importRecordStatus.setStatusDescription(
                        MessageFormat.format(SUBJECT_EXAM_ADD_FAIL, subjectName, className, examName));
            }
        } else {
            boolean update = subjectExamDao.update(
                    gotSubjectExam.getSubjectExamId(), exam.getExamId(),
                    registeredSubject.getSubjectId(),
                    subjectExam.getMaximumMarks());
            if (update) {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_UPDATED);
                importRecordStatus.setStatusDescription(
                        MessageFormat.format(SUBJECT_EXAM_UPDATE_SUCCESS, subjectName, className, examName));
            } else {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                importRecordStatus.setStatusDescription(
                        MessageFormat.format(SUBJECT_EXAM_UPDATE_FAIL, subjectName, className, examName));
            }
        }
    }

    /**
     * Handle student marks data.
     *
     * @param importRecordStatus the import record status
     * @throws DaoException the dao exception
     */
    private void handleStudentMarksData(
            ImportRecordStatusDto importRecordStatus) throws DaoException {
        StudentExamDto studentExam = (StudentExamDto) importRecordStatus.getContent();
        StudentDto student = studentExam.getStudent();
        int studentId = student.getStudentId();
        String admissionNumber = student.getAdmissionNumber();
        SubjectExamDto subjectExam = studentExam.getSubjectExam();
        int subjectExamId = subjectExam.getSubjectExamId();

        ExamDto exam = subjectExam.getExam();
        String examName = exam.getExamName();

        StudentExamDto gotStudentExam = studentExamDao.getStudentExam(studentId, subjectExamId);
        if (gotStudentExam == null) {
            if (studentExamDao.create(studentId, studentExam) > 0) {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                importRecordStatus.setStatusDescription(
                        MessageFormat.format(STUDENT_EXAM_ADD_SUCCESS, admissionNumber, examName));
            } else {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                importRecordStatus.setStatusDescription(
                        MessageFormat.format(STUDENT_EXAM_ADD_FAIL, admissionNumber, examName));
            }
        } else {
            studentExam.setStudentExamId(gotStudentExam.getStudentExamId());
            if (studentExamDao.update(studentExam)) {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_UPDATED);
                importRecordStatus.setStatusDescription(
                        MessageFormat.format(STUDENT_EXAM_UPDATE_SUCCESS, admissionNumber, examName));
            } else {
                importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                importRecordStatus.setStatusDescription(
                        MessageFormat.format(STUDENT_EXAM_UPDATE_FAIL, admissionNumber, examName));
            }
        }
    }

    /**
     * Validate subject exam.
     * 
     * @param subjectExam the subject exam
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     * @throws ValidationException the validation exception
     */
    private void validateSubjectExam(SubjectExamDto subjectExam)
            throws DaoException, CacheException, ValidationException {
        ExamDto exam = null;
        RegisteredSubjectDto registeredSubject = null;
        RegisteredClassDto registeredClass = null;

        registeredSubject = validateRegisteredSubject(subjectExam.getRegisteredSubject());
        subjectExam.setRegisteredSubject(registeredSubject);
        registeredClass = registeredSubject.getRegisteredClass();
        ClassDto classDto = registeredClass.getClassDto();

        exam = subjectExam.getExam();
        String examName = exam.getExamName();
        ExamDto gotExam = getExam(registeredClass.getClassId(), examName);
        if (gotExam == null || gotExam.getExamId() == 0) {
            throw new ValidationException(MessageFormat.format(SUBJECT_EXAM_FAIL_NO_EXAM,
                    examName, registeredClass.getClassId() + "-" + classDto.getClassName()));
        }
        exam.setExamId(gotExam.getExamId());
    }

    /**
     * Validate student exam.
     * 
     * @param studentExam the student exam
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     * @throws ValidationException the validation exception
     */
    private void validateStudentExam(StudentExamDto studentExam)
            throws DaoException, CacheException, ValidationException {

        SubjectExamDto subjectExam = studentExam.getSubjectExam();
        validateSubjectExam(subjectExam);

        ExamDto exam = subjectExam.getExam();
        RegisteredSubjectDto registeredSubject = subjectExam.getRegisteredSubject();

        SubjectDto subject = registeredSubject.getSubject();
        String subjectName = subject.getSubjectName();
        String examName = exam.getExamName();
        String className = registeredSubject.getRegisteredClass().getClassDto().getClassName();

        SubjectExamDto gotSubjectExam = getSubjectExam(exam.getExamId(),
                registeredSubject.getSubjectId());
        if (gotSubjectExam == null || gotSubjectExam.getSubjectExamId() == 0) {
            throw new ValidationException(
                    MessageFormat.format(STUDENT_EXAM_ADD_NO_SUBJECT_IN_EXAM, subjectName, examName, className));
        }
        subjectExam.setSubjectExamId(gotSubjectExam.getSubjectExamId());
        studentExam.setSubjectExamId(gotSubjectExam.getSubjectExamId());

        StudentDto student = studentExam.getStudent();
        String admissionNumber = student.getAdmissionNumber();
        // Update if student already exists or add if the student does not exist.
        StudentDto existingStudent = getStudent(admissionNumber);
        // Create a new student
        if (existingStudent == null || existingStudent.getStudentId() == 0) {
            throw new ValidationException(
                    MessageFormat.format(STUDENT_EXAM_ADD_NO_STUDENT, admissionNumber));
        }
        student.setStudentId(existingStudent.getStudentId());
    }

    /**
     * Update exam.
     *
     * @param exam the exam
     * @param rule the rule
     * @param fieldValue the field value
     * @return the exam
     * @throws DataException the data exception
     */
    private ExamDto updateExam(ExamDto exam, Rule rule,
            String fieldValue) throws DataException {
        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();

        RegisteredClassDto registeredClass = exam.getRegisteredClass();
        if (registeredClass == null) {
            registeredClass = new RegisteredClassDto();
        }

        SchoolDto school = registeredClass.getSchool();
        if (school == null) {
            school = new SchoolDto();
        }

        String validatedValue = DataTypeValidator.validate(fieldValue, dataType, fieldName);
        if (fieldName.equals(ExamFieldNames.BRANCH_CODE)) {
            BranchDto branch = new BranchDto();
            branch.setBranchCode(validatedValue);
            school.setBranch(branch);
        } else if (fieldName.equals(ExamFieldNames.DIVISION_CODE)) {
            DivisionDto division = new DivisionDto();
            division.setDivisionCode(validatedValue);
            school.setDivision(division);
        } else if (fieldName.equals(ExamFieldNames.SCHOOL_NAME)) {
            school.setSchoolName(validatedValue);
        } else if (fieldName.equals(ExamFieldNames.CLASS_NAME)) {
            ClassDto classDto = new ClassDto();
            classDto.setClassName(validatedValue);
            registeredClass.setClassDto(classDto);
        } else if (fieldName.equals(ExamFieldNames.MEDIUM_NAME)) {
            MediumDto medium = new MediumDto();
            medium.setDescription(validatedValue);
            registeredClass.setMedium(medium);
        } else if (fieldName.equals(ExamFieldNames.SECTION_NAME)) {
            SectionDto section = new SectionDto();
            section.setSectionName(validatedValue);
            registeredClass.setSection(section);
        } else if (fieldName.equals(ExamFieldNames.EXAM_NAME)) {
            exam.setExamName(validatedValue);
        } else if (fieldName.equals(ExamFieldNames.EXAM_DATE)) {
            exam.setExamDate(validatedValue);
        } else if (fieldName.equals(ExamFieldNames.EXAM_COMPLETED)) {
            exam.setExamCompleted(ConversionUtil.toBoolean(validatedValue));
        }

        registeredClass.setSchool(school);
        exam.setRegisteredClass(registeredClass);
        return exam;
    }

    /**
     * Update subject exam.
     *
     * @param subjectExam the subject exam
     * @param rule the rule
     * @param fieldValue the field value
     * @return the subject exam dto
     * @throws DataException the data exception
     */
    private SubjectExamDto updateSubjectExam(SubjectExamDto subjectExam, Rule rule,
            String fieldValue) throws DataException {
        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();

        RegisteredSubjectDto registeredSubject = subjectExam.getRegisteredSubject();
        if (registeredSubject == null) {
            registeredSubject = new RegisteredSubjectDto();
        }

        RegisteredClassDto registeredClass = registeredSubject.getRegisteredClass();
        if (registeredClass == null) {
            registeredClass = new RegisteredClassDto();
        }

        SchoolDto school = registeredClass.getSchool();
        if (school == null) {
            school = new SchoolDto();
        }

        ExamDto exam = subjectExam.getExam();
        if (exam == null) {
            exam = new ExamDto();
        }

        if (fieldName.equals(SubjectExamFieldNames.BRANCH_CODE)) {
            BranchDto branch = new BranchDto();
            branch.setBranchCode(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            school.setBranch(branch);
        } else if (fieldName.equals(SubjectExamFieldNames.DIVISION_CODE)) {
            DivisionDto division = new DivisionDto();
            division.setDivisionCode(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            school.setDivision(division);
        } else if (fieldName.equals(SubjectExamFieldNames.SCHOOL_NAME)) {
            school.setSchoolName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        } else if (fieldName.equals(SubjectExamFieldNames.CLASS_NAME)) {
            ClassDto classDto = new ClassDto();
            classDto.setClassName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            registeredClass.setClassDto(classDto);
        } else if (fieldName.equals(SubjectExamFieldNames.MEDIUM_NAME)) {
            MediumDto medium = new MediumDto();
            medium.setDescription(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            registeredClass.setMedium(medium);
        } else if (fieldName.equals(SubjectExamFieldNames.SECTION_NAME)) {
            SectionDto section = new SectionDto();
            section.setSectionName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            registeredClass.setSection(section);
        } else if (fieldName.equals(SubjectExamFieldNames.EXAM_NAME)) {
            exam.setExamName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        } else if (fieldName.equals(SubjectExamFieldNames.SUBJECT_NAME)) {
            SubjectDto subject = new SubjectDto();
            subject.setSubjectName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            registeredSubject.setSubject(subject);
        } else if (fieldName.equals(SubjectExamFieldNames.MAXIMUM_MARKS)) {
            subjectExam.setMaximumMarks(
                    Integer.parseInt(DataTypeValidator.validate(fieldValue, dataType, fieldName)));
        }

        registeredClass.setSchool(school);
        registeredSubject.setRegisteredClass(registeredClass);
        subjectExam.setExam(exam);
        subjectExam.setRegisteredSubject(registeredSubject);
        return subjectExam;
    }

    /**
     * Update student exam.
     *
     * @param studentExam the student exam
     * @param rule the rule
     * @param fieldValue the field value
     * @return the student exam dto
     * @throws DataException the data exception
     */
    private StudentExamDto updateStudentExam(StudentExamDto studentExam, Rule rule,
            String fieldValue) throws DataException {
        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();

        SubjectExamDto subjectExam = studentExam.getSubjectExam();
        if (subjectExam == null) {
            subjectExam = new SubjectExamDto();
        }
        RegisteredSubjectDto registeredSubject = subjectExam.getRegisteredSubject();
        if (registeredSubject == null) {
            registeredSubject = new RegisteredSubjectDto();
        }

        RegisteredClassDto registeredClass = registeredSubject.getRegisteredClass();
        if (registeredClass == null) {
            registeredClass = new RegisteredClassDto();
        }

        SchoolDto school = registeredClass.getSchool();
        if (school == null) {
            school = new SchoolDto();
        }

        ExamDto exam = subjectExam.getExam();
        if (exam == null) {
            exam = new ExamDto();
        }

        if (fieldName.equals(StudentExamFieldNames.BRANCH_CODE)) {
            BranchDto branch = new BranchDto();
            branch.setBranchCode(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            school.setBranch(branch);
        } else if (fieldName.equals(StudentExamFieldNames.DIVISION_CODE)) {
            DivisionDto division = new DivisionDto();
            division.setDivisionCode(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            school.setDivision(division);
        } else if (fieldName.equals(StudentExamFieldNames.SCHOOL_NAME)) {
            school.setSchoolName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        } else if (fieldName.equals(StudentExamFieldNames.CLASS_NAME)) {
            ClassDto classDto = new ClassDto();
            classDto.setClassName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            registeredClass.setClassDto(classDto);
        } else if (fieldName.equals(StudentExamFieldNames.MEDIUM_NAME)) {
            MediumDto medium = new MediumDto();
            medium.setDescription(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            registeredClass.setMedium(medium);
        } else if (fieldName.equals(StudentExamFieldNames.SECTION_NAME)) {
            SectionDto section = new SectionDto();
            section.setSectionName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            registeredClass.setSection(section);
        } else if (fieldName.equals(StudentExamFieldNames.EXAM_NAME)) {
            exam.setExamName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        } else if (fieldName.equals(StudentExamFieldNames.SUBJECT_NAME)) {
            SubjectDto subject = new SubjectDto();
            subject.setSubjectName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            registeredSubject.setSubject(subject);
        } else if (fieldName.equals(StudentExamFieldNames.ADMISSION_NUMBER)) {
            StudentDto student = new StudentDto();
            student.setAdmissionNumber(DataTypeValidator.validate(fieldValue, dataType, fieldName));
            studentExam.setStudent(student);
        } else if (fieldName.equals(StudentExamFieldNames.OBTAINED_MARKS)) {
            studentExam.setObtainedMarks(
                    Integer.parseInt(DataTypeValidator.validate(fieldValue, dataType, fieldName)));
        }

        registeredClass.setSchool(school);
        registeredSubject.setRegisteredClass(registeredClass);
        subjectExam.setExam(exam);
        subjectExam.setRegisteredSubject(registeredSubject);
        studentExam.setSubjectExam(subjectExam);
        return studentExam;
    }

}
