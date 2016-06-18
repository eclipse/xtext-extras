/**
 * Copyright (c) 2012 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtext.xbase.tests.typesystem;

import java.util.List;
import org.eclipse.xtext.xbase.tests.typesystem.AbstractClosureTypeTest2;
import org.junit.Test;

/**
 * @author Sebastian Zarnekow - Initial contribution and API
 */
@SuppressWarnings("all")
public class ClosureTypeTest1 extends AbstractClosureTypeTest2 {
  @Test
  public void testScenario_1_1_a_01() throws Exception {
    List<Object> _resolvesClosuresTo = this.resolvesClosuresTo("{\n\t\t\tval iter = null as Iterable<StringBuffer>\n\t\t\tval testData = null as ClosureTypeResolutionTestData1\n\t\t\tval Iterable<String> res = testData.method_1_1_a(iter) []\n\t\t}", "(List<StringBuffer>)=>List<String>");
    this.withEquivalents(_resolvesClosuresTo, "ListFunction1<StringBuffer, String>");
  }
  
  @Test
  public void testScenario_1_1_a_02() throws Exception {
    List<Object> _resolvesClosuresTo = this.resolvesClosuresTo("{\n\t\t\tval iter = null as Iterable<StringBuffer>\n\t\t\tval testData = null as ClosureTypeResolutionTestData1\n\t\t\tval Iterable<? extends String> res = testData.method_1_1_a(iter) []\n\t\t}", "(List<StringBuffer>)=>List<String>");
    this.withEquivalents(_resolvesClosuresTo, "ListFunction1<StringBuffer, String>");
  }
  
  @Test
  public void testScenario_1_1_a_03() throws Exception {
    List<Object> _resolvesClosuresTo = this.resolvesClosuresTo("{\n\t\t\tval iter = null as Iterable<StringBuffer>\n\t\t\tval testData = null as ClosureTypeResolutionTestData1\n\t\t\tval Iterable<? super String> res = testData.method_1_1_a(iter) []\n\t\t}", "(List<StringBuffer>)=>List<String>");
    this.withEquivalents(_resolvesClosuresTo, "ListFunction1<StringBuffer, String>");
  }
  
  @Test
  public void testScenario_1_1_a_04() throws Exception {
    List<Object> _resolvesClosuresTo = this.resolvesClosuresTo("{\n\t\t\tval iter = null as Iterable<StringBuffer>\n\t\t\tval testData = null as ClosureTypeResolutionTestData1 \n\t\t\ttestData.method_1_1_a(iter) []\n\t\t}", "(List<StringBuffer>)=>List<Object>");
    this.withEquivalents(_resolvesClosuresTo, "ListFunction1<StringBuffer, Object>");
  }
  
  @Test
  public void testScenario_1_1_a_05() throws Exception {
    List<Object> _resolvesClosuresTo = this.resolvesClosuresTo("{\n\t\t\tval testData = null as ClosureTypeResolutionTestData1 \n\t\t\ttestData.<StringBuffer, String>method_1_1_a(null) []\n\t\t}", "(List<StringBuffer>)=>List<String>");
    this.withEquivalents(_resolvesClosuresTo, "ListFunction1<StringBuffer, String>");
  }
  
  @Test
  public void testScenario_1_1_a_06() throws Exception {
    List<Object> _resolvesClosuresTo = this.resolvesClosuresTo("{\n\t\t\tval iter = null as Iterable<? extends StringBuffer>\n\t\t\tval testData = null as ClosureTypeResolutionTestData1\n\t\t\tval Iterable<String> res = testData.method_1_1_a(iter) []\n\t\t}", "(List<StringBuffer>)=>List<String>");
    this.withEquivalents(_resolvesClosuresTo, "ListFunction1<StringBuffer, String>");
  }
  
  @Test
  public void testScenario_1_1_a_07() throws Exception {
    List<Object> _resolvesClosuresTo = this.resolvesClosuresTo("{\n\t\t\tval iter = null as Iterable<? extends StringBuffer>\n\t\t\tval testData = null as ClosureTypeResolutionTestData1\n\t\t\tval Iterable<? extends String> res = testData.method_1_1_a(iter) []\n\t\t}", "(List<StringBuffer>)=>List<String>");
    this.withEquivalents(_resolvesClosuresTo, "ListFunction1<StringBuffer, String>");
  }
  
