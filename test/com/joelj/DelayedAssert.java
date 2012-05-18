package com.joelj;

import org.testng.Assert;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Several methods for asserting.
 * The difference from Junit or TestNG Assert class is that the assertion exceptions are not thrown when they happen,
 * but rather they are stored and thrown when #assertDelayed is called.
 *
 * This is useful when you want to assert several values in a row and you want the feedback for all the assertions
 * even if one of the first assertions failed.
 *
 * <pre>
 *     DelayedAssert.assertEquals("value1", "value1");
 *     DelayedAssert.assertEquals("value3", "value2"); // Fails
 *     DelayedAssert.assertEquals("value2", "value3"); // Fails, still gets evaluated
 *     DelayedAssert.assertEquals("value4", "value4"); // Still gets evaluated
 *     DelayedAssert.assertDelayed(); // Assertions are thrown here, bundled as a DelayedAssert.DelayedAssertionError.
 * </pre>
 *
 * User: joeljohnson
 * Date: 5/15/12
 * Time: 8:54 PM
 */
public class DelayedAssert {
	private static ThreadLocal<List<AssertionError>> delayedThrowable = new ThreadLocal<List<AssertionError>>();

	public static void assertEquals(Object actual, Object expected, String message) {
		try {
			org.testng.Assert.assertEquals(actual, expected, message);
		} catch(AssertionError t) {
			List<AssertionError> assertionErrors = delayedThrowable.get();
			if(assertionErrors == null) {
				assertionErrors = new ArrayList<AssertionError>();
				delayedThrowable.set(assertionErrors);
			}

			assertionErrors.add(t);
		}
	}

	public static void assertEquals(Object actual, Object expected) {
		assertEquals(actual, expected, null);
	}

	public static void assertDelayed() {
		List<AssertionError> assertionErrors = delayedThrowable.get();
		if(assertionErrors != null) {
			delayedThrowable.set(null);
			if(assertionErrors.size() == 1) {
				throw assertionErrors.get(0);
			} else if(assertionErrors.size() > 0) {
				throw new DelayedAssertionError(assertionErrors);
			}
		}
	}

	public static void assertEquals(List<String> actual, List<String> expected) {
		if(actual == null || expected == null) {
			assertEquals(actual, expected);
			return;
		}

		try {
			Assert.assertEquals(actual.size(), expected.size(), "given lists must be the same size");
			for(int i = 0; i < actual.size(); i++) {
				Assert.assertEquals(actual.get(i), expected.get(i), "each element in lists must be the same at index " + i);
			}
		} catch(AssertionError t) {
			List<AssertionError> assertionErrors = delayedThrowable.get();
			if(assertionErrors == null) {
				assertionErrors = new ArrayList<AssertionError>();
				delayedThrowable.set(assertionErrors);
			}

			assertionErrors.add(t);
		}
	}

	private static class DelayedAssertionError extends AssertionError {
		private final Collection<AssertionError> errors;

		public DelayedAssertionError(Collection<AssertionError> errors) {
			this.errors = errors;
		}

		@SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
		@Override
		public void printStackTrace(PrintStream stream) {
			synchronized (stream) {
				stream.println("Multiple Failures");
				for (AssertionError error : errors) {
					error.printStackTrace(stream);
					stream.println();
				}
				stream.flush();
			}
		}

		@SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
		@Override
		public void printStackTrace(PrintWriter writer) {
			synchronized (writer) {
				writer.println("Multiple Failures");
				for (AssertionError error : errors) {
					error.printStackTrace(writer);
					writer.println();
				}
				writer.flush();
			}
		}
	}
}
