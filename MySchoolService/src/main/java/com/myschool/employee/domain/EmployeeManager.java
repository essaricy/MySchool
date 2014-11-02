package com.myschool.employee.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.application.domain.ProfileManager;
import com.myschool.application.dto.MySchoolProfileDto;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.FileSystemException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.util.StringUtil;
import com.myschool.employee.constant.EmployeeNotificationTo;
import com.myschool.employee.dao.EmployeeContactDao;
import com.myschool.employee.dao.EmployeeDao;
import com.myschool.employee.dao.EmployeeDocumentDao;
import com.myschool.employee.dao.EmployeeEducationDao;
import com.myschool.employee.dao.EmployeeExperienceDao;
import com.myschool.employee.dao.EmployeePromotionDao;
import com.myschool.employee.dao.EmployeeSubjectDao;
import com.myschool.employee.domain.validator.EmployeeValidator;
import com.myschool.employee.dto.EmployeeContact;
import com.myschool.employee.dto.EmployeeDocument;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.employee.dto.EmployeeEducation;
import com.myschool.employee.dto.EmployeeExperience;
import com.myschool.employee.dto.EmployeePromotion;
import com.myschool.employee.dto.EmployeeSearchCriteriaDto;
import com.myschool.employee.dto.EmployeeSubjectDto;
import com.myschool.infra.filesystem.agent.ImageFileSystem;
import com.myschool.infra.filesystem.agent.TempFileSystem;
import com.myschool.infra.image.constants.ImageSize;
import com.myschool.notification.constants.NotificationEndPoint;
import com.myschool.notification.constants.NotificationType;
import com.myschool.notification.domain.NotificationManager;
import com.myschool.user.constants.UserType;
import com.myschool.user.domain.UserManager;
import com.myschool.user.dto.UsersDto;

/**
 * The Class EmployeeManager.
 */
@Component
public class EmployeeManager {

    /** The employee dao. */
    @Autowired
    private EmployeeDao employeeDao;

    /** The user manager. */
    @Autowired
    private UserManager userManager;

    /** The employee exim manager. */
    @Autowired
    private EmployeeValidator employeeValidator;

    /** The notification manager. */
    @Autowired
    private NotificationManager notificationManager;

    /** The temp file system. */
    @Autowired
    private TempFileSystem tempFileSystem;

    /** The image file system. */
    @Autowired
    private ImageFileSystem imageFileSystem;

    /** The profile manager. */
    @Autowired
    private ProfileManager profileManager;

    /** The employee contact dao. */
    @Autowired
    private EmployeeContactDao employeeContactDao;

    /** The employee education dao. */
    @Autowired
    private EmployeeEducationDao employeeEducationDao;

    /** The employee experience dao. */
    @Autowired
    private EmployeeExperienceDao employeeExperienceDao;

    /** The employee document dao. */
    @Autowired
    private EmployeeDocumentDao employeeDocumentDao;

    /** The employee promotion dao. */
    @Autowired
    private EmployeePromotionDao employeePromotionDao;

    /** The employee subject dao. */
    @Autowired
    private EmployeeSubjectDao employeeSubjectDao;