  @Test
  public void testScenario_1_1_a_08() throws Exception {
    List<Object> _resolvesClosuresTo = this.resolvesClosuresTo("{\n\t\t\tval iter = null as Iterable<? extends StringBuffer>\n\t\t\tval testData = null as ClosureTypeResolutionTestData1\n\t\t\tval Iterable<? super String> res = testData.method_1_1_a(iter) []\n\t\t}", "(List<StringBuffer>)=>List<String>");
    this.withEquivalents(_resolvesClosuresTo, "ListFunction1<StringBuffer, String>");
  }
  
  @Test
  public void testScenario_1_1_a_09() throws Exception {
    List<Object> _resolvesClosuresTo = this.resolvesClosuresTo("{\n\t\t\tval iter = null as Iterable<? extends StringBuffer>\n\t\t\tval testData = null as ClosureTypeResolutionTestData1 \n\t\t\ttestData.method_1_1_a(iter) []\n\t\t}", "(List<StringBuffer>)=>List<Object>");
    this.withEquivalents(_resolvesClosuresTo, "ListFunction1<StringBuffer, Object>");
  }
  
  @Test
  public void testScenario_1_1_a_10() throws Exception {
    List<Object> _resolvesClosuresTo = this.resolvesClosuresTo("{\n\t\t\tval iter = null as Iterable<? super StringBuffer>\n\t\t\tval testData = null as ClosureTypeResolutionTestData1\n\t\t\tval Iterable<String> res = testData.method_1_1_a(iter) []\n\t\t}", "(List<Object>)=>List<String>");
    this.withEquivalents(_resolvesClosuresTo, "ListFunction1<Object, String>");
  }
  
  @Test
  public void testScenario_1_1_a_11() throws Exception {
    List<Object> _resolvesClosuresTo = this.resolvesClosuresTo("{\n\t\t\tval iter = null as Iterable<? super StringBuffer>\n\t\t\tval testData = null as ClosureTypeResolutionTestData1\n\t\t\tval Iterable<? extends String> res = testData.method_1_1_a(iter) []\n\t\t}", "(List<Object>)=>List<String>");
    this.withEquivalents(_resolvesClosuresTo, "ListFunction1<Object, String>");
  }
  
  @Test
  public void testScenario_1_1_a_12() throws Exception {
    List<Object> _resolvesClosuresTo = this.resolvesClosuresTo("{\n\t\t\tval iter = null as Iterable<? super StringBuffer>\n\t\t\tval testData = null as ClosureTypeResolutionTestData1\n\t\t\tval Iterable<? super String> res = testData.method_1_1_a(iter) []\n\t\t}", "(List<Object>)=>List<String>");
    this.withEquivalents(_resolvesClosuresTo, "ListFunction1<Object, String>");
  }
  
  @Test
  public void testScenario_1_1_a_13() throws Exception {
    List<Object> _resolvesClosuresTo = this.resolvesClosuresTo("{\n\t\t\tval iter = null as Iterable<? super StringBuffer>\n\t\t\tval testData = null as ClosureTypeResolutionTestData1 \n\t\t\ttestData.method_1_1_a(iter) []\n\t\t}", "(List<Object>)=>List<Object>");
    this.withEquivalents(_resolvesClosuresTo, "ListFunction1<Object, Object>");
  }
  
  @Test
  public void testScenario_1_1_b_01() throws Exception {
    List<Object> _resolvesClosuresTo = this.resolvesClosuresTo("{\n\t\t\tval iter = null as Iterable<StringBuffer>\n\t\t\tval testData = null as ClosureTypeResolutionTestData1 \n\t\t\tval Iterable<String> res = testData.method_1_1_b(iter) []\n\t\t}", "(List<StringBuffer>)=>List<? extends String>");
    this.withEquivalents(_resolvesClosuresTo, "ListFunction1<StringBuffer, ? extends String>");
  }
  
