package com.myschool.infra.filesystem.agent;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.myschool.exim.constants.EximPolicy;
import com.quasar.core.exception.FileSystemException;
import com.quasar.core.util.FileUtil;

/**
 * The Class ImportTemplatesFileSystem.
 */
@Component
public class ImportTemplatesFileSystem extends AbstractSubFileSystem {

    /**
     * Gets the import templates.
     *
     * @return the import templates
     * @throws FileSystemException the file system exception
     */
    public List<File> getImportTemplates() throws FileSystemException {
        List<File> importTemplates = null;
        File[] files = getFiles();
        if (files != null && files.length != 0) {
            importTemplates = Arrays.asList(files);
        }
        return importTemplates;
    }

    /**
     * Gets the import template.
     *
     * @param eximPolicy the exim policy
     * @return the import template
     * @throws FileSystemException the file system exception
     */
    public File getImportTemplate(EximPolicy eximPolicy) throws FileSystemException {
        if (eximPolicy == null) {
            throw new FileSystemException("No such import policy found for " + eximPolicy);
        }
        List<File> importTemplates = getImportTemplates();
        if (importTemplates == null || importTemplates.isEmpty()) {
            throw new FileSystemException("There are no templates available.");
        }
        for (File importTemplate : importTemplates) {
            String fileName = FileUtil.getFileName(importTemplate.getName());
            if (fileName != null && fileName.equalsIgnoreCase(eximPolicy.toString())) {
                return importTemplate;
            }
        }
        return null;
    }

}
