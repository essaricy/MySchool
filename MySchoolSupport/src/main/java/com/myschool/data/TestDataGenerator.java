package com.myschool.data;

import java.util.List;

import com.myschool.data.generator.DataGenerator;
import com.myschool.data.generator.EmployeeDataGenerator;

public class TestDataGenerator {

    public static void main(String[] args) {
        TestDataGenerator dataGenerator = new TestDataGenerator();
        dataGenerator.generate();
    }

    private void generate() {
        DataFactory dataFactory = DataFactory.getInstance();
        dataFactory.addCustomData("GENDER", new String[] {"M", "F"});
        dataFactory.addCustomData("BLOOD_GROUP", new String[] {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"});
        dataFactory.addCustomData("NATIONALITY", new String[] {"IN", "IN"});
        dataFactory.addCustomData("MARITAL_STATUS", new String[] {"Single", "Married", "Divorced"});
        //dataFactory.addCustomData("MARITAL_STATUS", new String[] {"Single", "Married", "Divorced"});

        DataGenerator dataGenerator = new EmployeeDataGenerator();
        List<List<Object>> records = dataGenerator.getRecords(10);
        for (List<Object> record : records) {
            System.out.println(record);
        }
    }

}
