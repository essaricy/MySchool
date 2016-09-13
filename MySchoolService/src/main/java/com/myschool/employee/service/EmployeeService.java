package com.myschool.employee.service;

import java.io.File;
import java.util.List;

import com.myschool.application.service.Servicable;
import com.myschool.common.constants.RecordStatus;
import com.myschool.common.exception.ServiceException;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.employee.dto.EmployeeSearchCriteriaDto;
import com.myschool.image.constant.ImageSize;

/**
 * The Interface EmployeeService.
 */
public interface EmployeeService extends Servicable<EmployeeDto> {

    /**
     * Gets the.
     *
     * @param employeeNumber the employee number
     * @return the employee dto
     * @throws ServiceException the service exception
     */
    EmployeeDto get(String employeeNumber) throws ServiceException;

    /**
     * Delete.
     *
     * @param employeeNumber the employee number
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean delete(String employeeNumber) throws ServiceException;


    /**
     * Gets the all.
     *
     * @param employeeSearchCriteriaDto the employee search criteria dto
     * @return the all
     * @throws ServiceException the service exception
     */
    List<EmployeeDto> getAll(EmployeeSearchCriteriaDto employeeSearchCriteriaDto) throws ServiceException;

    /**
     * Gets the last employee number.
     *
     * @return the last employee number
     * @throws ServiceException the service exception
     */
    String getLastEmployeeNumber() throws ServiceException;

    /**
     * Gets the next.
     *
     * @param employeeNumber the employee number
     * @param recordStatus the record status
     * @return the next
     * @throws ServiceException the service exception
     */
    EmployeeDto getNext(String employeeNumber, RecordStatus recordStatus) throws ServiceException;

	/**
     * Gets the previous.
     *
     * @param employeeNumber the employee number
     * @param recordStatus the record status
     * @return the previous
     * @throws ServiceException the service exception
     */
	EmployeeDto getPrevious(String employeeNumber, RecordStatus recordStatus) throws ServiceException;

    /**
     * Gets the evanescent image.
     *
     * @param referenceNumber the reference number
     * @param imageSize the image size
     * @return the evanescent image
     * @throws ServiceException the service exception
     */
    File getEvanescentImage(String referenceNumber, ImageSize imageSize) throws ServiceException;

}
