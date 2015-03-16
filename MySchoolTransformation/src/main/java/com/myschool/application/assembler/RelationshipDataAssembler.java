package com.myschool.application.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.myschool.common.dto.Relationship;

/**
 * The Class RelationshipDataAssembler.
 */
public class RelationshipDataAssembler {

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @return the relationship
     * @throws SQLException the sQL exception
     */
    public static Relationship create(ResultSet resultSet) throws SQLException {
        return create(resultSet, false);
    }

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @param aliased the aliased
     * @return the relationship
     * @throws SQLException the sQL exception
     */
    public static Relationship create(ResultSet resultSet, boolean aliased) throws SQLException {
        Relationship relationship = new Relationship();
        if (aliased) {
            relationship.setCode(resultSet.getString("REF_RELATIONSHIP_CODE"));
            relationship.setName(resultSet.getString("REF_RELATIONSHIP_NAME"));
        } else {
            relationship.setCode(resultSet.getString("CODE"));
            relationship.setName(resultSet.getString("NAME"));
        }
        return relationship;
    }

}
