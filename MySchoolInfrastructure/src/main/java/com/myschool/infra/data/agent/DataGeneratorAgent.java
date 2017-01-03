package com.myschool.infra.data.agent;

import java.io.File;
import java.text.MessageFormat;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.myschool.common.exception.AgentException;
import com.myschool.common.exception.ConfigurationException;
import com.myschool.infra.agent.AbstractAgent;
import com.myschool.infra.data.constants.DataGeneratorConstants;
import com.quasar.core.exception.FileSystemException;
import com.quasar.core.util.FileUtil;
import com.quasar.core.util.PropertiesUtil;
import com.quasar.core.util.StringUtil;

/**
 * The Class DataGeneratorAgent.
 */
@Component
public abstract class DataGeneratorAgent extends AbstractAgent {

    /** The Constant MISSING_FILE. */
    private static final String MISSING_FILE = "Missing {0}";

    /** The first names file. */
    protected File firstNamesFile;

    /** The last names file. */
    protected File lastNamesFile;

    /** The cities file. */
    protected File citiesFile;

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#loadConfiguration(java.io.File)
     */
    @Override
    public void loadConfiguration(File configFile)
            throws ConfigurationException {
        try {
            properties = PropertiesUtil.loadNestedProperties(configFile);
            File parentFile = configFile.getParentFile();
            String dataLocale = properties.getProperty(DataGeneratorConstants.DATA_LOCALE);
            if (StringUtil.isNullOrBlank(dataLocale)) {
                throw new ConfigurationException("Missing property " + DataGeneratorConstants.DATA_LOCALE + " for data generation agent.");
            }
            // First Names file
            String firstnames = properties.getProperty(DataGeneratorConstants.DATA_FIRSTNAMES);
            String message = MessageFormat.format(MISSING_FILE, firstnames);
            firstNamesFile = FileUtil.checkFile(new File(parentFile, firstnames), message, message);

            // Last Names file
            String lastnames = properties.getProperty(DataGeneratorConstants.DATA_LASTNAMES);
            message = MessageFormat.format(MISSING_FILE, lastnames);
            lastNamesFile = FileUtil.checkFile(new File(parentFile, lastnames), message, message);

            // Last Names file
            String cities = properties.getProperty(DataGeneratorConstants.DATA_CITIES);
            message = MessageFormat.format(MISSING_FILE, cities);
            citiesFile = FileUtil.checkFile(new File(parentFile, cities), message, message);

        } catch (FileSystemException fileSystemException) {
            throw new ConfigurationException(fileSystemException.getMessage(), fileSystemException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#validate()
     */
    @Override
    public void validate() throws AgentException {
    }

    /**
     * Gets the unique id.
     *
     * @return the unique id
     */
    public String getUniqueId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public abstract String getFirstName();

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public abstract String getLastName();

    /**
     * Gets the email address.
     *
     * @return the email address
     */
    public abstract String getEmailAddress();

    /*
     * com.myschool.acl.constant.SigninSecurityLevel
     * com.myschool.apar.constants.Ledger
     * com.myschool.application.constants.Device
     * com.myschool.application.constants.IssueStatus
     * com.myschool.attendance.constants.LeaveStatus
     * com.myschool.attendance.dto.AttendanceCode
     * com.myschool.common.constants.DocumentApplicability
     * com.myschool.common.constants.RecordStatus
     * com.myschool.exim.constants.EximPolicy
     * com.myschool.exim.constants.UploadStatus
     * com.myschool.filesystem.dto.AbsenceCode
     * com.myschool.graph.constant.ToDateType
     * com.myschool.image.constant.ImageSize
     * com.myschool.notification.constants.NotificationMedium
     * com.myschool.notification.constants.NotificationStatus
     * com.myschool.report.constants.ReportKey
     * com.myschool.user.constants.UserType
     *
     * names
     * gender
     * dates
     * yes/no
     * nationality
     * blood group
     * religion
     * caste
     * nationality
     * languages
     * address
     * phone number
     * age
     * marital status
     * relationship code
     * religion
     * 
     */
}
