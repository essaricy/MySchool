package com.myschool.web.exim.bean;

import java.io.Serializable;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


/**
 * The Class ImportBean.
 */
@Component
public class ImportBean implements Serializable{
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The import key. */
    private String importKey;

    /** The file. */
    private MultipartFile importingFile;

    /**
     * Gets the import key.
     *
     * @return the import key
     */
    public String getImportKey() {
        return importKey;
    }

    /**
     * Sets the import key.
     *
     * @param importKey the new import key
     */
    public void setImportKey(String importKey) {
        this.importKey = importKey;
    }

    /**
     * Gets the importing file.
     *
     * @return the importing file
     */
    public MultipartFile getImportingFile() {
        return importingFile;
    }

    /**
     * Sets the importing file.
     *
     * @param importingFile the new importing file
     */
    public void setImportingFile(MultipartFile importingFile) {
        this.importingFile = importingFile;
    }

    /**
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation 
     * of this object.
     */
    public String toString() {
        final String SEPARATOR = ", ";
        StringBuilder retValue = new StringBuilder();
        retValue.append("ImportBean ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("importKey = ").append(this.importKey).append(SEPARATOR)
            .append("importingFile = ").append(this.importingFile).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
