/*******************************************************************************
 * Copyright (c) 2012 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.xtext.xbase.typesystem.internal;

import java.util.List;

import org.eclipse.xtext.common.types.JvmIdentifiableElement;
import org.eclipse.xtext.common.types.JvmTypeParameter;
import org.eclipse.xtext.diagnostics.AbstractDiagnostic;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.xbase.XExpression;
import org.eclipse.xtext.xbase.typesystem.IResolvedTypes;
import org.eclipse.xtext.xbase.typesystem.computation.IApplicableCandidate;
import org.eclipse.xtext.xbase.typesystem.references.ITypeReferenceOwner;
import org.eclipse.xtext.xbase.typesystem.references.LightweightBoundTypeArgument;
import org.eclipse.xtext.xbase.typesystem.references.LightweightTypeReference;
import org.eclipse.xtext.xbase.typesystem.references.UnboundTypeReference;

/**
 * @author Sebastian Zarnekow - Initial contribution and API
 * TODO JavaDoc, toString - delegates to parent except for the reassigned types
 */
public class ReassigningStackedResolvedTypes extends StackedResolvedTypes {
	
	protected ReassigningStackedResolvedTypes(ResolvedTypes parent) {
		super(parent);
	}
	
	@Override
	protected void acceptCandidate(XExpression expression, IApplicableCandidate candidate) {
		getParent().acceptCandidate(expression, candidate);
	}
	
	@Override
	public ITypeReferenceOwner getReferenceOwner() {
		return getParent().getReferenceOwner();
	}
	
	@Override
	protected void acceptType(XExpression expression, TypeData typeData) {
		getParent().acceptType(expression, typeData);
	}
	
	@Override
	protected void setPropagatedType(XExpression expression) {
		getParent().setPropagatedType(expression);
	}
	
	@Override
	protected void setRefinedType(XExpression expression) {
		getParent().setRefinedType(expression);
	}
	
	@Override
	public void setType(JvmIdentifiableElement identifiable, LightweightTypeReference reference) {
		getParent().setType(identifiable, reference);
	}

	@Override
	public void acceptHint(Object handle, LightweightBoundTypeArgument boundTypeArgument) {
		getParent().acceptHint(handle, boundTypeArgument);
	}
	
	@Override
	protected void acceptUnboundTypeReference(Object handle, UnboundTypeReference reference) {
		getParent().acceptUnboundTypeReference(handle, reference);
	}
	
	@Override
	public void addDiagnostic(AbstractDiagnostic diagnostic) {
		getParent().addDiagnostic(diagnostic);
	}
	
	@Override
	protected void addDeferredLogic(IAcceptor<? super IResolvedTypes> code) {
		getParent().addDeferredLogic(code);
	}
	
	@Override
	public List<LightweightBoundTypeArgument> getAllHints(Object handle) {
		return getParent().getAllHints(handle);
	}
	
	@Override
	protected void mergeInto(ResolvedTypes parent) {
		throw new UnsupportedOperationException("Should not be invoked");
	}
	
	@Override
	protected UnboundTypeReference createUnboundTypeReference(XExpression expression,
			JvmTypeParameter type) {
		throw new UnsupportedOperationException("Should not be invoked");
	}
	
	@Override
	protected void mergeIntoParent() {
		throw new UnsupportedOperationException("Should not be invoked");
	}
	
	@Override
	protected void performMergeIntoParent() {
		throw new UnsupportedOperationException("Should not be invoked");
	}
	
	@Override
	protected void prepareMergeIntoParent() {
		throw new UnsupportedOperationException("Should not be invoked");
	}
	
}