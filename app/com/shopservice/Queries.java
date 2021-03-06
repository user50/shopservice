package com.shopservice;

import org.w3c.dom.Document;
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

    public static final String CONFIGURATION_FILE = "conf" + File.separator + "queries.conf";
    private static final String EMPTY = "";

    private static Queries instance;

    public static Queries getInstance() {
        if (instance==null)
            instance = new Queries();

        return instance;
    }

    public Queries(){
        try {
            FileInputStream file = new FileInputStream(new File(CONFIGURATION_FILE));
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder =  builderFactory.newDocumentBuilder();
            document = builder.parse(file);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        xPath =  XPathFactory.newInstance().newXPath();
    }

    public String getCategoriesQuery(String clientId) {
        return getValue("/root/client[@id='"+clientId+"']/categories/getCategories");
    }

    public String getProductQuery(String clientId) {
        return getValue("/root/client[@id='"+clientId+"']/product");
    }

    public String getQuery4GetParentCategories(String clientId)
    {
        return getValue("/root/client[@id='"+clientId+"']/categories/getParentCategories");
    }

    private String getValue(String XPathQuery)
    {
        try {
            return ((String) xPath.compile(XPathQuery).evaluate(document, XPathConstants.STRING)).trim();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return EMPTY;
    }


}
