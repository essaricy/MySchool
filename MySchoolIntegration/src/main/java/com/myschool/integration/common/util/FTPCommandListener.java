package com.myschool.integration.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ProtocolCommandEvent;
import org.apache.commons.net.ProtocolCommandListener;

public class FTPCommandListener implements ProtocolCommandListener {

    private static final Log LOGGER = LogFactory.getLog(FTPCommandListener.class);

    //private static final String COMMAND_LOG = "{0} ";

    @Override
    public void protocolCommandSent(ProtocolCommandEvent protocolCommandEvent) {
        //int replyCode = protocolCommandEvent.getReplyCode();
        String message = protocolCommandEvent.getMessage();
        String command = protocolCommandEvent.getCommand();
        //LOGGER.debug(message);
    }

    @Override
    public void protocolReplyReceived(ProtocolCommandEvent protocolCommandEvent) {
        int replyCode = protocolCommandEvent.getReplyCode();
        String message = protocolCommandEvent.getMessage();
        //String command = protocolCommandEvent.getCommand();
        //LOGGER.debug(message);
    }

}
