package com.myschool.integration.agent;

import java.io.File;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.myschool.common.exception.FileSystemException;
import com.myschool.common.util.PropertiesUtil;
import com.myschool.file.util.FileUtil;

@Component
public class IntegrationProperties {

    /** The properties. */
    protected Properties properties;

    @PostConstruct
    public void init() throws FileSystemException {
        File file = new File("D:\\projects\\GitHub\\MySchool\\MySchoolIntegration\\src\\main\\resources\\config\\integration.properties");
        this.properties = PropertiesUtil.loadNestedProperties(file);
    }

    public Properties getProperties() {
        return properties;
    }

    /**
     * Gets the file.
     * 
     * @param key the key
     * @return the file
     */
    public File getFile(String key) {
        File file = null;
        String value = properties.getProperty(key);
        if (value != null) {
            file = new File(value);
            FileUtil.createDirectory(file);
        }
        return file;
    }

    public String getProperty(String key) {
        if (key != null) {
            return properties.getProperty(key);
        }
        return null;
    }

}
