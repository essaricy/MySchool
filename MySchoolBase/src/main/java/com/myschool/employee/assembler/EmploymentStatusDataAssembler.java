package com.myschool.employee.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.myschool.employee.dto.EmploymentStatus;

/**
 * The Class EmploymentStatusDataAssembler.
 */
public class EmploymentStatusDataAssembler {

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @return the employment status
     * @throws SQLException the sQL exception
     */
    public static EmploymentStatus create(ResultSet resultSet)
            throws SQLException {
        return create(resultSet, false);
    }

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @param aliased the aliased
     * @return the employment status
     * @throws SQLException the sQL exception
     */
    public static EmploymentStatus create(ResultSet resultSet, boolean aliased)
            throws SQLException {
        EmploymentStatus employmentStatus = new EmploymentStatus();
        if (aliased) {
            employmentStatus.setStatusId(resultSet.getInt("EMPLOYMENT_STATUS_STATUS_ID"));
            employmentStatus.setDescription(resultSet.getString("EMPLOYMENT_STATUS_DESCRIPTION"));
        } else {
            employmentStatus.setStatusId(resultSet.getInt("STATUS_ID"));
            employmentStatus.setDescription(resultSet.getString("DESCRIPTION"));
        }
        return employmentStatus;
    }

}
