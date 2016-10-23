package com.myschool.exim.domain;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.application.dao.DocumentDao;
import com.myschool.application.dao.RelationshipDao;
import com.myschool.attendance.dao.AttendanceDao;
import com.myschool.branch.dao.BranchDao;
import com.myschool.branch.dao.DivisionDao;
import com.myschool.branch.dao.RegionDao;
import com.myschool.branch.dao.StateDao;
import com.myschool.branch.dto.BranchDto;
import com.myschool.branch.dto.DivisionDto;
import com.myschool.clazz.dao.ClassDao;
import com.myschool.clazz.dao.MediumDao;
import com.myschool.clazz.dao.RegisteredClassDao;
import com.myschool.clazz.dao.RegisteredSubjectDao;
import com.myschool.clazz.dao.SectionDao;
import com.myschool.clazz.dao.SubjectDao;
import com.myschool.clazz.dto.ClassDto;
import com.myschool.clazz.dto.MediumDto;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.clazz.dto.RegisteredSubjectDto;
import com.myschool.clazz.dto.SectionDto;
import com.myschool.clazz.dto.SubjectDto;
import com.myschool.common.constants.CacheKeyConstants;
import com.myschool.common.constants.DocumentApplicability;
import com.myschool.common.dto.DocumentDto;
import com.myschool.common.dto.FamilyMemberDto;
import com.myschool.common.dto.Relationship;
import com.myschool.common.dto.Rule;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.util.StringUtil;
import com.myschool.employee.dao.DesignationDao;
import com.myschool.employee.dto.DesignationDto;
import com.myschool.exam.dao.ExamDao;
import com.myschool.exam.dao.ExamGradeDao;
import com.myschool.exam.dao.SubjectExamDao;
import com.myschool.exam.dto.ExamDto;
import com.myschool.exam.dto.SubjectExamDto;
import com.myschool.exim.constants.EximPolicy;
import com.myschool.exim.constants.UploadStatus;
import com.myschool.exim.dao.StudentExamDao;
import com.myschool.exim.dto.ImportRecordStatusDto;
import com.myschool.exim.dto.ImportStatusDto;
import com.myschool.exim.dto.UploadFileTrackerDto;
import com.myschool.exim.dto.UploadRecordTrackerDto;
import com.myschool.exim.exception.EximException;
import com.myschool.exim.util.ImportUtil;
import com.myschool.infra.cache.agent.OutMemoryCacheAgent;
import com.myschool.infra.cache.exception.CacheException;
import com.myschool.infra.filesystem.agent.RulesFileSystem;
import com.myschool.infra.filesystem.agent.TempFileSystem;
import com.myschool.infra.filesystem.util.FileUtil;
import com.myschool.school.dao.SchoolDao;
import com.myschool.school.dto.SchoolDto;
import com.myschool.student.dao.StudentDao;
import com.myschool.student.dao.StudentDocumentDao;
import com.myschool.student.dao.StudentFamilyDao;
import com.myschool.student.domain.StudentManager;
import com.myschool.student.dto.StudentDto;
import com.myschool.user.constants.UserType;
import com.myschool.user.dao.UserDao;

/**
 * The Class AbstractEximManager.
 */
@Component
public abstract class AbstractEximManager {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(AbstractEximManager.class);

    /** The out memory cache agent. */
    @Autowired
    protected OutMemoryCacheAgent outMemoryCacheAgent;

    /** The upload manager. */
    @Autowired
    protected UploadManager uploadManager;

    /** The rules file system. */
    @Autowired
    protected RulesFileSystem rulesFileSystem;

    /** The temp file system. */
    @Autowired
    protected TempFileSystem tempFileSystem;

    /** The student manager. */
    @Autowired
    protected StudentManager studentManager;

    /** The branch dao. */
    @Autowired
    protected BranchDao branchDao;

    /** The division dao. */
    @Autowired
    protected DivisionDao divisionDao;

    /** The school dao. */
    @Autowired
    protected SchoolDao schoolDao;

    /** The class dao. */
    @Autowired
    protected ClassDao classDao;

    @Autowired
    protected RegisteredClassDao registeredClassDao;

    /** The medium dao. */
    @Autowired
    protected MediumDao mediumDao;

    /** The section dao. */
    @Autowired
    protected SectionDao sectionDao;

    /** The attendance dao. */
    @Autowired
    protected AttendanceDao attendanceDao;

    /** The region dao. */
    @Autowired
    protected RegionDao regionDao;

    /** The subject dao. */
    @Autowired
    protected SubjectDao subjectDao;

