package com.myschool.employee.dao;

import java.util.List;

import com.myschool.common.exception.DaoException;
import com.myschool.employee.dto.EmployeeExperience;

/**
 * The Interface EmployeeExperienceDao.
 */
public interface EmployeeExperienceDao {

    /**
     * Gets the.
     * 
     * @param employeeExperienceId the employee experience id
     * @return the employee experience
     * @throws DaoException the dao exception
     */
    EmployeeExperience get(int employeeExperienceId) throws DaoException;

    /**
     * Gets the by employee.
     * 
     * @param employeeId the employee id
     * @return the employee experiences
     * @throws DaoException the dao exception
     */
    List<EmployeeExperience> getByEmployee(int employeeId)
            throws DaoException;

    /**
     * Creates the.
     * 
     * @param employeeId the employee id
     * @param employeeExperience the employee experience
     * @return the int
     * @throws DaoException the dao exception
     */
    int create(int employeeId, EmployeeExperience employeeExperience)
            throws DaoException;

    /**
     * Creates the.
     * 
     * @param employeeId the employee id
     * @param employeeExperienceList the employee experience list
     * @throws DaoException the dao exception
     */
    void create(int employeeId, List<EmployeeExperience> employeeExperienceList)
            throws DaoException;

    /**
     * Update.
     * 
     * @param employeeExperienceId the employee experience id
     * @param employeeExperience the employee experience
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean update(int employeeExperienceId,
            EmployeeExperience employeeExperience) throws DaoException;

    /**
     * Gets the.
     * 
     * @param employeeId the employee id
     * @param employeeExperience the employee experience
     * @return the employee experience
     * @throws DaoException the dao exception
     */
    EmployeeExperience get(int employeeId, EmployeeExperience employeeExperience)
            throws DaoException;

    /**
     * Delete.
     * 
     * @param experienceId the experience id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean delete(int experienceId) throws DaoException;

    /**
     * Gets the by employee.
     * 
     * @param employeeNumber the employee number
     * @return the by employee
     * @throws DaoException the dao exception
     */
    List<EmployeeExperience> getByEmployee(String employeeNumber) throws DaoException;

    /**
     * Update.
     * 
     * @param employeeExperiences the employee experiences
     * @throws DaoException the dao exception
     */
    void update(List<EmployeeExperience> employeeExperiences) throws DaoException;

}
