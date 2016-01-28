package com.myschool.integration.processor.attendance;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component("AttendanceProcessor")
public class AttendanceProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("process()");
        String exchangeId = exchange.getExchangeId();
        Message message = exchange.getIn();
        Map<String, Object> properties = exchange.getProperties();
        System.out.println("exchangeId " + exchangeId);
        System.out.println("message " + message);
        System.out.println("properties " + properties);
        /*if (true) {
            throw new Exception("from com.myschool.framework.transfer.file.AttendanceBatchProcessor");
        }*/
    }

}
