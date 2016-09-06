package com.myschool.data.generator;

import java.util.ArrayList;
import java.util.List;

import com.myschool.data.type.DataType;
import com.myschool.data.type.DataTypeName;
import com.myschool.data.type.DataTypeNumber;

public abstract class DataGenerator {

    public List<List<Object>> getRecords(int rows) {
        List<List<Object>> records = new ArrayList<List<Object>>();
        List<DataType> datatypes = getColumns();

        for (int rowNumber = 0; rowNumber < rows; rowNumber++) {
            List<Object> record = new ArrayList<Object>();
            records.add(record);
            for (DataType datatype : datatypes) {
                if (datatype instanceof DataTypeNumber) {
                    DataTypeNumber number = (DataTypeNumber) datatype;
                    if (number.isRandom()) {
                        
                    } else {
                        int start = number.getStart();
                        int increment = number.getIncrement();
                        int result = 0;
                        result = start + (rowNumber-1) + increment;
                        record.add(result);
                    }
                } else if (datatype instanceof DataTypeName) {
                    DataTypeName dataTypeName = (DataTypeName) datatype;
                    int placement = dataTypeName.getPlacement();
                    
                }
            }
        }
        return records;
    }

    protected abstract List<DataType> getColumns();

}
