package com.joelj.jcss.dom;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * User: joeljohnson
 * Date: 5/17/12
 * Time: 11:17 PM
 */
public class CssDocumentTest {
	private CssDocument document;

	@BeforeClass
	public void setUp() throws Exception {
		String str = "<root>" +
						"<one class='class1'/>" +
						"<two class='class2'>" +
							"<three class='class1'/>" +
						"</two>" +
						"<three id='anId' />" +
					 "</root>";
		InputStream xmlStream = new ByteArrayInputStream(str.getBytes());
		document = new CssDocument(xmlStream);
	}

	@Test
	public void testDocument() {
		Assert.assertEquals(document.getName(), "#document");
	}

	@Test
	public void testDocument_simple() throws Exception {
		CssNode element = document.findElement("two > three");

		Assert.assertEquals(element.getName(), "three");
		Assert.assertTrue(element.hasAttribute("class"));
		Assert.assertEquals(element.getAttribute("class"), "class1");
	}

	@Test
	public void testDocument_nested() throws Exception {
		CssNode element = document.findElement("root>one+two>three");
		Assert.assertEquals(element.getName(), "three");
		Assert.assertTrue(element.hasAttribute("class"));
		Assert.assertEquals(element.getAttribute("class"), "class1");
	}

	@Test
	public void testDocument_childQueries() throws Exception {
		CssNode root = document.findElement("root");
		Assert.assertEquals(root.getName(), "root");

		List<CssNode> threeNodes = root.findElements("three");
		Assert.assertEquals(threeNodes.size(), 2);

		Assert.assertEquals(threeNodes.get(0).getName(), "three");
		Assert.assertTrue(threeNodes.get(0).hasAttribute("class"));

		Assert.assertEquals(threeNodes.get(1).getName(), "three");
		Assert.assertFalse(threeNodes.get(1).hasAttribute("class"));
	}

	@Test
	public void testDocument_id() throws Exception {
		CssNode element = document.findElement("three#anId");
		Assert.assertEquals(element.getName(), "three");
		Assert.assertTrue(element.hasAttribute("id"));
	}

	@Test
	public void testDocument_findElement_doesntExist() throws Exception {
		try {
			document.findElement("node#doesntexist");
			Assert.fail("Searching findElement with a selector that doesn't match anything should throw an exception.");
		} catch(NoSuchElementException ignore) {}
	}

	@Test
	public void testDocument_findElements_doesntExist() throws Exception {
		List<CssNode> elements = document.findElements("node#doesntexist");
		Assert.assertEquals(elements.size(), 0);
	}

}
