package com.myschool.infra.application.constants;

/**
 * The Enum ExternalizeDataFormat.
 */
public enum ExternalizeDataFormat {

    /** The EXCEL. */
    EXCEL,

    /** The CSV. */
    CSV,

    /** The SQL. */
    SQL;

    /**
     * Gets the externalize data format.
     *
     * @param dataFormatName the data format name
     * @return the externalize data format
     */
    public static ExternalizeDataFormat getExternalizeDataFormat(String dataFormatName) {
        ExternalizeDataFormat externalizeDataFormat = null;
        ExternalizeDataFormat[] values = ExternalizeDataFormat.values();
        for (ExternalizeDataFormat value : values) {
            if (dataFormatName.equalsIgnoreCase(value.toString())) {
                externalizeDataFormat = value;
                break;
            }
        }
        return externalizeDataFormat;
    }
}
