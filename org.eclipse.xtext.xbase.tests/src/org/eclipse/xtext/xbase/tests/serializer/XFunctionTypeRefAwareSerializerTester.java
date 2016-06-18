/*******************************************************************************
 * Copyright (c) 2011 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.xbase.tests.serializer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.common.types.TypesPackage;
import org.eclipse.xtext.util.EmfFormatter;
import org.eclipse.xtext.xtype.XtypePackage;
import org.junit.Assert;

/**
 * @author Sebastian Zarnekow - Initial contribution and API
 */
public class XFunctionTypeRefAwareSerializerTester extends SerializerTester {

	@Override
	protected void assertEqualWithEmfFormatter(EObject semanticObject, EObject parsed) {
		String expected = EmfFormatter.objToStr(semanticObject, 
				XtypePackage.Literals.XFUNCTION_TYPE_REF__TYPE, TypesPackage.Literals.JVM_SPECIALIZED_TYPE_REFERENCE__EQUIVALENT);
		String actual = EmfFormatter.objToStr(parsed,
				XtypePackage.Literals.XFUNCTION_TYPE_REF__TYPE, TypesPackage.Literals.JVM_SPECIALIZED_TYPE_REFERENCE__EQUIVALENT);
		Assert.assertEquals(expected, actual);
	}
	
}
