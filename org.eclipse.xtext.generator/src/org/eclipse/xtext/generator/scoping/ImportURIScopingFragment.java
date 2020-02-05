/*******************************************************************************
 * Copyright (c) 2009 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.xtext.generator.scoping;

import org.eclipse.xtext.scoping.IGlobalScopeProvider;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.scoping.impl.ImportUriGlobalScopeProvider;
import org.eclipse.xtext.scoping.impl.SimpleLocalScopeProvider;

/**
 * @author Sebastian Zarnekow - Initial contribution and API
 */
@Deprecated
public class ImportURIScopingFragment extends AbstractScopingFragment {

	@Override
	protected Class<? extends IScopeProvider> getLocalScopeProvider() {
		return SimpleLocalScopeProvider.class;
	}

	@Override
	protected Class<? extends IGlobalScopeProvider> getGlobalScopeProvider() {
		return ImportUriGlobalScopeProvider.class;
	}
}