  @Test
  public void testScenario_1_1_b_02() throws Exception {
    List<Object> _resolvesClosuresTo = this.resolvesClosuresTo("{\n\t\t\tval iter = null as Iterable<StringBuffer>\n\t\t\tval testData = null as ClosureTypeResolutionTestData1 \n\t\t\tval Iterable<? extends String> res = testData.method_1_1_b(iter) []\n\t\t}", "(List<StringBuffer>)=>List<String>");
    this.withEquivalents(_resolvesClosuresTo, "ListFunction1<StringBuffer, String>");
  }
  
  @Test
  public void testScenario_1_1_b_03() throws Exception {
    List<Object> _resolvesClosuresTo = this.resolvesClosuresTo("{\n\t\t\tval iter = null as Iterable<StringBuffer>\n\t\t\tval testData = null as ClosureTypeResolutionTestData1 \n\t\t\tval Iterable<? super String> res = testData.method_1_1_b(iter) []\n\t\t}", "(List<StringBuffer>)=>List<String>");
    this.withEquivalents(_resolvesClosuresTo, "ListFunction1<StringBuffer, String>");
  }
  
  @Test
  public void testScenario_1_1_b_04() throws Exception {
    List<Object> _resolvesClosuresTo = this.resolvesClosuresTo("{\n\t\t\tval iter = null as Iterable<StringBuffer>\n\t\t\tval testData = null as ClosureTypeResolutionTestData1 \n\t\t\ttestData.method_1_1_b(iter) []\n\t\t}", "(List<StringBuffer>)=>List<Object>");
    this.withEquivalents(_resolvesClosuresTo, "ListFunction1<StringBuffer, Object>");
  }
  
  @Test
  public void testScenario_1_1_b_05() throws Exception {
    List<Object> _resolvesClosuresTo = this.resolvesClosuresTo("{\n\t\t\tval testData = null as ClosureTypeResolutionTestData1 \n\t\t\ttestData.<StringBuffer, String>method_1_1_b(null) []\n\t\t}", "(List<StringBuffer>)=>List<String>");
    this.withEquivalents(_resolvesClosuresTo, "ListFunction1<StringBuffer, String>");
  }
  
  @Test
  public void testScenario_1_1_c_01() throws Exception {
    List<Object> _resolvesClosuresTo = this.resolvesClosuresTo("{\n\t\t\tval iter = null as Iterable<StringBuffer>\n\t\t\tval testData = null as ClosureTypeResolutionTestData1 \n\t\t\t// invalid but should infer String since Object would be invalid, too\n\t\t\tval Iterable<String> res = testData.method_1_1_c(iter) []\n\t\t}", "(List<StringBuffer>)=>List<String>");
    this.withEquivalents(_resolvesClosuresTo, "ListFunction1<StringBuffer, String>");
  }
  
  @Test
  public void testScenario_1_1_c_02() throws Exception {
    List<Object> _resolvesClosuresTo = this.resolvesClosuresTo("{\n\t\t\tval iter = null as Iterable<StringBuffer>\n\t\t\tval testData = null as ClosureTypeResolutionTestData1 \n\t\t\tval Iterable<? super String> res = testData.method_1_1_c(iter) []\n\t\t}", "(List<StringBuffer>)=>List<String>");
    this.withEquivalents(_resolvesClosuresTo, "ListFunction1<StringBuffer, String>");
  }
  
  @Test
  public void testScenario_1_1_c_03() throws Exception {
    List<Object> _resolvesClosuresTo = this.resolvesClosuresTo("{\n\t\t\tval iter = null as Iterable<StringBuffer>\n\t\t\tval testData = null as ClosureTypeResolutionTestData1 \n\t\t\ttestData.method_1_1_c(iter) []\n\t\t}", "(List<StringBuffer>)=>List<Object>");
    this.withEquivalents(_resolvesClosuresTo, "ListFunction1<StringBuffer, Object>");
  }
  
  @Test
  public void testScenario_1_1_c_04() throws Exception {
    List<Object> _resolvesClosuresTo = this.resolvesClosuresTo("{\n\t\t\tval testData = null as ClosureTypeResolutionTestData1 \n\t\t\ttestData.<StringBuffer, String>method_1_1_c(null) []\n\t\t}", "(List<StringBuffer>)=>List<String>");
    this.withEquivalents(_resolvesClosuresTo, "ListFunction1<StringBuffer, String>");
  }
}
