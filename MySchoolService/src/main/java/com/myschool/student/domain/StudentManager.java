package com.myschool.student.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.application.domain.ProfileManager;
import com.myschool.application.dto.MySchoolProfileDto;
import com.myschool.clazz.dao.ClassDao;
import com.myschool.clazz.dao.RegisteredClassDao;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.common.constants.MySchoolConstant;
import com.myschool.common.dto.FamilyMemberDto;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.FileSystemException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.util.StringUtil;
import com.myschool.exam.assembler.StudentExamDataAssembler;
import com.myschool.exam.dao.ExamDao;
import com.myschool.exam.dao.ExamGradeDao;
import com.myschool.exam.dto.ExamDto;
import com.myschool.exam.dto.ExamGradeDto;
import com.myschool.exam.dto.StudentExamDto;
import com.myschool.exam.dto.StudentInExamDto;
import com.myschool.exim.dao.StudentExamDao;
import com.myschool.infra.filesystem.agent.ImageFileSystem;
import com.myschool.infra.filesystem.agent.TempFileSystem;
import com.myschool.infra.image.constants.ImageSize;
import com.myschool.notification.constants.NotificationEndPoint;
import com.myschool.notification.constants.NotificationType;
import com.myschool.notification.domain.NotificationManager;
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

/**
 * The Class StudentManager.
 */
@Component
public class StudentManager {

    /** The user manager. */
    @Autowired
    private UserManager userManager;

    /** The notification manager. */
    @Autowired
    private NotificationManager notificationManager;

    /** The profile manager. */
    @Autowired
    private ProfileManager profileManager;

    /** The student dao. */
    @Autowired
    private StudentDao studentDao;

    /** The student document dao. */
    @Autowired
    private StudentDocumentDao studentDocumentDao;

    /** The student family dao. */
    @Autowired
    private StudentFamilyDao studentFamilyDao;

    /** The class dao. */
    @Autowired
    private ClassDao classDao;

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

    /** The image file system. */
    @Autowired
    private ImageFileSystem imageFileSystem;

