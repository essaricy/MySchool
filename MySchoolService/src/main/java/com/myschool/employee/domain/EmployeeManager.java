package com.myschool.employee.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.application.domain.ProfileManager;
import com.myschool.application.dto.MySchoolProfileDto;
import com.myschool.common.assembler.ImageDataAssembler;
import com.myschool.common.constants.RecordStatus;
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
import com.myschool.image.constant.ImageSize;
import com.myschool.infra.filesystem.agent.TempFileSystem;
import com.myschool.infra.storage.StorageAccessAgent;
import com.myschool.infra.storage.exception.StorageAccessException;
import com.myschool.notification.constants.NotificationEndPoint;
import com.myschool.notification.constants.NotificationType;
import com.myschool.notification.domain.NotificationManager;
import com.myschool.storage.dto.StorageItem;
import com.myschool.user.constants.UserType;
import com.myschool.user.domain.UserManager;
import com.myschool.user.dto.UsersDto;

/**
 * The Class EmployeeManager.
 */
@Component
public class EmployeeManager {

    private static final Logger LOGGER = Logger.getLogger(EmployeeManager.class);

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

    /** The storage access agent. */
    @Autowired
    private StorageAccessAgent storageAccessAgent;

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
                StorageItem storageItem = storageAccessAgent.EMPLOYEE_STORAGE.get(RecordStatus.get(employee.isVerified()), employee.getEmployeeNumber());
                employee.setImageAccess(ImageDataAssembler.create(storageItem));
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        } catch (StorageAccessException storageAccessException) {
            throw new DataException(storageAccessException.getMessage(), storageAccessException);
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
                StorageItem storageItem = storageAccessAgent.EMPLOYEE_STORAGE.get(RecordStatus.get(employee.isVerified()), employee.getEmployeeNumber());
                employee.setImageAccess(ImageDataAssembler.create(storageItem));
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        } catch (StorageAccessException storageAccessException) {
            throw new DataException(storageAccessException.getMessage(), storageAccessException);
        }
        return employee;
    }

	/**
     * Gets the next.
     *
     * @param employeeNumber the employee number
     * @param recordStatus the record status
     * @return the next
     * @throws DataException the data exception
     */
	public EmployeeDto getNext(String employeeNumber, RecordStatus recordStatus) throws DataException {
		EmployeeDto employee = null;
        try {
        	if (recordStatus == null) {
        		throw new DataException("Search type must be either " + RecordStatus.VERIFIED + " or " + RecordStatus.UNVERIFIED);
        	}
            String nextEmployeeNumber = employeeDao.getNextEmployeeNumber(employeeNumber, recordStatus);
            if (nextEmployeeNumber != null) {
            	employee = employeeDao.get(nextEmployeeNumber);
            	if (employee != null) {
            		employee.setEmployeeContact(employeeContactDao.get(employee.getEmployeeId()));
            		StorageItem storageItem = storageAccessAgent.EMPLOYEE_STORAGE.get(RecordStatus.get(employee.isVerified()), employee.getEmployeeNumber());
                    employee.setImageAccess(ImageDataAssembler.create(storageItem));
            	}
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        } catch (StorageAccessException storageAccessException) {
            throw new DataException(storageAccessException.getMessage(), storageAccessException);
        }
        return employee;
    }

	/**
     * Gets the previous.
     *
     * @param employeeNumber the employee number
     * @param recordStatus the record status
     * @return the previous
     * @throws DataException the data exception
     */
	public EmployeeDto getPrevious(String employeeNumber, RecordStatus recordStatus) throws DataException {
		EmployeeDto employee = null;
        try {
        	if (recordStatus == null) {
        		throw new DataException("Search type must be either " + RecordStatus.VERIFIED + " or " + RecordStatus.UNVERIFIED);
        	}
            String previousEmployeeNumber = employeeDao.getPreviousEmployeeNumber(employeeNumber, recordStatus);
            if (previousEmployeeNumber != null) {
            	employee = employeeDao.get(previousEmployeeNumber);
            	if (employee != null) {
            		employee.setEmployeeContact(employeeContactDao.get(employee.getEmployeeId()));
            		StorageItem storageItem = storageAccessAgent.EMPLOYEE_STORAGE.get(RecordStatus.get(employee.isVerified()), employee.getEmployeeNumber());
                    employee.setImageAccess(ImageDataAssembler.create(storageItem));
            	}
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        } catch (StorageAccessException storageAccessException) {
            throw new DataException(storageAccessException.getMessage(), storageAccessException);
        }
        return employee;
    }

    /**
     * Update employee image.
     *
     * @param referenceNumber the reference number
     * @param employeeNumber the employee number
     * @param verified the verified
     * @throws DataException the data exception
     */
    public void updateEmployeeImage(String referenceNumber, String employeeNumber, boolean verified) throws DataException {
        try {
            if (referenceNumber != null && employeeNumber != null) {
                File file = tempFileSystem.getEmployeeImage(referenceNumber, ImageSize.ORIGINAL);
                storageAccessAgent.EMPLOYEE_STORAGE.update(file, RecordStatus.get(verified), employeeNumber);
            }
        } catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
        } catch (StorageAccessException storageAccessException) {
            throw new DataException(storageAccessException.getMessage(), storageAccessException);
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
            //boolean verified = employee.isVerified();
            boolean verify = employee.isVerify();
            if (verify) {
                employee.setVerified(true);
            } else {
                employee.setVerified(false);
            }
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
            // If employee information is verified then create login and notification.
            if (verify) {
                employee.setEmployeeId(employeeId);
                createEmployeeProfile(employee);
            }
            // Update employee Image
            String referenceNumber = employee.getImageName();
            if (!StringUtil.isNullOrBlank(referenceNumber)) {
                updateEmployeeImage(referenceNumber, employeeNumber, employee.isVerified());
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
            System.out.println("employeeNumber=" + employeeNumber);
            EmployeeDto existingEmployee = employeeDao.get(employeeNumber);
            if (existingEmployee == null) {
                throw new DataException("Employee with Employee Number (" + employeeNumber + ") does not exist.");
            }
            boolean verify = employee.isVerify();
            boolean alreadyVerified = existingEmployee.isVerified();
            employeeId = existingEmployee.getEmployeeId();
            if (verify) {
                System.out.println("Set to verified");
                employee.setVerified(true);
            } else {
                employee.setVerified(alreadyVerified);
            }
            employeeDao.update(existingEmployee.getEmployeeId(), employee);
            System.out.println("Updated employee info");
            // Update employee attributes.
            employeeContactDao.update(employeeId, employee.getEmployeeContact());
            System.out.println("Updated employee contact info");

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
            System.out.println("Updated employee documents");
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
            System.out.println("Updated employee education");
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
            System.out.println("Updated employee experience");
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
            System.out.println("Updated employee promotion");
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
            System.out.println("Updated employee subjects info");
            // If the employee data is verified
            if (verify) {
                if (alreadyVerified) {
                    // This employee is already verified. nothing to do.
                } else {
                    System.out.println("Employee data has been verified. Moving to VERIFIED.");
                    // This employee data has not been verified before. so create employee profile
                    employee.setEmployeeId(employeeId);
                    createEmployeeProfile(employee);
                    System.out.println("Creted employee proifile for login");

                    // Update the employee image if one is provided now. update to UNVERIFIED
                    String imageName = employee.getImageName();
                    if (!StringUtil.isNullOrBlank(imageName)) {
                        System.out.println("Updating employee image with " + imageName);
                        updateEmployeeImage(imageName, employeeNumber, false);
                        System.out.println("Updated employee image provided now.");
                    }
                    // Now the employee image has been updated. Move the image to verified images.
                    storageAccessAgent.EMPLOYEE_STORAGE.verify(employeeNumber);
                    System.out.println("Moved employee image to VERIFIED, if one did exist.");
                }
            } else {
                // This is just save. could be VERIFIED or UNVERIFIED.
                // Update the employee image if one is provided now
                String imageName = employee.getImageName();
                if (!StringUtil.isNullOrBlank(imageName)) {
                    System.out.println("Updating employee image with " + imageName);
                    updateEmployeeImage(imageName, employeeNumber, alreadyVerified);
                    System.out.println("Updated employee image to " + RecordStatus.get(alreadyVerified));
                }
            }
            System.out.println("EVERYTHING completed.");
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        } catch (DaoException daoException) {
            throw new DataException("Unable to update employee now. Please try again.");
        } catch (StorageAccessException storageAccessException) {
            throw new DataException(storageAccessException.getMessage(), storageAccessException);
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
            EmployeeDto employee = employeeDao.get(employeeNumber);
            if (employee != null) {
                storageAccessAgent.EMPLOYEE_STORAGE.delete(RecordStatus.get(employee.isVerified()), employeeNumber);
                return employeeDao.delete(employeeNumber);
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        } catch (StorageAccessException storageAccessException) {
            throw new DataException(storageAccessException.getMessage(), storageAccessException);
        }
        return true;
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
                    try {
                        StorageItem storageItem = storageAccessAgent.EMPLOYEE_STORAGE.get(RecordStatus.get(employee.isVerified()), employee.getEmployeeNumber());
                        employee.setImageAccess(ImageDataAssembler.create(storageItem));
                    } catch (StorageAccessException storageAccessException) {
                        LOGGER.error(storageAccessException.getMessage(), storageAccessException);
                    }
                }
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }/* catch (StorageAccessException storageAccessException) {
            throw new DataException(storageAccessException.getMessage(), storageAccessException);
        }*/
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
            file = tempFileSystem.getEmployeeImage(referenceNumber, imageSize);
        } catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
        }
        return file;
    }

}
