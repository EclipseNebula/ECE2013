package com.remainsoftware.ece2013.nebula.snippets.grid;

import javax.annotation.PostConstruct;

import org.eclipse.nebula.widgets.geomap.GeoMapBrowser;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class GeoMapBrowserSnippet01 {

	@PostConstruct
	public void main(Composite parent) {
		parent.setLayout(new FillLayout());
		new GeoMapBrowser(parent, SWT.NONE);
		parent.open();
	}
}