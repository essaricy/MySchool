package com.myschool.application.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRRewindableDataSource;

/**
 * The Class ReportDataSource.
 */
public class ReportDataSource implements JRRewindableDataSource {

    /** The header names. */
    private List<String> headerNames;

    /** The values. */
    private List<Map<String, Object>> values;

    /** The iterator. */
    private Iterator<Map<String, Object>> iterator;

    /** The current record. */
    private Map<String, Object> currentRecord;

    /**
     * Data source.
     *
     */
    public ReportDataSource() {
        //this.columns = columns;
        this.headerNames = new ArrayList<String>();
        this.values = new ArrayList<Map<String, Object>>();
    }

    /**
     * Adds the header.
     *
     * @param headerName the header name
     */
    public void addHeader(String headerName) {
        headerNames.add(headerName);
    }

    /**
     * Adds the.
     * 
     * @param values the values
     */
    public void add(Object... values) {
        Map<String, Object> row = new HashMap<String, Object>();
        for (int i = 0; i < values.length; i++) {
            row.put(headerNames.get(i), values[i]);
        }
        this.values.add(row);
    }

    /**
     * Gets the field value.
     * 
     * @param field the field
     * @return the field value
     * @throws JRException the jR exception
     */
    public Object getFieldValue(JRField field) throws JRException {
        return currentRecord.get(field.getName());
    }

    /**
     * Next.
     * 
     * @return true, if successful
     * @throws JRException the jR exception
     */
    public boolean next() throws JRException {
        if (iterator == null) {
            this.iterator = values.iterator();
        }
        boolean hasNext = iterator.hasNext();
        if (hasNext) {
            currentRecord = iterator.next();
        }
        return hasNext;
    }

    /**
     * Move first.
     * 
     * @throws JRException the jR exception
     */
    public void moveFirst() throws JRException {
        this.iterator = null;
    }

}
