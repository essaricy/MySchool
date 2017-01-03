package com.myschool.exim.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.myschool.exim.constants.EximPolicy;
import com.myschool.exim.dto.EximDto;
import com.quasar.core.util.ConversionUtil;


/**
 * The Class EximDataAssembler.
 */
public class EximDataAssembler {

    /**
     * Creates the exim.
     *
     * @param resultSet the result set
     * @return the import dto
     * @throws SQLException the sQL exception
     */
    public static EximDto createExim(ResultSet resultSet) throws SQLException {
        EximDto exim = new EximDto();
        exim.setEximPolicy(EximPolicy.getPolicy(resultSet.getString("EXIM_KEY")));
        exim.setDescription(resultSet.getString("DESCRIPTION"));
        exim.setCanImport(ConversionUtil.toBoolean(resultSet.getString("CAN_IMPORT")));
        exim.setCanExport(ConversionUtil.toBoolean(resultSet.getString("CAN_EXPORT")));
        return exim;
    }

}
