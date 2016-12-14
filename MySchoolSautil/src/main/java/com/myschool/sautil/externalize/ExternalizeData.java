package com.myschool.sautil.externalize;

import java.io.File;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.ConfigurationException;
import com.myschool.common.exception.FileSystemException;
import com.myschool.common.util.CollectionUtil;
import com.myschool.common.util.StringUtil;
import com.myschool.file.util.FileUtil;
import com.myschool.infra.application.constants.ExternalizeAction;
import com.myschool.infra.application.constants.ExternalizeDataFormat;
import com.myschool.sautil.base.StandAloneUtility;

/**
 * The Class ExternalizeData.
 */
@Component
public class ExternalizeData extends StandAloneUtility {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(ExternalizeData.class);

    /** The Constant OPTION_ACTION. */
    public static final String OPTION_ACTION = "--action";

    /** The Constant OPTION_DATA_FORMAT. */
    public static final String OPTION_DATA_FORMAT = "--data-format";

    /** The Constant OPTION_EXT_CFG. */
    public static final String OPTION_EXT_CFG = "--ext-cfg";

    /** The Constant OPTION_TRACKER_ID. */
    public static final String OPTION_TRACKER_ID = "--tracker-id";

    /** The externalize. */
    @Autowired
    private Externalize externalize;

    /** The tracker id. */
    private Integer trackerId;

