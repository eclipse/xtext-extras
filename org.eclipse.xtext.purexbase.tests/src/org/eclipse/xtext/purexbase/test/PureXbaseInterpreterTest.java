/*******************************************************************************
 * Copyright (c) 2019 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.purexbase.test;

import org.eclipse.xtext.purexbase.pureXbase.Model;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.util.IResourceScopeCache;
import org.eclipse.xtext.xbase.XExpression;
import org.eclipse.xtext.xbase.interpreter.IEvaluationResult;
import org.eclipse.xtext.xbase.interpreter.IExpressionInterpreter;
import org.eclipse.xtext.xbase.junit.evaluation.AbstractXbaseEvaluationTest;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * @author Sebastian Zarnekow - Initial contribution and API
 * @author Eva Poell - Tests for Try with Resources and ternary if
 */
@RunWith(XtextRunner.class)
@InjectWith(RuntimeInjectorProvider.class)
public class PureXbaseInterpreterTest extends AbstractXbaseEvaluationTest {

	@Inject
	private ParseHelper<Model> parseHelper;
	@Inject
	private ValidationTestHelper validationHelper;
	@Inject
	private IExpressionInterpreter interpreter;
	@Inject
	private IResourceScopeCache cache;

	@After
	public void tearDown() throws Exception {
		interpreter = null;
		parseHelper = null;
	}

	@Test
	public void testInvokeProtectedMethod() throws Exception {
		// assuming a context where protected members are accessible, e.g. an
		// interpreted operation
		// in the very same class
		assertEvaluatesTo("", "{ val x = new testdata.VisibilitySuperType() x.protectedProperty }", false);
	}

	@Override
	public void assertEvaluatesTo(Object expectation, String model) {
		assertEvaluatesTo(expectation, model, true);
	}

	@Override
	public void assertEvaluatesToArray(Object[] expectation, String model) {
		assertEvaluatesTo(expectation, model, true);
	}

	public void assertEvaluatesTo(Object expectation, String model, boolean validate) {
		XExpression expression = null;
		try {
			expression = expression(model, validate);
			IEvaluationResult result = interpreter.evaluate(expression);
			assertNull("Expected no exception. Model was: " + model + ", Exception was: " + result.getException(),
					result.getException());
			if (expectation != null && expectation.getClass().isArray())
				assertArrayEquals("Model was: " + model, (Object[]) expectation, (Object[]) result.getResult());
			else
				assertEquals("Model was: " + model, expectation, result.getResult());
		} catch (Exception e) {
			if (e instanceof RuntimeException)
				throw (RuntimeException) e;
			throw new RuntimeException(e);
		} finally {
			if (expression != null) {
				cache.clear(expression.eResource());
			}
		}
	}

	@Override
	public void assertEvaluatesWithException(Class<? extends Throwable> expectatedException, String model) {
		XExpression expression = null;
		try {
			expression = expression(model, true);
			IEvaluationResult result = interpreter.evaluate(expression);
			assertTrue("Expected " + expectatedException.getSimpleName() + " but got: " + result.getException(),
					expectatedException.isInstance(result.getException()));
		} catch (Exception e) {
			if (e instanceof RuntimeException)
				throw (RuntimeException) e;
			throw new RuntimeException(e);
		} finally {
			if (expression != null) {
				cache.clear(expression.eResource());
			}
		}
	}

	protected XExpression expression(String string, boolean resolve) throws Exception {
		Model model = parseHelper.parse(string);
		if (resolve) {
			validationHelper.assertNoErrors(model);
		}
		return model.getBlock();
	}

	@Test
	public void testTryWithoutResources() {
		assertEvaluatesTo("Test",
				"try {val a = new java.io.StringReader(\"Test\"); val b = com.google.common.io.CharStreams.toString(a);"
						+ " return b;" + "}  catch (java.io.IOException e) { throw e }");
	}

	@Test
	public void testTryWithResources_easy() {
		assertEvaluatesTo("Test", "try (val a = new java.io.StringReader(\"Test\")) { "
				+ "com.google.common.io.CharStreams.toString(a)" + "} catch (java.io.IOException e) { throw e }");
	}