    /** The registered subject dao. */
    @Autowired
    protected RegisteredSubjectDao registeredSubjectDao;

    /** The exam dao. */
    @Autowired
    protected ExamDao examDao;

    /** The student dao. */
    @Autowired
    protected StudentDao studentDao;

    /** The student document dao. */
    @Autowired
    protected StudentDocumentDao studentDocumentDao;

    @Autowired
    protected StudentFamilyDao studentFamilyDao;

    /** The subject exam. */
    @Autowired
    protected SubjectExamDao subjectExamDao;

    /** The user dao. */
    @Autowired
    protected UserDao userDao;

    /** The student exam dao. */
    @Autowired
    protected StudentExamDao studentExamDao;

    /** The exam grade dao. */
    @Autowired
    protected ExamGradeDao examGradeDao;

    /** The state dao. */
    @Autowired
    protected StateDao stateDao;

    /** The designation dao. */
    @Autowired
    protected DesignationDao designationDao;

    /** The document dao. */
    @Autowired
    protected DocumentDao documentDao;

    /** The relationship dao. */
    @Autowired
    private RelationshipDao relationshipDao;

    /** The region name. */
    protected String regionName;

    /**
     * Update content.
     * 
     * @param eximPolicy the exim policy
     * @param content the content
     * @param rule the rule
     * @param fieldValue the field value
     * @return the object
     * @throws DataException the data exception
     * @throws ValidationException the validation exception
     */
    protected abstract Object updateContent(EximPolicy eximPolicy,
            Object content, Rule rule, String fieldValue) throws DataException,
            ValidationException;

    /**
     * Validate record.
     * 
     * @param eximPolicy the exim policy
     * @param content the content
     * @return the import record status dto
     */
    protected abstract ImportRecordStatusDto validateRecord(EximPolicy eximPolicy, Object content);

    /**
     * Process record.
     * 
     * @param eximPolicy the exim policy
     * @param importRecordStatus the import record status
     */
    protected abstract void processRecord(EximPolicy eximPolicy, ImportRecordStatusDto importRecordStatus);


