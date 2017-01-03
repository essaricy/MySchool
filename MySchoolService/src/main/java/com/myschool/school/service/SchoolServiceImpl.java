package com.myschool.school.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.common.exception.ServiceException;
import com.myschool.school.domain.SchoolManager;
import com.myschool.school.dto.SchoolDto;
import com.quasar.core.exception.DataException;

/**
 * The Class BranchServiceImpl.
 */
@Service
public class SchoolServiceImpl implements SchoolService {

    /** The branch manager. */
    @Autowired
    private SchoolManager schoolManager;

    /*
     * (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(SchoolDto schoolDto) throws ServiceException {
        boolean created = false;
        try {
            created = schoolManager.create(schoolDto);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return created;
    }

    /*
     * (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#delete(int)
     */
    @Override
    public boolean delete(int schoolId) throws ServiceException {
        boolean deleted = false;
        try {
            deleted = schoolManager.delete(schoolId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return deleted;
    }

    /*
     * (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#get(int)
     */
    @Override
    public SchoolDto get(int schoolId) throws ServiceException {
    	SchoolDto school = null;
        try {
            school = schoolManager.get(schoolId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return school;
    }

    /*
     * (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#getAll()
     */
    @Override
    public List<SchoolDto> getAll() throws ServiceException {
        List<SchoolDto> schools = null;
        try {
            schools = schoolManager.getAll();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return schools;
    }

    /*
     * (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#update(int,
     * java.lang.Object)
     */
    @Override
    public boolean update(int schoolId, SchoolDto schoolDto)
            throws ServiceException {
        boolean updated = false;
        try {
            updated = schoolManager.update(schoolId, schoolDto);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return updated;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.SchoolService#getByBranch(int)
     */
    @Override
    public List<SchoolDto> getByBranch(int branchId) throws ServiceException {
        List<SchoolDto> schools = null;
        try {
            schools = schoolManager.getByBranch(branchId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return schools;
    }

}
