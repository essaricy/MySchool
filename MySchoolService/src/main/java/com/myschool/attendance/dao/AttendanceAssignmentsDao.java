package com.myschool.attendance.dao;

import java.util.List;

import com.myschool.branch.dto.BranchDto;
import com.myschool.branch.dto.RegionDto;
import com.myschool.branch.dto.StateDto;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.common.exception.DaoException;
import com.myschool.school.dto.SchoolDto;

/**
 * The Interface AttendanceAssignmentsDao.
 */
public interface AttendanceAssignmentsDao {

    /**
     * Gets the assigned states.
     * 
     * @param attendanceProfileId the attendance profile id
     * @return the assigned states
     * @throws DaoException the dao exception
     */
    List<StateDto> getAssignedStates(int attendanceProfileId) throws DaoException;

    /**
     * Gets the assigned regions.
     * 
     * @param attendanceProfileId the attendance profile id
     * @return the assigned regions
     * @throws DaoException the dao exception
     */
    List<RegionDto> getAssignedRegions(int attendanceProfileId) throws DaoException;

    /**
     * Gets the assigned branches.
     * 
     * @param attendanceProfileId the attendance profile id
     * @return the assigned branches
     * @throws DaoException the dao exception
     */
    List<BranchDto> getAssignedBranches(int attendanceProfileId) throws DaoException;

    /**
     * Gets the assigned schools.
     * 
     * @param attendanceProfileId the attendance profile id
     * @return the assigned schools
     * @throws DaoException the dao exception
     */
    List<SchoolDto> getAssignedSchools(int attendanceProfileId) throws DaoException;

    /**
     * Gets the assigned classes.
     * 
     * @param attendanceProfileId the attendance profile id
     * @return the assigned classes
     * @throws DaoException the dao exception
     */
    List<RegisteredClassDto> getAssignedClasses(int attendanceProfileId) throws DaoException;

    /**
     * Creates the.
     * 
     * @param <T> the generic type
     * @param attendanceProfileId the attendance profile id
     * @param assignments the assignments
     * @throws DaoException the dao exception
     */
    <T> void create(int attendanceProfileId, List<T> assignments) throws DaoException;

    /**
     * Delete.
     * 
     * @param attendanceProfileId the attendance profile id
     * @param assignment the assignment
     * @throws DaoException the dao exception
     */
    void delete(int attendanceProfileId, Class<? extends Object> assignment) throws DaoException;

}