	@Test
	public void testTryWithResources_lambda() {
		String result = "Proxy for org.eclipse.xtext.xbase.lib.Functions$Function0: [ {\n"
				+ "  <XMemberFeatureCallImplCustom>.println(<XStringLiteralImpl>)\n" + "} ]";
		assertEvaluatesTo(result,
				"try (val someCloseable = [ System.out.println(\"Closing\") ]) {return someCloseable.toString}"
						+ " catch (java.io.IOException e) { throw e }");
	}

	@Test
	public void testTryWithResources_2Resources() {
		assertEvaluatesTo("Test",
				"try (val sr = new java.io.StringReader(\"Test\");" + " val buffy = new java.io.BufferedReader(sr)) {"
						+ " return buffy.readLine" + "} catch (java.io.IOException e) { throw e }");
	}

	@Test
	public void testTryWithResources_2NestedResources() {
		assertEvaluatesTo("Test", "try (val buffy = new java.io.BufferedReader(new java.io.StringReader(\"Test\"))) {"
				+ " return buffy.readLine }" + " catch (java.io.IOException e) { throw e }");
	}

	@Test
	public void testTryWithResources_if1() {
		assertEvaluatesTo("Test1", "try (val a = new java.io.StringReader(if (true) \"Test1\" else \"Test2\")) {"
				+ " com.google.common.io.CharStreams.toString(a)" + "} catch (java.io.IOException e) { throw e }");
	}

	@Test
	public void testTryWithResources_if2() {
		assertEvaluatesTo("Test1",
				"try (val a = if (true) new java.io.StringReader(\"Test1\") else new java.io.StringReader(\"Test2\")) {"
						+ " com.google.common.io.CharStreams.toString(a)"
						+ "} catch (java.io.IOException e) { throw e }");
	}
	// ------------------------------------------------------------------------------------------------------------------

	@Test
	public void testTernaryIf_1() {
		assertEvaluatesTo(Integer.valueOf(20),
				"{ var number = (false)? new Double('-10') : new Integer('20') number.intValue }");
	}

	@Test
	public void testTernaryIf_2() {
		assertEvaluatesTo(Integer.valueOf(10),
				"{ var number = (true)? new Integer('10') : new Integer('20') number.intValue }");
	}

	@Test
	public void testTernaryIf_3() {
		assertEvaluatesTo(Integer.valueOf(3),
				"{ var number = (true)? (true)? new Integer('3') : new Integer('4') : new Integer('5') number.intValue }");
	}

	@Test
	public void testTernaryIf_4() {
		assertEvaluatesTo(Integer.valueOf(4),
				"{ var number = (true)? (!true)? new Integer('3') : new Integer('4') : new Integer('5') number.intValue }");
	}

	@Test
	public void testTernaryIf_5() {
		assertEvaluatesTo(Integer.valueOf(5),
				"{ var number = (!true)? (true)? new Integer('3') : new Integer('4') : new Integer('5') number.intValue }");
	}

	@Test
	public void testTernaryIf_6() {
		assertEvaluatesTo(Integer.valueOf(6),
				"{ var number = (!true)? new Integer('3') : (true)? new Integer('6') : new Integer('7') number.intValue }");
	}

	@Test
	public void testTernaryIf_7() {
		assertEvaluatesTo(Integer.valueOf(7),
				"{ var number = if (true) {(!true)? new Integer('3') : new Integer('7')} else new Integer('5') number.intValue }");
	}

	// ------------------------------------------------------------------------------------------------------------------
	// Copied from
	// /org.eclipse.xtext.xbase.tests/src/org/eclipse/xtext/xbase/tests/interpreter/XbaseInterpreterTest.java
	@Override
	@Ignore
	@Test
	public void testClosure_31() throws Exception {
		super.testClosure_31();
	}

	@Override
	@Ignore
	@Test
	public void testClosure_32() throws Exception {
		super.testClosure_32();
	}

	@Override
	@Ignore
	@Test
	public void testArrays_01() throws Exception {
		super.testArrays_01();
	}

	@Override
	@Ignore
	@Test
	public void testArrays_02() throws Exception {
		super.testArrays_02();
	}

	@Override
	@Ignore
	@Test
	public void testArrays_04() throws Exception {
		super.testArrays_01();
	}
}
