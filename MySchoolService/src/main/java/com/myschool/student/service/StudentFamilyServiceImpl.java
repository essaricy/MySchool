package com.myschool.student.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.common.dto.FamilyMemberDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.student.domain.StudentFamilyManager;

/**
 * The Class StudentFamilyServiceImpl.
 */
@Service
public class StudentFamilyServiceImpl implements StudentFamilyService {

    /** The student family manager. */
    @Autowired
    private StudentFamilyManager studentFamilyManager;

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(FamilyMemberDto familyMember) throws ServiceException {
        throw new ServiceException("Use create(admissionNumber, FamilyMemberDto)");
    }

    /* (non-Javadoc)
     * @see com.myschool.student.service.StudentFamilyService#create(java.lang.String, com.myschool.common.dto.FamilyMemberDto)
     */
    @Override
    public boolean create(String admissionNumber, FamilyMemberDto familyMember) throws ServiceException {
        try {
            return studentFamilyManager.create(admissionNumber, familyMember);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#getAll()
     */
    @Override
    public List<FamilyMemberDto> getAll() throws ServiceException {
        throw new ServiceException("Use get(familyMemberId)");
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#get(int)
     */
    @Override
    public FamilyMemberDto get(int familyMemberId) throws ServiceException {
        try {
            return studentFamilyManager.get(familyMemberId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int familyMemberId, FamilyMemberDto familyMember) throws ServiceException {
        try {
            return studentFamilyManager.update(familyMemberId, familyMember);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#delete(int)
     */
    @Override
    public boolean delete(int familyMemberId) throws ServiceException {
        try {
            return studentFamilyManager.delete(familyMemberId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.student.service.StudentFamilyService#validate(com.myschool.common.dto.FamilyMemberDto)
     */
    @Override
    public void validate(FamilyMemberDto familyMember) throws ServiceException {
        try {
            studentFamilyManager.validate(familyMember);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.student.service.StudentFamilyService#validate(java.util.List)
     */
    @Override
    public void validate(List<FamilyMemberDto> familyMembers) throws ServiceException {
        try {
            studentFamilyManager.validate(familyMembers);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.student.service.StudentFamilyService#getByStudent(java.lang.String)
     */
    @Override
    public List<FamilyMemberDto> getByStudent(String admissionNumber) throws ServiceException {
        try {
            return studentFamilyManager.getByStudent(admissionNumber);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

}
