package com.myschool.infra.remote.ftp.client;

import org.apache.commons.net.ProtocolCommandEvent;
import org.apache.commons.net.ProtocolCommandListener;

public class FTPCommandListener implements ProtocolCommandListener {

    //private static final Logger LOGGER = Logger.getLogger(FTPCommandListener.class);

    //private static final String COMMAND_LOG = "{0} ";

    @Override
    public void protocolCommandSent(ProtocolCommandEvent protocolCommandEvent) {
        //int replyCode = protocolCommandEvent.getReplyCode();
        //String message = protocolCommandEvent.getMessage();
        //String command = protocolCommandEvent.getCommand();
        //LOGGER.debug(message);
    }

    @Override
    public void protocolReplyReceived(ProtocolCommandEvent protocolCommandEvent) {
        //int replyCode = protocolCommandEvent.getReplyCode();
        //String message = protocolCommandEvent.getMessage();
        //String command = protocolCommandEvent.getCommand();
        //LOGGER.debug(message);
    }

}
