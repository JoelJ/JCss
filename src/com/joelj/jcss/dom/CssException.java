package com.joelj.jcss.dom;

/**
 * User: joeljohnson
 * Date: 5/17/12
 * Time: 11:50 PM
 */
public class CssException extends Exception {
	public CssException(Throwable t) {
		super(t);
	}

	public CssException(String message) {
		super(message);
	}

	public CssException(String message, Throwable t) {
		super(message, t);
	}
}
