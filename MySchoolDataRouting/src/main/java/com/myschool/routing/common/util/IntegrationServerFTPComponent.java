package com.myschool.routing.common.util;

import java.io.File;

import org.apache.camel.Message;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.myschool.routing.exception.RoutingException;

@Component("IntegrationServerFTPComponent")
public class IntegrationServerFTPComponent {

    private static final Log LOGGER = LogFactory.getLog(IntegrationServerFTPComponent.class);

    private static final String OUT_FILE = "OUT_FILE";

    //@Autowired
    //private MediaServerFTPClientProxy mediaServerFTPClientProxy;

    public void send(Message message, File file) throws RoutingException {
        String filePath = null;

        try {
            filePath = (String) message.getHeader(OUT_FILE);
            if (filePath == null) {
                throw new RoutingException("Missing OUT_FILE in header");
            }
            filePath = filePath.replace("\\", "/");
            System.out.println("filePath="+filePath);
            LOGGER.debug("send " + filePath);
            //mediaServerFTPClientProxy.storeFile(mediaType, filePath, file);
        }/* catch (MediaServerException mediaServerException) {
            throw new CommandProcessException(mediaServerException.getMessage(), mediaServerException);
        }*/ finally {
            
        }
    }

}
