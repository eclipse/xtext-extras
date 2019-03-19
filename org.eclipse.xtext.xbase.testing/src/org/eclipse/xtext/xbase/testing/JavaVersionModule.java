/*******************************************************************************
 * Copyright (c) 2015 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.xtext.xbase.testing;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.Constants;
import org.eclipse.xtext.util.JavaVersion;
import org.eclipse.xtext.xbase.compiler.GeneratorConfig;
import org.eclipse.xtext.xbase.compiler.GeneratorConfigProvider;
import org.eclipse.xtext.xbase.compiler.IGeneratorConfigProvider;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.name.Named;

/**
 * A Guice module for setting a fixed target Java version for compilation.
 * 
 * @author Miro Spoenemann - Initial contribution and API
 * @since 2.8
 */
public class JavaVersionModule implements Module {

	private final JavaVersion targetVersion;

	/**
	 * @since 2.9
	 */
	public JavaVersionModule(JavaVersion version) {
		this.targetVersion = version;
	}

	@Override
	public void configure(Binder binder) {
		binder.bind(IGeneratorConfigProvider.class).toInstance(new GeneratorConfigProvider());
	}

	private class GeneratorConfigProvider extends org.eclipse.xtext.xbase.compiler.GeneratorConfigProvider {
		@Inject
		@Named(Constants.LANGUAGE_NAME)
		private String languageId;

		@Override
		public GeneratorConfig get(EObject context) {
			Resource _eResource = null;
			if (context != null) {
				_eResource = context.eResource();
			}
			ResourceSet _resourceSet = null;
			if (_eResource != null) {
				_resourceSet = _eResource.getResourceSet();
			}
			final ResourceSet resourceSet = _resourceSet;
			if ((resourceSet != null)) {
				final GeneratorConfigProvider.GeneratorConfigAdapter adapter = GeneratorConfigProvider.GeneratorConfigAdapter
						.findInEmfObject(resourceSet);
				if (((adapter != null) && adapter.getLanguage2GeneratorConfig().containsKey(this.languageId))) {
					return adapter.getLanguage2GeneratorConfig().get(this.languageId);
				}
			}
			GeneratorConfig result = new GeneratorConfig();
			result.setJavaSourceVersion(targetVersion);
			return result;
		}

	}

}