    /**
     * Gets the.
     * 
     * @param employeeId the employee id
     * @return the employee dto
     * @throws DataException the data exception
     */
    public EmployeeDto get(int employeeId) throws DataException {
        EmployeeDto employee = null;
        try {
            employee = employeeDao.get(employeeId);
            if (employee != null) {
                employee.setEmployeeContact(employeeContactDao.get(employeeId));
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return employee;
    }

    /**
     * Gets the.
     * 
     * @param employeeNumber the employee number
     * @return the employee dto
     * @throws DataException the data exception
     */
    public EmployeeDto get(String employeeNumber) throws DataException {
        EmployeeDto employee = null;
        try {
            employee = employeeDao.get(employeeNumber);
            if (employee != null) {
                employee.setEmployeeContact(employeeContactDao.get(employee.getEmployeeId()));
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return employee;
    }

    /**
     * Update employee image.
     * 
     * @param referenceNumber the reference number
     * @param employeeNumber the employee number
     * @throws DataException the data exception
     */
    public void updateEmployeeImage(String referenceNumber, String employeeNumber) throws DataException {
        try {
            if (referenceNumber != null && employeeNumber != null) {
                File fromFile = tempFileSystem.getEmployeeImage(referenceNumber, ImageSize.ORIGINAL);
                imageFileSystem.createEmployeeImage(employeeNumber, fromFile);
            }
        } catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
        }
    }

    /**
     * Creates the.
     *
     * @param employee the employee dto
     * @return the int
     * @throws DataException the data exception
     */
    public int create(EmployeeDto employee) throws DataException {
        int employeeId = 0;
        String employeeNumber = null;
        try {
            // Validate the employee
            employeeValidator.validate(employee);
            employeeNumber = employee.getEmployeeNumber();
            EmployeeDto existingEmployee = employeeDao.get(employeeNumber);
            if (existingEmployee != null) {
                throw new DataException("Employee with Employee Number (" + employeeNumber + ") already exists.");
            }
            boolean verified = employee.isVerified();
            employeeId = employeeDao.create(employee);
            if (employeeId == 0) {
                throw new DataException("Unable to create Employee now.");
            }
            // Create employee attributes.
            employeeContactDao.create(employeeId, employee.getEmployeeContact());
            employeeDocumentDao.create(employeeId, employee.getEmployeeDocuments());
            employeeEducationDao.create(employeeId, employee.getEmployeeEducations());
            employeeExperienceDao.create(employeeId, employee.getEmployeeExperiences());
            employeePromotionDao.create(employeeId, employee.getEmployeePromotions());
            employeeSubjectDao.create(employeeId, employee.getEmployeeSubjects());
            // Update employee Image
            String referenceNumber = employee.getImageName();
            if (!StringUtil.isNullOrBlank(referenceNumber)) {
                updateEmployeeImage(referenceNumber, employeeNumber);
            }
            // If employee information is verified then create login and notification.
            if (verified) {
                employee.setEmployeeId(employeeId);
                createEmployeeProfile(employee);
            }
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        } catch (DaoException daoException) {
            // Rollback Employee details if already  have been saved.
            try {
                if (!StringUtil.isNullOrBlank(employeeNumber)) {
                    employeeDao.delete(employeeNumber);
                }
            } catch (DaoException daoException2) {
                throw new DataException("Unable to create employee now. Please try again.");
            }
            throw new DataException("Unable to create employee now. Please try again.");
        }
        return employeeId;
    }

    /**
     * Update.
     * 
     * @param employeeId the employee id
     * @param employee the employee
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(int employeeId, EmployeeDto employee) throws DataException {

        String employeeNumber = null;
        try {
            // Validate the employee
            employeeValidator.validate(employee);
            employeeNumber = employee.getEmployeeNumber();
            EmployeeDto existingEmployee = employeeDao.get(employeeNumber);
            if (existingEmployee == null) {
                throw new DataException("Employee with Employee Number (" + employeeNumber + ") does not exist.");
            }
            employeeId = existingEmployee.getEmployeeId();
            employeeDao.update(existingEmployee.getEmployeeId(), employee);
            // Update employee attributes.
            employeeContactDao.update(employeeId, employee.getEmployeeContact());

            // Update employee documents
            List<EmployeeDocument> employeeDocuments = employee.getEmployeeDocuments();
            if (employeeDocuments != null && !employeeDocuments.isEmpty()) {
                for (EmployeeDocument employeeDocument : employeeDocuments) {
                    int employeeDocumentId = employeeDocument.getEmployeeDocumentId();
                    if (employeeDocumentId == 0) {
                        employeeDocumentDao.create(employeeId, employeeDocument);
                    } else {
                        employeeDocumentDao.update(employeeDocumentId, employeeDocument);
                    }
                }
            }
            // Update employee educations
            List<EmployeeEducation> employeeEducations = employee.getEmployeeEducations();
            if (employeeEducations != null && !employeeEducations.isEmpty()) {
                for (EmployeeEducation employeeEducation : employeeEducations) {
                    int educationId = employeeEducation.getEducationId();
                    if (educationId == 0) {
                        employeeEducationDao.create(employeeId, employeeEducation);
                    } else {
                        employeeEducationDao.update(educationId, employeeEducation);
                    }
                }
            }
            // Update employee experiences
            List<EmployeeExperience> employeeExperiences = employee.getEmployeeExperiences();
            if (employeeExperiences != null && !employeeExperiences.isEmpty()) {
                for (EmployeeExperience employeeExperience : employeeExperiences) {
                    int experienceId = employeeExperience.getExperienceId();
                    if (experienceId == 0) {
                        employeeExperienceDao.create(employeeId, employeeExperience);
                    } else {
                        employeeExperienceDao.update(experienceId, employeeExperience);
                    }
                }
            }
            // Update employee Promotions
            List<EmployeePromotion> employeePromotions = employee.getEmployeePromotions();
            if (employeePromotions != null && !employeePromotions.isEmpty()) {
                for (EmployeePromotion employeePromotion : employeePromotions) {
                    int promotionId = employeePromotion.getPromotionId();
                    if (promotionId == 0) {
                        employeePromotionDao.create(employeeId, employeePromotion);
                    } else {
                        employeePromotionDao.update(promotionId, employeePromotion);
                    }
                }
            }
            // Update employee subjects
            List<EmployeeSubjectDto> employeeSubjects = employee.getEmployeeSubjects();
            if (employeeSubjects != null && !employeeSubjects.isEmpty()) {
                for (EmployeeSubjectDto employeeSubject : employeeSubjects) {
                    int employeeSubjectId = employeeSubject.getEmployeeSubjectId();
                    if (employeeSubjectId == 0) {
                        employeeSubjectDao.create(employeeId, employeeSubject);
                    } else {
                        employeeSubjectDao.update(employeeSubjectId, employeeSubject);
                    }
                }
            }
            String referenceNumber = employee.getImageName();
            if (!StringUtil.isNullOrBlank(referenceNumber)) {
                updateEmployeeImage(referenceNumber, employeeNumber);
            }
            // If employee is not verified already and verified now then create user profile and a notification.
            boolean verified = employee.isVerified();
            boolean alreadyVerified = existingEmployee.isVerified();
            if (!alreadyVerified && verified) {
                employee.setEmployeeId(employeeId);
                createEmployeeProfile(employee);
            }
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        } catch (DaoException daoException) {
            throw new DataException("Unable to update employee now. Please try again.");
        }
        return true;
    }

    /**
     * Delete.
     *
     * @param employeeNumber the employee number
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean delete(String employeeNumber) throws DataException {
        try {
            return employeeDao.delete(employeeNumber);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Gets the last employee number.
     *
     * @return the next employee number
     * @throws DataException the data exception
     */
    public String getLastEmployeeNumber() throws DataException {
        String employeeNumber = null;
        try {
            employeeNumber = employeeDao.getLastEmployeeNumber();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return employeeNumber;
    }

    /**
     * Gets the all.
     *
     * @param employeeSearchCriteriaDto the employee search criteria dto
     * @return the all
     * @throws DataException the data exception
     */
    public List<EmployeeDto> getAll(
            EmployeeSearchCriteriaDto employeeSearchCriteriaDto) throws DataException {
        List<EmployeeDto> employees = null;
        try {
            employees = employeeDao.getAll(employeeSearchCriteriaDto);
            if (employees != null && !employees.isEmpty()) {
                for (EmployeeDto employee : employees) {
                    EmployeeDto reportingTo = employee.getReportingTo();
                    if (reportingTo != null && reportingTo.getEmployeeId() != 0) {
                        employee.setReportingTo(employeeDao.get(reportingTo.getEmployeeId()));
                    }
                }
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return employees;
    }

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DataException the data exception
     */
    public List<EmployeeDto> getAll() throws DataException {
        return getAll((EmployeeSearchCriteriaDto) null);
    }

    /**
     * Gets the notification email id.
     * 
     * @param myschoolProfile the myschool profile
     * @param employee the employee
     * @return the notification email id
     */
    public String getNotificationEmailId(MySchoolProfileDto myschoolProfile, EmployeeDto employee) {
        String notificationEmailId = null;
        if (myschoolProfile.isEmailActive() && myschoolProfile.isEmailEmployees()) {
            EmployeeContact employeeContact = employee.getEmployeeContact();
            if (employeeContact != null) {
                EmployeeNotificationTo emailNotificationTo = employeeContact.getEmailNotificationTo();
                if (emailNotificationTo != null) {
                    if (emailNotificationTo == EmployeeNotificationTo.PERSONAL) {
                        String personalEmailId = employeeContact.getPersonalEmailId();
                        if (!StringUtil.isNullOrBlank(personalEmailId)) {
                            notificationEmailId = personalEmailId;
                        }
                    } else if (emailNotificationTo == EmployeeNotificationTo.WORK) {
                        String officeEmailId = employeeContact.getOfficeMobileNumber();
                        if (!StringUtil.isNullOrBlank(officeEmailId)) {
                            notificationEmailId = officeEmailId;
                        }
                    }
                }
            }
        }
        return notificationEmailId;
    }

    /**
     * Gets the notification mobile number.
     * 
     * @param myschoolProfile the myschool profile
     * @param employee the employee
     * @return the notification mobile number
     */
    public String getNotificationMobileNumber(MySchoolProfileDto myschoolProfile, EmployeeDto employee) {
        String notificationMobileNumber = null;
        if (myschoolProfile.isSmsActive() && myschoolProfile.isSmsEmployees()) {
            EmployeeContact employeeContact = employee.getEmployeeContact();
            if (employeeContact != null) {
                EmployeeNotificationTo smsNotificationTo = employeeContact.getSmsNotificationTo();
                if (smsNotificationTo != null) {
                    if (smsNotificationTo == EmployeeNotificationTo.PERSONAL) {
                        String personalMobileNumber = employeeContact.getPersonalMobileNumber();
                        if (!StringUtil.isNullOrBlank(personalMobileNumber)) {
                            notificationMobileNumber = personalMobileNumber;
                        }
                    } else if (smsNotificationTo == EmployeeNotificationTo.WORK) {
                        String officeMobileNumber = employeeContact.getOfficeMobileNumber();
                        if (!StringUtil.isNullOrBlank(officeMobileNumber)) {
                            notificationMobileNumber = officeMobileNumber;
                        }
                    }
                }
            }
        }
        return notificationMobileNumber;
    }

    /**
     * Creates the employee profile.
     * 
     * @param employee the employee
     * @throws DataException the data exception
     */
    private void createEmployeeProfile(EmployeeDto employee) throws DataException {
        int employeeId = employee.getEmployeeId();
        UsersDto user = userManager.getUser(UserType.EMPLOYEE, employeeId);
        if (user == null) {
            int userId = userManager.createUser(employee);
            if (userId > 0) {
                MySchoolProfileDto myschoolProfile = profileManager.getMyschoolProfile();
                List<Integer> mailNotifyingIds = new ArrayList<Integer>();
                List<Integer> smsNotifyingIds = new ArrayList<Integer>();
                String notificationEmailId = getNotificationEmailId(myschoolProfile, employee);
                if (notificationEmailId != null) {
                    mailNotifyingIds.add(employeeId);
                }
                String notificationMobileNumber = getNotificationMobileNumber(myschoolProfile, employee);
                if (notificationMobileNumber != null) {
                    smsNotifyingIds.add(employeeId);
                }
                notificationManager.createNotification(
                        NotificationEndPoint.EMPLOYEE,
                        NotificationType.REGISTRATION, mailNotifyingIds,
                        smsNotifyingIds);
            }
        }
    }

}