    /* (non-Javadoc)
     * @see com.myschool.sautil.base.StandAloneUtility#validateParameters()
     */
    @Override
    public void validateParameters() throws ConfigurationException {
        try {
            String actionOptionValue = executionProperties.getProperty(OPTION_ACTION);
            if (actionOptionValue == null) {
                throw new ConfigurationException("Must specify '" + OPTION_ACTION + "' option. Allowed values are "
                        + CollectionUtil.toCommaSeperatedString(ExternalizeAction.values()));
            }
            ExternalizeAction externalizeAction = ExternalizeAction.getExternalizeAction(actionOptionValue);
            if (externalizeAction == null) {
                throw new ConfigurationException("Invalid value for action. Allowed values are "
                        + CollectionUtil.toCommaSeperatedString(ExternalizeAction.values()));
            }

            // Validate externalize data format
            String dataFormatValue = executionProperties.getProperty(OPTION_DATA_FORMAT);
            if (dataFormatValue == null) {
                throw new ConfigurationException("Must specify '" + OPTION_DATA_FORMAT + "' option. Allowed values are "
                        + CollectionUtil.toCommaSeperatedString(ExternalizeDataFormat.values()));
            }
            ExternalizeDataFormat externalizeDataFormat = ExternalizeDataFormat.getExternalizeDataFormat(dataFormatValue);
            if (externalizeDataFormat == null) {
                throw new ConfigurationException("Invalid value for data-format. Allowed values are "
                        + CollectionUtil.toCommaSeperatedString(ExternalizeDataFormat.values()));
            }

            // Validate external directory option
            String extDirOptionValue = executionProperties.getProperty(OPTION_EXT_DIR);
            if (extDirOptionValue == null) {
                throw new ConfigurationException("Must specify '" + OPTION_EXT_DIR + "' option.");
            }
            File extDir = new File(extDirOptionValue);
            FileUtil.checkDirectory(extDir.getAbsolutePath(),
                    "No such directory " + extDirOptionValue, "Cannot access the directory " + extDirOptionValue + " or it is not a directory at all.");

            // Validate external configuration option
            String extCfgOptionValue = executionProperties.getProperty(OPTION_EXT_CFG);
            if (extCfgOptionValue == null) {
                throw new ConfigurationException("Must specify '" + OPTION_EXT_CFG + "' option.");
            }
            // Validate tracker id option
            String trackerIdOptionValue = executionProperties.getProperty(OPTION_TRACKER_ID);
            if (!StringUtil.isNullOrBlank(trackerIdOptionValue)) {
                try {
                    trackerId = new Integer(trackerIdOptionValue);
                } catch (NumberFormatException numberFormatException) {
                    throw new ConfigurationException("Invalid value for '" + OPTION_TRACKER_ID + "' option.");
                }
            }
            File mappingFile = new File(extCfgOptionValue);
            FileUtil.checkFile(mappingFile.getAbsolutePath(),
                    "No such file " + mappingFile, "Cannot access the file " + mappingFile);
        } catch (FileSystemException fileSystemException) {
            throw new ConfigurationException(fileSystemException.getMessage(), fileSystemException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.sautil.base.StandAloneUtility#startProcess()
     */
    public void startProcess() throws Exception {
        LOGGER.info("Externalize data program has started at " + new Date());
        ExternalizeAction externalizeAction = ExternalizeAction.getExternalizeAction(executionProperties.getProperty(OPTION_ACTION));
        File extDir = new File(executionProperties.getProperty(OPTION_EXT_DIR));
        File mappingFile = new File(executionProperties.getProperty(OPTION_EXT_CFG));

        // Validate externalize data action
        if (externalizeAction == ExternalizeAction.INIT) {
            externalize.importData(extDir, mappingFile, trackerId);
        } else if (externalizeAction == ExternalizeAction.LOAD) {
            externalize.importData(extDir, mappingFile, trackerId);
        } else if (externalizeAction == ExternalizeAction.EXTRACT) {
        } else if (externalizeAction == ExternalizeAction.RELOAD) {
        }

        /* else if (externalizeDataOption == ExternalizeDataOption.EXPORT_TO_EXCEL) {
            externalize.exportData(null);
        } else if (externalizeDataOption == ExternalizeDataOption.EXPORT_TO_SQL_SCRIPT) {
            String exportToDirName = properties.getProperty(OPTION_EXTDIR);
            if (exportToDirName == null) {
                throw new ConfigurationException("Must specify '" + OPTION_EXTDIR + "' option.");
            }
            File exportToDirectory = new File(exportToDirName);
            FileUtil.createDirectory(exportToDirectory);
            FileUtil.checkDirectory(exportToDirectory.getAbsolutePath(),
                    "No such directory " + exportToDirName, "Cannot access the directory " + exportToDirName + " or it is not a directory at all.");
            boolean exportDataToSqlScript = externalize.exportDataToSqlScript(exportToDirectory);
            if (exportDataToSqlScript) {
                System.out.println("Data has been successfully exported to file: " + exportToDirectory);
            } else {
                throw new ApplicationException("Unable to export data to the specified file.");
            }
        } else if (externalizeDataOption == ExternalizeDataOption.IMPORT_FROM_SQL_SCRIPT) {
            String importFromDirName = properties.getProperty(OPTION_EXTDIR);
            if (importFromDirName == null) {
                throw new ConfigurationException("Must specify '" + OPTION_EXTDIR + "' option.");
            }
            File importFromDirectory = new File(importFromDirName);
            FileUtil.checkDirectory(importFromDirectory.getAbsolutePath(),
                    "No such directory " + importFromDirName, "Cannot access the directory " + importFromDirName + " or it is not a directory at all.");
            boolean importDataFromSqlScript = externalize.importDataFromSqlScript(importFromDirectory);
            if (importDataFromSqlScript) {
                System.out.println("Data has been successfully exported to file: " + importFromDirectory);
            } else {
                throw new ApplicationException("Unable to export data to the specified file.");
            }
        }*/
        LOGGER.info("Externalize data program has ended at " + new Date());
        System.out.println("Externalize data program has ended at " + new Date());
    }

    /* (non-Javadoc)
     * @see com.myschool.sautil.base.StandAloneUtility#getUsageText()
     */
    public String getUsageText() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Usage: ").append(this.getClass().getName()).append(" [OPTIONS] \n");
        buffer.append("Valid Options are\n");
        buffer.append(String.format(PADDING_WIDTH, OPTION_HELP)).append("For Help\n");
        buffer.append(String.format(PADDING_WIDTH, OPTION_ACTION)).append("Action command to run.\n");
        buffer.append(String.format(PADDING_WIDTH, OPTION_DATA_FORMAT)).append("Specify data format to use.\n");
        buffer.append(String.format(PADDING_WIDTH, OPTION_EXT_DIR)).append("External directory to use for externalizing data.\n");
        buffer.append(String.format(PADDING_WIDTH, OPTION_EXT_CFG)).append("Specify external configuration file to use.\n");
        return buffer.toString();
    }

}
