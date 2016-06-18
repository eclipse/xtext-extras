/**
 * Copyright (c) 2013 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtext.xbase.tests.typesystem;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.eclipse.xtext.xbase.tests.typesystem.InvariantCheckingEagerReentrantTypeResolver;
import org.eclipse.xtext.xbase.typesystem.internal.AbstractRootedReentrantTypeResolver;
import org.eclipse.xtext.xbase.typesystem.internal.DefaultBatchTypeResolver;

/**
 * @author Sebastian Zarnekow
 */
@SuppressWarnings("all")
public class InvariantCheckingEagerBatchTypeResolver extends DefaultBatchTypeResolver {
  @Inject
  private Provider<InvariantCheckingEagerReentrantTypeResolver> resolverProvider;
  
  @Override
  protected AbstractRootedReentrantTypeResolver createResolver() {
    return this.resolverProvider.get();
  }
}
