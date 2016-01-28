package com.myschool.integration.common.util;

import java.io.File;

import org.apache.camel.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.integration.common.exception.CommandProcessException;
import com.myschool.integration.common.exception.MediaServerException;

@Component("MediaServerFTPComponent")
public class MediaServerFTPComponent {

    //private static final Log LOGGER = LogFactory.getLog(MediaServerFTPComponent.class);

    private static final String MEDIA_TYPE = "MEDIA_TYPE";

    @Autowired
    private MediaServerFTPClientProxy mediaServerFTPClientProxy;

    public void send(Message message, File file) throws CommandProcessException {
        String filePath = null;

        try {
            String mediaType = (String) message.getHeader(MEDIA_TYPE);
            if (mediaType == null || mediaType.trim().length() == 0) {
                throw new CommandProcessException("Missing MEDIA_TYPE in header");
            }
            filePath = (String) message.getHeader("MEDIA_FILE");
            if (filePath == null) {
                throw new CommandProcessException("Missing MEDIA_FILE in header");
            }
            filePath = filePath.replace("\\", "/");
            mediaServerFTPClientProxy.storeFile(mediaType, filePath, file);
        } catch (MediaServerException mediaServerException) {
            throw new CommandProcessException(mediaServerException.getMessage(), mediaServerException);
        }
    }

}
