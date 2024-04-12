package com.github.tombessa.autofundsexplorer.util;


import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

/**
 *
 * @author antonyonne.casa
 */
public class MergeXml {
    /**
     * Merges java files to the string.
     * @param fileNames the file names.
     * @return the result string
     */
    public static String merge(List<String> fileNames) {
        try {
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();
            XPathExpression compiledExpression = xpath.compile("/*");
            return documentToString(merge(compiledExpression, fileNames));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static Document merge(XPathExpression expression, List<String> fileNames) throws Exception {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document base = docBuilder.parse(new File(fileNames.get(0)));

        Node results = (Node) expression.evaluate(base, XPathConstants.NODE);
        if (results == null) {
            throw new IOException(fileNames.get(0) + ": expression does not evaluate to node");
        }

        for (int index = 1; index < fileNames.size(); index += 1) {
            Document merge = docBuilder.parse(new File(fileNames.get(index)));
            Node nextResults = (Node) expression.evaluate(merge, XPathConstants.NODE);
            while (nextResults.hasChildNodes()) {
                Node kid = nextResults.getFirstChild();
                nextResults.removeChild(kid);
                kid = base.importNode(kid, true);
                results.appendChild(kid);
            }
        }
        return base;
    }

    private static String documentToString(Document doc) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        return writer.getBuffer().toString();
    }
}