package com.myschool.student.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.dto.FamilyMemberDto;
import com.myschool.common.dto.Relationship;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ValidationException;
import com.myschool.student.dao.StudentFamilyDao;
import com.myschool.student.dto.StudentDto;
import com.myschool.student.validator.StudentFamilyValidator;

/**
 * The Class StudentFamilyManager.
 */
@Component
public class StudentFamilyManager {

    /** The student family dao. */
    @Autowired
    private StudentFamilyDao studentFamilyDao;

    /** The student manager. */
    @Autowired
    private StudentManager studentManager;

    /** The student Family validator. */
    @Autowired
    private StudentFamilyValidator studentFamilyValidator;

    /**
     * Gets the.
     * 
     * @param familyMemberId the family member id
     * @return the family member dto
     * @throws DataException the data exception
     */
    public FamilyMemberDto get(int familyMemberId) throws DataException {
        try {
            return studentFamilyDao.get(familyMemberId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Creates the.
     * 
     * @param admissionNumber the admission number
     * @param familyMember the family member
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean create(String admissionNumber, FamilyMemberDto familyMember) throws DataException {
        try {
            StudentDto student = studentManager.getStudent(admissionNumber);
            if (student == null) {
                throw new DataException("There is no student with admission number '" + admissionNumber + "'");
            }
            studentFamilyValidator.validate(familyMember);
            return studentFamilyDao.create(student.getStudentId(), familyMember) > 0;
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
    }

    /**
     * Validate.
     * 
     * @param familyMember the family member
     * @throws DataException the data exception
     */
    public void validate(FamilyMemberDto familyMember) throws DataException {
        try {
            studentFamilyValidator.validate(familyMember);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
    }

    /**
     * Update.
     * 
     * @param familyMemberId the family member id
     * @param familyMember the family member
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(int familyMemberId,
            FamilyMemberDto familyMember) throws DataException {
        try {
            studentFamilyValidator.validate(familyMemberId, familyMember);
            return studentFamilyDao.update(familyMemberId, familyMember);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
    }

    /**
     * Delete.
     * 
     * @param familyMemberId the student document id
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean delete(int familyMemberId) throws DataException {
        try {
            studentFamilyValidator.validate(familyMemberId);
            return studentFamilyDao.delete(familyMemberId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
    }

    /**
     * Validate.
     * 
     * @param familyMembers the family members
     * @throws DataException the data exception
     */
    public void validate(List<FamilyMemberDto> familyMembers) throws DataException {
        boolean fatherPresent = false;
        boolean motherPresent = false;
        boolean guardianPresent = false;
        String relationShipcode = null;
        Relationship relationship = null;
        FamilyMemberDto familyMember = null;
        try {
            int documentsSize = (familyMembers == null) ? 0 : familyMembers.size();
            for (int index=0; index < documentsSize; index++) {
                familyMember = familyMembers.get(index);
                // Check if all the documents are valid or not.
                studentFamilyValidator.validate(familyMember);
                relationship = familyMember.getRelationship();
                relationShipcode = relationship.getCode();
                // Check if more than one 'Father' entries are found.
                if (relationShipcode.equalsIgnoreCase("F")) {
                    if (fatherPresent) {
                        throw new DataException("Father information is already provided.");
                    }
                    fatherPresent = true;
                }
                // Check if more than one 'Mother' entries are found.
                if (relationShipcode.equalsIgnoreCase("M")) {
                    if (motherPresent) {
                        throw new DataException("Mother information is already provided.");
                    }
                    motherPresent = true;
                }
                // Check if more than one 'Guardian' entries are found.
                if (relationShipcode.equalsIgnoreCase("G")) {
                    if (guardianPresent) {
                        throw new DataException("Guardian information is already provided.");
                    }
                    guardianPresent = true;
                }
            }
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
    }

    /**
     * Gets the by student.
     * 
     * @param admissionNumber the admission number
     * @return the by student
     * @throws DataException the data exception
     */
    public List<FamilyMemberDto> getByStudent(String admissionNumber) throws DataException {
        try {
            return studentFamilyDao.getByStudent(admissionNumber);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Validate parent.
     * 
     * @param familyMembers the family members
     * @throws DataException the data exception
     */
    public void validateParent(List<FamilyMemberDto> familyMembers) throws DataException {
        boolean fatherPresent = false;
        boolean motherPresent = false;
        boolean guardianPresent = false;
        String relationShipcode = null;
        Relationship relationship = null;
        FamilyMemberDto familyMember = null;
        try {
            int numberOfFamilyMembers = (familyMembers == null) ? 0 : familyMembers.size();
            for (int index=0; index < numberOfFamilyMembers; index++) {
                familyMember = familyMembers.get(index);
                // Check if all the documents are valid or not.
                relationship = familyMember.getRelationship();
                relationShipcode = relationship.getCode();
                // Check if more than one 'Father' entries are found.
                if (!fatherPresent && relationShipcode.equalsIgnoreCase("F")) {
                    fatherPresent = true;
                }
                // Check if more than one 'Mother' entries are found.
                if (!motherPresent && relationShipcode.equalsIgnoreCase("M")) {
                    motherPresent = true;
                }
                // Check if more than one 'Guardian' entries are found.
                if (!guardianPresent && relationShipcode.equalsIgnoreCase("G")) {
                    guardianPresent = true;
                }
            }
            if (!fatherPresent && !motherPresent && !guardianPresent) {
                throw new ValidationException("You must provide Father, Mother or Guardian information.");
            }
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
    }

}
