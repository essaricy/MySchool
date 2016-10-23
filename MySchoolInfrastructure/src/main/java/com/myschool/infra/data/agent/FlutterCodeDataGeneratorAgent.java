package com.myschool.infra.data.agent;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.fluttercode.datafactory.impl.DataFactory;
import org.fluttercode.datafactory.impl.DefaultContentDataValues;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.ConfigurationException;
import com.myschool.infra.data.support.LocaleSpecificAddresses;
import com.myschool.infra.data.support.LocaleSpecificNames;

/**
 * The Class FlutterCodeDataGeneratorAgent.
 */
@Component
public class FlutterCodeDataGeneratorAgent extends DataGeneratorAgent {

    /** The data factory. */
    private DataFactory dataFactory;

    /* (non-Javadoc)
     * @see com.myschool.infra.data.agent.DataGeneratorAgent#loadConfiguration(java.io.File)
     */
    @Override
    public void loadConfiguration(File configFile)
            throws ConfigurationException {

        try {
            System.out.println("FlutterCodeDataGeneratorAgent.loadConfiguration()");
            super.loadConfiguration(configFile);
            List<String> firstNamesList = FileUtils.readLines(firstNamesFile);
            List<String> lastNamesList = FileUtils.readLines(lastNamesFile);
            List<String> citiesList = FileUtils.readLines(citiesFile);

            String[] firstNamesArray = firstNamesList.toArray(new String[0]);
            String[] lastNamesArray = lastNamesList.toArray(new String[0]);
            String[] citiesArray = citiesList.toArray(new String[0]);

            dataFactory = new DataFactory();
            dataFactory.setNameDataValues(new LocaleSpecificNames(firstNamesArray, lastNamesArray));
            dataFactory.setAddressDataValues(new LocaleSpecificAddresses(citiesArray));
            dataFactory.setContentDataValues(new DefaultContentDataValues());
        } catch (IOException ioException) {
            throw new ConfigurationException(ioException.getMessage(), ioException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.data.agent.DataGeneratorAgent#getFirstName()
     */
    @Override
    public String getFirstName() {
        return dataFactory.getFirstName();
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.data.agent.DataGeneratorAgent#getLastName()
     */
    @Override
    public String getLastName() {
        return dataFactory.getLastName();
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.data.agent.DataGeneratorAgent#getEmailAddress()
     */
    @Override
    public String getEmailAddress() {
        //return dataFactory.getEmailAddress();
        return "no-one@nowhere.com";
    }

    /*public String getFirstName() {
        dataFactory.getAddress();
        dataFactory.getBirthDate();
        dataFactory.getBusinessName();
        dataFactory.getCity();
        dataFactory.getDate(baseDate, minDaysFromDate, maxDaysFromDate);
        dataFactory.getDateBetween(minDate, maxDate);
        dataFactory.getEmailAddress();
        dataFactory.getFirstName();
        dataFactory.getLastName();
        dataFactory.getNumber();
        dataFactory.getNumberBetween(min, max);
        dataFactory.getNumberUpTo(max);
        dataFactory.getRandomChars(length);
        dataFactory.getRandomText(length);
        dataFactory.getRandomWord();
        dataFactory.getStreetName();
        return dataFactory.getFirstName();
    }*/

}
