/*******************************************************************************
 * Copyright (c) 2015 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.xtext.xbase.typesystem.internal;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.linking.ILinkingDiagnosticMessageProvider.ILinkingDiagnosticContext;
import org.eclipse.xtext.xbase.typesystem.IResolvedTypes;

/**
 * @author dhuebner - Initial contribution and API
 */
public class TypeAwareLinkingDiagnosticContext implements ILinkingDiagnosticContext {

	private ILinkingDiagnosticContext delegate;
	private IResolvedTypes resolvedTypes;

	public TypeAwareLinkingDiagnosticContext(ILinkingDiagnosticContext delegate, IResolvedTypes resolvedTypes) {
		this.delegate = delegate;
		this.resolvedTypes = resolvedTypes;
	}

	public IResolvedTypes getResolvedTypes() {
		return resolvedTypes;
	}

	@Override
	public EObject getContext() {
		return delegate.getContext();
	}

	@Override
	public EReference getReference() {
		return delegate.getReference();
	}

	@Override
	public String getLinkText() {
		return delegate.getLinkText();
	}

}
