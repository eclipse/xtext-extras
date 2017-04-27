/*
 * generated by Xtext
 */
package org.eclipse.xtext.xbase.testlanguages.ide

import com.google.inject.Guice
import org.eclipse.xtext.xbase.testlanguages.XImportSectionTestLangRuntimeModule
import org.eclipse.xtext.xbase.testlanguages.XImportSectionTestLangStandaloneSetup

/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
class XImportSectionTestLangIdeSetup extends XImportSectionTestLangStandaloneSetup {

	override createInjector() {
		Guice.createInjector(new XImportSectionTestLangRuntimeModule, new XImportSectionTestLangIdeModule)
	}
}
