package com.myschool.student.dao;

import java.util.List;

import com.myschool.common.dto.FamilyMemberDto;
import com.myschool.common.dto.Relationship;
import com.myschool.common.exception.DaoException;

/**
 * The Interface StudentFamilyDao.
 */
public interface StudentFamilyDao {

    /**
     * Gets the.
     * 
     * @param familyMemberId the family member id
     * @return the family member dto
     * @throws DaoException the dao exception
     */
    FamilyMemberDto get(int familyMemberId) throws DaoException;

    /**
     * Creates the.
     * 
     * @param studentId the student id
     * @param familyMember the family member
     * @return the int
     * @throws DaoException the dao exception
     */
    int create(int studentId, FamilyMemberDto familyMember) throws DaoException;

    /**
     * Creates the.
     * 
     * @param studentId the student id
     * @param familyMembers the family members
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean create(int studentId, List<FamilyMemberDto> familyMembers)
            throws DaoException;

    /**
     * Update.
     * 
     * @param familyMemberId the family member id
     * @param familyMember the family member
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean update(int familyMemberId, FamilyMemberDto familyMember) throws DaoException;

    /**
     * Delete.
     * 
     * @param familyMemberId the family member id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean delete(int familyMemberId) throws DaoException;

    /**
     * Gets the by student.
     * 
     * @param admissionNumber the admission number
     * @return the by student
     * @throws DaoException the dao exception
     */
    List<FamilyMemberDto> getByStudent(String admissionNumber) throws DaoException;

    /**
     * Gets the family member.
     * 
     * @param studentId the student id
     * @param relationship the relationship
     * @param familyMemberName the family member name
     * @return the family member
     * @throws DaoException the dao exception
     */
    FamilyMemberDto get(int studentId, Relationship relationship,
            String familyMemberName) throws DaoException;


}
