package com.myschool.student.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.myschool.student.dto.AdmissionStatus;

/**
 * The Class AdmissionStatusDataAssembler.
 */
public class AdmissionStatusDataAssembler {

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @return the admission status
     * @throws SQLException the sQL exception
     */
    public static AdmissionStatus create(ResultSet resultSet)
            throws SQLException {
        return create(resultSet, false);
    }

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @param aliased the aliased
     * @return the admission status
     * @throws SQLException the sQL exception
     */
    public static AdmissionStatus create(ResultSet resultSet, boolean aliased)
            throws SQLException {
        AdmissionStatus admissionStatus = new AdmissionStatus();
        if (aliased) {
            admissionStatus.setStatusId(resultSet.getInt("ADMISSION_STATUS_STATUS_ID"));
            admissionStatus.setDescription(resultSet.getString("ADMISSION_STATUS_DESCRIPTION"));
        } else {
            admissionStatus.setStatusId(resultSet.getInt("STATUS_ID"));
            admissionStatus.setDescription(resultSet.getString("DESCRIPTION"));
        }
        return admissionStatus;
    }

}