    /** The student validator. */
    @Autowired
    private StudentValidator studentValidator;

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
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return student;
    }

    /**
     * Gets the next.
     *
     * @param admissionNumber the admission number
     * @param type the type
     * @return the next
     * @throws DataException the data exception
     */
    public StudentDto getNext(String admissionNumber, String type) throws DataException {
        StudentDto student = null;
        try {
        	if (type == null || !(type.equals(MySchoolConstant.VERIFIED) || type.equals(MySchoolConstant.UNVERIFIED))) {
        		throw new DataException("Search type must be either " + MySchoolConstant.VERIFIED + " or " + MySchoolConstant.UNVERIFIED);
        	}
            String nextAdmissionNumber = studentDao.getNextAdmissionNumber(admissionNumber, type);
            if (nextAdmissionNumber != null) {
            	student = studentDao.get(nextAdmissionNumber);
            	if (student != null) {
            		student.setFamilyMembers(studentFamilyDao.getByStudent(student.getAdmissionNumber()));
            		student.setDocumentsSubmitted(studentDocumentDao.getByStudent(student.getAdmissionNumber()));
            	}
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return student;
    }

    /**
     * Gets the previous.
     *
     * @param admissionNumber the admission number
     * @param type the type
     * @return the previous
     * @throws DataException the data exception
     */
    public StudentDto getPrevious(String admissionNumber, String type) throws DataException {
    	StudentDto student = null;
        try {
        	if (type == null || !(type.equals(MySchoolConstant.VERIFIED) || type.equals(MySchoolConstant.UNVERIFIED))) {
        		throw new DataException("Search type must be either " + MySchoolConstant.VERIFIED + " or " + MySchoolConstant.UNVERIFIED);
        	}
            String previousAdmissionNumber = studentDao.getPreviousAdmissionNumber(admissionNumber, type);
            if (previousAdmissionNumber != null) {
            	student = studentDao.get(previousAdmissionNumber);
            	if (student != null) {
            		student.setFamilyMembers(studentFamilyDao.getByStudent(student.getAdmissionNumber()));
            		student.setDocumentsSubmitted(studentDocumentDao.getByStudent(student.getAdmissionNumber()));
            	}
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
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
            boolean verified = student.isVerified();
            studentId = studentDao.create(student);
            if (studentId == 0) {
                throw new DataException("Unable to create Student now.");
            }
            // Create student attributes.
            studentFamilyDao.create(studentId, student.getFamilyMembers());
            studentDocumentDao.create(studentId, student.getDocumentsSubmitted());
            // Update student Image
            String referenceNumber = student.getImageName();
            if (!StringUtil.isNullOrBlank(referenceNumber)) {
                updateStudentImage(referenceNumber, admissionNumber);
            }
            // If student information is verified then create login and notification.
            if (verified) {
                student.setStudentId(studentId);
                createStudentProfile(student);
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
            StudentDto existingStudent = studentDao.get(admissionNumber);
            if (existingStudent == null) {
                throw new DataException("Student with Admission Number (" + admissionNumber + ") does not exist.");
            }
            studentId = existingStudent.getStudentId();
            studentDao.update(existingStudent.getStudentId(), student);

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
            String referenceNumber = student.getImageName();
            if (!StringUtil.isNullOrBlank(referenceNumber)) {
                updateStudentImage(referenceNumber, admissionNumber);
            }
            // If student is not verified already and verified now then create user profile and a notification.
            boolean verified = student.isVerified();
            boolean alreadyVerified = existingStudent.isVerified();
            if (!alreadyVerified && verified) {
                student.setStudentId(studentId);
                createStudentProfile(student);
            }
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        } catch (DaoException daoException) {
            throw new DataException("Unable to update student now. Please try again.");
        }
        return true;
    }

    /**
     * Update student image.
     *
     * @param secureToken the secure token
     * @param admissionNumber the admission number
     * @throws DataException the data exception
     */
    public void updateStudentImage(String secureToken, String admissionNumber) throws DataException {
        try {
            if (secureToken != null && admissionNumber != null) {
                File fromFile = tempFileSystem.getStudentImage(secureToken, ImageSize.ORIGINAL);
                imageFileSystem.createStudentImage(admissionNumber, fromFile);
            }
        } catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
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
            studentDao.delete(admissionNumber);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
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
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return student;
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
            MySchoolProfileDto mySchoolProfile = profileManager.getMyschoolProfile();
            for (FamilyMemberDto familyMember : familyMembers) {
                if (familyMember != null) {
                    int familyMemberId = familyMember.getFamilyMemberId();
                    if (canMailParents(mySchoolProfile, familyMember)) {
                        mailNotifyingIds.add(familyMemberId);
                    }
                    if (canSmsParents(mySchoolProfile, familyMember)) {
                        smsNotifyingIds.add(familyMemberId);
                    }
                }
            }
            notificationManager.createNotification(
                    NotificationEndPoint.STUDENT,
                    NotificationType.REGISTRATION, mailNotifyingIds,
                    smsNotifyingIds);
        }
    }

    /**
     * Can mail parents.
     * 
     * @param mySchoolProfile the my school profile
     * @param familyMember the family member
     * @return true, if successful
     */
    public boolean canMailParents(MySchoolProfileDto mySchoolProfile,
            FamilyMemberDto familyMember) {
        return mySchoolProfile.isEmailActive() && mySchoolProfile.isEmailStudents() && familyMember.isAvailEmail();
    }

    /**
     * Can sms parents.
     * 
     * @param mySchoolProfile the my school profile
     * @param familyMember the family member
     * @return true, if successful
     */
    public boolean canSmsParents(MySchoolProfileDto mySchoolProfile,
            FamilyMemberDto familyMember) {
        return mySchoolProfile.isSmsActive() && mySchoolProfile.isSmsStudents() && familyMember.isAvailEmail();
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
                MySchoolProfileDto myschoolProfile = profileManager.getMyschoolProfile();
                List<Integer> mailNotifyingIds = new ArrayList<Integer>();
                List<Integer> smsNotifyingIds = new ArrayList<Integer>();
                List<FamilyMemberDto> familyMembers = student.getFamilyMembers();
                if (notifyEmail(myschoolProfile, familyMembers)) {
                    mailNotifyingIds.add(studentId);
                }
                if (notifySMS(myschoolProfile, familyMembers)) {
                    smsNotifyingIds.add(studentId);
                }
                notificationManager.createNotification(
                        NotificationEndPoint.STUDENT,
                        NotificationType.REGISTRATION, mailNotifyingIds,
                        smsNotifyingIds);
            }
        }
    }

    /**
     * Notify email.
     * 
     * @param myschoolProfile the myschool profile
     * @param familyMembers the family members
     * @return true, if successful
     */
    public boolean notifyEmail(
            MySchoolProfileDto myschoolProfile,
            List<FamilyMemberDto> familyMembers) {
        if (myschoolProfile.isEmailActive() && myschoolProfile.isEmailStudents()) {
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
     * @param myschoolProfile the myschool profile
     * @param familyMembers the family members
     * @return true, if successful
     */
    public boolean notifySMS(
            MySchoolProfileDto myschoolProfile,
            List<FamilyMemberDto> familyMembers) {
        if (myschoolProfile.isSmsActive() && myschoolProfile.isSmsStudents()) {
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

}
