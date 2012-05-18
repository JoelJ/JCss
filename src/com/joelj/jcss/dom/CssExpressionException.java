package com.joelj.jcss.dom;

import javax.xml.xpath.XPathExpressionException;

/**
 * User: joeljohnson
 * Date: 5/16/12
 * Time: 9:50 PM
 */
public class CssExpressionException extends CssException {
	public CssExpressionException(String cssSelector, String xpathExpression, XPathExpressionException e) {
		super("Invalid Css Selector: ''" + cssSelector + "''. Converted to ''" + xpathExpression + "''" + e);
	}
}
