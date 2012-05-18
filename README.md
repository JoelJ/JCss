[![Build Status](https://buildhive.cloudbees.com/job/JoelJ/job/JCss/badge/icon)](https://buildhive.cloudbees.com/job/JoelJ/job/JCss/)

Description
===========
JCss is a simple library for querying XML structures via CSS.

The library simply takes the given CSS selectors and converts them to Xpath.

If you find a CSS selector that doesn't give the proper results
(i.e. it probably isn't converting to the right Xpath)
please feel free to add a test case to CssUtilsTest.

Examples
========

example.xml
-----------
	<root>
		<one class='class1'/>
		<two class='class2'>
			<three class='class1'/>
		</two>
		<three id='anId' />
	</root>

Example.java
------------
	public class Example {
		public static void main(String[] args) {
			CssDocument document = new CssDocument(new File("example.xml"));
			List<CssNode> result = document.findElements("*.class1");
			assert result.size() == 2;

			CssNode node = document.findElement("three#anId");
			assert node.getName().equals("three");
			assert node.hasAttribute("id");
			assert node.getAttribute("id").equals("anId");

			CssNode root = document.findElement("root");
			CssNode threeClass1 = root.findElement("three.class1");
			assert threeClass1.getName("class").equals("three");
			assert threeClass1.hasAttribute("class");

			CssNode nestedNode = document.findElement("two three");
			assert node.getName().equals("three");
    	}
	}
