package com.myschool.infra.image.agent;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.myschool.image.constant.ImageSize;
import com.myschool.infra.filesystem.util.FileUtil;
import com.myschool.infra.image.dto.ImageResizingOptionDto;

import net.coobird.thumbnailator.Thumbnails;

/**
 * The Class ThumbnailatorImageScalingAgent.
 */
@Component
public class ThumbnailatorImageScalingAgent extends ImageScalingAgent {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.myschool.infra.image.agent.ImageScalingAgent#resizeImage(java
     * .io.File, java.io.File,
     * com.myschool.infra.image.constants.ImageResizeType,
     * com.myschool.infra.image.dto.ImageResizingOptionDto)
     */
    @Override
    protected void resizeImage(File file, File resizedFile,
            ImageSize imageResizeType,
            ImageResizingOptionDto imageResizingOption) throws IOException {
        Thumbnails.of(file).size(
                (int) imageResizingOption.getWidth(), (int) imageResizingOption.getHeight())
                .outputFormat(FileUtil.getExtension(file.getName())).toFile(resizedFile);
    }

}
