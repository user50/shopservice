package com.shopservice;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Queries {
    private Document document;
    private XPath xPath;

    private static final String CONFIGURATION_FILE = "conf" + File.separator + "queries.conf";

    public Queries(){
        try {
            FileInputStream file = new FileInputStream(new File(CONFIGURATION_FILE));
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder =  builderFactory.newDocumentBuilder();
            document = builder.parse(file);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        xPath =  XPathFactory.newInstance().newXPath();
    }

    public String getProductQueriesById(String clientId){
        String expression = "/root/client[@id='"+clientId+"']/item/byId";
        System.out.println(expression);
        Node node = null;
        try {
            node = (Node) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return node.getFirstChild().getNodeValue();
    }

}
