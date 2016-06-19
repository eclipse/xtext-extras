/**
 * Copyright (c) 2012 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtext.xbase.tests.typesystem;

import com.google.common.collect.Iterators;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.common.types.JvmGenericType;
import org.eclipse.xtext.common.types.JvmIdentifiableElement;
import org.eclipse.xtext.common.types.JvmTypeParameter;
import org.eclipse.xtext.common.types.JvmTypeParameterDeclarator;
import org.eclipse.xtext.common.types.JvmTypeReference;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.xbase.XAbstractFeatureCall;
import org.eclipse.xtext.xbase.XConstructorCall;
import org.eclipse.xtext.xbase.XExpression;
import org.eclipse.xtext.xbase.XbasePackage;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.eclipse.xtext.xbase.tests.AbstractXbaseTestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Sebastian Zarnekow - Initial contribution and API
 */
@SuppressWarnings("all")
public abstract class AbstractTypeArgumentTest extends AbstractXbaseTestCase {
  public Iterator<XExpression> bindTypeArgumentsTo(final String expression, final String... typeArguments) {
    final List<XExpression> expressions = this.findExpressionWithTypeArguments(expression);
    Iterator<XExpression> _iterator = expressions.iterator();
    return this.and(_iterator, typeArguments);
  }
  
  public Iterator<XExpression> and(final Iterator<XExpression> expressions, final String... typeArguments) {
    boolean _hasNext = expressions.hasNext();
    Assert.assertTrue(_hasNext);
    final XExpression expression = expressions.next();
    this.hasTypeArguments(expression, typeArguments);
    return expressions;
  }
  
  public void done(final Iterator<XExpression> exhaustedIterator) {
    Assert.assertFalse(this.doneCalled);
    boolean _hasNext = exhaustedIterator.hasNext();
    Assert.assertFalse(_hasNext);
    this.doneCalled = true;
  }
  
  protected abstract void hasTypeArguments(final XExpression expression, final String... typeArguments);
  
  private boolean doneCalled = false;
  
  @Before
  public void resetDoneState() {
    this.doneCalled = false;
  }
  
  @After
  public void checkDoneState() {
  }
  
  private static Set<String> seenExpressions;
  
  @BeforeClass
  public static void createSeenExpressionsSet() {
    HashSet<String> _newHashSet = CollectionLiterals.<String>newHashSet();
    AbstractTypeArgumentTest.seenExpressions = _newHashSet;
  }
  
  @AfterClass
  public static void discardSeenExpressions() {
    AbstractTypeArgumentTest.seenExpressions = null;
  }
  
  @Override
  protected XExpression expression(final CharSequence expression, final boolean resolve) throws Exception {
    XExpression _xblockexpression = null;
    {
      String _string = expression.toString();
      final String string = _string.replace("$$", "org::eclipse::xtext::xbase::lib::");
      boolean _add = AbstractTypeArgumentTest.seenExpressions.add(string);
      boolean _not = (!_add);
      if (_not) {
        Assert.fail(("Duplicate expression under test: " + expression));
      }
      _xblockexpression = super.expression(string, resolve);
    }
    return _xblockexpression;
  }
  
  protected abstract void resolveTypes(final XExpression expression);
  
