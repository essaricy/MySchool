package com.myschool.infra.filesystem.agent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.myschool.common.exception.FileSystemException;
import com.myschool.common.util.ConversionUtil;
import com.myschool.download.dto.BrochureDto;
import com.myschool.infra.filesystem.util.FileUtil;

/**
 * The Class BrochuresFileSystem.
 */
@Component
public class BrochuresFileSystem extends AbstractSubFileSystem {

    /**
     * Gets the brochures.
     *
     * @return the brochures
     * @throws FileSystemException the file system exception
     */
    public List<BrochureDto> getBrochures() throws FileSystemException {
        List<BrochureDto> brochures = null; 
        File[] allBrochures = getFiles();
        if (allBrochures != null) {
            brochures = new ArrayList<BrochureDto>(allBrochures.length);
            for (File file : allBrochures) {
                brochures.add(createBrochure(file));
            }
        }
        return brochures;
    }

    /**
     * Gets the brochure.
     *
     * @param brochureName the brochure name
     * @return the brochure
     * @throws FileSystemException the file system exception
     */
    public BrochureDto getBrochure(String brochureName) throws FileSystemException {
        File brochureFile = getFile(brochureName);
        FileUtil.checkFile(brochureFile, "No such file.", "No such file.");
        return createBrochure(brochureFile);
    }

    /**
     * Creates the brochure.
     * 
     * @param brochureFile the brochure file
     * @return the brochure dto
     */
    private BrochureDto createBrochure(File brochureFile) {
        BrochureDto brochure = new BrochureDto();
        brochure.setBrochureFile(brochureFile);
        brochure.setBrochureName(FileUtil.getFileName(brochureFile.getName()));
        brochure.setBrochureType(FileUtil.getExtension(brochureFile.getName()).toUpperCase());
        brochure.setLastUpdatedOn(ConversionUtil.fromApplicationDate(brochureFile.lastModified()));
        return brochure;
    }
}
