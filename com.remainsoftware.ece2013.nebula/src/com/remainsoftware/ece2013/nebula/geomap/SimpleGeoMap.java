package com.remainsoftware.ece2013.nebula.geomap;

import javax.annotation.PostConstruct;

import org.eclipse.nebula.widgets.geomap.GeoMapBrowser;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

public class SimpleGeoMap {

	@PostConstruct
	public void main(Composite parent) {
		parent.setLayout(new FillLayout());
		new GeoMapBrowser(parent, SWT.NONE);
	}
}