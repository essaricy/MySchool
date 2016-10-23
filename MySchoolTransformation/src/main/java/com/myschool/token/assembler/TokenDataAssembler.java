package com.myschool.token.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.myschool.token.dto.Token;

/**
 * The Class TokenDataAssembler.
 */
public class TokenDataAssembler {

    /**
     * Creates the.
     *
     * @param resultSet the result set
     * @return the token
     * @throws SQLException the SQL exception
     */
    public static Token create(ResultSet resultSet) throws SQLException {
        Token token = new Token();
        token.setConsumedBy(resultSet.getString("CONSUMED_BY"));
        token.setDisposedOn(resultSet.getString("DISPOSED_ON"));
        token.setGeneratedOn(resultSet.getString("GENERATED_ON"));
        token.setNecessity(resultSet.getString("NECESSITY"));
        token.setTokenId(resultSet.getString("TOKEN_ID"));
        token.setValidity(resultSet.getInt("VALIDITY"));
        return token;
    }

}
