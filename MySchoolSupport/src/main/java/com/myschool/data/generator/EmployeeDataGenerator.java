package com.myschool.data.generator;

import java.util.ArrayList;
import java.util.List;

import com.myschool.data.type.DataType;
import com.myschool.data.type.DataTypeCustomList;
import com.myschool.data.type.DataTypeDate;
import com.myschool.data.type.DataTypeName;
import com.myschool.data.type.DataTypeNumber;

public class EmployeeDataGenerator extends DataGenerator {

    @Override
    protected List<DataType> getColumns() {
        List<DataType> fields = new ArrayList<DataType>();
        fields.add(new DataTypeNumber("EMPLOYEE_NUMBER", 1, 1));
        fields.add(new DataTypeName("FIRST_NAME", DataTypeName.FIRST_NAME));
        fields.add(new DataTypeName("MIDDLE_NAME", DataTypeName.MIDDLE_NAME));
        fields.add(new DataTypeName("LAST_NAME", DataTypeName.LAST_NAME));
        fields.add(new DataTypeCustomList("GENDER", "GENDER"));
        fields.add(new DataTypeDate("DATE_OF_BIRTH"));
        fields.add(new DataTypeCustomList("BLOOD_GROUP", "BLOOD_GROUP"));
        fields.add(new DataTypeCustomList("NATIONALITY", "NATIONALITY"));
        fields.add(new DataTypeCustomList("MARITAL_STATUS", "MARITAL_STATUS"));
        fields.add(new DataTypeDate("WEDDING_DAY"));
        fields.add(new DataTypeCustomList("EMPLOYED_AT"));
        fields.add(new DataTypeNumber("DESIGNATION_CODE", true, 0, 0, 100, 599));
        fields.add(new DataTypeCustomList("EMPLOYEE_STATUS", "EMPLOYEE_STATUS"));
        fields.add(new DataTypeDate("EMPLOYMENT_START_DATE"));
        fields.add(new DataTypeDate("EMPLOYMENT_END_DATE"));
        fields.add(new DataTypeNumber("REPORTING_TO", true, 0, 0, 1, 500));
        fields.add(new DataTypeCustomList("REMARKS"));
        return fields;
    }

}