    /**
     * Upload data.
     * 
     * @param eximPolicy the exim policy
     * @param uploadFile the upload file
     * @param uploadFileTracker the upload file tracker
     * @return the import status dto
     * @throws DataException the data exception
     */
    public ImportStatusDto uploadData(EximPolicy eximPolicy, File uploadFile,
            UploadFileTrackerDto uploadFileTracker) throws DataException {
        int numberOfRows = 0;

        FileInputStream fileInputStream = null;

        HSSFWorkbook workbook = null;
        HSSFSheet currentSheet = null;
        HSSFRow currentRow = null;

        ImportStatusDto importStatusDto = null;
        ImportRecordStatusDto importRecordStatus = null;
        UploadRecordTrackerDto uploadRecordTracker = new UploadRecordTrackerDto();
        List<ImportRecordStatusDto> importRecordStatusList = new ArrayList<ImportRecordStatusDto>();

        try {
            // Exim policy validation
            if (eximPolicy == null) {
                throw new EximException("Invalid exim policy: " + eximPolicy);
            }
            // Create a caching point for faster access.
            regionName = CacheKeyConstants.TEMP_REGION_PREFIX + eximPolicy.name();
            outMemoryCacheAgent.createRegion(regionName);

            // Do preliminary file validation
            String invalidFileMessage = "Upload file does not exist or is not readable.";
            FileUtil.checkFile(uploadFile, invalidFileMessage, invalidFileMessage);
            // Update the file tracker status initially, only if a upload tracker is associated.
            if (uploadFileTracker != null) {
                uploadFileTracker.setRemarks("File has been picked up for processing.");
                uploadFileTracker.setUploadStatus(UploadStatus.STARTED);
                uploadFileTracker.setUploadStartTime(new Timestamp(new Date().getTime()));
                updateUploadFileTracker(uploadFileTracker);
            }
            // Get the list of rules
            List<Rule> rules = rulesFileSystem.getRules(eximPolicy);
            if (rules == null || rules.isEmpty()) {
                throw new DataException("Rules are not defined for " + eximPolicy);
            }
            fileInputStream = new FileInputStream(uploadFile);
            workbook = new HSSFWorkbook(fileInputStream);
            // Consider only the first sheet.
            currentSheet = workbook.getSheetAt(0);

            if (currentSheet != null) {
                numberOfRows = currentSheet.getLastRowNum();
                // Update total number of record
                if (uploadFileTracker != null) {
                    // Exclude header row.
                    uploadFileTracker.setTotalRecords(numberOfRows);
                    updateUploadFileTracker(uploadFileTracker);
                }
                StringBuffer recordData = new StringBuffer();
                // First row will be the header row. SKIP HEADER RECORD.
                for (int row = 1; row <= numberOfRows; row++) {
                    try {
                        importRecordStatus = new ImportRecordStatusDto();
                        currentRow = currentSheet.getRow(row);
                        if (currentRow == null) {
                            continue;
                        }
                        if (uploadFileTracker != null) {
                            // create upload record tracker and get it
                            uploadRecordTracker.setRecordNumber(currentRow.getRowNum());
                            uploadRecordTracker.setUploadStatus(UploadStatus.STARTED);
                        }
                        Object content = getContent(eximPolicy, rules, currentRow, recordData);
                        importRecordStatus = validateRecord(eximPolicy, content);

                        if (uploadFileTracker != null) {
                            uploadRecordTracker.setRecordData(recordData.toString());
                        }

                        if (importRecordStatus == null) {
                            if (uploadFileTracker != null) {
                                uploadRecordTracker.setUploadStatus(UploadStatus.FAILED);
                                uploadRecordTracker.setRemarks("Unable to process the record.");
                            }
                        } else {
                            importRecordStatus.setContent(content);
                            int actionCode = importRecordStatus.getActionCode();
                            if (actionCode == ImportRecordStatusDto.ACTION_CODE_SKIP) {
                                if (uploadFileTracker != null) {
                                    uploadRecordTracker.setUploadStatus(UploadStatus.FAILED);
                                    uploadRecordTracker.setRemarks(importRecordStatus.getStatusDescription());
                                }
                            } else {
                                // update importRecordStatus status inside processRecord method.
                                processRecord(eximPolicy, importRecordStatus);
                                if (uploadFileTracker != null) {
                                    int statusCode = importRecordStatus.getStatusCode();
                                    if (statusCode == ImportRecordStatusDto.STATUS_FAILED
                                            || statusCode == ImportRecordStatusDto.STATUS_INVALID_DATA
                                            || statusCode == ImportRecordStatusDto.STATUS_UNPROCESSED) {
                                        uploadRecordTracker.setUploadStatus(UploadStatus.FAILED);
                                    } else {
                                        uploadRecordTracker.setUploadStatus(UploadStatus.COMPLETED);
                                    }
                                    uploadRecordTracker.setRemarks(importRecordStatus.getStatusDescription());
                                }
                            }
                        }
                    } catch (Exception exception) {
                        if (uploadFileTracker != null) {
                            uploadRecordTracker.setUploadStatus(UploadStatus.FAILED);
                            uploadRecordTracker.setRemarks(exception.getMessage());
                        }
                        importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                        importRecordStatus.setStatusDescription(exception.getMessage());
                    } finally {
                        try {
                            if (uploadFileTracker != null) {
                                int uploadFileId = uploadFileTracker.getUploadFileId();
                                uploadManager.createUploadRecordTracker(uploadFileId, uploadRecordTracker);
                                // update number of records processed.
                                uploadFileTracker.setProcessedRecords(uploadFileTracker.getProcessedRecords() + 1);
                                updateUploadFileTracker(uploadFileTracker);
                            }
                        } catch (DataException dataException) {
                            LOGGER.fatal(dataException.getMessage(), dataException);
                        }
                        importRecordStatusList.add(importRecordStatus);
                    }
                }
            }
            if (uploadFileTracker != null) {
                uploadFileTracker.setUploadStatus(UploadStatus.COMPLETED);
                uploadFileTracker.setRemarks("File has been processed successfully.");
            }
        } catch (Exception exception) {
            if (uploadFileTracker != null) {
                uploadFileTracker.setUploadStatus(UploadStatus.FAILED);
                uploadFileTracker.setRemarks(exception.getMessage());
            }
            throw new DataException(exception.getMessage(), exception);
        } finally {
            try {
                importStatusDto = ImportUtil.getImportStatus(importRecordStatusList);
                if (uploadFileTracker != null) {
                    // Update the upload file tracker status,
                    uploadFileTracker.setUploadEndTime(new Timestamp(new Date().getTime()));
                    updateUploadFileTracker(uploadFileTracker);
                }
                if (regionName != null) {
                    // Remove the cache point
                    outMemoryCacheAgent.destroyRegion(regionName);
                }
            } catch (CacheException cacheException) {
                throw new DataException(cacheException.getMessage(), cacheException);
            }
        }
        return importStatusDto;
    }

