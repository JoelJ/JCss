package com.joelj.jcss.dom;

import com.joelj.jcss.parse.CssUtils;
import org.w3c.dom.*;

import javax.xml.xpath.*;
import java.util.List;

/**
 * User: joeljohnson
 * Date: 5/16/12
 * Time: 9:45 PM
 */
public interface CssNode {
	String getName();
	boolean hasAttribute(String attributeName);
	String getAttribute(String attributeName) throws AttributeNotFoundException;
	CssNode findElement(String cssSelector) throws CssExpressionException, NoSuchElementException;
	List<CssNode> findElements(String cssSelector) throws CssExpressionException;
}

class CssNodeImpl implements CssNode {
	private final Node node;
	private final XPath xpath;

	CssNodeImpl(Node node) {
		this.node = node;

		XPathFactory factory = XPathFactory.newInstance();
		this.xpath = factory.newXPath();
	}

	@Override
	public String getName() {
		return node.getNodeName();
	}

	@Override
	public boolean hasAttribute(String attributeName) {
		return node.getAttributes().getNamedItem(attributeName) != null;
	}

	@Override
	public String getAttribute(String attributeName) throws AttributeNotFoundException {
		Node attributeNode = node.getAttributes().getNamedItem(attributeName);
		if(attributeNode == null) {
			throw new AttributeNotFoundException(attributeName);
		}
		return attributeNode.getNodeValue();
	}

	/**
	 * Finds the first element that matches the given selector.
	 * @throws CssExpressionException if no element is found matching the selector.
	 */
	@Override
	public CssNode findElement(String cssSelector) throws CssExpressionException, NoSuchElementException {
		String xpathExpression = CssUtils.convertCssToXpath(cssSelector) + "[1]";
		try {
			NodeList cssNodes = evaluateXpath(xpathExpression);
			if(cssNodes.getLength() <= 0) {
				throw new NoSuchElementException("No elements match the given selector. ''" + cssSelector + "''");
			}
			return new CssNodeImpl(cssNodes.item(0));
		} catch (XPathExpressionException e) {
			throw new CssExpressionException(cssSelector, xpathExpression, e);
		}
	}

	@Override
	public List<CssNode> findElements(String cssSelector) throws CssExpressionException {
		String xpathExpression = CssUtils.convertCssToXpath(cssSelector);
		try {
			return new CssNodeList(evaluateXpath(xpathExpression));
		} catch (XPathExpressionException e) {
			throw new CssExpressionException(cssSelector, xpathExpression, e);
		}
	}

	private NodeList evaluateXpath(String xpathExpression) throws CssExpressionException, XPathExpressionException {
		XPathExpression expr = xpath.compile(xpathExpression);
		Object result = expr.evaluate(node, XPathConstants.NODESET);
		return (NodeList) result;
	}
}