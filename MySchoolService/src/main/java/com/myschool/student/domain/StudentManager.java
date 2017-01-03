package com.myschool.student.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.clazz.dao.RegisteredClassDao;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.common.assembler.ImageDataAssembler;
import com.myschool.common.constants.RecordStatus;
import com.myschool.common.dto.FamilyMemberDto;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.ValidationException;
import com.myschool.exam.assembler.StudentExamDataAssembler;
import com.myschool.exam.dao.ExamDao;
import com.myschool.exam.dao.ExamGradeDao;
import com.myschool.exam.dto.ExamDto;
import com.myschool.exam.dto.ExamGradeDto;
import com.myschool.exam.dto.StudentExamDto;
import com.myschool.exam.dto.StudentInExamDto;
import com.myschool.exim.dao.StudentExamDao;
import com.myschool.image.constant.ImageSize;
import com.myschool.infra.filesystem.agent.TempFileSystem;
import com.myschool.infra.storage.StorageAccessAgent;
import com.myschool.infra.storage.exception.StorageAccessException;
import com.myschool.organization.dao.OrganizationManager;
import com.myschool.organization.dto.OrganizationPreferences;
import com.myschool.storage.dto.StorageItem;
import com.myschool.student.dao.StudentDao;
import com.myschool.student.dao.StudentDocumentDao;
import com.myschool.student.dao.StudentFamilyDao;
import com.myschool.student.dto.StudentDocument;
import com.myschool.student.dto.StudentDto;
import com.myschool.student.dto.StudentPerformaceDto;
import com.myschool.student.dto.StudentSearchCriteriaDto;
import com.myschool.student.validator.StudentValidator;
import com.myschool.user.constants.UserType;
import com.myschool.user.domain.UserManager;
import com.myschool.user.dto.UsersDto;
import com.quasar.core.exception.DataException;
import com.quasar.core.exception.FileSystemException;
import com.quasar.core.util.StringUtil;

/**
 * The Class StudentManager.
 */
@Component
public class StudentManager {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(StudentManager.class);

    /** The user manager. */
    @Autowired
    private UserManager userManager;

    /** The notification manager. *//*
    @Autowired
    private NotificationManager notificationManager;*/

    @Autowired
    private OrganizationManager organizationManager;

    /** The student dao. */
    @Autowired
    private StudentDao studentDao;

    /** The student document dao. */
    @Autowired
    private StudentDocumentDao studentDocumentDao;

    /** The student family dao. */
    @Autowired
    private StudentFamilyDao studentFamilyDao;

    /** The registered class dao. */
    @Autowired
    private RegisteredClassDao registeredClassDao;

    /** The exam dao. */
    @Autowired
    private ExamDao examDao;

    /** The exam grade dao. */
    @Autowired
    private ExamGradeDao examGradeDao;

    /** The student exam dao. */
    @Autowired
    private StudentExamDao studentExamDao;

    /** The temp file system. */
    @Autowired
    private TempFileSystem tempFileSystem;

    /** The student validator. */
    @Autowired
    private StudentValidator studentValidator;

    /** The storage access agent. */
    @Autowired
    private StorageAccessAgent storageAccessAgent;

