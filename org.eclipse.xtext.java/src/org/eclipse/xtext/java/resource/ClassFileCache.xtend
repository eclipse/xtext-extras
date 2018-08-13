/*******************************************************************************
 * Copyright (c) 2018 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.java.resource

import java.util.HashMap
import java.util.Map
import org.eclipse.jdt.internal.compiler.env.IBinaryType
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.util.internal.EmfAdaptable
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Function
import org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
import org.eclipse.jdt.internal.compiler.lookup.LookupEnvironment

/**
 * @author Christian Dietrich - Initial contribution and API
 * @since 2.14
 */
@EmfAdaptable
class ClassFileCache {
	
	static val NULL = new IBinaryTypeNullImpl()
	
	//TODO: concurrency
	//TODO: clear
	//TODO: weak?
	val Map<String, IBinaryType> cache = new ConcurrentHashMap()
	
	def boolean containsKey(String qualifiedName) {
		return cache.containsKey(qualifiedName)
	}
	
	def IBinaryType get(String qualifiedName) {
		val result = cache.get(qualifiedName)
		if (result === NULL) {
			return null
		}
		return result
	}
	
	def void put(String qualifiedName, IBinaryType answer) {
		if (answer === null) {
			cache.put(qualifiedName, NULL)			
		} else {
			cache.put(qualifiedName, answer)
		}
	}
	
	def IBinaryType computeIfAbsent(String qualifiedName, Function<? super String, ? extends IBinaryType> fun) {
		cache.computeIfAbsent(qualifiedName, fun)
	}
	
	def void clear() {
		cache.clear()
	}
	
	static class IBinaryTypeNullImpl implements IBinaryType{
		
		override enrichWithExternalAnnotationsFor(ITypeAnnotationWalker walker, Object member, LookupEnvironment environment) {
			throw new UnsupportedOperationException("TODO: auto-generated method stub")
		}
		
		override getAnnotations() {
			throw new UnsupportedOperationException("TODO: auto-generated method stub")
		}
		
		override getEnclosingMethod() {
			throw new UnsupportedOperationException("TODO: auto-generated method stub")
		}
		
		override getEnclosingTypeName() {
			throw new UnsupportedOperationException("TODO: auto-generated method stub")
		}
		
		override getExternalAnnotationStatus() {
			throw new UnsupportedOperationException("TODO: auto-generated method stub")
		}
		
		override getFields() {
			throw new UnsupportedOperationException("TODO: auto-generated method stub")
		}
		
		override getGenericSignature() {
			throw new UnsupportedOperationException("TODO: auto-generated method stub")
		}
		
		override getInterfaceNames() {
			throw new UnsupportedOperationException("TODO: auto-generated method stub")
		}
		
		override getMemberTypes() {
			throw new UnsupportedOperationException("TODO: auto-generated method stub")
		}
		
		override getMethods() {
			throw new UnsupportedOperationException("TODO: auto-generated method stub")
		}
		
		override getMissingTypeNames() {
			throw new UnsupportedOperationException("TODO: auto-generated method stub")
		}
		
		override getModule() {
			throw new UnsupportedOperationException("TODO: auto-generated method stub")
		}
		
		override getName() {
			throw new UnsupportedOperationException("TODO: auto-generated method stub")
		}
		
		override getSourceName() {
			throw new UnsupportedOperationException("TODO: auto-generated method stub")
		}
		
		override getSuperclassName() {
			throw new UnsupportedOperationException("TODO: auto-generated method stub")
		}
		
		override getTagBits() {
			throw new UnsupportedOperationException("TODO: auto-generated method stub")
		}
		
		override getTypeAnnotations() {
			throw new UnsupportedOperationException("TODO: auto-generated method stub")
		}
		
		override isAnonymous() {
			throw new UnsupportedOperationException("TODO: auto-generated method stub")
		}
		
		override isLocal() {
			throw new UnsupportedOperationException("TODO: auto-generated method stub")
		}
		
		override isMember() {
			throw new UnsupportedOperationException("TODO: auto-generated method stub")
		}
		
		override sourceFileName() {
			throw new UnsupportedOperationException("TODO: auto-generated method stub")
		}
		
		override getModifiers() {
			throw new UnsupportedOperationException("TODO: auto-generated method stub")
		}
		
		override isBinaryType() {
			throw new UnsupportedOperationException("TODO: auto-generated method stub")
		}
		
		override getFileName() {
			throw new UnsupportedOperationException("TODO: auto-generated method stub")
		}
		
	}
	
}