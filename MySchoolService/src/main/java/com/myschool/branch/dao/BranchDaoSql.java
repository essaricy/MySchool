package com.myschool.branch.dao;

import java.util.HashMap;
import java.util.Map;

import com.myschool.branch.dto.BranchDto;
import com.myschool.branch.dto.RegionDto;
import com.myschool.branch.dto.StateDto;
import com.myschool.common.util.DatabaseUtil;

/**
 * The Class BranchDaoSql.
 */
public class BranchDaoSql {

    /** The Constant SELECT_ALL. */
    public static final String SELECT_ALL;

    /** The Constant SELECT_BY_REGION_ID. */
    public static final String SELECT_BY_REGION_ID;

    /** The Constant SELECT_BY_ID. */
    public static final String SELECT_BY_ID;

    /** The Constant SELECT_BY_BRANCH_CODE. */
    public static final String SELECT_BY_BRANCH_CODE;

    /** The Constant INSERT. */
    public static final String INSERT;

    /** The Constant UPDATE. */
    public static final String UPDATE;

    /** The Constant DELETE. */
    public static final String DELETE;

    static {
        StringBuffer buffer = new StringBuffer();

        buffer.append("SELECT ");
        buffer.append("BRANCH.BRANCH_ID, ");
        buffer.append("BRANCH.BRANCH_CODE, ");
        buffer.append("BRANCH.DESCRIPTION, ");
        buffer.append("BRANCH.ADDRESS, ");
        buffer.append("BRANCH.PHONE_NUMBER, ");
        buffer.append("BRANCH.EMAIL_ID, ");
        buffer.append("BRANCH.MAP_URL,");
        buffer.append("REF_REGION.REGION_ID AS REF_REGION_REGION_ID, ");
        buffer.append("REF_REGION.REGION_NAME AS REF_REGION_REGION_NAME, ");
        buffer.append("REF_STATE.STATE_ID AS REF_STATE_STATE_ID, ");
        buffer.append("REF_STATE.STATE_NAME AS REF_STATE_STATE_NAME ");
        buffer.append("FROM BRANCH ");
        buffer.append("INNER JOIN REF_REGION ON REF_REGION.REGION_ID = BRANCH.REF_REGION_ID ");
        buffer.append("INNER JOIN REF_STATE ON REF_STATE.STATE_ID = REF_REGION.REF_STATE_ID ");
        SELECT_ALL = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE REF_REGION.REGION_ID=?");
        SELECT_BY_REGION_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE BRANCH_ID=?");
        SELECT_BY_ID = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE BRANCH_CODE=?");
        SELECT_BY_BRANCH_CODE = buffer.toString();
        buffer.setLength(0);

        buffer.append("INSERT INTO BRANCH(");
        buffer.append("BRANCH_ID, BRANCH_CODE, DESCRIPTION, ");
        buffer.append("ADDRESS, REF_REGION_ID, PHONE_NUMBER, EMAIL_ID, MAP_URL");
        buffer.append(")VALUES ( ?, ?, ?, ?, ?, ?, ?, NULL)");
        INSERT = buffer.toString();
        buffer.setLength(0);

        buffer.append("UPDATE BRANCH ");
        buffer.append("SET BRANCH_CODE=?, ");
        buffer.append("DESCRIPTION=?, ");
        buffer.append("ADDRESS=?, ");
        buffer.append("REF_REGION_ID=?, ");
        buffer.append("PHONE_NUMBER=?, ");
        buffer.append("EMAIL_ID=? ");
        buffer.append("WHERE BRANCH_ID=? ");
        UPDATE = buffer.toString();
        buffer.setLength(0);

        buffer.append("DELETE FROM BRANCH WHERE BRANCH_ID=?");
        DELETE = buffer.toString();
        buffer.setLength(0);
    }

    /**
     * Gets the branch search query.
     * 
     * @param branchCriteria the branch criteria
     * @return the branch search query
     */
    public static String getBranchSearchQuery(BranchDto branchCriteria) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(SELECT_ALL);
        if (branchCriteria != null) {
            RegionDto region = branchCriteria.getRegion();
            Map<String, String> whereClauseMap = new HashMap<String, String>();
            int branchId = branchCriteria.getBranchId();
            if (branchId != 0) {
                whereClauseMap.put("BRANCH.BRANCH_ID=?", String.valueOf(branchId));
            }
            whereClauseMap.put("BRANCH.ADDRESS LIKE '%?%'", branchCriteria.getAddress());
            whereClauseMap.put("BRANCH.BRANCH_CODE='?'", branchCriteria.getBranchCode());
            whereClauseMap.put("BRANCH.DESCRIPTION LIKE '%?%'", branchCriteria.getDescription());
            whereClauseMap.put("BRANCH.EMAIL_ID LIKE '%?%'", branchCriteria.getEmailId());
            whereClauseMap.put("BRANCH.PHONE_NUMBER LIKE '%?%'", branchCriteria.getPhoneNumber());
            if (region != null) {
                whereClauseMap.put("REF_REGION.REGION_NAME LIKE '%?%'", region.getRegionName());
                StateDto state = region.getState();
                if (state != null) {
                    whereClauseMap.put("REF_STATE.STATE_NAME LIKE '%?%'", state.getStateName());
                }
            }
            buffer.append(DatabaseUtil.getWhereClause(whereClauseMap));
        }
        return buffer.toString();
    }

}
