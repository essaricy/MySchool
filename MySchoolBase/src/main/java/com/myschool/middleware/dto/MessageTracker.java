package com.myschool.middleware.dto;

import java.io.File;
import java.io.Serializable;

/**
 * The Class MessageTracker.
 */
public class MessageTracker implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The temp file id. */
	private String tempFileId;

	/** The input file. */
	private File inputFile;

	/** The output file. */
	private File outputFile;

	/**
	 * Gets the temp file id.
	 *
	 * @return the temp file id
	 */
	public String getTempFileId() {
		return tempFileId;
	}

	/**
	 * Sets the temp file id.
	 *
	 * @param tempFileId the new temp file id
	 */
	public void setTempFileId(String tempFileId) {
		this.tempFileId = tempFileId;
	}

	/**
	 * Gets the input file.
	 *
	 * @return the input file
	 */
	public File getInputFile() {
		return inputFile;
	}

	/**
	 * Sets the input file.
	 *
	 * @param inputFile the new input file
	 */
	public void setInputFile(File inputFile) {
		this.inputFile = inputFile;
	}

	/**
	 * Gets the output file.
	 *
	 * @return the output file
	 */
	public File getOutputFile() {
		return outputFile;
	}

	/**
	 * Sets the output file.
	 *
	 * @param outputFile the new output file
	 */
	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}

    /**
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation 
     * of this object.
     */
    public String toString() {
        final String SEPARATOR = ", ";
        final StringBuffer returnValue = new StringBuffer();
        returnValue.append("\nMessageTracker ( ")
            .append("tempFileId = ").append(this.tempFileId).append(SEPARATOR)
            .append("inputFile = ").append(this.inputFile).append(SEPARATOR)
            .append("outputFile = ").append(this.outputFile).append(SEPARATOR)
            .append(" )");
        return returnValue.toString();
    }

}
