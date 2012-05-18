package com.joelj.jcss.dom;

/**
 * User: joeljohnson
 * Date: 5/17/12
 * Time: 11:50 PM
 */
public class AttributeNotFoundException extends CssException {
	public AttributeNotFoundException(String attributeName) {
		super("The given attribute does not exist on node: " + attributeName);
	}
}
