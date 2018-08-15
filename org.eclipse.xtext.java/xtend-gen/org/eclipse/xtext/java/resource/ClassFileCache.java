/**
 * Copyright (c) 2018 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtext.java.resource;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.jdt.internal.compiler.env.IBinaryAnnotation;
import org.eclipse.jdt.internal.compiler.env.IBinaryField;
import org.eclipse.jdt.internal.compiler.env.IBinaryMethod;
import org.eclipse.jdt.internal.compiler.env.IBinaryNestedType;
import org.eclipse.jdt.internal.compiler.env.IBinaryType;
import org.eclipse.jdt.internal.compiler.env.IBinaryTypeAnnotation;
import org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker;
import org.eclipse.jdt.internal.compiler.lookup.BinaryTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.LookupEnvironment;
import org.eclipse.xtext.util.internal.EmfAdaptable;

/**
 * @author Christian Dietrich - Initial contribution and API
 * @since 2.14
 */
@EmfAdaptable
@SuppressWarnings("all")
public class ClassFileCache {
  public static class IBinaryTypeNullImpl implements IBinaryType {
    @Override
    public ITypeAnnotationWalker enrichWithExternalAnnotationsFor(final ITypeAnnotationWalker walker, final Object member, final LookupEnvironment environment) {
      throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }
    
    @Override
    public IBinaryAnnotation[] getAnnotations() {
      throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }
    
    @Override
    public char[] getEnclosingMethod() {
      throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }
    
    @Override
    public char[] getEnclosingTypeName() {
      throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }
    
    @Override
    public BinaryTypeBinding.ExternalAnnotationStatus getExternalAnnotationStatus() {
      throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }
    
    @Override
    public IBinaryField[] getFields() {
      throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }
    
    @Override
    public char[] getGenericSignature() {
      throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }
    
    @Override
    public char[][] getInterfaceNames() {
      throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }
    
    @Override
    public IBinaryNestedType[] getMemberTypes() {
      throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }
    
    @Override
    public IBinaryMethod[] getMethods() {
      throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }
    
    @Override
    public char[][][] getMissingTypeNames() {
      throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }
    
    @Override
    public char[] getModule() {
      throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }
    
    @Override
    public char[] getName() {
      throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }
    
    @Override
    public char[] getSourceName() {
      throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }
    
    @Override
    public char[] getSuperclassName() {
      throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }
    
    @Override
    public long getTagBits() {
      throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }
    
    @Override
    public IBinaryTypeAnnotation[] getTypeAnnotations() {
      throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }
    
    @Override
    public boolean isAnonymous() {
      throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }
    
    @Override
    public boolean isLocal() {
      throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }
    
    @Override
    public boolean isMember() {
      throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }
    
    @Override
    public char[] sourceFileName() {
      throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }
    
    @Override
    public int getModifiers() {
      throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }
    
    @Override
    public boolean isBinaryType() {
      throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }
    
    @Override
    public char[] getFileName() {
      throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }
  }
  
  public static class ClassFileCacheAdapter extends AdapterImpl {
    private ClassFileCache element;
    
    public ClassFileCacheAdapter(final ClassFileCache element) {
      this.element = element;
    }
    
    public ClassFileCache get() {
      return this.element;
    }
    
    @Override
    public boolean isAdapterForType(final Object object) {
      return object == ClassFileCache.class;
    }
  }
  
  private final static ClassFileCache.IBinaryTypeNullImpl NULL = new ClassFileCache.IBinaryTypeNullImpl();
  
  private final Map<String, IBinaryType> cache = new ConcurrentHashMap<String, IBinaryType>();
  
  public boolean containsKey(final String qualifiedName) {
    return this.cache.containsKey(qualifiedName);
  }
  
  public IBinaryType get(final String qualifiedName) {
    final IBinaryType result = this.cache.get(qualifiedName);
    if ((result == ClassFileCache.NULL)) {
      return null;
    }
    return result;
  }
  
  public void put(final String qualifiedName, final IBinaryType answer) {
    if ((answer == null)) {
      this.cache.put(qualifiedName, ClassFileCache.NULL);
    } else {
      this.cache.put(qualifiedName, answer);
    }
  }
  
  public IBinaryType computeIfAbsent(final String qualifiedName, final Function<? super String, ? extends IBinaryType> fun) {
    return this.cache.computeIfAbsent(qualifiedName, fun);
  }
  
  public void clear() {
    this.cache.clear();
  }
  
  public static ClassFileCache findInEmfObject(final Notifier emfObject) {
    for (Adapter adapter : emfObject.eAdapters()) {
    	if (adapter instanceof ClassFileCache.ClassFileCacheAdapter) {
    		return ((ClassFileCache.ClassFileCacheAdapter) adapter).get();
    	}
    }
    return null;
  }
  
  public static ClassFileCache removeFromEmfObject(final Notifier emfObject) {
    List<Adapter> adapters = emfObject.eAdapters();
    for(int i = 0, max = adapters.size(); i < max; i++) {
    	Adapter adapter = adapters.get(i);
    	if (adapter instanceof ClassFileCache.ClassFileCacheAdapter) {
    		emfObject.eAdapters().remove(i);
    		return ((ClassFileCache.ClassFileCacheAdapter) adapter).get();
    	}
    }
    return null;
  }
  
  public void attachToEmfObject(final Notifier emfObject) {
    ClassFileCache result = findInEmfObject(emfObject);
    if (result != null)
    	throw new IllegalStateException("The given EMF object already contains an adapter for ClassFileCache");
    ClassFileCache.ClassFileCacheAdapter adapter = new ClassFileCache.ClassFileCacheAdapter(this);
    emfObject.eAdapters().add(adapter);
  }
}