    /**
     * Gets the content.
     * 
     * @param eximPolicy the exim policy
     * @param rules the rules
     * @param currentRow the current row
     * @param recordData the record data
     * @return the content
     * @throws EximException the exim exception
     */
    protected Object getContent(EximPolicy eximPolicy, List<Rule> rules,
            HSSFRow currentRow, StringBuffer recordData) throws EximException {
        int fieldPosition = 0;
        String fieldValue = null;
        Object content = null;

        try {
            recordData.setLength(0);
            short numberOfColumns = currentRow.getLastCellNum();
            int numberOfRules = rules.size();

            if (numberOfRules != numberOfColumns) {
                throw new EximException("Expected " + numberOfRules + " columns. Found " + numberOfColumns);
            }
            for (Rule rule : rules) {
                fieldPosition = rule.getFieldPosition();
                fieldValue = ImportUtil.getCellValue(currentRow, fieldPosition, rule.getFieldName());
                recordData.append(fieldValue).append("|");
                ImportUtil.processPreliminaryRules(rule, fieldValue);
                content = updateContent(eximPolicy, content, rule, fieldValue);
            }
        } catch (DataException dataException) {
            throw new EximException(dataException.getMessage(), dataException);
        } catch (ValidationException validationException) {
            throw new EximException(validationException.getMessage(), validationException);
        }
        return content;
    }

    /**
     * Put entry.
     * 
     * @param prefix the prefix
     * @param key the key
     * @param object the object
     * @throws CacheException the cache exception
     */
    protected void putEntry(String prefix, String key, Object object) throws CacheException {
        if (object != null) {
            outMemoryCacheAgent.putEntry(regionName, prefix + "_" + key, object);
        }
    }

    /**
     * Put entry.
     * 
     * @param key the key
     * @param object the object
     * @throws CacheException the cache exception
     */
    protected void putEntry(String key, Object object) throws CacheException {
        if (object != null) {
            outMemoryCacheAgent.putEntry(regionName, key, object);
        }
    }

    /**
     * Gets the entry.
     * 
     * @param prefix the prefix
     * @param key the key
     * @return the entry
     * @throws CacheException the cache exception
     */
    protected Object getEntry(String prefix, String key) throws CacheException {
        return outMemoryCacheAgent.getEntry(regionName, prefix + "_" + key);
    }

    /**
     * Gets the entry.
     * 
     * @param key the key
     * @return the entry
     * @throws CacheException the cache exception
     */
    protected Object getEntry(String key) throws CacheException {
        return outMemoryCacheAgent.getEntry(regionName, key);
    }

    /**
     * Update upload file tracker.
     * 
     * @param uploadFileTracker the upload file tracker
     * @throws DataException the data exception
     */
    private void updateUploadFileTracker(UploadFileTrackerDto uploadFileTracker)
            throws DataException {
        if (uploadFileTracker != null) {
            uploadManager.updateUploadFileTracker(
                    uploadFileTracker.getUploadFileId(), uploadFileTracker);
        }
    }

