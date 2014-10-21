package com.myschool.infra.logging;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.spi.LoggingEvent;

/**
 * The Class DateAndSizeBasedRollingFileAppender.
 */
public class DateAndSizeBasedRollingFileAppender extends RollingFileAppender {

    /** The Constant LOG_FILE_PATTERN. */
    private static final String LOG_FILE_PATTERN = "{0}_{1}.log";

    /** The log date. */
    private int logDate;

    /** The file prefix. */
    private String filePrefix;

    /** The date pattern. */
    private String datePattern;

    /** The previous minute. */

    private SimpleDateFormat dateFormat;
    /**
     * Setter method to set the log file name by appending the current time to
     * the name of the log file.
     *
     * @param fileName
     *            Log File Name
     */
    public void setFile(String fileName) {
        this.filePrefix = fileName;
        super.setFile(getFileName(fileName));
    }

    /**
     * Gets the file name.
     * 
     * @param fileName the file name
     * @param appendDate the append date
     * @return the file name
     */
    private String getFileName(String fileName, boolean appendDate) {
        String fileSuffix = null;

        Calendar calendar = Calendar.getInstance();
        logDate = calendar.get(Calendar.DAY_OF_YEAR);
        if (fileName.indexOf(".log") != -1) {
            fileName = fileName.substring(0, fileName.indexOf(".log"));
        }

        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat(datePattern);
        }
        fileSuffix = dateFormat.format(calendar.getTime());
        fileName = MessageFormat.format(LOG_FILE_PATTERN, fileName, fileSuffix);
        return fileName;
    }

    /**
     * Gets the file name.
     * 
     * @param fileName the file name
     * @return the file name
     */
    private String getFileName(String fileName) {
        return getFileName(fileName, false);
    }

    /**
     *
     * Setter for the variable datePattern.
     *
     * @param datePattern
     *            datePattern to be set.
     */
    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

    /**
     * Getter for the variable datePattern.
     *
     * @return datePattern
     */
    public String getDatePattern() {
        return this.datePattern;
    }

    /* (non-Javadoc)
     * @see org.apache.log4j.FileAppender#getFile()
     */
    @Override
    public String getFile() {
        return super.getFile();
    }

    /* (non-Javadoc)
     * @see org.apache.log4j.WriterAppender#append(org.apache.log4j.spi.LoggingEvent)
     */
    @Override
    public void append(LoggingEvent event) {
        Calendar calendar = Calendar.getInstance();
        int currentDate = calendar.get(Calendar.DAY_OF_YEAR);
        if (currentDate != logDate) {
            changeFile();
        }
        super.subAppend(event);
    }

    /**
     * Change file.
     */
    private void changeFile() {
        try {
            reset();
            String fileName = getFileName(filePrefix);
            this.setFile(fileName, false, this.bufferedIO, this.bufferSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
