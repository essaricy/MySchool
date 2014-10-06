package com.myschool.common.util;

import java.io.File;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * The Class XslUtil.
 */
public class XslUtil {

    /**
     * Transform.
     * 
     * @param xslFile the xsl file
     * @param xmlFile the xml file
     * @param outputFile the output file
     * @throws TransformerException the transformer exception
     */
    public static void transform(File xslFile, File xmlFile, File outputFile) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        StreamSource xslStream = new StreamSource(xslFile);
        Transformer transformer = factory.newTransformer(xslStream);

        StreamSource in = new StreamSource(xmlFile);
        StreamResult out = new StreamResult(outputFile);
        transformer.transform(in, out);
    }

}
