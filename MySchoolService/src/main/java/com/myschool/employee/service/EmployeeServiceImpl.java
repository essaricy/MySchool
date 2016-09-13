package com.myschool.employee.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.common.constants.RecordStatus;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.employee.domain.EmployeeManager;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.employee.dto.EmployeeSearchCriteriaDto;
import com.myschool.image.constant.ImageSize;

/**
 * The Class EmployeeServiceImpl.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    /** The employee manager. */
    @Autowired
    private EmployeeManager employeeManager;

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(EmployeeDto employeeDto) throws ServiceException {
        try {
            return (employeeManager.create(employeeDto) > 0);
       } catch (DataException dataException) {
           throw new ServiceException(dataException.getMessage(),
                   dataException);
       }
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#delete(int)
     */
    @Override
    public boolean delete(int employeeId) throws ServiceException {
        throw new ServiceException("Deleting an employee is not supported. Instead mark the employee as Terminated.");
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#get(int)
     */
    @Override
    public EmployeeDto get(int employeeId) throws ServiceException {
        return null;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#getAll()
     */
    @Override
    public List<EmployeeDto> getAll() throws ServiceException {
        try {
            return employeeManager.getAll();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int employeeId, EmployeeDto employeeDto)
            throws ServiceException {
        try {
            return employeeManager.update(employeeId, employeeDto);
       } catch (DataException dataException) {
           throw new ServiceException(dataException.getMessage(),
                   dataException);
       }
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.EmployeeService#get(java.lang.String)
     */
    @Override
    public EmployeeDto get(String employeeNumber) throws ServiceException {
        try {
            return employeeManager.get(employeeNumber);
       } catch (DataException dataException) {
           throw new ServiceException(dataException.getMessage(),
                   dataException);
       }
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.EmployeeService#delete(java.lang.String)
     */
    @Override
    public boolean delete(String employeeNumber) throws ServiceException {
        throw new ServiceException("Deleting an employee is not supported. Instead mark the employee as Terminated.");
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.service.EmployeeService#getAll(com.myschool.student.dto.EmployeeSearchCriteriaDto)
     */
    @Override
    public List<EmployeeDto> getAll(
            EmployeeSearchCriteriaDto employeeSearchCriteriaDto)
            throws ServiceException {
        try {
            return employeeManager.getAll(employeeSearchCriteriaDto);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.service.EmployeeService#getNextEmployeeNumber()
     */
    @Override
    public String getLastEmployeeNumber() throws ServiceException {
        try {
            return employeeManager.getLastEmployeeNumber();
       } catch (DataException dataException) {
           throw new ServiceException(dataException.getMessage(),
                   dataException);
       }
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.service.EmployeeService#getNext(java.lang.String, com.myschool.common.constants.RecordStatus)
     */
    @Override
	public EmployeeDto getNext(String employeeNumber, RecordStatus recordStatus) throws ServiceException {
        try {
            return employeeManager.getNext(employeeNumber, recordStatus);
       } catch (DataException dataException) {
           throw new ServiceException(dataException.getMessage(),
                   dataException);
       }
    }

	/* (non-Javadoc)
	 * @see com.myschool.employee.service.EmployeeService#getPrevious(java.lang.String, com.myschool.common.constants.RecordStatus)
	 */
	@Override
	public EmployeeDto getPrevious(String employeeNumber, RecordStatus recordStatus) throws ServiceException {
        try {
            return employeeManager.getPrevious(employeeNumber, recordStatus);
       } catch (DataException dataException) {
           throw new ServiceException(dataException.getMessage(),
                   dataException);
       }
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.service.EmployeeService#getEvanescentImage(java.lang.String, com.myschool.image.constant.ImageSize)
     */
    @Override
    public File getEvanescentImage(String referenceNumber,
            ImageSize imageSize) throws ServiceException {
        try {
            return employeeManager.getEvanescentImage(referenceNumber, imageSize);
       } catch (DataException dataException) {
           throw new ServiceException(dataException.getMessage(),
                   dataException);
       }
    }

}