    /**
     * Gets the.
     * 
     * @param studentId the student id
     * @return the student
     * @throws DataException the data exception
     */
    public StudentDto get(int studentId) throws DataException {
        StudentDto student = null;
        try {
            student = studentDao.get(studentId);
            if (student != null) {
                student.setFamilyMembers(studentFamilyDao.getByStudent(student.getAdmissionNumber()));
                student.setDocumentsSubmitted(studentDocumentDao.getByStudent(student.getAdmissionNumber()));
                StorageItem storageItem = storageAccessAgent.STUDENT_STORAGE.get(RecordStatus.get(student.isVerified()), student.getAdmissionNumber());
                student.setImageAccess(ImageDataAssembler.create(storageItem));
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        } catch (StorageAccessException storageAccessException) {
            throw new DataException(storageAccessException.getMessage(), storageAccessException);
        }
        return student;
    }

    /**
     * Gets the student.
     *
     * @param admissionNumber the admission number
     * @return the student
     * @throws DataException the data exception
     */
    public StudentDto getStudent(String admissionNumber) throws DataException {
        StudentDto student = null;
        try {
            student = studentDao.get(admissionNumber);
            if (student != null) {
                student.setFamilyMembers(studentFamilyDao.getByStudent(student.getAdmissionNumber()));
                student.setDocumentsSubmitted(studentDocumentDao.getByStudent(student.getAdmissionNumber()));
                StorageItem storageItem = storageAccessAgent.STUDENT_STORAGE.get(RecordStatus.get(student.isVerified()), student.getAdmissionNumber());
                student.setImageAccess(ImageDataAssembler.create(storageItem));
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        } catch (StorageAccessException storageAccessException) {
            throw new DataException(storageAccessException.getMessage(), storageAccessException);
        }
        return student;
    }

    /**
     * Gets the next.
     *
     * @param admissionNumber the admission number
     * @param recordStatus the record status
     * @return the next
     * @throws DataException the data exception
     */
    public StudentDto getNext(String admissionNumber, RecordStatus recordStatus) throws DataException {
        StudentDto student = null;
        try {
        	if (recordStatus == null) {
        		throw new DataException("Search type must be either " + RecordStatus.VERIFIED + " or " + RecordStatus.UNVERIFIED);
        	}
            String nextAdmissionNumber = studentDao.getNextAdmissionNumber(admissionNumber, recordStatus);
            if (nextAdmissionNumber != null) {
            	student = studentDao.get(nextAdmissionNumber);
            	if (student != null) {
            		student.setFamilyMembers(studentFamilyDao.getByStudent(student.getAdmissionNumber()));
            		student.setDocumentsSubmitted(studentDocumentDao.getByStudent(student.getAdmissionNumber()));
            		StorageItem storageItem = storageAccessAgent.STUDENT_STORAGE.get(RecordStatus.get(student.isVerified()), student.getAdmissionNumber());
                    student.setImageAccess(ImageDataAssembler.create(storageItem));
            	}
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        } catch (StorageAccessException storageAccessException) {
            throw new DataException(storageAccessException.getMessage(), storageAccessException);
        }
        return student;
    }

    /**
     * Gets the previous.
     *
     * @param admissionNumber the admission number
     * @param recordStatus the record status
     * @return the previous
     * @throws DataException the data exception
     */
    public StudentDto getPrevious(String admissionNumber, RecordStatus recordStatus) throws DataException {
    	StudentDto student = null;
        try {
        	if (recordStatus == null) {
        		throw new DataException("Search type must be either " + RecordStatus.VERIFIED + " or " + RecordStatus.UNVERIFIED);
        	}
            String previousAdmissionNumber = studentDao.getPreviousAdmissionNumber(admissionNumber, recordStatus);
            if (previousAdmissionNumber != null) {
            	student = studentDao.get(previousAdmissionNumber);
            	if (student != null) {
            		student.setFamilyMembers(studentFamilyDao.getByStudent(student.getAdmissionNumber()));
            		student.setDocumentsSubmitted(studentDocumentDao.getByStudent(student.getAdmissionNumber()));
            		StorageItem storageItem = storageAccessAgent.STUDENT_STORAGE.get(RecordStatus.get(student.isVerified()), student.getAdmissionNumber());
                    student.setImageAccess(ImageDataAssembler.create(storageItem));
            	}
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        } catch (StorageAccessException storageAccessException) {
            throw new DataException(storageAccessException.getMessage(), storageAccessException);
        }
        return student;
    }

    /**
     * Creates the.
     * 
     * @param student the student
     * @return the int
     * @throws DataException the data exception
     */
    public int create(StudentDto student) throws DataException {
        int studentId = 0;
        String admissionNumber = null;
        try {
            // Validate the student
            studentValidator.validate(student);
            admissionNumber = student.getAdmissionNumber();
            StudentDto existingStudent = studentDao.get(admissionNumber);
            if (existingStudent != null) {
                throw new DataException("Student with Admission Number (" + admissionNumber + ") already exists.");
            }
            boolean verify = student.isVerify();
            System.out.println("verify=" + verify);
            if (verify) {
                student.setVerified(true);
            } else {
                student.setVerified(false);
            }
            studentId = studentDao.create(student);
            if (studentId == 0) {
                throw new DataException("Unable to create Student now.");
            }
            // Create student attributes.
            studentFamilyDao.create(studentId, student.getFamilyMembers());
            studentDocumentDao.create(studentId, student.getDocumentsSubmitted());
            // If student information is verified then create login and notification.
            if (verify) {
                student.setStudentId(studentId);
                createStudentProfile(student);
                System.out.println("Created student login profile.");
            }
            // Update student Image
            String referenceNumber = student.getImageName();
            System.out.println("referenceNumber=" + referenceNumber);
            if (!StringUtil.isNullOrBlank(referenceNumber)) {
                updateStudentImage(referenceNumber, admissionNumber, student.isVerified());
            }
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        } catch (DaoException daoException) {
            // Rollback student details if already  have been saved.
            try {
                if (!StringUtil.isNullOrBlank(admissionNumber)) {
                    studentDao.delete(admissionNumber);
                }
            } catch (DaoException daoException2) {
                throw new DataException("Unable to create Student now. Please try again.");
            }
            throw new DataException("Unable to create Student now. Please try again.");
        }
        return studentId;
    }

    /**
     * Update.
     *
     * @param studentId the student id
     * @param student the student
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(int studentId, StudentDto student) throws DataException {

        String admissionNumber = null;
        try {
            // Validate the student
            studentValidator.validate(student);
            admissionNumber = student.getAdmissionNumber();
            System.out.println("admissionNumber=" + admissionNumber);
            StudentDto existingStudent = studentDao.get(admissionNumber);
            if (existingStudent == null) {
                throw new DataException("Student with Admission Number (" + admissionNumber + ") does not exist.");
            }
            boolean verify = student.isVerify();
            boolean alreadyVerified = existingStudent.isVerified();
            studentId = existingStudent.getStudentId();
            System.out.println("verify=" + verify);
            System.out.println("alreadyVerified=" + alreadyVerified);
            if (verify) {
                System.out.println("Set to verified");
                student.setVerified(true);
            } else {
                student.setVerified(alreadyVerified);
            }
            studentDao.update(existingStudent.getStudentId(), student);
            System.out.println("Updated student info");

            // Update student family details.
            List<FamilyMemberDto> familyMembers = student.getFamilyMembers();
            if (familyMembers != null && !familyMembers.isEmpty()) {
                for (FamilyMemberDto familyMember : familyMembers) {
                    int familyMemberId = familyMember.getFamilyMemberId();
                    if (familyMemberId == 0) {
                        studentFamilyDao.create(studentId, familyMember);
                    } else {
                        studentFamilyDao.update(familyMemberId, familyMember);
                    }
                }
            }
            System.out.println("Updated student family info");
            // Update student documents
            List<StudentDocument> studentDocuments = student.getDocumentsSubmitted();
            if (studentDocuments != null && !studentDocuments.isEmpty()) {
                for (StudentDocument studentDocument : studentDocuments) {
                    int studentDocumentId = studentDocument.getStudentDocumentId();
                    if (studentDocumentId == 0) {
                        studentDocumentDao.create(studentId, studentDocument);
                    } else {
                        studentDocumentDao.update(studentDocumentId, studentDocument);
                    }
                }
            }
            System.out.println("Updated student documents info");
            // If the student data is verified
            if (verify) {
                if (alreadyVerified) {
                    // This student is already verified. nothing to do.
                } else {
                    System.out.println("Student data has been verified. Moving to VERIFIED.");
                    // This student data has not been verified before. so create student profile
                    student.setStudentId(studentId);
                    createStudentProfile(student);
                    System.out.println("Creted student proifile for login");

                    // Update the student image if one is provided now. update to UNVERIFIED
                    String imageName = student.getImageName();
                    if (!StringUtil.isNullOrBlank(imageName)) {
                        System.out.println("Updating student image with " + imageName);
                        updateStudentImage(imageName, admissionNumber, false);
                        System.out.println("Updated student image provided now.");
                    }
                    // Now the student image has been updated. Move the image to verified images.
                    storageAccessAgent.STUDENT_STORAGE.verify(admissionNumber);
                    System.out.println("Moved student image to VERIFIED, if one did exist.");
                }
            } else {
                // This is just save. could be VERIFIED or UNVERIFIED.
                // Update the student image if one is provided now
                String imageName = student.getImageName();
                if (!StringUtil.isNullOrBlank(imageName)) {
                    System.out.println("Updating student image with " + imageName);
                    updateStudentImage(imageName, admissionNumber, alreadyVerified);
                    System.out.println("Updated student image to " + RecordStatus.get(alreadyVerified));
                }
            }
            System.out.println("EVERYTHING completed.");
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        } catch (DaoException daoException) {
            throw new DataException("Unable to update student now. Please try again.");
        } catch (StorageAccessException storageAccessException) {
            throw new DataException(storageAccessException.getMessage(), storageAccessException);
        }
        return true;
    }

    /**
     * Update student image.
     *
     * @param secureToken the secure token
     * @param admissionNumber the admission number
     * @param verified the verified
     * @throws DataException the data exception
     */
    public void updateStudentImage(String secureToken, String admissionNumber, boolean verified) throws DataException {
        try {
            if (secureToken != null && admissionNumber != null) {
                File file = tempFileSystem.getStudentImage(secureToken, ImageSize.ORIGINAL);
                System.out.println("updateStudentImage() file=" + file);
                storageAccessAgent.STUDENT_STORAGE.update(file, RecordStatus.get(verified), admissionNumber);
            }
        } catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
        } catch (StorageAccessException storageAccessException) {
            throw new DataException(storageAccessException.getMessage(), storageAccessException);
        }
    }

    /**
     * Delete.
     *
     * @param admissionNumber the admission number
     * @throws DataException the data exception
     */
    public void delete(String admissionNumber) throws DataException {
        try {
            StudentDto student = studentDao.get(admissionNumber);
            if (student != null) {
                studentDao.delete(admissionNumber);
                storageAccessAgent.STUDENT_STORAGE.delete(RecordStatus.get(student.isVerified()), admissionNumber);
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        } catch (StorageAccessException storageAccessException) {
            throw new DataException(storageAccessException.getMessage(), storageAccessException);
        }
    }

    /**
     * Gets the performance.
     *
     * @param admissionNumber the admission number
     * @return the performance
     * @throws DataException the data exception
     */
    public List<StudentPerformaceDto> getPerformance(String admissionNumber) throws DataException {

        StudentPerformaceDto studentPerformace = null;
        StudentInExamDto studentInExam = null;
        List<StudentPerformaceDto> studentPerformaces = null;
        List<StudentExamDto> studentExams = null;

        try {
            // Get the class by admission number
            RegisteredClassDto registeredClass = registeredClassDao.getByStudent(admissionNumber);
            if (registeredClass == null) {
                throw new DataException("Student is not present with admission number (" + admissionNumber + ") in any class");
            }
            int classId = registeredClass.getClassId();
            // Get the exams in the class
            List<ExamDto> examsInClass = examDao.getByClass(classId);
            if (examsInClass != null) {
                List<ExamGradeDto> grades = examGradeDao.getGrades();
                studentPerformaces = new ArrayList<StudentPerformaceDto>(examsInClass.size());
                for (ExamDto exam : examsInClass) {
                    if (exam != null) {
                        studentPerformace = new StudentPerformaceDto();
                        studentPerformace.setExam(exam);

                        studentExams = studentExamDao.getStudentSubjectMarksInExam(exam.getExamId(), admissionNumber);
                        studentInExam = StudentExamDataAssembler.createStudentInExam(studentExams, grades);

                        studentPerformace.setStudentInExam(studentInExam);
                        studentPerformaces.add(studentPerformace);
                    }
                }
            }
            // Arrange a list of unique subjects in that exam.
            // For each exam arrange the marks under the student.
            // handle the scenario in case if a subject is present in one exam and not present in another.
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return studentPerformaces;
    }

    /**
     * Delete family member.
     *
     * @param familyMemberId the family member id
     * @throws DataException the data exception
     */
    public void deleteFamilyMember(int familyMemberId) throws DataException {
        try {
            studentFamilyDao.delete(familyMemberId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Terminate admission.
     * 
     * @param admissionNumber the admission number
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean terminateAdmission(String admissionNumber) throws DataException {
        try {
            return studentDao.terminateAdmission(admissionNumber);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Gets the current ay students.
     *
     * @param classId the class id
     * @return the current ay students
     * @throws DataException the data exception
     */
    public List<StudentDto> getCurrentAyStudents(int classId) throws DataException {
        List<StudentDto> students = null;
        try {
            students = studentDao.getCurrentAyStudents(classId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return students; 
    }

    /**
     * Gets the family member.
     *
     * @param familyMemberId the family member id
     * @return the family member
     * @throws DataException the data exception
     */
    public FamilyMemberDto getFamilyMember(int familyMemberId) throws DataException {
        FamilyMemberDto familyMember = null;
        try {
            familyMember = studentFamilyDao.get(familyMemberId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return familyMember; 
    }

    /**
     * Gets the last admission number.
     *
     * @return the last admission number
     * @throws DataException the data exception
     */
    public String getLastAdmissionNumber() throws DataException {
        String admissionNumber = null;
        try {
            admissionNumber = studentDao.getLastAdmissionNumber();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return admissionNumber;
    }

    /**
     * Search.
     *
     * @param studentSearchCriteriaDto the student search criteria dto
     * @return the list
     * @throws DataException the data exception
     */
    public List<StudentDto> search(
            StudentSearchCriteriaDto studentSearchCriteriaDto) throws DataException {
        List<StudentDto> students = null;
        try {
            students = studentDao.search(studentSearchCriteriaDto);
            if (students != null && !students.isEmpty()) {
                for (StudentDto student : students) {
                    if (student != null) {
                        StorageItem storageItem = null;
                        try {
                            storageItem = storageAccessAgent.STUDENT_STORAGE.get(RecordStatus.get(student.isVerified()), student.getAdmissionNumber());
                            student.setImageAccess(ImageDataAssembler.create(storageItem));
                        } catch (StorageAccessException storageAccessException) {
                            LOGGER.error(storageAccessException.getMessage(), storageAccessException);
                        }
                    }
                }
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return students;
    }

    /**
     * Search.
     *
     * @return the list
     * @throws DataException the data exception
     */
    public List<StudentDto> search() throws DataException {
        return search((StudentSearchCriteriaDto)null);
    }

    /**
     * Creates the student notification.
     * 
     * @param familyMembers the family members
     * @throws DaoException the dao exception
     * @throws DataException the data exception
     */
    public void createStudentNotification(List<FamilyMemberDto> familyMembers)
            throws DaoException, DataException {
        List<Integer> mailNotifyingIds = new ArrayList<Integer>();
        List<Integer> smsNotifyingIds = new ArrayList<Integer>();

        if (familyMembers != null && !familyMembers.isEmpty()) {
            OrganizationPreferences preferences = organizationManager.getOrganizationPreferences();
            for (FamilyMemberDto familyMember : familyMembers) {
                if (familyMember != null) {
                    int familyMemberId = familyMember.getFamilyMemberId();
                    if (canMailParents(preferences, familyMember)) {
                        mailNotifyingIds.add(familyMemberId);
                    }
                    if (canSmsParents(preferences, familyMember)) {
                        smsNotifyingIds.add(familyMemberId);
                    }
                }
            }
            /*notificationManager.createNotification(
                    NotificationEndPoint.STUDENT,
                    NotificationType.REGISTRATION, mailNotifyingIds,
                    smsNotifyingIds);*/
        }
    }

    /**
     * Can mail parents.
     * 
     * @param familyMember the family member
     * @return true, if successful
     */
    public boolean canMailParents(OrganizationPreferences preferences,
            FamilyMemberDto familyMember) {
        return preferences.isEmailActive() && preferences.isEmailStudents() && familyMember.isAvailEmail();
    }

    /**
     * Can sms parents.
     * 
     * @param familyMember the family member
     * @return true, if successful
     */
    public boolean canSmsParents(OrganizationPreferences preferences,
            FamilyMemberDto familyMember) {
        return preferences.isSmsActive() && preferences.isSmsStudents() && familyMember.isAvailEmail();
    }

    /**
     * Creates the student profile.
     * 
     * @param student the student
     * @throws DataException the data exception
     */
    private void createStudentProfile(StudentDto student) throws DataException {
        int studentId = student.getStudentId();
        UsersDto user = userManager.getUser(UserType.STUDENT, studentId);
        if (user == null) {
            int userId = userManager.createUser(student);
            if (userId > 0) {
                OrganizationPreferences preferences = organizationManager.getOrganizationPreferences();
                List<Integer> mailNotifyingIds = new ArrayList<Integer>();
                List<Integer> smsNotifyingIds = new ArrayList<Integer>();
                List<FamilyMemberDto> familyMembers = student.getFamilyMembers();
                if (notifyEmail(preferences, familyMembers)) {
                    mailNotifyingIds.add(studentId);
                }
                if (notifySMS(preferences, familyMembers)) {
                    smsNotifyingIds.add(studentId);
                }
                /*notificationManager.createNotification(
                        NotificationEndPoint.STUDENT,
                        NotificationType.REGISTRATION, mailNotifyingIds,
                        smsNotifyingIds);*/
            }
        }
    }

    /**
     * Notify email.
     * 
     * @param familyMembers the family members
     * @return true, if successful
     */
    public boolean notifyEmail(OrganizationPreferences preferences,
            List<FamilyMemberDto> familyMembers) {
        if (preferences.isEmailActive() && preferences.isEmailStudents()) {
            if (familyMembers != null && !familyMembers.isEmpty()) {
                for (FamilyMemberDto familyMember : familyMembers) {
                    if (familyMember != null) {
                        String emailId = familyMember.getEmailId();
                        if (familyMember.isAvailEmail() && !StringUtil.isNullOrBlank(emailId)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Notify sms.
     * 
     * @param familyMembers the family members
     * @return true, if successful
     */
    public boolean notifySMS(OrganizationPreferences preferences,
            List<FamilyMemberDto> familyMembers) {
        if (preferences.isSmsActive() && preferences.isSmsStudents()) {
            if (familyMembers != null && !familyMembers.isEmpty()) {
                for (FamilyMemberDto familyMember : familyMembers) {
                    if (familyMember != null) {
                        String mobileNumber = familyMember.getMobileNumber();
                        if (familyMember.isAvailSMS() && !StringUtil.isNullOrBlank(mobileNumber)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    /**
     * Gets the evanescent image.
     *
     * @param referenceNumber the reference number
     * @param imageSize the image size
     * @return the evanescent image
     * @throws DataException the data exception
     */
    public File getEvanescentImage(String referenceNumber,
            ImageSize imageSize) throws DataException {
        File file = null;
        try {
            System.out.println("getEvanescentImage(" + referenceNumber + ", " + imageSize + ")");
            file = tempFileSystem.getStudentImage(referenceNumber, imageSize);
            System.out.println("file===" + file);
        } catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
        }
        return file;
    }

}
