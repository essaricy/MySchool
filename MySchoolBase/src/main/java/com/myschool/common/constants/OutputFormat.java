package com.myschool.common.constants;

/**
 * The Enum OutputFormat.
 */
public enum OutputFormat {

    /** The JSON. */
    JSON,
    
    /** The XML. */
    XML;

    /**
     * Gets the output format.
     *
     * @param outputFormatName the output format name
     * @return the output format
     */
    public static OutputFormat getOutputFormat(String outputFormatName) {
        OutputFormat outputFormat = null;
        if (outputFormatName != null) {
            OutputFormat[] values = values();
            for (OutputFormat loopingOutputFormat : values) {
                if (outputFormatName.equals(loopingOutputFormat.toString())) {
                    outputFormat = loopingOutputFormat;
                    break;
                }
            }
        }
        return outputFormat;
    }

}
