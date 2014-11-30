package com.myschool.exim.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * The Enum EximPolicy.
 */
public enum EximPolicy {

    /** The ACADEMICS. */
    ACADEMICS(1),

    /** The DOCUMENTS. */
    DOCUMENTS(1),

    /** The STATES. */
    STATES(1),

    /** The DIVISIONS. */
    DIVISIONS(1),

    /** The DESIGNATIONS. */
    DESIGNATIONS(1),

    /** The EXAM_GRADES. */
    EXAM_GRADES(1),

    /** The MASTER_CLASSES. */
    MASTER_CLASSES(1),

    /** The MASTER_MEDIUMS. */
    MASTER_MEDIUMS(1),

    /** The MASTER_SUBJECTS. */
    MASTER_SUBJECTS(1),

    /** The MASTER_SECTIONS. */
    MASTER_SECTIONS(1),

    /** The EMPLOYMENT_STATUS. */
    EMPLOYMENT_STATUS(1),

    /** The ADMISSION_STATUS. */
    ADMISSION_STATUS(1),

    /** The HOLIDAYS. */
    HOLIDAYS(1),

    /** The REGIONS. */
    REGIONS(2, STATES),

    /** The BRANCHES. */
    BRANCHES(3, REGIONS),

    /** The SCHOOLS. */
    SCHOOLS(4, BRANCHES, DIVISIONS),

    /** The EMPLOYEES. */
    EMPLOYEES(4, BRANCHES, DESIGNATIONS, EMPLOYMENT_STATUS),

    /** The REGISTERE d_ classes. */
    REGISTERED_CLASSES(5, SCHOOLS, MASTER_CLASSES, MASTER_MEDIUMS, MASTER_SECTIONS),

    /** The EMPLOYEE_CONTACT. */
    EMPLOYEE_CONTACT(5, EMPLOYEES),

    /** The EMPLOYEE_EDUCATION. */
    EMPLOYEE_EDUCATION(5, EMPLOYEES),

    /** The EMPLOYEE_EXPERIENCE. */
    EMPLOYEE_EXPERIENCE(5, EMPLOYEES),

    /** The EMPLOYEE_DOCUMENT. */
    EMPLOYEE_DOCUMENT(5, EMPLOYEES, DOCUMENTS),

    /** The EMPLOYEE_PROMOTION. */
    EMPLOYEE_PROMOTION(5, EMPLOYEES, DESIGNATIONS),

    /** The REGISTERED_SUBJECTS. */
    REGISTERED_SUBJECTS(6, MASTER_SUBJECTS, REGISTERED_CLASSES),

    /** The REFERENCE_ATTENDANCE. */
    REFERENCE_ATTENDANCE(6, REGISTERED_CLASSES),

    /** The EXAMS. */
    EXAMS(6, REGISTERED_CLASSES),

    /** The STUDENTS. */
    STUDENTS(6, REGISTERED_CLASSES),

    /** The EMPLOYEE_SUBJECT. */
    EMPLOYEE_SUBJECT(7, EMPLOYEES, REGISTERED_SUBJECTS),

    /** The STUDENT_DOCUMENT. */
    STUDENT_DOCUMENT(7, STUDENTS, DOCUMENTS),

    /** The STUDENT_FAMILY. */
    STUDENT_FAMILY(7, STUDENTS),

    /** The SUBJECTS_IN_EXAMS. */
    SUBJECTS_IN_EXAMS(7, EXAMS, REGISTERED_SUBJECTS),

    /** The STUDENT_ATTENDANCE. */
    STUDENT_ATTENDANCE(7, STUDENTS, REFERENCE_ATTENDANCE),

    /** The STUDENT_MARKS. */
    STUDENT_MARKS(7, STUDENTS, EXAMS);

    /** The rank. */
    private int rank;
    
    /** The depends on. */
    private EximPolicy[] dependsOn;

    /**
     * Instantiates a new exim policy.
     * 
     * @param rank the rank
     */
    EximPolicy(int rank) {
        this(rank, (EximPolicy[])null);
    }

    /**
     * Instantiates a new exim policy.
     * 
     * @param rank the rank
     * @param dependsOn the depends on
     */
    EximPolicy(int rank, EximPolicy... dependsOn) {
        this.rank = rank;
        this.dependsOn = dependsOn;
    }

    /**
     * Gets the rank.
     * 
     * @return the rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * Gets the depends on.
     * 
     * @return the depends on
     */
    public EximPolicy[] getDependsOn() {
        return dependsOn;
    }

    /**
     * Gets the policy.
     * 
     * @param policyName the policy name
     * @return the policy
     */
    public static EximPolicy getPolicy(String policyName) {
        EximPolicy eximPolicy = null;
        if (policyName != null) {
            EximPolicy[] values = values();
            for (EximPolicy eximEnumeration : values) {
                if (policyName.equals(eximEnumeration.toString())) {
                    eximPolicy = eximEnumeration;
                }
            }
        }
        return eximPolicy;
    }

    /**
     * Gets the start rank.
     * 
     * @return the start rank
     */
    public static int getStartRank() {
        int startRank = Integer.MAX_VALUE;
        EximPolicy[] eximPolicies = values();
        for (EximPolicy eximPolicy : eximPolicies) {
            if (eximPolicy.getRank() < startRank) {
                startRank = eximPolicy.getRank();
            }
        }
        return startRank;
    }

    /**
     * Gets the end rank.
     * 
     * @return the end rank
     */
    public static int getEndRank() {
        int endRank = Integer.MIN_VALUE;
        EximPolicy[] eximPolicies = values();
        for (EximPolicy eximPolicy : eximPolicies) {
            if (eximPolicy.getRank() > endRank) {
                endRank = eximPolicy.getRank();
            }
        }
        return endRank;
    }

    /**
     * Gets the exim policies.
     * 
     * @param rank the rank
     * @return the exim policies
     */
    public static List<EximPolicy> getEximPolicies(int rank) {
        List<EximPolicy> eximPoliciesInRank = new ArrayList<EximPolicy>();
        EximPolicy[] eximPolicies = values();
        for (EximPolicy eximPolicy : eximPolicies) {
            if (eximPolicy.getRank() == rank) {
                eximPoliciesInRank.add(eximPolicy);
            }
        }
        return eximPoliciesInRank;
    }

    /**
     * Gets the exim policies by rank order.
     * 
     * @return the exim policies by rank order
     */
    public static List<EximPolicy> getEximPoliciesByRankOrder() {
        List<EximPolicy> eximPoliciesByRankOrder = new ArrayList<EximPolicy>();
        int startRank = EximPolicy.getStartRank();
        int endRank = EximPolicy.getEndRank();
        for (int rank = startRank; rank <= endRank; rank++) {
            eximPoliciesByRankOrder.addAll(getEximPolicies(rank));
        }
        return eximPoliciesByRankOrder;
    }

}