    /**
     * Gets the branch.
     * 
     * @param branchCode the branch code
     * @return the branch
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    protected BranchDto getBranch(String branchCode) throws DaoException, CacheException {
        BranchDto branch = null;
        Object entry = getEntry(CacheKeyConstants.BRANCH, branchCode);
        if (entry == null) {
            branch = branchDao.get(branchCode);
            putEntry(CacheKeyConstants.BRANCH, branchCode, branch);
        } else {
            branch = (BranchDto) entry;
        }
        return branch;
    }

    /**
     * Gets the division.
     * 
     * @param divisionCode the division code
     * @return the division
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    protected DivisionDto getDivision(String divisionCode) throws DaoException, CacheException {
        DivisionDto division = null;
        Object entry = getEntry(CacheKeyConstants.DIVISION, divisionCode);
        if (entry == null) {
            division = divisionDao.get(divisionCode);
            putEntry(CacheKeyConstants.DIVISION, divisionCode, division);
        } else {
            division = (DivisionDto) entry;
        }
        return division;
    }

    /**
     * Gets the school.
     * 
     * @param schoolDto the school dto
     * @return the school
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    protected SchoolDto getSchool(SchoolDto schoolDto) throws DaoException, CacheException {
        SchoolDto school = null;
        BranchDto branch = schoolDto.getBranch();
        DivisionDto division = schoolDto.getDivision();

        String key = branch.getBranchCode() + division.getDivisionCode() + schoolDto.getSchoolName();
        Object entry = getEntry(CacheKeyConstants.SCHOOL, key);
        if (entry == null) {
            school = schoolDao.get(schoolDto);
            putEntry(CacheKeyConstants.SCHOOL, key, school);
        } else {
            school = (SchoolDto) entry;
        }
        return school;
    }

    /**
     * Gets the class.
     * 
     * @param className the class name
     * @return the class
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    protected ClassDto getClass(String className) throws DaoException, CacheException {
        ClassDto classDto = null;
        Object entry = getEntry(CacheKeyConstants.CLASS, className);
        if (entry == null) {
            classDto = classDao.get(className);
            putEntry(CacheKeyConstants.CLASS, className, classDto);
        } else {
            classDto = (ClassDto) entry;
        }
        return classDto;
    }

    /**
     * Gets the registered class.
     * 
     * @param registeredClassDto the registered class dto
     * @return the registered class
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    protected RegisteredClassDto getRegisteredClass(
            RegisteredClassDto registeredClassDto) throws DaoException,
            CacheException {
        RegisteredClassDto registeredClass = null;

        SchoolDto school = registeredClassDto.getSchool();
        ClassDto classDto = registeredClassDto.getClassDto();
        MediumDto medium = registeredClassDto.getMedium();
        SectionDto section = registeredClassDto.getSection();
        BranchDto branch = school.getBranch();
        DivisionDto division = school.getDivision();

        String key = branch.getBranchCode() + division.getDivisionCode()
                + school.getSchoolName() + classDto.getClassName()
                + medium.getDescription() + section.getSectionName();

        Object entry = getEntry(CacheKeyConstants.REGISTERED_CLASS, key);
        if (entry == null) {
            registeredClass = registeredClassDao.get(registeredClassDto);
            putEntry(CacheKeyConstants.REGISTERED_CLASS, key, registeredClass);
        } else {
            registeredClass = (RegisteredClassDto) entry;
        }
        return registeredClass;
    }

    /**
     * Gets the medium.
     * 
     * @param mediumName the medium name
     * @return the medium
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    protected MediumDto getMedium(String mediumName) throws DaoException, CacheException {
        MediumDto medium = null;
        Object entry = getEntry(CacheKeyConstants.MEDIUM, mediumName);
        if (entry == null) {
            medium = mediumDao.get(mediumName);
            putEntry(CacheKeyConstants.MEDIUM, mediumName, medium);
        } else {
            medium = (MediumDto) entry;
        }
        return medium;
    }

    /**
     * Gets the section.
     * 
     * @param sectionName the section name
     * @return the section
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    protected SectionDto getSection(String sectionName) throws DaoException, CacheException {
        SectionDto section = null;
        Object entry = getEntry(CacheKeyConstants.SECION, sectionName);
        if (entry == null) {
            section = sectionDao.get(sectionName);
            putEntry(CacheKeyConstants.SECION, sectionName, section);
        } else {
            section = (SectionDto) entry;
        }
        return section;
    }

    /**
     * Gets the subject.
     * 
     * @param subjectName the subject name
     * @return the subject
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    protected SubjectDto getSubject(String subjectName) throws DaoException, CacheException {
        SubjectDto subject = null;
        Object entry = getEntry(CacheKeyConstants.SUBJECT, subjectName);
        if (entry == null) {
            subject = subjectDao.get(subjectName);
            putEntry(CacheKeyConstants.SUBJECT, subjectName, subject);
        } else {
            subject = (SubjectDto) entry;
        }
        return subject;
    }

    /**
     * Gets the document.
     * 
     * @param documentName the document name
     * @return the document
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    protected DocumentDto getDocument(String documentName) throws DaoException, CacheException {
        DocumentDto subject = null;
        Object entry = getEntry(CacheKeyConstants.DOCUMENT, documentName);
        if (entry == null) {
            subject = documentDao.get(documentName);
            putEntry(CacheKeyConstants.DOCUMENT, documentName, subject);
        } else {
            subject = (DocumentDto) entry;
        }
        return subject;
    }

    /**
     * Gets the exam.
     * 
     * @param classId the class id
     * @param examName the exam name
     * @return the exam
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    protected ExamDto getExam(int classId, String examName) throws DaoException, CacheException {
        ExamDto exam = null;
        String key = classId + examName;
        Object entry = getEntry(CacheKeyConstants.EXAM, key);
        if (entry == null) {
            exam = examDao.get(classId, examName);
            putEntry(CacheKeyConstants.EXAM, key, exam);
        } else {
            exam = (ExamDto) entry;
        }
        return exam;
    }

    /**
     * Gets the designation.
     * 
     * @param designationId the designation id
     * @return the designation
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    protected DesignationDto getDesignation(int designationId) throws DaoException, CacheException {
        DesignationDto designation = null;

        String key = String.valueOf(designationId);
        Object entry = getEntry(CacheKeyConstants.DESIGNATION, key);
        if (entry == null) {
            designation = designationDao.get(designationId);
            putEntry(CacheKeyConstants.DESIGNATION, key, designation);
        } else {
            designation = (DesignationDto) entry;
        }
        return designation;
    }

    /**
     * Gets the student.
     * 
     * @param admissionNumber the admission number
     * @return the student
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    protected StudentDto getStudent(String admissionNumber) throws DaoException, CacheException {
        StudentDto student = null;
        Object entry = getEntry(CacheKeyConstants.STUDENT, admissionNumber);
        if (entry == null) {
            student = studentDao.get(admissionNumber);
            if (student != null) {
                student.setFamilyMembers(studentFamilyDao.getByStudent(admissionNumber));
            }
            putEntry(CacheKeyConstants.STUDENT, admissionNumber, student);
        } else {
            student = (StudentDto) entry;
        }
        return student;
    }

    /**
     * Gets the famiy member.
     * 
     * @param studentId the student id
     * @param relationship the relationship
     * @param familyMemberName the family member name
     * @return the famiy member
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    protected FamilyMemberDto getFamiyMember(int studentId,
            Relationship relationship, String familyMemberName)
            throws DaoException, CacheException {
        FamilyMemberDto familyMember = null;
        String key = studentId + relationship.getCode() + familyMemberName;
        Object entry = getEntry(CacheKeyConstants.FAMILY_MEMBER, key);
        if (entry == null) {
            familyMember = studentFamilyDao.get(studentId, relationship, familyMemberName);
            putEntry(CacheKeyConstants.FAMILY_MEMBER, key, familyMember);
        } else {
            familyMember = (FamilyMemberDto) entry;
        }
        return familyMember;
    }

    /**
     * Gets the subject exam.
     * 
     * @param examId the exam id
     * @param registeredSubjectId the registered subject id
     * @return the subject exam
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    protected SubjectExamDto getSubjectExam(int examId, int registeredSubjectId)
            throws DaoException, CacheException {
        SubjectExamDto subejctExam = null;
        String key = examId + "" + registeredSubjectId;
        Object entry = getEntry(CacheKeyConstants.SUBJECT_EXAM, key);
        if (entry == null) {
            subejctExam = subjectExamDao.getSubjectExam(examId, registeredSubjectId);
            putEntry(CacheKeyConstants.SUBJECT_EXAM, key, subejctExam);
        } else {
            subejctExam = (SubjectExamDto) entry;
        }
        return subejctExam;
    }

    /**
     * Gets the registered subject.
     * 
     * @param classId the class id
     * @param subjectId the subject id
     * @return the registered subject
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    protected RegisteredSubjectDto getRegisteredSubject(int classId,
            int subjectId) throws DaoException, CacheException {
        RegisteredSubjectDto registeredSubject = null;

        String cacheKey = classId + "" + subjectId;
        Object entry = getEntry(CacheKeyConstants.REGISTERED_SUBJECT, cacheKey);
        if (entry == null) {
            registeredSubject = registeredSubjectDao.get(classId, subjectId);
            putEntry(CacheKeyConstants.REGISTERED_SUBJECT, cacheKey, registeredSubject);
        } else {
            registeredSubject = (RegisteredSubjectDto) entry;
        }
        return registeredSubject;
    }

    /**
     * Validate branch.
     * 
     * @param inBranch the in branch
     * @return the branch dto
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    protected BranchDto validateBranch(BranchDto inBranch)
            throws ValidationException, DaoException, CacheException {
        if (inBranch == null) {
            throw new ValidationException("Branch information is not specified.");
        }
        String branchCode = inBranch.getBranchCode();
        if (StringUtil.isNullOrBlank(branchCode)) {
            throw new ValidationException("Branch code is not specified.");
        }
        BranchDto outBranch = getBranch(branchCode);
        if (outBranch.getBranchId() == 0) {
            throw new ValidationException("Branch (" + branchCode + ") does not exists.");
        }
        return outBranch;
    }

    /**
     * Validate division.
     * 
     * @param inDivision the in division
     * @return the division dto
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    protected DivisionDto validateDivision(DivisionDto inDivision)
            throws ValidationException, DaoException, CacheException {
        if (inDivision == null) {
            throw new ValidationException("Division information is not specified.");
        }
        String divisionCode = inDivision.getDivisionCode();
        if (StringUtil.isNullOrBlank(divisionCode)) {
            throw new ValidationException("Division code is not specified.");
        }
        DivisionDto outDivision = getDivision(divisionCode);
        if (outDivision == null || outDivision.getDivisionId() == 0) {
            throw new ValidationException("Division (" + divisionCode + ") does not exists.");
        }
        return outDivision;
    }

    /**
     * Validate school.
     * 
     * @param inSchool the in school
     * @return the school dto
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    protected SchoolDto validateSchool(SchoolDto inSchool)
            throws ValidationException, DaoException, CacheException {
        if (inSchool == null) {
            throw new ValidationException("School information is not specified.");
        }
        String schoolName = inSchool.getSchoolName();
        if (StringUtil.isNullOrBlank(schoolName)) {
            throw new ValidationException("School name is not specified.");
        }
        inSchool.setDivision(validateDivision(inSchool.getDivision()));
        inSchool.setBranch(validateBranch(inSchool.getBranch()));
        SchoolDto outSchool = getSchool(inSchool);
        if (outSchool == null || outSchool.getSchoolId() == 0) {
            throw new ValidationException("School (" + schoolName + ") does not exists.");
        }
        return outSchool;
    }

    /**
     * Validate class.
     * 
     * @param inClass the in class
     * @return the class dto
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    protected ClassDto validateClass(ClassDto inClass)
            throws ValidationException, DaoException, CacheException {
        if (inClass == null) {
            throw new ValidationException("Class information is not specified.");
        }
        String className = inClass.getClassName();
        if (StringUtil.isNullOrBlank(className)) {
            throw new ValidationException("Class name is not specified.");
        }
        ClassDto outClass = getClass(className);
        if (outClass == null || outClass.getClassId() == 0) {
            throw new ValidationException("Class (" + className + ") does not exists.");
        }
        return outClass;
    }

    /**
     * Validate medium.
     * 
     * @param inMedium the in medium
     * @return the medium dto
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    protected MediumDto validateMedium(MediumDto inMedium)
            throws ValidationException, DaoException, CacheException {
        if (inMedium == null) {
            throw new ValidationException("Medium information is not specified.");
        }
        String description = inMedium.getDescription();
        if (StringUtil.isNullOrBlank(description)) {
            throw new ValidationException("Medium name is not specified.");
        }
        MediumDto outMedium = getMedium(description);
        if (outMedium == null || outMedium.getMediumId() == 0) {
            throw new ValidationException("Medium (" + description + ") does not exists.");
        }
        return outMedium;
    }

    /**
     * Validate section.
     * 
     * @param inSection the in section
     * @return the section dto
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    protected SectionDto validateSection(SectionDto inSection)
            throws ValidationException, DaoException, CacheException {
        if (inSection == null) {
            throw new ValidationException("Section information is not specified.");
        }
        String sectionName = inSection.getSectionName();
        if (StringUtil.isNullOrBlank(sectionName)) {
            throw new ValidationException("Section name is not specified.");
        }
        SectionDto outSection = getSection(sectionName);
        if (outSection == null || outSection.getSectionId() == 0) {
            throw new ValidationException("Section (" + sectionName + ") does not exists.");
        }
        return outSection;
    }

    /**
     * Validate subject.
     * 
     * @param inSubject the in subject
     * @return the subject dto
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    protected SubjectDto validateSubject(SubjectDto inSubject)
            throws ValidationException, DaoException, CacheException {
        if (inSubject == null) {
            throw new ValidationException("Subject information is not specified.");
        }
        String subjectName = inSubject.getSubjectName();
        if (StringUtil.isNullOrBlank(subjectName)) {
            throw new ValidationException("Subject name is not specified.");
        }
        SubjectDto outSubject = getSubject(subjectName);
        if (outSubject == null || outSubject.getSubjectId() == 0) {
            throw new ValidationException("Subject (" + subjectName + ") does not exists.");
        }
        return outSubject;
    }

    /**
     * Validate till class.
     * 
     * @param inRegisteredClass the in registered class
     * @return the registered class dto
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    protected RegisteredClassDto validateRegisteredClass(
            RegisteredClassDto inRegisteredClass) throws ValidationException,
            DaoException, CacheException {
        if (inRegisteredClass == null) {
            throw new ValidationException("Registered Class Information is not specified.");
        }

        SchoolDto validateSchool = validateSchool(inRegisteredClass.getSchool());
        MediumDto validateMedium = validateMedium(inRegisteredClass.getMedium());
        SectionDto validateSection = validateSection(inRegisteredClass.getSection());
        ClassDto classDto = validateClass(inRegisteredClass.getClassDto());
        RegisteredClassDto outRegisteredClass = getRegisteredClass(inRegisteredClass);
        if (outRegisteredClass == null || outRegisteredClass.getClassId() == 0) {
            throw new ValidationException("Registered Class (" + classDto.getClassName() + ") does not exists.");
        }
        outRegisteredClass.setClassDto(classDto);
        outRegisteredClass.setSchool(validateSchool);
        outRegisteredClass.setMedium(validateMedium);
        outRegisteredClass.setSection(validateSection);
        return outRegisteredClass;
    }

    /**
     * Validate registered subject.
     * 
     * @param inRegisteredSubject the in registered subject
     * @return the registered subject dto
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    protected RegisteredSubjectDto validateRegisteredSubject(
            RegisteredSubjectDto inRegisteredSubject) throws ValidationException,
            DaoException, CacheException {
        if (inRegisteredSubject == null) {
            throw new ValidationException("Registered Subject Information is not specified.");
        }

        RegisteredClassDto registeredClass = validateRegisteredClass(inRegisteredSubject.getRegisteredClass());
        SubjectDto subject = validateSubject(inRegisteredSubject.getSubject());
        RegisteredSubjectDto outRegisteredSubject = getRegisteredSubject(registeredClass.getClassId(), subject.getSubjectId());
        if (outRegisteredSubject == null || outRegisteredSubject.getSubjectId() == 0) {
            throw new ValidationException("Registered Subject (" + subject.getSubjectName() + ") does not exists.");
        }
        outRegisteredSubject.setRegisteredClass(registeredClass);
        outRegisteredSubject.setSubject(subject);
        return outRegisteredSubject;
    }

    /**
     * Validate document.
     * 
     * @param inDocument the in document
     * @param userType the user type
     * @return the document dto
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    public DocumentDto validateDocument(DocumentDto inDocument, UserType userType)
            throws ValidationException, DaoException, CacheException {
        if (inDocument == null) {
            throw new ValidationException("Document information is not specified.");
        }
        String documentName = inDocument.getName();
        if (StringUtil.isNullOrBlank(documentName)) {
            throw new ValidationException("Document name is not specified.");
        }
        DocumentDto outDocument = getDocument(documentName);
        if (outDocument == null || outDocument.getDocumentId() == 0) {
            throw new ValidationException("Document (" + documentName + ") does not exists.");
        }
        if (userType == UserType.EMPLOYEE
                && (outDocument.getApplicabilityForEmployee() == null
                || outDocument.getApplicabilityForEmployee() == DocumentApplicability.NOT_APPLICABLE)) {
            throw new ValidationException("Document (" + documentName + ") is not applicable for " + UserType.EMPLOYEE);
        }
        if (userType == UserType.STUDENT
                && (outDocument.getApplicabilityForStudent() == null
                || outDocument.getApplicabilityForStudent() == DocumentApplicability.NOT_APPLICABLE)) {
            throw new ValidationException("Document (" + documentName + ") is not applicable for " + UserType.STUDENT);
        }
        return outDocument;
    }


    /**
     * Validate relationship.
     * 
     * @param inRelationship the in relationship
     * @return the relationship
     * @throws DaoException the dao exception
     * @throws ValidationException the validation exception
     * @throws CacheException the cache exception
     */
    protected Relationship validateRelationship(Relationship inRelationship)
            throws DaoException, ValidationException, CacheException {
        if (inRelationship == null) {
            throw new ValidationException("Relationship information is not specified.");
        }
        String relationshipCode = inRelationship.getCode();
        if (StringUtil.isNullOrBlank(relationshipCode)) {
            throw new ValidationException("Invalid value for Relationship Code (" + relationshipCode + ").");
        }
        Relationship outRelationship = getRelationship(relationshipCode);
        if (outRelationship == null) {
            throw new ValidationException("Relationship (" + relationshipCode + ") does not exists.");
        }
        return outRelationship;
    }

    /**
     * Gets the relationship.
     * 
     * @param relationshipCode the relationship code
     * @return the relationship
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    protected Relationship getRelationship(String relationshipCode) throws DaoException, CacheException {
        Relationship relationship = null;
        Object entry = getEntry(CacheKeyConstants.RELATIONSHIP, relationshipCode);
        if (entry == null) {
            relationship = relationshipDao.get(relationshipCode);
            putEntry(CacheKeyConstants.RELATIONSHIP, relationshipCode, relationship);
        } else {
            relationship = (Relationship) entry;
        }
        return relationship;
    }

}
