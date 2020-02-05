/*******************************************************************************
 * Copyright (c) 2012 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.xtext.xbase.compiler;

import org.eclipse.emf.common.notify.impl.AdapterImpl;

/**
 * @author Michael Clay - Initial contribution and API
 */
public class FileHeaderAdapter extends AdapterImpl {

	private String headerText;

	public String getHeaderText() {
		return headerText;
	}

	public void setHeaderText(String documentation) {
		this.headerText = documentation;
	}
	
	@Override
	public boolean isAdapterForType(Object type) {
		return type == FileHeaderAdapter.class;
	}
}
