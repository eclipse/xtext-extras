/**
 * Copyright (c) 2013 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtext.xbase.tests.typesystem;

import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.xbase.tests.typesystem.EagerReentrantTypeResolver;
import org.eclipse.xtext.xbase.tests.typesystem.ValidatingRootResolvedTypes;
import org.eclipse.xtext.xbase.typesystem.internal.RootResolvedTypes;

/**
 * @author Sebastian Zarnekow
 */
@SuppressWarnings("all")
public class InvariantCheckingEagerReentrantTypeResolver extends EagerReentrantTypeResolver {
  @Override
  protected RootResolvedTypes createResolvedTypes(final CancelIndicator monitor) {
    return new ValidatingRootResolvedTypes(this, monitor);
  }
}