  protected List<XExpression> findExpressionWithTypeArguments(final CharSequence expression) {
    try {
      final XExpression xExpression = this.expression(expression, false);
      this.resolveTypes(xExpression);
      TreeIterator<EObject> _eAll = EcoreUtil2.eAll(xExpression);
      Iterator<XExpression> _filter = Iterators.<XExpression>filter(_eAll, XExpression.class);
      final Function1<XExpression, Boolean> _function = (XExpression it) -> {
        boolean _switchResult = false;
        boolean _matched = false;
        if (it instanceof XAbstractFeatureCall) {
          _matched=true;
          boolean _and = false;
          if (!((!((XAbstractFeatureCall)it).isTypeLiteral()) && (!((XAbstractFeatureCall)it).isPackageFragment()))) {
            _and = false;
          } else {
            boolean _or = false;
            EList<JvmTypeReference> _typeArguments = ((XAbstractFeatureCall)it).getTypeArguments();
            boolean _isEmpty = _typeArguments.isEmpty();
            boolean _not = (!_isEmpty);
            if (_not) {
              _or = true;
            } else {
              boolean _switchResult_1 = false;
              JvmIdentifiableElement _feature = ((XAbstractFeatureCall)it).getFeature();
              final JvmIdentifiableElement feature = _feature;
              boolean _matched_1 = false;
              if (feature instanceof JvmTypeParameterDeclarator) {
                _matched_1=true;
                EList<JvmTypeParameter> _typeParameters = ((JvmTypeParameterDeclarator)feature).getTypeParameters();
                boolean _isEmpty_1 = _typeParameters.isEmpty();
                _switchResult_1 = (!_isEmpty_1);
              }
              if (!_matched_1) {
                _switchResult_1 = false;
              }
              _or = _switchResult_1;
            }
            _and = _or;
          }
          _switchResult = _and;
        }
        if (!_matched) {
          if (it instanceof XConstructorCall) {
            _matched=true;
            _switchResult = ((!((XConstructorCall)it).getTypeArguments().isEmpty()) || 
              (!((JvmGenericType) ((XConstructorCall)it).getConstructor().getDeclaringType()).getTypeParameters().isEmpty()));
          }
        }
        if (!_matched) {
          _switchResult = false;
        }
        return Boolean.valueOf(_switchResult);
      };
      Iterator<XExpression> _filter_1 = IteratorExtensions.<XExpression>filter(_filter, _function);
      final List<XExpression> result = IteratorExtensions.<XExpression>toList(_filter_1);
      final Function1<XExpression, Integer> _function_1 = (XExpression it) -> {
        EReference _switchResult = null;
        boolean _matched = false;
        if (it instanceof XAbstractFeatureCall) {
          _matched=true;
          _switchResult = XbasePackage.Literals.XABSTRACT_FEATURE_CALL__FEATURE;
        }
        if (!_matched) {
          if (it instanceof XConstructorCall) {
            _matched=true;
            _switchResult = XbasePackage.Literals.XCONSTRUCTOR_CALL__CONSTRUCTOR;
          }
        }
        final EReference structuralFeature = _switchResult;
        List<INode> _findNodesForFeature = NodeModelUtils.findNodesForFeature(it, structuralFeature);
        INode _head = IterableExtensions.<INode>head(_findNodesForFeature);
        return Integer.valueOf(_head.getOffset());
      };
      return IterableExtensions.<XExpression, Integer>sortBy(result, _function_1);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testBug461923_01() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val Iterable<String> it = null com.google.common.collect.ImmutableList.builder.addAll(it).build }", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testBug461923_02() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val Iterable<? extends String> it = null com.google.common.collect.ImmutableList.builder.addAll(it).build }", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testBug461923_03() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val Iterable<? super String> it = null com.google.common.collect.ImmutableList.builder.addAll(it).build }", "Object");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testBug461923_04() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val Iterable<String> it = null com.google.common.collect.ImmutableList.builder.addAll(it.map[it]).build }", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "String");
    this.done(_and);
  }
  
  @Test
  public void testBug461923_05() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val Iterable<? extends String> it = null com.google.common.collect.ImmutableList.builder.addAll(it.map[it]).build }", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "? extends String", "String");
    this.done(_and);
  }
  
  @Test
  public void testBug461923_06() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val Iterable<? super String> it = null com.google.common.collect.ImmutableList.builder.addAll(it.map[it]).build }", "Object");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "? super String", "Object");
    this.done(_and);
  }
  
  @Test
  public void testBug461923_07() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val Iterable<String> it = null com.google.common.collect.ImmutableList.builder.addAll(it.filter[true]).build }", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testBug461923_08() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val Iterable<? extends String> it = null com.google.common.collect.ImmutableList.builder.addAll(it.filter[true]).build }", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "? extends String");
    this.done(_and);
  }
  
  @Test
  public void testBug461923_09() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val Iterable<? super String> it = null com.google.common.collect.ImmutableList.builder.addAll(it.filter[true]).build }", "Object");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "? super String");
    this.done(_and);
  }
  
  @Test
  public void testBug461923_10() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val Iterable<String> it = null com.google.common.collect.ImmutableList.builder.addAll(it.filter[true]).addAll(it.filter[true]).build }", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testBug461923_11() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val Iterable<? extends String> it = null com.google.common.collect.ImmutableList.builder.addAll(it.filter[true]).addAll(it.filter[true]).build }", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "? extends String");
    Iterator<XExpression> _and_1 = this.and(_and, "? extends String");
    this.done(_and_1);
  }
  
  @Test
  public void testBug461923_12() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val Iterable<? super String> it = null com.google.common.collect.ImmutableList.builder.addAll(it.filter[true]).addAll(it.filter[true]).build }", "Object");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "? super String");
    Iterator<XExpression> _and_1 = this.and(_and, "? super String");
    this.done(_and_1);
  }
  
  @Test
  public void testBug461923_13() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val java.util.Set<String> it = null new java.util.ArrayList().addAll(it) }", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testBug461923_14() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val java.util.Set<? extends String> it = null new java.util.ArrayList().addAll(it) }", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testBug461923_15() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val java.util.Set<? super String> it = null new java.util.ArrayList().addAll(it) }", "Object");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testBug461923_16() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val java.util.List<String> it = null new java.util.ArrayList().addAll(it.subList(1,1)) }", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testBug461923_17() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val java.util.List<? extends String> it = null new java.util.ArrayList().addAll(it.subList(1,1)) }", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testBug461923_18() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val java.util.List<? super String> it = null new java.util.ArrayList().addAll(it.subList(1,1)) }", "Object");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testRawType_01() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val java.util.Set set = newHashSet() set }", "");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testRawType_02() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val java.util.Set set = newHashSet set.head }", "");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "");
    this.done(_and);
  }
  
  @Test
  public void testRawType_03() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("(null as java.util.Set<java.util.Set>).head", "Set");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testRawType_04() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val java.util.Set<java.util.Set> set = newHashSet set.head }", "Set");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Set");
    this.done(_and);
  }
  
  @Test
  public void testRawType_05() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val java.util.Set<java.util.Set> set = newHashSet(newHashSet) set.head }", "Set");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "");
    Iterator<XExpression> _and_1 = this.and(_and, "Set");
    this.done(_and_1);
  }
  
  @Test
  public void testNumberLiteralInClosure_01() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList().map[42]", "Object");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Object", "Integer");
    this.done(_and);
  }
  
  @Test
  public void testOverloadedTypeParameters_01() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("testdata::OverloadedMethods::<String>overloadedTypeParameters(null)", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testOverloadedTypeParameters_02() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("testdata::OverloadedMethods::<String, String>overloadedTypeParameters(null)", "String", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testOverloadedMethods_07() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tvar java.util.List<CharSequence> list = null\n\t\t\tvar Object o = null\n\t\t\torg::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::overloadedWithTypeParam(list, o)\n\t\t}", "CharSequence");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testOverloadedMethods_08() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tvar java.util.List<CharSequence> list = null\n\t\t\tvar String s = null\n\t\t\torg::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::overloadedWithTypeParam(list, s)\n\t\t}", "CharSequence");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testOverloadedMethods_09() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tvar java.util.List<? extends CharSequence> list = null\n\t\t\tvar Object o = null\n\t\t\torg::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::overloadedWithTypeParam(list, o)\n\t\t}", "CharSequence");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testOverloadedMethods_10() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tvar java.util.List<? extends CharSequence> list = null\n\t\t\tvar String s = null\n\t\t\torg::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::overloadedWithTypeParam(list, s)\n\t\t}", "? extends CharSequence");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testOverloadedMethods_11() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tvar java.util.List<CharSequence> list = null\n\t\t\torg::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::overloadedWithTypeParam(list, null)\n\t\t}", "CharSequence");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testOverloadedMethods_12() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tvar java.util.List<? extends CharSequence> list = null\n\t\t\torg::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::overloadedWithTypeParam(list, null)\n\t\t}", "? extends CharSequence");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testOverloadedOperators_07() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("(0..Math::sqrt(1l).intValue).filter[ i | 1l % i == 0 ].isEmpty", "Integer");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testOverloadedOperators_08() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("(1..2).map[ toString ].reduce[ i1, i2| i1 + i2 ]", "Integer", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testOverloadedOperators_09() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("(1..2).map[ toString.length ].reduce[ i1, i2| i1 + i2 ]", "Integer", "Integer");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Integer");
    this.done(_and);
  }
  
  @Test
  public void testOverloadedOperators_10() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("(1..2).map[ new java.math.BigInteger(toString) ].reduce[ i1, i2| i1 + i2 ]", "Integer", "BigInteger");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "BigInteger");
    this.done(_and);
  }
  
  @Test
  public void testOverloadedOperators_15() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("(1..2).map[ new java.math.BigInteger(toString) ].map[ i | i.toString + i ]", "Integer", "BigInteger");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "BigInteger", "String");
    this.done(_and);
  }
  
  @Test
  public void testOverloadedOperators_16() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("(1..2).map[ new java.math.BigInteger(toString) ].map[ i | i + String::valueOf(i) ]", "Integer", "BigInteger");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "BigInteger", "String");
    this.done(_and);
  }
  
  @Test
  public void testOverloadedOperators_17() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("(0..Math::sqrt(1l).intValue).filter[ i | 1l % i == 0 ].empty", "Integer");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testOverloadedOperators_20() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("(null as Iterable<StringBuilder>) + (null as Iterable<StringBuffer>) + (null as Iterable<String>)", "AbstractStringBuilder & Serializable");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Serializable & CharSequence");
    this.done(_and);
  }
  
  @Test
  public void testForExpression_01() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("for(String x : new java.util.ArrayList<String>()) x.length", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testForExpression_02() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("for(String x : newArrayList(\'foo\')) x.length", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testForExpression_03() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("for(String x : <String>newArrayList()) x.length", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testForExpression_04() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("for(x : new java.util.ArrayList<String>()) x.length", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testForExpression_05() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("for(x : <String>newArrayList()) x.length", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testForExpression_06() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("for(x : newArrayList(\'foo\')) x.length", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testImplicitImportPrintln_01() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("println(null)", "Object");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testImplicitImportPrintln_02() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("<String>println(null)", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testImplicitImportEmptyList_01() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("<String>emptyList", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testImplicitImportEmptyList_02() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("emptyList", "Object");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testMethodTypeParamInference_00() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new java.util.ArrayList<String>().findFirst(e | true)", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testMethodTypeParamInference_01() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new java.util.ArrayList<String>().findFirst(e|e == \'foo\')", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testMethodTypeParamInference_02() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new java.util.ArrayList<String>().<String>findFirst(e|e == \'foo\')", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testMethodTypeParamInference_03() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("$$IterableExtensions::findFirst(new java.util.ArrayList<String>) [e | true]", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testMethodTypeParamInference_04() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("$$IterableExtensions::findFirst(new java.util.ArrayList<String>) [e|e == \'foo\']", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testMethodTypeParamInference_05() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("$$IterableExtensions::<String>findFirst(new java.util.ArrayList<String>) [e|e == \'foo\']", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testMethodTypeParamInference_06() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("(null as Iterable<? extends String>).findFirst(e | true)", "? extends String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testMethodTypeParamInference_07() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("(null as Iterable<? extends String>).map[e | true]", "? extends String", "Boolean");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testMethodTypeParamInference_08() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("(null as Iterable<? super String>).findFirst(e | true)", "? super String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testMethodTypeParamInference_09() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("(null as Iterable<? super String>).map[e | true]", "? super String", "Boolean");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testMethodTypeParamInference_10() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("(null as Iterable<? extends String>).findFirst(null)", "? extends String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testMethodTypeParamInference_11() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("(null as Iterable<? extends String>).map(null)", "? extends String", "Object");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testMethodTypeParamInference_12() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("(null as Iterable<? super String>).findFirst(null)", "? super String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testMethodTypeParamInference_13() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("(null as Iterable<? super String>).map(null)", "? super String", "Object");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testMethodTypeParamInference_14() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("(null as java.util.Collection<? super String>).addAll(null as Iterable<? extends String>)", "? super String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testMethodTypeParamInference_15() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("(null as java.util.Collection<? super String>).addAll(null as Iterable<String>)", "? super String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testMethodTypeParamInference_16() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("(null as java.util.Collection<String>).addAll(null as Iterable<? extends String>)", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testMethodTypeParamInference_17() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("(null as java.util.Collection<String>).addAll(null as Iterable<String>)", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testMethodTypeParamInference_18() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("testdata::OverloadedMethods::addAllSuperExtends(null as java.util.List<CharSequence>, null as java.util.List<String>)", "CharSequence");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testMethodTypeParamInference_19() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval Iterable<String> expectation = testdata::OverloadedMethods::addAllSuperExtends2(null as java.util.List<CharSequence>, null as java.util.List<String>)\n\t\t}", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testMethodTypeParamInference_20() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval Iterable<CharSequence> expectation = testdata::OverloadedMethods::addAllSuperExtends2(null as java.util.List<CharSequence>, null as java.util.List<String>)\n\t\t}", "CharSequence");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testMethodTypeParamInference_21() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("testdata::OverloadedMethods::<CharSequence>addAllSuperExtends(null as java.util.List<CharSequence>, null as java.util.List<String>)", "CharSequence");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testMethodTypeParamInference_22() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("testdata::OverloadedMethods::<String>addAllSuperExtends(null as java.util.List<CharSequence>, null as java.util.List<String>)", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Ignore("TODO subsequent usages of local vars should contribute to the expectation")
  @Test
  public void testMethodTypeParamInference_23() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval actual = testdata::OverloadedMethods::addAllSuperExtends2(null as java.util.List<CharSequence>, null as java.util.List<String>)\n\t\t\tval Iterable<String> expectation = actual\n\t\t}", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testMethodTypeParamInference_24() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval actual = testdata::OverloadedMethods::addAllSuperExtends2(null as java.util.List<CharSequence>, null as java.util.List<String>)\n\t\t\tval Iterable<CharSequence> expectation = actual\n\t\t}", "CharSequence");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testMethodTypeParamInference_25() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval Iterable<CharSequence> expectation = testdata::OverloadedMethods::addAllSuperExtends2(null as java.util.List<Object>, null as java.util.List<String>)\n\t\t}", "CharSequence");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Ignore("TODO")
  @Test
  public void testMethodTypeParamInference_26() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval actual = testdata::OverloadedMethods::addAllSuperExtends2(null as java.util.List<Object>, null as java.util.List<String>)\n\t\t\tval Iterable<CharSequence> expectation = actual\n\t\t}", "CharSequence");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testTypeForVoidClosure() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'foo\',\'bar\').forall []", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testClosure_01() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ var closure = [|\'literal\']\n\t\t  new testdata.ClosureClient().invoke0(closure)\t}", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testClosure_03() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo(((("{\n" + 
      "  var java.util.List<? super String> list = null;\n") + 
      "  list.map(e|e)\n") + 
      "}"), "? super String", "Object");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testClosure_04() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo(((("{\n" + 
      "  var java.util.List<? super String> list = null;\n") + 
      "  list.map(e|false)\n") + 
      "}"), "? super String", "Boolean");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testClosure_05() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo(((("{\n" + 
      "  val func = [|\'literal\']\n") + 
      "  new testdata.ClosureClient().useProvider(func)\n") + 
      "}"), "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testClosure_06() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new testdata.ClosureClient().useProvider(null as com.google.inject.Provider<? extends Iterable<String>[]>)", "? extends Iterable<String>[]");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testClosure_07() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new testdata.ClosureClient().useProvider(null as com.google.inject.Provider<? extends String>)", "? extends String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testClosure_08() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new testdata.ClosureClient().useProvider(null as =>Iterable<String>[])", "Iterable<String>[]");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testClosure_09() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new testdata.ClosureClient().useProvider(null as =>String)", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testClosure_10() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new testdata.ClosureClient().invoke0(null as =>String)", "? extends String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testClosure_13() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ \n\t\t\tval mapper = [ x | x ]\n\t\t\tnewArrayList(1).map(mapper)\n\t\t}", "Integer");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Integer", "Integer");
    this.done(_and);
  }
  
  @Test
  public void testClosure_13a() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ \n\t\t\tval mapper = [ x | x ]\n\t\t\tnewArrayList(1).map(mapper).head\n\t\t}", "Integer");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Integer", "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer");
    this.done(_and_1);
  }
  
  @Test
  public void testClosure_13b() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ \n\t\t\tval mapper = [ x | x ]\n\t\t\tnewArrayList(1).map(mapper).findFirst [ true ]\n\t\t}", "Integer");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Integer", "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer");
    this.done(_and_1);
  }
  
  @Test
  public void testClosure_16() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ \n\t\t\tval fun = [ x | x ]\n\t\t\tval java.util.List<String> list = newArrayList(fun.apply(null))\n\t\t\tfun\n\t\t}", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testClosure_17() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ \n\t\t\tval fun = [ x | x ]\n\t\t\tval java.util.List<String> list = newArrayList.map(fun)\n\t\t\tfun\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "String");
    this.done(_and);
  }
  
  @Test
  public void testClosure_18() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ \n\t\t\tval fun = [ x | x ]\n\t\t\tval java.util.Set<String> list = newArrayList.map(fun)\n\t\t\tfun\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "String");
    this.done(_and);
  }
  
  @Test
  public void testClosure_19() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ \n\t\t\tval fun = [ x | x ]\n\t\t\tval java.util.ArrayList<String> list = newArrayList.map(fun)\n\t\t\tfun\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "String");
    this.done(_and);
  }
  
  @Test
  public void testClosure_20() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ \n\t\t\tval fun = [ x | x ]\n\t\t\tval Iterable<CharSequence> list = newArrayList.map(fun)\n\t\t\tfun\n\t\t}", "CharSequence");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "CharSequence", "CharSequence");
    this.done(_and);
  }
  
  @Test
  public void testClosure_21() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ \n\t\t\tval fun = [ x | x ]\n\t\t\tval list = newArrayList.map(fun)\n\t\t\tval Iterable<String> iter = list\n\t\t\tfun\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "String");
    this.done(_and);
  }
  
  @Test
  public void testClosure_22() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ \n\t\t\tval fun = [ CharSequence x | x ]\n\t\t\tval java.util.List<String> list = $$ListExtensions::map(newArrayList, fun)\n\t\t\tfun\n\t\t}", "CharSequence", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "CharSequence");
    this.done(_and);
  }
  
  @Test
  public void testClosure_23() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ \n\t\t\tval fun = [ Object x | x.toString ]\n\t\t\tval java.util.Set<String> list = $$ListExtensions::map(newArrayList, fun)\n\t\t\tfun\n\t\t}", "Object", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Object");
    this.done(_and);
  }
  
  @Test
  public void testClosure_24() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ \n\t\t\tval fun = [ x | x ]\n\t\t\tval java.util.ArrayList<String> list = $$ListExtensions::map(newArrayList, fun)\n\t\t\tfun\n\t\t}", "String", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testClosure_25() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ \n\t\t\tval fun = [ x | x ]\n\t\t\tval Iterable<String> list = $$ListExtensions::map(newArrayList, fun)\n\t\t\tfun\n\t\t}", "String", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testClosure_26() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ \n\t\t\tval fun = [ x | x ]\n\t\t\tval list = $$ListExtensions::map(newArrayList, fun)\n\t\t\tval Iterable<String> iter = list\n\t\t\tfun\n\t\t}", "String", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testClosure_27() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ \n\t\t\tval mapper = [ x | x ]\n\t\t\t$$ListExtensions::map(newArrayList(1), mapper)\n\t\t}", "Integer", "Integer");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Integer");
    this.done(_and);
  }
  
  @Test
  public void testTypeArgs() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new java.util.ArrayList<String>() += \'foo\'", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testEMap_01() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ \n          val eMap = new org.eclipse.emf.common.util.BasicEMap<Integer, String>()\n\t\t  eMap.map[ getKey ].head\n         }", "Integer", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Entry<Integer, String>", "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer");
    this.done(_and_1);
  }
  
  @Test
  public void testEMap_02() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ \n          val eMap = new org.eclipse.emf.common.util.BasicEMap<Integer, String>()\n\t\t  eMap.map[ getValue ].head\n         }", "Integer", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Entry<Integer, String>", "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testEMap_03() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ \n          val eMap = new org.eclipse.emf.common.util.BasicEMap<Integer, String>()\n\t\t  eMap.map[ it ].head\n         }", "Integer", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Entry<Integer, String>", "Entry<Integer, String>");
    Iterator<XExpression> _and_1 = this.and(_and, "Entry<Integer, String>");
    this.done(_and_1);
  }
  
  @Test
  public void testEMap_04() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ \n          val eMap = new org.eclipse.emf.common.util.BasicEMap<Integer, String>()\n\t\t  eMap.map\n         }", "Integer", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testEMap_05() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ \n          val org.eclipse.emf.common.util.BasicEMap<? extends Integer, String> eMap = null\n\t\t  eMap.map[ key ].head\n         }", "Entry<? extends Integer, String>", "Integer");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Integer");
    this.done(_and);
  }
  
  @Test
  public void testEMap_06() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ \n          val org.eclipse.emf.common.util.BasicEMap<? extends Integer, String> eMap = null\n\t\t  eMap.map[ value ].head\n         }", "Entry<? extends Integer, String>", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testEMap_07() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ \n          val org.eclipse.emf.common.util.BasicEMap<? extends Integer, String> eMap = null\n\t\t  eMap.map[ it ].head\n         }", "Entry<? extends Integer, String>", "Entry<? extends Integer, String>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Entry<? extends Integer, String>");
    this.done(_and);
  }
  
  @Test
  public void testEMap_09() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ \n          val org.eclipse.emf.common.util.BasicEMap<? super Integer, String> eMap = null\n\t\t  eMap.map[ key ].head\n         }", "Entry<? super Integer, String>", "Object");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Object");
    this.done(_and);
  }
  
  @Test
  public void testEMap_10() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ \n          val org.eclipse.emf.common.util.BasicEMap<? super Integer, String> eMap = null\n\t\t  eMap.map[ value ].head\n         }", "Entry<? super Integer, String>", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testEMap_11() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ \n          val org.eclipse.emf.common.util.BasicEMap<? super Integer, String> eMap = null\n\t\t  eMap.map[ it ].head\n         }", "Entry<? super Integer, String>", "Entry<? super Integer, String>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Entry<? super Integer, String>");
    this.done(_and);
  }
  
  @Test
  public void testConstructorCall_01() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new java.util.ArrayList<String>()", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testConstructorCall_02() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new java.util.HashMap<String,Boolean>", "String", "Boolean");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testConstructorCall_03() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new java.util.ArrayList()", "Object");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testConstructorCall_04() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new java.util.ArrayList<? extends String>()", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testConstructorCall_05() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new java.util.HashMap<? extends String, ? extends Boolean>", "String", "Boolean");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testConstructorCall_06() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new java.util.ArrayList<? super String>()", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testConstructorCall_07() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new java.util.HashMap<? super String,Boolean>", "String", "Boolean");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testConstructorTypeParameters_01() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new constructorTypeParameters.KeyValue(new constructorTypeParameters.WritableValue, \'\')", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testConstructorTypeParameters_02() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new constructorTypeParameters.KeyValue(new constructorTypeParameters.WritableValue, 1.0)", "Double");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testConstructorTypeInference_01() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new testdata.GenericType1(\'\')", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testConstructorTypeInference_02() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("<java.util.List<String>>newArrayList().add(new java.util.ArrayList())", "List<String>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testConstructorTypeInference_03() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("<java.util.List<String>>newArrayList().add(0, new java.util.ArrayList())", "List<String>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testConstructorTypeInference_04() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList.add(new java.util.ArrayList())", "ArrayList<Object>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Object");
    this.done(_and);
  }
  
  @Test
  public void testConstructorTypeInference_05() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new testdata.GenericType2", "Number");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testConstructorTypeInference_06() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new testdata.GenericType2(0)", "Integer");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testConstructorTypeInference_07() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new testdata.GenericType2(0, 1)", "Integer");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testConstructorTypeInference_08() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new testdata.GenericType2(new Integer(0), new Integer(0).doubleValue)", "Number & Comparable<?>");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testConstructorTypeInference_09() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("<Iterable<String>>newArrayList().add(new java.util.LinkedList)", "Iterable<String>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testConstructorTypeInference_10() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("<Iterable<String>>newArrayList().add(null)", "Iterable<String>");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testConstructorTypeInference_11() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("<java.util.Map<Iterable<String>, Iterable<Integer>>>newArrayList().head", "Map<Iterable<String>, Iterable<Integer>>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Map<Iterable<String>, Iterable<Integer>>");
    this.done(_and);
  }
  
  @Test
  public void testVarArgs_01() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(new Double(\'-20\'), new Integer(\'20\'))", "Number & Comparable<?>");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testVarArgs_02() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(if (true) new Double(\'-20\') else new Integer(\'20\'))", "Number & Comparable<?>");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testVarArgs_03() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(if (true) new Double(\'-20\') else new Integer(\'20\'), new Integer(\'29\'))", "Number & Comparable<?>");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testVarArgs_04() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(if (true) new Double(\'-20\') else new Integer(\'20\'), new Double(\'29\'))", "Number & Comparable<?>");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testVarArgs_05() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(if (true) new Double(\'-20\') else new Integer(\'20\'), new Integer(\'29\'), new Double(\'29\'))", "Number & Comparable<?>");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testFeatureCall_01() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new java.util.ArrayList<String>().get(23)", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testFeatureCall_02() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new testdata.ClassWithVarArgs().toList()", "Object");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testFeatureCall_03() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new testdata.ClassWithVarArgs().toList(\'\')", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testFeatureCall_04() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new testdata.ClassWithVarArgs().toList(\'\', \'\')", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testFeatureCall_05() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new testdata.ClassWithVarArgs().toList(null as String[])", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testFeatureCall_06() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new testdata.ClassWithVarArgs().toList(null as int[])", "int[]");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testFeatureCall_07() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new testdata.ClassWithVarArgs().toList(\'\', 1)", "Comparable<?> & Serializable");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testFeatureCall_08() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new testdata.ClassWithVarArgs().toNumberList()", "Number");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testFeatureCall_09() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new testdata.ClassWithVarArgs().toNumberList(0)", "Integer");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testFeatureCall_10() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new testdata.ClassWithVarArgs().toNumberList(0, 1)", "Integer");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testFeatureCall_11() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new testdata.ClassWithVarArgs().toNumberList(new Integer(0), new Integer(0).doubleValue)", "Number & Comparable<?>");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testFeatureCall_12() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new testdata.ClassWithVarArgs().toNumberList(null as Float[])", "Float");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testFeatureCall_13() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|s)", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "String");
    this.done(_and);
  }
  
  @Test
  public void testFeatureCall_14() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map [it|it]", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "String");
    this.done(_and);
  }
  
  @Test
  public void testFeatureCall_15() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map [it]", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "String");
    this.done(_and);
  }
  
  @Test
  public void testFeatureCall_16() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("(null as Iterable<String>).map(s|s)", "String", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testFeatureCall_17() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("$$ListExtensions::map(newArrayList(\'\')) [s|s]", "String", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testFeatureCall_18() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|s).head", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testFeatureCall_19() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|s.toString).head", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testFeatureCall_20() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|1)", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Integer");
    this.done(_and);
  }
  
  @Test
  public void testFeatureCall_21() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|1).head", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer");
    this.done(_and_1);
  }
  
  @Test
  public void testFeatureCall_22() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|s.length)", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Integer");
    this.done(_and);
  }
  
  @Test
  public void testFeatureCall_23() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("<String>newArrayList.map(s|s.length)", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Integer");
    this.done(_and);
  }
  
  @Test
  public void testFeatureCall_24() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|s.length).head", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer");
    this.done(_and_1);
  }
  
  @Test
  public void testFeatureCall_25() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("<String>newArrayList.map(s|s.length).head", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer");
    this.done(_and_1);
  }
  
  @Test
  public void testFeatureCall_26() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|s != null)", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Boolean");
    this.done(_and);
  }
  
  @Test
  public void testFeatureCall_27() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|s.length+1)", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Integer");
    this.done(_and);
  }
  
  @Test
  public void testFeatureCall_28() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|1).map(i|i+1)", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer", "Integer");
    this.done(_and_1);
  }
  
  @Test
  public void testFeatureCall_29() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|1).toList()", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer");
    this.done(_and_1);
  }
  
  @Test
  public void testFeatureCall_30() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|1).toList().map(i|i)", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer");
    Iterator<XExpression> _and_2 = this.and(_and_1, "Integer", "Integer");
    this.done(_and_2);
  }
  
  @Test
  public void testFeatureCall_31() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|1).toList().map(i|i+1)", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer");
    Iterator<XExpression> _and_2 = this.and(_and_1, "Integer", "Integer");
    this.done(_and_2);
  }
  
  @Test
  public void testFeatureCall_32() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ var it = newArrayList(\'\').map(s|1).toList() it.map(i|i+1) }", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer");
    Iterator<XExpression> _and_2 = this.and(_and_1, "Integer", "Integer");
    this.done(_and_2);
  }
  
  @Test
  public void testFeatureCall_33() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ var it = newArrayList(\'\').map(s|1).toList() map(i|i+1) }", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer");
    Iterator<XExpression> _and_2 = this.and(_and_1, "Integer", "Integer");
    this.done(_and_2);
  }
  
  @Test
  public void testFeatureCall_34() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ var it = newArrayList(\'\').map(s|1).toList() it }", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer");
    this.done(_and_1);
  }
  
  @Test
  public void testFeatureCall_35() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ var java.util.List<? extends Integer> it = null map(i|i+1) }", "? extends Integer", "Integer");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testFeatureCall_36() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ var java.util.List<? extends Integer> it = null map(i|i) }", "? extends Integer", "Integer");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testFeatureCall_37() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(newArrayList(\'\').map(s|1))", "List<Integer>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String", "Integer");
    this.done(_and_1);
  }
  
  @Test
  public void testFeatureCall_38() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(newArrayList(\'\').map(s|1)).map(iterable|iterable.size())", "List<Integer>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String", "Integer");
    Iterator<XExpression> _and_2 = this.and(_and_1, "List<Integer>", "Integer");
    this.done(_and_2);
  }
  
  @Test
  public void testFeatureCall_39() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(newArrayList(\'\')).map(iterable|iterable.size())", "ArrayList<String>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "ArrayList<String>", "Integer");
    this.done(_and_1);
  }
  
  @Test
  public void testFeatureCall_40() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(newArrayList(\'\')).map(iterable|iterable.size()).head", "ArrayList<String>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "ArrayList<String>", "Integer");
    Iterator<XExpression> _and_2 = this.and(_and_1, "Integer");
    this.done(_and_2);
  }
  
  @Test
  public void testFeatureCall_41() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(newArrayList(\'\')).map(iterable|iterable.size()).map(e|e)", "ArrayList<String>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "ArrayList<String>", "Integer");
    Iterator<XExpression> _and_2 = this.and(_and_1, "Integer", "Integer");
    this.done(_and_2);
  }
  
  @Test
  public void testFeatureCall_42() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|1).map(i|1)", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer", "Integer");
    this.done(_and_1);
  }
  
  @Test
  public void testFeatureCall_43() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|1).map(i|1).head", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer", "Integer");
    Iterator<XExpression> _and_2 = this.and(_and_1, "Integer");
    this.done(_and_2);
  }
  
  @Test
  public void testFeatureCall_44() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|s.length).map(i|i)", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer", "Integer");
    this.done(_and_1);
  }
  
  @Test
  public void testFeatureCall_45() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|s.length).map(i|i).head", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer", "Integer");
    Iterator<XExpression> _and_2 = this.and(_and_1, "Integer");
    this.done(_and_2);
  }
  
  @Test
  public void testFeatureCall_46() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|s.length + 1 == 5).map(b|b)", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Boolean");
    Iterator<XExpression> _and_1 = this.and(_and, "Boolean", "Boolean");
    this.done(_and_1);
  }
  
  @Test
  public void testFeatureCall_47() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|s.length + 1 == 5).map(b|b).head", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Boolean");
    Iterator<XExpression> _and_1 = this.and(_and, "Boolean", "Boolean");
    Iterator<XExpression> _and_2 = this.and(_and_1, "Boolean");
    this.done(_and_2);
  }
  
  @Test
  public void testFeatureCall_48() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|s.length + 1 == 5).map(b| { \'length\'.length b })", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Boolean");
    Iterator<XExpression> _and_1 = this.and(_and, "Boolean", "Boolean");
    this.done(_and_1);
  }
  
  @Test
  public void testFeatureCall_49() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|s.length + 1 == 5).map(b| { \'length\'.length b }).head", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Boolean");
    Iterator<XExpression> _and_1 = this.and(_and, "Boolean", "Boolean");
    Iterator<XExpression> _and_2 = this.and(_and_1, "Boolean");
    this.done(_and_2);
  }
  
  @Test
  public void testFeatureCall_50() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|s.length + 1 == 5).map(Boolean b|!b)", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Boolean");
    Iterator<XExpression> _and_1 = this.and(_and, "Boolean", "Boolean");
    this.done(_and_1);
  }
  
  @Test
  public void testFeatureCall_51() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|s.length + 1 == 5).map(Boolean b|!b).head", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Boolean");
    Iterator<XExpression> _and_1 = this.and(_and, "Boolean", "Boolean");
    Iterator<XExpression> _and_2 = this.and(_and_1, "Boolean");
    this.done(_and_2);
  }
  
  @Test
  public void testFeatureCall_52() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|s.length + 1 == 5).map(b| ! b )", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Boolean");
    Iterator<XExpression> _and_1 = this.and(_and, "Boolean", "Boolean");
    this.done(_and_1);
  }
  
  @Test
  public void testFeatureCall_53() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|s.length + 1 == 5).map(b| ! b ).head", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Boolean");
    Iterator<XExpression> _and_1 = this.and(_and, "Boolean", "Boolean");
    Iterator<XExpression> _and_2 = this.and(_and_1, "Boolean");
    this.done(_and_2);
  }
  
  @Test
  public void testFeatureCall_54() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|s.length + 1 == 5).map(b| { !b } )", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Boolean");
    Iterator<XExpression> _and_1 = this.and(_and, "Boolean", "Boolean");
    this.done(_and_1);
  }
  
  @Test
  public void testFeatureCall_55() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|s.length + 1 == 5).map(b| { !b } ).head", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Boolean");
    Iterator<XExpression> _and_1 = this.and(_and, "Boolean", "Boolean");
    Iterator<XExpression> _and_2 = this.and(_and_1, "Boolean");
    this.done(_and_2);
  }
  
  @Test
  public void testFeatureCall_56() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|s.length + 1 == 5).map(b| { b.operator_not } )", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Boolean");
    Iterator<XExpression> _and_1 = this.and(_and, "Boolean", "Boolean");
    this.done(_and_1);
  }
  
  @Test
  public void testFeatureCall_57() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|s.length + 1 == 5).map(b| { b.operator_not } ).head", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Boolean");
    Iterator<XExpression> _and_1 = this.and(_and, "Boolean", "Boolean");
    Iterator<XExpression> _and_2 = this.and(_and_1, "Boolean");
    this.done(_and_2);
  }
  
  @Test
  public void testFeatureCall_58() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo(((("newArrayList(\'\').map(s|" + 
      "$$ObjectExtensions::operator_equals(") + 
      "\t$$IntegerExtensions::operator_plus(s.length,1), 5)") + 
      ").map(b| $$BooleanExtensions::operator_not(b) )"), "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Boolean");
    Iterator<XExpression> _and_1 = this.and(_and, "Boolean", "Boolean");
    this.done(_and_1);
  }
  
  @Test
  public void testFeatureCall_59() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo(((("newArrayList(\'\').map(s|" + 
      "$$ObjectExtensions::operator_equals(") + 
      "\t$$IntegerExtensions::operator_plus(s.length,1), 5)") + 
      ").map(b| $$BooleanExtensions::operator_not(b) ).head"), "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Boolean");
    Iterator<XExpression> _and_1 = this.and(_and, "Boolean", "Boolean");
    Iterator<XExpression> _and_2 = this.and(_and_1, "Boolean");
    this.done(_and_2);
  }
  
  @Test
  public void testFeatureCall_60() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|s.length + 1 * 5).map(b| b / 5 )", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer", "Integer");
    this.done(_and_1);
  }
  
  @Test
  public void testFeatureCall_61() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(s|s.length + 1 * 5).map(b| b / 5 ).head", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer", "Integer");
    Iterator<XExpression> _and_2 = this.and(_and_1, "Integer");
    this.done(_and_2);
  }
  
  @Test
  public void testFeatureCall_62() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map[ length + 1 * 5 ].map [ it / 5 ].head", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer", "Integer");
    Iterator<XExpression> _and_2 = this.and(_and_1, "Integer");
    this.done(_and_2);
  }
  
  @Test
  public void testFeatureCall_63() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map[ length + 1 * 5 - length + 1 * 5 ].map [ it / 5 + 1 / it ).head", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer", "Integer");
    Iterator<XExpression> _and_2 = this.and(_and_1, "Integer");
    this.done(_and_2);
  }
  
  @Test
  public void testFeatureCall_64() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val list = newArrayList(if (false) new Double(\'-20\') else new Integer(\'20\')).map(v|v.intValue)\n           val Object o = list.head \n           list\n        }", "Number & Comparable<?>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Number & Comparable<?>", "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer");
    this.done(_and_1);
  }
  
  @Test
  public void testFeatureCall_65() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val list = newArrayList(if (false) new Double(\'-20\') else new Integer(\'20\')).map(v|v.intValue)\n           val Object o = list.head \n           list.head\n        }", "Number & Comparable<?>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Number & Comparable<?>", "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer");
    Iterator<XExpression> _and_2 = this.and(_and_1, "Integer");
    this.done(_and_2);
  }
  
  @Test
  public void testFeatureCall_66() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val list = newArrayList(if (false) new Double(\'-20\') else new Integer(\'20\')).map(v|v.compareTo(null))\n           val Object o = list.head \n           list.head\n        }", "Number & Comparable<?>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Number & Comparable<?>", "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer");
    Iterator<XExpression> _and_2 = this.and(_and_1, "Integer");
    this.done(_and_2);
  }
  
  @Test
  public void testFeatureCall_67() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val list = $$ListExtensions::map(newArrayList(if (false) new Double(\'-20\') else new Integer(\'20\'))) [ v|v.intValue ]\n           val Object o = list.head \n           list\n        }", "Number & Comparable<?>", "Integer");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Number & Comparable<?>");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer");
    this.done(_and_1);
  }
  
  @Test
  public void testFeatureCall_68() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val list = $$ListExtensions::map(newArrayList(null as Integer)) [ v|v.intValue ]\n           val Object o = list.head \n           list\n        }", "Integer", "Integer");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer");
    this.done(_and_1);
  }
  
  @Test
  public void testFeatureCall_69() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val list = newArrayList(null as Integer).map [ v|v.intValue ]\n           val Object o = list.head \n           list\n        }", "Integer");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Integer", "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer");
    this.done(_and_1);
  }
  
  @Test
  public void testFeatureCall_70() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val list = newArrayList(null as Integer).map [ v|v.intValue ]\n           val Object o = list.head \n           list.findFirst [ Number it | intValue == 0 ]\n        }", "Integer");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Integer", "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer");
    Iterator<XExpression> _and_2 = this.and(_and_1, "Integer");
    this.done(_and_2);
  }
  
  @Test
  public void testFeatureCall_71() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.forall[String s | s.length > 0]\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testFeatureCall_72() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.findFirst[String s | true]\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testFeatureCall_73() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList.map[String s | s.substring(1,1) ]", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "String");
    this.done(_and);
  }
  
  @Test
  public void testFeatureCall_74() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList.map[ s | s.toString ]", "Object");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Object", "String");
    this.done(_and);
  }
  
  @Test
  public void testFeatureCall_75() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.forall[ s | s.toString.length > 0 ]\n\t\t\tlist\n\t\t}", "Object");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Object");
    this.done(_and);
  }
  
  @Test
  public void testFeatureCall_76() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("(1..20).map[ toString.length ].reduce[ i1,  i2 | i1 + i2 ]", "Integer", "Integer");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Integer");
    this.done(_and);
  }
  
  @Test
  public void testFeatureCall_77() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').get(0)", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testFeatureCall_78() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("<String>newArrayList().get(0)", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testFeatureCallWithExpectation_01() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val java.util.Set<java.util.Set<String>> set = newHashSet(newHashSet) set.head }", "Set<String>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "Set<String>");
    this.done(_and_1);
  }
  
  @Test
  public void testFeatureCallWithExpectation_02() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val Iterable<CharSequence> set = newHashSet(\'\') }", "CharSequence");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testFeatureCallWithExpectation_03() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val Iterable<CharSequence> set = newHashSet() }", "CharSequence");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testFeatureCallWithExpectation_04() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val java.util.List<CharSequence> set = newHashSet(\'\') }", "CharSequence");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testFeatureCallWithExpectation_05() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val Iterable<? super CharSequence> set = newHashSet(\'\') }", "CharSequence");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testFeatureCallWithExpectation_06() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val Iterable<? extends CharSequence> set = newHashSet(\'\') }", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testJEP101Example_01() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val foo.JEP101List<String> ls = foo::JEP101List::nil }", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testJEP101Example_02() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("foo::JEP101List::cons(42, foo::JEP101List::nil)", "Integer");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Integer");
    this.done(_and);
  }
  
  @Test
  public void testJEP101Example_03() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val String s = foo::JEP101List::nil.head }", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testToList_01() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val Iterable<? extends String> iter = null org::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::fixedToList(iter) }", "? extends String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testToList_02() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val Iterable<? super String> iter = null org::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::fixedToList(iter) }", "? super String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testToList_03() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val Iterable<String> iter = null org::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::fixedToList(iter) }", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testToList_04() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val Iterable<? extends String> iter = null org::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::brokenToList(iter) }", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testToList_05() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val Iterable<? super String> iter = null org::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::brokenToList(iter) }", "Object");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testToList_06() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val Iterable<String> iter = null org::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::brokenToList(iter) }", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testToList_07() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val Iterable<? extends String> iter = null org::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::brokenToList2(iter) }", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testToList_08() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val Iterable<? super String> iter = null org::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::brokenToList2(iter) }", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testToList_09() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val Iterable<String> iter = null org::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::brokenToList2(iter) }", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testFeatureCall_Bug342134_00() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("(null as java.util.List<String>).map(e|newArrayList(e)).flatten", "String", "ArrayList<String>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testFeatureCall_Bug342134_01() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("(null as java.util.List<String>).map(e|newArrayList(e)).flatten.head", "String", "ArrayList<String>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    Iterator<XExpression> _and_2 = this.and(_and_1, "String");
    this.done(_and_2);
  }
  
  @Test
  public void testFeatureCall_Bug342134_02() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(e|newArrayList(e)).flatten", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "ArrayList<String>");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    Iterator<XExpression> _and_2 = this.and(_and_1, "String");
    this.done(_and_2);
  }
  
  @Test
  public void testFeatureCall_Bug342134_04() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(e|newArrayList(e))", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "ArrayList<String>");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testFeatureCall_Bug342134_05() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(e|newArrayList(e)).head", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "ArrayList<String>");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    Iterator<XExpression> _and_2 = this.and(_and_1, "ArrayList<String>");
    this.done(_and_2);
  }
  
  @Test
  public void testFeatureCall_Bug342134_06() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("<String>newArrayList.map(e|newArrayList(e)).flatten", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "ArrayList<String>");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    Iterator<XExpression> _and_2 = this.and(_and_1, "String");
    this.done(_and_2);
  }
  
  @Test
  public void testFeatureCall_Bug342134_07() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\').map(e|<CharSequence>newArrayList(e)).flatten", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "ArrayList<CharSequence>");
    Iterator<XExpression> _and_1 = this.and(_and, "CharSequence");
    Iterator<XExpression> _and_2 = this.and(_and_1, "CharSequence");
    this.done(_and_2);
  }
  
  @Test
  public void testFeatureCall_Bug342134_08() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList.map(String e|<String>newArrayList(e)).flatten", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "ArrayList<String>");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    Iterator<XExpression> _and_2 = this.and(_and_1, "String");
    this.done(_and_2);
  }
  
  @Test
  public void testFeatureCall_Bug342134_09() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(newArrayList(\'\')).flatten", "ArrayList<String>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testFeatureCall_Bug342134_10() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("<java.util.List<String>>newArrayList().flatten", "List<String>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Ignore("TODO this should work")
  @Test
  public void testBug_391758() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval iterable = newArrayList\n\t\t\titerable.fold(newArrayList) [ list , elem | null as java.util.List<String> ]\n\t\t}", "Object");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Object", "List<String>");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testBounds_07() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ var java.util.List<Integer> list = null list.last }", "Integer");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testBounds_08() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ var java.util.List<? extends Integer> list = null list.last }", "? extends Integer");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testBounds_09() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ var java.util.List<? super Integer> list = null list.last }", "? super Integer");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testBounds_10() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ var java.util.List<Iterable<Integer>> list = null list.last }", "Iterable<Integer>");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testBounds_11() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ var java.util.List<Iterable<Integer>> list = null list.last.last }", "Iterable<Integer>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Integer");
    this.done(_and);
  }
  
  @Test
  public void testBounds_12() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ var java.util.List<? extends Iterable<Integer>> list = null list.last }", "? extends Iterable<Integer>");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testBounds_13() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ var java.util.List<? extends Iterable<Integer>> list = null list.last.last }", "? extends Iterable<Integer>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Integer");
    this.done(_and);
  }
  
  @Test
  public void testBounds_14() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ var java.util.List<Iterable<? extends Integer>> list = null list.last }", "Iterable<? extends Integer>");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testBounds_15() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ var java.util.List<Iterable<? extends Integer>> list = null list.last.last }", "Iterable<? extends Integer>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "? extends Integer");
    this.done(_and);
  }
  
  @Test
  public void testBounds_16() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ var java.util.List<? extends Iterable<? extends Integer>> list = null list.last }", "? extends Iterable<? extends Integer>");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testBounds_17() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ var java.util.List<? extends Iterable<? extends Integer>> list = null list.last.last }", "? extends Iterable<? extends Integer>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "? extends Integer");
    this.done(_and);
  }
  
  @Test
  public void testBounds_18() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ var java.util.List<Iterable<? super Integer>> list = null list.last }", "Iterable<? super Integer>");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testBounds_19() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ var java.util.List<Iterable<? super Integer>> list = null list.last.last }", "Iterable<? super Integer>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "? super Integer");
    this.done(_and);
  }
  
  @Test
  public void testBounds_20() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ var java.util.List<? extends Iterable<? super Integer>> list = null list.last }", "? extends Iterable<? super Integer>");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testBounds_21() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ var java.util.List<? extends Iterable<? super Integer>> list = null list.last.last }", "? extends Iterable<? super Integer>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "? super Integer");
    this.done(_and);
  }
  
  @Test
  public void testImplicitReceiverBounds_07() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ var java.util.List<Integer> it = null last }", "Integer");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testImplicitReceiverBounds_08() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ var java.util.List<? extends Integer> it = null last }", "? extends Integer");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testImplicitReceiverBounds_09() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ var java.util.List<? super Integer> it = null last }", "? super Integer");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testReceiverIsPartiallyResolved_01() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList.get(0)", "Object");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testReceiverIsPartiallyResolved_02() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList.toString", "Object");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testTypeByTransitiveExpectation_02() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList.subList(1,1).subList(1,1).head", "Object");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Object");
    this.done(_and);
  }
  
  @Test
  public void testExpectationActualMismatch_01() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("(null as java.util.ArrayList<Integer>).add(println(null as Double))", "Double");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testExpectationActualMismatch_02() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.add(null as Integer)\n\t\t\tlist.get(0).toString\n\t\t\tlist.add(println(null as Double))\n\t\t}", "Integer");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Double");
    this.done(_and);
  }
  
  @Test
  public void testDependentTypeArgumentResolution_01() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tvar Iterable<CharSequence> from = null\n\t\t\torg::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::copyIntoList(from, newArrayList)\n\t\t}", "CharSequence");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "CharSequence");
    this.done(_and);
  }
  
  @Test
  public void testDependentTypeArgumentResolution_02() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tvar Iterable<? extends CharSequence> from = null\n\t\t\torg::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::copyIntoList(from, newArrayList)\n\t\t}", "? extends CharSequence");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "CharSequence");
    this.done(_and);
  }
  
  @Test
  public void testDependentTypeArgumentResolution_03() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\torg::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::copyIntoList(null, newArrayList)\n\t\t}", "Object");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Object");
    this.done(_and);
  }
  
  @Test
  public void testDependentTypeArgumentResolution_04() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\torg::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::<String>copyIntoList(null, newArrayList)\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDependentTypeArgumentResolution_05() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tvar Iterable<? super CharSequence> from = null\n\t\t\torg::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::copyIntoList(from, newArrayList)\n\t\t}", "? super CharSequence");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Object");
    this.done(_and);
  }
  
  @Test
  public void testDependentTypeArgumentResolution_06() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tvar Iterable<String> from = null\n\t\t\torg::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::constrainedCopyIntoList(from, newArrayList)\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDependentTypeArgumentResolution_07() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tvar Iterable<? extends String> from = null\n\t\t\torg::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::constrainedCopyIntoList(from, newArrayList)\n\t\t}", "? extends String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDependentTypeArgumentResolution_08() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\torg::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::constrainedCopyIntoList(null, newArrayList)\n\t\t}", "Serializable");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Serializable");
    this.done(_and);
  }
  
  @Test
  public void testDependentTypeArgumentResolution_09() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\torg::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::<String>constrainedCopyIntoList(null, newArrayList)\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDependentTypeArgumentResolution_10() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tvar Iterable<? super String> from = null\n\t\t\torg::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::constrainedCopyIntoList(from, newArrayList)\n\t\t}", "? super String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Object");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_001() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList", "Object");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_002() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval String s = list.get(0)\n\t\t\tlist\n\t\t}", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_003() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval String s = list.head\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_004() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.add(\'\')\n\t\t\tlist\n\t\t}", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_005() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\t$$CollectionExtensions::addAll(list, null as java.util.ArrayList<String>)\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_006() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\t$$CollectionExtensions::addAll(list, \'\', \'\')\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_007() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.addAll(null as java.util.ArrayList<String>)\n\t\t\tlist\n\t\t}", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_008() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\t$$CollectionExtensions::addAll(list, null as java.util.ArrayList<String>)\n\t\t\tlist.head\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_009() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.addAll(newHashSet(\'\'))\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_010() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.addAll(null as java.util.Collection<String>)\n\t\t\tlist\n\t\t}", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_011() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.addAll(\'\', \'\', \'\')\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_012() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval secondList = newArrayList\n\t\t\tlist.add(\'\')\n\t\t\tlist.addAll(secondList)\n\t\t\tsecondList\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_013() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval secondList = newArrayList\n\t\t\tlist.addAll(secondList)\n\t\t\tlist.add(\'\')\n\t\t\tsecondList\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_014() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval Iterable<String> sublist = list.subList(1, 1)\n\t\t\tlist\n\t\t}", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_015() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval java.util.Set<String> sublist = list.subList(1, 1)\n\t\t\tlist\n\t\t}", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_016() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval java.util.Iterator<String> sublist = list.subList(1, 1).iterator\n\t\t\tlist\n\t\t}", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_017() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tfor(String s: list.subList(1, 1)) {}\n\t\t\tlist\n\t\t}", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_018() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.add(new Integer(0))\n\t\t\tlist.add(new Integer(0).doubleValue)\n\t\t\tlist\n\t\t}", "Number & Comparable<?>");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_019() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.add(new Integer(0))\n\t\t\tlist.get(0).toString\n\t\t\tlist.add(new Integer(0).doubleValue)\n\t\t\tlist\n\t\t}", "Integer");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_020() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval second = newArrayList\n\t\t\tlist.add(second.get(0))\n\t\t\tlist.add(\'\')\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_021() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval second = newArrayList\n\t\t\tlist.add(second.get(0))\n\t\t\tlist.add(\'\')\n\t\t\tsecond\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_022() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval second = newArrayList\n\t\t\tlist.add(second.head)\n\t\t\tlist.add(\'\')\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_023() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval second = newArrayList\n\t\t\tlist.add(second.head)\n\t\t\tlist.add(\'\')\n\t\t\tsecond\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_024() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval second = newArrayList\n\t\t\tlist.add(\'\')\n\t\t\tlist.add(second.get(0))\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_025() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval second = newArrayList\n\t\t\tlist.add(\'\')\n\t\t\tlist.add(second.get(0))\n\t\t\tsecond\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_026() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval second = newArrayList\n\t\t\tlist.add(\'\')\n\t\t\tlist.add(second.head)\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_027() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval second = newArrayList\n\t\t\tlist.add(\'\')\n\t\t\tlist.add(second.head)\n\t\t\tsecond\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_028() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval second = newArrayList\n\t\t\tlist.add(second.get(0))\n\t\t\tsecond.add(\'\')\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_029() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval second = newArrayList\n\t\t\tlist.add(second.get(0))\n\t\t\tsecond.add(\'\')\n\t\t\tsecond\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_030() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval second = newArrayList\n\t\t\tlist.add(second.head)\n\t\t\tsecond.add(\'\')\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_031() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval second = newArrayList\n\t\t\tlist.add(second.head)\n\t\t\tsecond.add(\'\')\n\t\t\tsecond\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_032() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval second = newArrayList\n\t\t\tsecond.add(\'\')\n\t\t\tlist.add(second.get(0))\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_033() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval second = newArrayList\n\t\t\tsecond.add(\'\')\n\t\t\tlist.add(second.get(0))\n\t\t\tsecond\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_034() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval second = newArrayList\n\t\t\tsecond.add(\'\')\n\t\t\tlist.add(second.head)\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_035() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval second = newArrayList\n\t\t\tsecond.add(\'\')\n\t\t\tlist.add(second.head)\n\t\t\tsecond\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_036() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList(newArrayList)\n\t\t\tval Iterable<String> s = list.head\n\t\t\tlist.head\n\t\t}", "ArrayList<String>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "ArrayList<String>");
    Iterator<XExpression> _and_2 = this.and(_and_1, "ArrayList<String>");
    this.done(_and_2);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_037() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList(newArrayList)\n\t\t\tval Iterable<String> s = list.flatten\n\t\t\tlist.head\n\t\t}", "ArrayList<String>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    Iterator<XExpression> _and_2 = this.and(_and_1, "ArrayList<String>");
    this.done(_and_2);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_041() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.addAll(\'\')\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_042() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval secondList = newArrayList\n\t\t\tlist.addAll(\'\')\n\t\t\tlist.addAll(secondList)\n\t\t\tsecondList\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_043() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval secondList = newArrayList\n\t\t\tlist.addAll(secondList)\n\t\t\tlist.addAll(\'\')\n\t\t\tsecondList\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_044() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.addAll(\'\', \'\', \'\')\n\t\t\tlist.head\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_045() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval secondList = newArrayList\n\t\t\tlist.addAll(\'\', \'\', \'\')\n\t\t\tlist.addAll(secondList)\n\t\t\tsecondList\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_046() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval secondList = newArrayList\n\t\t\tlist.addAll(secondList)\n\t\t\tlist.addAll(\'\', \'\', \'\')\n\t\t\tsecondList\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_047() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("println(newArrayList)", "ArrayList<Object>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Object");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_048() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval String s = println(list.get(0))\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_049() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval String s = println(println(list).head)\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "ArrayList<String>");
    Iterator<XExpression> _and_2 = this.and(_and_1, "String");
    this.done(_and_2);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_050() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tprintln(list).add(\'\')\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "ArrayList<String>");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_051() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\t$$CollectionExtensions::addAll(println(list), null as java.util.ArrayList<String>)\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "ArrayList<String>");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_053() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tprintln(list).addAll(null as java.util.ArrayList<String>)\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "ArrayList<String>");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_057() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tprintln(list).addAll(\'\', \'\', \'\')\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "ArrayList<String>");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_060() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval Iterable<String> sublist = println(println(list).subList(1, 1))\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "List<String>");
    Iterator<XExpression> _and_1 = this.and(_and, "ArrayList<String>");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_061() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval java.util.Set<String> sublist = println(println(list).subList(1, 1))\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "List<String>");
    Iterator<XExpression> _and_1 = this.and(_and, "ArrayList<String>");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_062() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval java.util.Iterator<String> sublist = println(println(println(list).subList(1, 1)).iterator)\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Iterator<String>");
    Iterator<XExpression> _and_1 = this.and(_and, "List<String>");
    Iterator<XExpression> _and_2 = this.and(_and_1, "ArrayList<String>");
    this.done(_and_2);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_063() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = println(newArrayList)\n\t\t\tfor(String s: println(list.subList(1, 1))) {}\n\t\t\tlist\n\t\t}", "ArrayList<String>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "List<String>");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_064() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.add(println(new Integer(0)))\n\t\t\tlist.add(println(new Integer(0).doubleValue))\n\t\t\tlist\n\t\t}", "Number & Comparable<?>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Double");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_065() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.add(println(new Integer(0)))\n\t\t\tprintln(list.get(0)).toString\n\t\t\tlist.add(println(new Integer(0).doubleValue))\n\t\t\tlist\n\t\t}", "Integer");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer");
    Iterator<XExpression> _and_2 = this.and(_and_1, "Double");
    this.done(_and_2);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_067() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new java.util.ArrayList", "Object");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_068() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval String s = list.get(0)\n\t\t\tlist\n\t\t}", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_069() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval String s = list.head\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_070() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.add(\'\')\n\t\t\tlist\n\t\t}", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_071() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\t$$CollectionExtensions::addAll(list, null as java.util.ArrayList<String>)\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_072() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\t$$CollectionExtensions::addAll(list, \'\', \'\')\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_073() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.addAll(null as java.util.ArrayList<String>)\n\t\t\tlist\n\t\t}", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_074() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.addAll(new java.util.ArrayList<String>)\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_075() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.addAll(newHashSet(\'\'))\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_076() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.addAll(null as java.util.Collection<String>)\n\t\t\tlist\n\t\t}", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_077() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.addAll(\'\', \'\', \'\')\n\t\t\tlist.head\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_078() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval secondList = new java.util.ArrayList\n\t\t\tlist.add(\'\')\n\t\t\tlist.addAll(secondList)\n\t\t\tsecondList\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_079() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval secondList = new java.util.ArrayList\n\t\t\tlist.addAll(secondList)\n\t\t\tlist.add(\'\')\n\t\t\tsecondList\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_080() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval Iterable<String> sublist = list.subList(1, 1)\n\t\t\tlist\n\t\t}", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_081() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval java.util.Set<String> sublist = list.subList(1, 1)\n\t\t\tlist\n\t\t}", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_082() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval java.util.Iterator<String> sublist = list.subList(1, 1).iterator\n\t\t\tlist\n\t\t}", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_083() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tfor(String s: list.subList(1, 1)) {}\n\t\t\tlist\n\t\t}", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_084() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.add(new Integer(0))\n\t\t\tlist.add(new Integer(0).doubleValue)\n\t\t\tlist\n\t\t}", "Number & Comparable<?>");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_085() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.add(new Integer(0))\n\t\t\tlist.get(0).toString\n\t\t\tlist.add(new Integer(0).doubleValue)\n\t\t\tlist\n\t\t}", "Integer");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_086() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval second = new java.util.ArrayList\n\t\t\tlist.add(second.get(0))\n\t\t\tlist.add(\'\')\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_087() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval second = new java.util.ArrayList\n\t\t\tlist.add(second.get(0))\n\t\t\tlist.add(\'\')\n\t\t\tsecond\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_088() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval second = new java.util.ArrayList\n\t\t\tlist.add(second.head)\n\t\t\tlist.add(\'\')\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_089() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval second = new java.util.ArrayList\n\t\t\tlist.add(second.head)\n\t\t\tlist.add(\'\')\n\t\t\tsecond\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_090() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval second = new java.util.ArrayList\n\t\t\tlist.add(\'\')\n\t\t\tlist.add(second.get(0))\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_091() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval second = new java.util.ArrayList\n\t\t\tlist.add(\'\')\n\t\t\tlist.add(second.get(0))\n\t\t\tsecond\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_092() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval second = new java.util.ArrayList\n\t\t\tlist.add(\'\')\n\t\t\tlist.add(second.head)\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_093() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval second = new java.util.ArrayList\n\t\t\tlist.add(\'\')\n\t\t\tlist.add(second.head)\n\t\t\tsecond\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_094() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval second = new java.util.ArrayList\n\t\t\tlist.add(second.get(0))\n\t\t\tsecond.add(\'\')\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_095() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval second = new java.util.ArrayList\n\t\t\tlist.add(second.get(0))\n\t\t\tsecond.add(\'\')\n\t\t\tsecond\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_096() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval second = new java.util.ArrayList\n\t\t\tlist.add(second.head)\n\t\t\tsecond.add(\'\')\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_097() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval second = new java.util.ArrayList\n\t\t\tlist.add(second.head)\n\t\t\tsecond.add(\'\')\n\t\t\tsecond\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_098() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval second = new java.util.ArrayList\n\t\t\tsecond.add(\'\')\n\t\t\tlist.add(second.get(0))\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_099() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval second = new java.util.ArrayList\n\t\t\tsecond.add(\'\')\n\t\t\tlist.add(second.get(0))\n\t\t\tsecond\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_100() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval second = new java.util.ArrayList\n\t\t\tsecond.add(\'\')\n\t\t\tlist.add(second.head)\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_101() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval second = new java.util.ArrayList\n\t\t\tsecond.add(\'\')\n\t\t\tlist.add(second.head)\n\t\t\tsecond\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_102() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.add(new java.util.HashSet)\n\t\t\tval Iterable<String> s = list.head\n\t\t\tlist.head\n\t\t}", "HashSet<String>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "HashSet<String>");
    Iterator<XExpression> _and_2 = this.and(_and_1, "HashSet<String>");
    this.done(_and_2);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_103() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.add(new java.util.ArrayList)\n\t\t\tval Iterable<String> s = list.flatten\n\t\t\tlist.head\n\t\t}", "ArrayList<String>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    Iterator<XExpression> _and_2 = this.and(_and_1, "ArrayList<String>");
    this.done(_and_2);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_107() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.addAll(\'\')\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_108() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval secondList = new java.util.ArrayList\n\t\t\tlist.addAll(\'\')\n\t\t\tlist.addAll(secondList)\n\t\t\tsecondList\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_109() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval secondList = new java.util.ArrayList\n\t\t\tlist.addAll(secondList)\n\t\t\tlist.addAll(\'\')\n\t\t\tsecondList\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_110() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.addAll(\'\', \'\', \'\')\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_111() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval secondList = new java.util.ArrayList\n\t\t\tlist.addAll(\'\', \'\', \'\')\n\t\t\tlist.addAll(secondList)\n\t\t\tsecondList\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_112() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval secondList = new java.util.ArrayList\n\t\t\tlist.addAll(secondList)\n\t\t\tlist.addAll(\'\', \'\', \'\')\n\t\t\tsecondList\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_113() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("println(new java.util.ArrayList)", "ArrayList<Object>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Object");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_114() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval String s = println(list.get(0))\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_115() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval String s = println(println(list).head)\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "ArrayList<String>");
    Iterator<XExpression> _and_2 = this.and(_and_1, "String");
    this.done(_and_2);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_116() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tprintln(list).add(\'\')\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "ArrayList<String>");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_117() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\t$$CollectionExtensions::addAll(println(list), null as java.util.ArrayList<String>)\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "ArrayList<String>");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_118() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\t$$CollectionExtensions::addAll(println(list), println(\'\'), println(\'\'))\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "ArrayList<String>");
    Iterator<XExpression> _and_2 = this.and(_and_1, "String");
    Iterator<XExpression> _and_3 = this.and(_and_2, "String");
    this.done(_and_3);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_119() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tprintln(list).addAll(null as java.util.ArrayList<String>)\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "ArrayList<String>");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_123() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tprintln(list).addAll(\'\', \'\', \'\')\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "ArrayList<String>");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_126() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval Iterable<String> sublist = println(println(list).subList(1, 1))\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "List<String>");
    Iterator<XExpression> _and_1 = this.and(_and, "ArrayList<String>");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_127() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval java.util.Set<String> sublist = println(println(list).subList(1, 1))\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "List<String>");
    Iterator<XExpression> _and_1 = this.and(_and, "ArrayList<String>");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_128() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval java.util.Iterator<String> sublist = println(println(println(list).subList(1, 1)).iterator)\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Iterator<String>");
    Iterator<XExpression> _and_1 = this.and(_and, "List<String>");
    Iterator<XExpression> _and_2 = this.and(_and_1, "ArrayList<String>");
    this.done(_and_2);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_129() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = println(new java.util.ArrayList)\n\t\t\tfor(String s: println(list.subList(1, 1))) {}\n\t\t\tlist\n\t\t}", "ArrayList<String>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "List<String>");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_130() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.add(println(new Integer(0)))\n\t\t\tlist.add(println(new Integer(0).doubleValue))\n\t\t\tlist\n\t\t}", "Number & Comparable<?>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Double");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_131() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.add(println(new Integer(0)))\n\t\t\tprintln(list.get(0)).toString\n\t\t\tlist.add(println(new Integer(0).doubleValue))\n\t\t\tlist\n\t\t}", "Integer");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Integer");
    Iterator<XExpression> _and_1 = this.and(_and, "Integer");
    Iterator<XExpression> _and_2 = this.and(_and_1, "Double");
    this.done(_and_2);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_133() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval fun = [String s| s]\n\t\t\tlist.map(fun)\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_134() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.map[String s| s]\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_135() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.add(newArrayList)\n\t\t\tval Iterable<String> s = list.head\n\t\t\tlist.head\n\t\t}", "ArrayList<String>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "ArrayList<String>");
    Iterator<XExpression> _and_2 = this.and(_and_1, "ArrayList<String>");
    this.done(_and_2);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_136() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.add(new java.util.ArrayList)\n\t\t\tval Iterable<String> s = list.head\n\t\t\tlist.head\n\t\t}", "ArrayList<String>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "ArrayList<String>");
    Iterator<XExpression> _and_2 = this.and(_and_1, "ArrayList<String>");
    this.done(_and_2);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_137() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\t$$CollectionExtensions::<String>addAll(println(list), null, null)\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "ArrayList<String>");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_138() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\t$$CollectionExtensions::<String>addAll(list, null, null)\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_139() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\t$$CollectionExtensions::<String>addAll(println(list), \'\', \'\')\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "ArrayList<String>");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_143() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.add(new java.util.ArrayList)\n\t\t\tval Iterable<String> s = $$IterableExtensions::head(list)\n\t\t\tlist.head\n\t\t}", "ArrayList<String>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "ArrayList<String>");
    Iterator<XExpression> _and_2 = this.and(_and_1, "ArrayList<String>");
    this.done(_and_2);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_148() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.add(new java.util.ArrayList)\n\t\t\tval Iterable<String> s = $$IterableExtensions::flatten(list)\n\t\t\tlist.head\n\t\t}", "ArrayList<String>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    Iterator<XExpression> _and_2 = this.and(_and_1, "ArrayList<String>");
    this.done(_and_2);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_149() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.add(newHashSet)\n\t\t\tval String s = $$IterableExtensions::flatten(list).head\n\t\t\tlist.head\n\t\t}", "HashSet<String>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    Iterator<XExpression> _and_2 = this.and(_and_1, "String");
    Iterator<XExpression> _and_3 = this.and(_and_2, "HashSet<String>");
    this.done(_and_3);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_150() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.add(new java.util.ArrayList)\n\t\t\tval String s = $$IterableExtensions::flatten(list).head\n\t\t\tlist.head\n\t\t}", "ArrayList<String>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    Iterator<XExpression> _and_2 = this.and(_and_1, "String");
    Iterator<XExpression> _and_3 = this.and(_and_2, "ArrayList<String>");
    this.done(_and_3);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_152() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.map[String s| s]\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_153() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\t$$IterableExtensions::map(list, [String s| s])\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_154() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval fun = [String s| s]\n\t\t\t$$IterableExtensions::map(list, fun)\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_155() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.map(println([String s| println(s)]))\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String", "String");
    Iterator<XExpression> _and_1 = this.and(_and, "(String)=>String");
    Iterator<XExpression> _and_2 = this.and(_and_1, "String");
    this.done(_and_2);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_156() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.addAll(null as java.util.ArrayList<String>)\n\t\t\tlist.head\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_157() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.addAll(newArrayList(\'\'))\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_158() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.addAll(null as String[])\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_159() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval secondList = newArrayList\n\t\t\tlist.addAll(null as String[])\n\t\t\tlist.addAll(secondList)\n\t\t\tsecondList\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_160() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval secondList = newArrayList\n\t\t\tlist.addAll(secondList)\n\t\t\tlist.addAll(null as String[])\n\t\t\tsecondList\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    Iterator<XExpression> _and_1 = this.and(_and, "String");
    this.done(_and_1);
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_161() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.add(null as Integer)\n\t\t\tlist.get(0)\n\t\t\tlist.add(println(null as Double))\n\t\t}", "Number & Comparable<?>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Double");
    this.done(_and);
  }
  
  @Test
  public void testRecursiveTypeArgumentResolution_01() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.addAll(list)\n\t\t\tlist\n\t\t}", "Object");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testRecursiveTypeArgumentResolution_02() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.add(list.head)\n\t\t\tlist\n\t\t}", "Object");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Object");
    this.done(_and);
  }
  
  @Test
  public void testRecursiveTypeArgumentResolution_03() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.addAll(list)\n\t\t\tlist.add(\'\')\n\t\t\tlist\n\t\t}", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testRecursiveTypeArgumentResolution_04() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.add(list.head)\n\t\t\tlist.add(\'\')\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testRecursiveTypeArgumentResolution_05() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.addAll(list)\n\t\t\tlist\n\t\t}", "Object");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testRecursiveTypeArgumentResolution_06() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.add(list.head)\n\t\t\tlist\n\t\t}", "Object");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Object");
    this.done(_and);
  }
  
  @Test
  public void testRecursiveTypeArgumentResolution_07() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.addAll(list)\n\t\t\tlist.add(\'\')\n\t\t\tlist\n\t\t}", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testRecursiveTypeArgumentResolution_08() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.add(list.head)\n\t\t\tlist.add(\'\')\n\t\t\tlist\n\t\t}", "String");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testFeatureCallWithOperatorOverloading_1() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ \n\t\t\tval java.util.List<? super CharSequence> list = null\n\t\t\tlist += null as Iterable<CharSequence>\n\t\t}", "? super CharSequence");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testFeatureCallWithOperatorOverloading_2() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new java.util.ArrayList<Byte>() += \'x\'.getBytes().iterator.next", "Byte");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Byte");
    this.done(_and);
  }
  
  @Test
  public void testFeatureCallWithOperatorOverloading_3() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new java.util.ArrayList<Byte>() += null", "Byte");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Byte");
    this.done(_and);
  }
  
  @Test
  public void testFeatureCallWithOperatorOverloading_4() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new java.util.ArrayList<Byte>() += newArrayList(\'x\'.getBytes().iterator.next)", "Byte");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Byte");
    Iterator<XExpression> _and_1 = this.and(_and, "Byte");
    this.done(_and_1);
  }
  
  @Test
  public void testFeatureCallWithOperatorOverloading_5() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("new java.util.ArrayList<Byte>() += \'x\'.getBytes()", "Byte");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Byte");
    this.done(_and);
  }
  
  @Test
  public void testStaticMethods_01() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\')", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testStaticMethods_02() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(\'\', \'\')", "String");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testStaticMethods_03() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList(newHashSet(\'\'))", "HashSet<String>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "String");
    this.done(_and);
  }
  
  @Test
  public void testStaticMethods_04() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newArrayList()", "Object");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Test
  public void testStaticMethods_05() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("newHashMap()", "Object", "Object");
    this.done(_bindTypeArgumentsTo);
  }
  
  @Ignore("TODO fix me")
  @Test
  public void testJava8Inferrence_01() throws Exception {
    Iterator<XExpression> _bindTypeArgumentsTo = this.bindTypeArgumentsTo("{ val Iterable<Iterable<Number>> l = java.util.Collections.singleton(java.util.Collections.singleton(1)) }", "Iterable<Number>");
    Iterator<XExpression> _and = this.and(_bindTypeArgumentsTo, "Number");
    this.done(_and);
  }
}
