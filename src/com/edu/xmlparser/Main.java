package com.edu.xmlparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;

public class Main {
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(System.getProperty("user.dir") + File.separator + "src\\resources\\ilya.properties"));
//        System.out.println(properties.getProperty("jobs"));
        System.out.println("XML validates against Schema.xsd. Result: " + validateXMLSchema());
    }

    private static boolean validateXMLSchema() {
        Document document = null;
        try {
            DocumentBuilderFactory factoryDoc = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factoryDoc.newDocumentBuilder();
            document = builder.parse(new File(System.getProperty("user.dir") + File.separator + "XML directory\\rulesXML.xml"));

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(System.getProperty("user.dir") + File.separator + "XML directory\\rulesSchema.xsd"));

            Validator validator = schema.newValidator();
            validator.validate(new DOMSource(document));
        } catch (SAXParseException e) {
            System.out.println(e.toString());
            System.out.println("Error when validate XML against XSD Schema\n" +
                    "Line: " + e.getLineNumber() + "\n" +
                    "Column: " + e.getColumnNumber() + "\n" +
                    "Error message: " + e.getMessage().substring(e.getMessage().indexOf(":") + 2));
            return false;
        } catch (SAXException | IOException | ParserConfigurationException ignored) {
        }
        System.out.println("Wellformed XML!");
        return true;
    }
}