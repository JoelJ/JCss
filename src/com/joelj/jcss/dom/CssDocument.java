package com.joelj.jcss.dom;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * User: joeljohnson
 * Date: 5/16/12
 * Time: 9:47 PM
 */
public class CssDocument extends CssNodeImpl {
	public CssDocument(File xmlFile) throws ParserConfigurationException, IOException, SAXException {
		super(loadDocumentFromFile(xmlFile));
	}

	public CssDocument(InputStream xmlInputStream) throws IOException, SAXException, ParserConfigurationException {
		super(loadDocumentFromInputStream(xmlInputStream));
	}

	private static Document loadDocumentFromFile(File xmlFile) throws ParserConfigurationException, IOException, SAXException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(xmlFile);
	}

	private static Document loadDocumentFromInputStream(InputStream xmlFile) throws ParserConfigurationException, IOException, SAXException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(xmlFile);
	}
}
