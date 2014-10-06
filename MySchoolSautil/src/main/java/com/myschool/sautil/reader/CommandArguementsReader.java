package com.myschool.sautil.reader;

import java.util.Properties;

/**
 * The Class CommandArguementsReader.
 */
public class CommandArguementsReader {

    /**
     * To properties.
     *
     * @param args the args
     * @return the properties
     */
    public Properties toProperties(String[] args) {
        String key = null;
        String value = null;
        String arguement = null;
        Properties properties = new Properties();
        if (args.length != 0) {
            int length = args.length;
            for (int index = 0; index < length; index++) {
                key = null;
                value = null;

                arguement = args[index];
                if (arguement != null && arguement.trim().startsWith("-")) {
                    key = arguement;
                }
                if (index+1 == length) {
                    // This is the last argument
                } else {
                    index = index + 1;
                    arguement = args[index];
                    if (arguement != null) {
                        if (arguement.trim().startsWith("-")) {
                            // This is a next key;
                            index = index -1;
                        } else {
                            value = arguement;
                        }
                    }
                }
                if (key != null && value != null) {
                    properties.put(key, value);
                }
            }
        }
        return properties;
    }

}
