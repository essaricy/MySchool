package com.myschool.common.constants;

/**
 * The Enum DocumentApplicability.
 */
public enum DocumentApplicability {

    /** The MANDATORY. */
    MANDATORY("M"),

    /** The NO t_ applicable. */
    NOT_APPLICABLE("N"),

    /** The OPTIONAL. */
    OPTIONAL("O");

    /** The applicability code. */
    private String applicabilityCode;

    /**
     * Instantiates a new document applicability.
     * 
     * @param applicabilityCode the applicability code
     */
    private DocumentApplicability(String applicabilityCode) {
        this.applicabilityCode = applicabilityCode;
    }

    /**
     * Gets the applicability code.
     * 
     * @return the applicability code
     */
    public String getApplicabilityCode() {
        return applicabilityCode;
    }

    /**
     * Gets the by code.
     * 
     * @param applicabilityCode the applicability code
     * @return the by code
     */
    public static DocumentApplicability getByCode(String applicabilityCode) {
        if (applicabilityCode != null) {
            for (DocumentApplicability documentApplicability : values()) {
                if (applicabilityCode.trim().equalsIgnoreCase(documentApplicability.getApplicabilityCode())) {
                    return documentApplicability;
                }
            }
        }
        return null;
    }

}
