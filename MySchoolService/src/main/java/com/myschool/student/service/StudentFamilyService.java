package com.myschool.student.service;

import java.util.List;

import com.myschool.application.service.Servicable;
import com.myschool.common.dto.FamilyMemberDto;
import com.myschool.common.exception.ServiceException;

/**
 * The Interface StudentFamilyService.
 */
public interface StudentFamilyService extends Servicable<FamilyMemberDto> {

    /**
     * Gets the by student.
     * 
     * @param admissionNumber the admission number
     * @return the by student
     * @throws ServiceException the service exception
     */
    List<FamilyMemberDto> getByStudent(String admissionNumber)
            throws ServiceException;

    /**
     * Validate.
     * 
     * @param familyMember the family member
     * @throws ServiceException the service exception
     */
    void validate(FamilyMemberDto familyMember) throws ServiceException;

    /**
     * Validate.
     * 
     * @param familyMembers the family members
     * @throws ServiceException the service exception
     */
    void validate(List<FamilyMemberDto> familyMembers) throws ServiceException;

    /**
     * Creates the.
     * 
     * @param admissionNumber the admission number
     * @param FamilyMember the family member
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean create(String admissionNumber, FamilyMemberDto FamilyMember)
            throws ServiceException;

}
