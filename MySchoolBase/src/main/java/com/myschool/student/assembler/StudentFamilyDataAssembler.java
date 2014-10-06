package com.myschool.student.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myschool.application.assembler.RelationshipDataAssembler;
import com.myschool.common.dto.FamilyMemberDto;
import com.myschool.common.dto.Relationship;
import com.myschool.common.util.ConversionUtil;

/**
 * The Class StudentFamilyDataAssembler.
 */
public class StudentFamilyDataAssembler {

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @return the family member dto
     * @throws SQLException the sQL exception
     */
    public static FamilyMemberDto create(ResultSet resultSet)
            throws SQLException {
        FamilyMemberDto familyMember = new FamilyMemberDto();
        familyMember.setFamilyMemberId(resultSet.getInt("FAMILY_MEMBER_ID"));
        familyMember.setRelationship(RelationshipDataAssembler.create(resultSet, true));
        familyMember.setName(resultSet.getString("NAME"));
        familyMember.setOccupation(resultSet.getString("OCCUPATION"));
        familyMember.setMobileNumber(resultSet.getString("MOBILE_NUMBER"));
        familyMember.setEmailId(resultSet.getString("EMAIL_ID"));
        familyMember.setAvailEmail(ConversionUtil.toBoolean(resultSet.getString("AVAIL_EMAIL")));
        familyMember.setAvailSMS(ConversionUtil.toBoolean(resultSet.getString("AVAIL_SMS")));
        return familyMember;
    }

    /**
     * Creates the.
     * 
     * @param familyMembersData the family members data
     * @return the list
     */
    public static List<FamilyMemberDto> create(JSONArray familyMembersData) {
        FamilyMemberDto familyMember = null;
        List<FamilyMemberDto> familyMembers = null;

        if (familyMembersData != null) {
            familyMembers = new ArrayList<FamilyMemberDto>();
            int numberOfFamilyMembers = familyMembersData.length();
            for (int index = 0; index < numberOfFamilyMembers; index++) {
                familyMember = create((JSONObject) familyMembersData.get(index));
                if (familyMember != null) {
                    familyMembers.add(familyMember);
                }
            }
        }
        return familyMembers;
    }

    /**
     * Creates the.
     * 
     * @param familyMemberData the family member data
     * @return the family member dto
     */
    public static FamilyMemberDto create(JSONObject familyMemberData) {
        FamilyMemberDto familyMember = null;
        if (familyMemberData != null) {
            familyMember = new FamilyMemberDto();
            familyMember.setFamilyMemberId(Integer.parseInt(familyMemberData.getString("FamilyMemberId")));
            Relationship relationship = new Relationship();
            relationship.setCode(familyMemberData.getString("RelationshipCode"));
            relationship.setName(familyMemberData.getString("RelationshipName"));
            familyMember.setRelationship(relationship);
            familyMember.setName(familyMemberData.getString("FamilyMemberName"));
            familyMember.setOccupation(familyMemberData.getString("Occupation"));
            familyMember.setMobileNumber(familyMemberData.getString("MobileNumber"));
            familyMember.setEmailId(familyMemberData.getString("EmailID"));
            familyMember.setAvailSMS(ConversionUtil.toBoolean(familyMemberData.getString("AvailSMS")));
            familyMember.setAvailEmail(ConversionUtil.toBoolean(familyMemberData.getString("AvailEmail")));
        }
        return familyMember;
    }

    /**
     * Creates the.
     * 
     * @param studentFamilyMembers the student family members
     * @return the jSON array
     */
    public static JSONArray create(List<FamilyMemberDto> studentFamilyMembers) {
        JSONArray jsonArray = null;
        if (studentFamilyMembers != null && !studentFamilyMembers.isEmpty()) {
            jsonArray = new JSONArray();
            for (FamilyMemberDto familyMember : studentFamilyMembers) {
                jsonArray.put(create(familyMember));
            }
        }
        return jsonArray;
    }

    /**
     * Creates the.
     * 
     * @param familyMember the family member
     * @return the jSON array
     */
    private static JSONArray create(FamilyMemberDto familyMember) {
        JSONArray jsonArray = null;
        if (familyMember != null) {
            Relationship relationship = familyMember.getRelationship();
            jsonArray = new JSONArray();
            jsonArray.put(familyMember.getFamilyMemberId());
            jsonArray.put(relationship.getCode());
            jsonArray.put(relationship.getName());
            jsonArray.put(familyMember.getName());
            jsonArray.put(familyMember.getOccupation());
            jsonArray.put(familyMember.getMobileNumber());
            jsonArray.put(familyMember.getEmailId());
            jsonArray.put(familyMember.isAvailSMS());
            jsonArray.put(familyMember.isAvailEmail());
        }
        return jsonArray;
    }
}
