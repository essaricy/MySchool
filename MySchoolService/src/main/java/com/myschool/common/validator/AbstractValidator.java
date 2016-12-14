package com.myschool.common.validator;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.application.dao.DocumentDao;
import com.myschool.clazz.dao.RegisteredClassDao;
import com.myschool.clazz.dao.RegisteredSubjectDao;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.clazz.dto.RegisteredSubjectDto;
import com.myschool.common.constants.DocumentApplicability;
import com.myschool.common.dto.DocumentDto;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.util.StringUtil;
import com.myschool.employee.dao.DesignationDao;
import com.myschool.employee.dto.DesignationDto;
import com.myschool.infra.cache.exception.CacheException;
import com.myschool.user.constants.UserType;

/**
 * The Class AbstractValidator.
 * 
 * @param <Type> the generic type
 */
@Component
public abstract class AbstractValidator<Type> {

    /** The Constant REQUIRED. */
    private static final String REQUIRED = "{0} is a required value";

    /** The Constant INVALID. */
    private static final String INVALID = "Value for {0} is invalid.";

    /** The document dao. */
    @Autowired
    private DocumentDao documentDao;

    /** The designation dao. */
    @Autowired
    private DesignationDao designationDao;

    /** The registered class dao. */
    @Autowired
    private RegisteredClassDao registeredClassDao;

    /** The registered subject dao. */
    @Autowired
    private RegisteredSubjectDao registeredSubjectDao;

    /**
     * Validate.
     * 
     * @param type the type
     * @throws ValidationException the validation exception
     */
    public void validate(Type type) throws ValidationException {
        if (type == null) {
            throw new ValidationException("No information is provided.");
        }
        doValidate(type);
    }

    /**
     * Validate.
     * 
     * @param id the id
     * @throws ValidationException the validation exception
     */
    public void validate(int id) throws ValidationException {
        if (id <= 0) {
            throw new ValidationException("Invalid value for ID.");
        }
    }

    /**
     * Validate update.
     * 
     * @param id the id
     * @param type the type
     * @throws ValidationException the validation exception
     */
    public void validate(int id, Type type) throws ValidationException {
        if (id <= 0) {
            throw new ValidationException("Invalid value for ID.");
        }
        if (type == null) {
            throw new ValidationException("No information is provided.");
        }
        doValidate(type);
    }

    /**
     * Validate.
     * 
     * @param code the code
     * @throws ValidationException the validation exception
     */
    public void validate(String code) throws ValidationException {
        if (StringUtil.isNullOrBlank(code)) {
            throw new ValidationException("Invalid value for code.");
        }
    }

    /**
     * Validate.
     * 
     * @param code the code
     * @param type the type
     * @throws ValidationException the validation exception
     */
    public void validate(String code, Type type) throws ValidationException {
        if (StringUtil.isNullOrBlank(code)) {
            throw new ValidationException("Invalid value for code.");
        }
        if (type == null) {
            throw new ValidationException("No information is provided.");
        }
        doValidate(type);
    }

    /**
     * Do validate.
     * 
     * @param type the type
     * @throws ValidationException the validation exception
     */
    protected abstract void doValidate(Type type) throws ValidationException;

    /**
     * Validate.
     * 
     * @param fieldValue the field value
     * @param fieldName the field name
     * @param dataType the data type
     * @param required the required
     * @return the string
     * @throws ValidationException the validation exception
     */
    protected String validate(String fieldValue, String fieldName,
            String dataType, boolean required) throws ValidationException {
        //System.out.println("validate(" + fieldValue + ", " + fieldName + ", " + dataType + ", " + required + ")");
        String validationError = null;
        try {
            if (fieldValue != null) {
                fieldValue = fieldValue.trim();
            }
            if (required) {
                if (StringUtil.isNullOrBlank(fieldValue)) {
                    validationError = MessageFormat.format(REQUIRED, fieldName);
                }
            }
            if (!StringUtil.isNullOrBlank(fieldValue) && !DataTypeValidator.validate(fieldValue, dataType)) {
                validationError = MessageFormat.format(INVALID, fieldName);
            }
            if (validationError != null) {
                throw new ValidationException(validationError);
            }
        } catch (DataException dataException) {
            throw new ValidationException(dataException.getMessage(), dataException);
        }
        return fieldValue;
    }


    /**
     * Validate document.
     * 
     * @param inDocument the in document
     * @param userType the user type
     * @return the document dto
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     */
    protected DocumentDto validateDocument(DocumentDto inDocument,
            UserType userType) throws ValidationException, DaoException {
        // check if the document is valid
        if (inDocument == null) {
            throw new ValidationException("Document Type is not specified.");
        }
        int documentId = inDocument.getDocumentId();
        DocumentDto outDocument = documentDao.get(documentId);
        if (outDocument == null) {
            throw new ValidationException("There is no Document Type.");
        }
        if (userType == UserType.EMPLOYEE) {
            DocumentApplicability applicabilityForEmployee = outDocument.getApplicabilityForEmployee();
            if (applicabilityForEmployee == DocumentApplicability.NOT_APPLICABLE) {
                throw new ValidationException("The Document is not applicable for Employee.");
            }
        }
        if (userType == UserType.STUDENT) {
            DocumentApplicability applicabilityForStudent = outDocument.getApplicabilityForStudent();
            if (applicabilityForStudent == DocumentApplicability.NOT_APPLICABLE) {
                throw new ValidationException("The Document is not applicable for Student.");
            }
        }
        return outDocument;
    }

    /**
     * Validate registered class.
     * 
     * @param inRegisteredClass the in registered class
     * @return the registered class dto
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     */
    protected RegisteredClassDto validateRegisteredClass(
            RegisteredClassDto inRegisteredClass) throws ValidationException,
            DaoException {
        // check if the Registered Class is valid
        if (inRegisteredClass == null) {
            throw new ValidationException("Registered Class is not specified.");
        }
        int classId = inRegisteredClass.getClassId();
        RegisteredClassDto outRegisteredClass = registeredClassDao.get(classId);
        if (outRegisteredClass == null) {
            throw new ValidationException("There is no Registered Class.");
        }
        return outRegisteredClass;
    }

    /**
     * Validate registered subject.
     * 
     * @param inRegisteredSubject the in registered subject
     * @return the registered subject dto
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     */
    protected RegisteredSubjectDto validateRegisteredSubject(
            RegisteredSubjectDto inRegisteredSubject) throws ValidationException,
            DaoException {
        // check if the Registered Subject is valid
        if (inRegisteredSubject == null) {
            throw new ValidationException("Registered Subject is not specified.");
        }
        int subjectId = inRegisteredSubject.getSubjectId();
        RegisteredSubjectDto outRegisteredSubject = registeredSubjectDao.get(subjectId);
        if (outRegisteredSubject == null) {
            throw new ValidationException("There is no Registered Subject.");
        }
        return outRegisteredSubject;
    }

    /**
     * Validate designation.
     * 
     * @param inDesignation the in designation
     * @return the designation dto
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws CacheException the cache exception
     */
    public DesignationDto validateDesignation(DesignationDto inDesignation)
            throws ValidationException, DaoException, CacheException {
        if (inDesignation == null) {
            throw new ValidationException("Designation information is not specified.");
        }
        int designationId = inDesignation.getDesignationId();
        if (designationId <= 0) {
            throw new ValidationException("Designation is a required value.");
        }
        DesignationDto outDesignation = designationDao.get(designationId);
        if (outDesignation == null || outDesignation.getDesignationId() == 0) {
            throw new ValidationException("Designation (" + designationId + ") does not exists.");
        }
        return outDesignation;
    }

}
