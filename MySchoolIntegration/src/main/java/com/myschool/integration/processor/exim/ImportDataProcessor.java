package com.myschool.integration.processor.exim;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component("ImportDataProcessor")
public class ImportDataProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("process()");
        String exchangeId = exchange.getExchangeId();
        Message message = exchange.getIn();
        Map<String, Object> properties = exchange.getProperties();
        System.out.println("exchangeId " + exchangeId);
        System.out.println("message " + message);
        System.out.println("properties " + properties);
    }

}
