/*******************************************************************************
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    hal - initial API and implementation
 *******************************************************************************/
package com.remainsoftware.ece2013.nebula.geomap;

import javax.annotation.PostConstruct;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.nebula.widgets.geomap.OsmTileServer;
import org.eclipse.nebula.widgets.geomap.PointD;
import org.eclipse.nebula.widgets.geomap.jface.GeoMapViewer;
import org.eclipse.nebula.widgets.geomap.jface.LabelImageProvider;
import org.eclipse.nebula.widgets.geomap.jface.LocationProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;

public class ConfiguredGeoMap  {

	private GeoMapViewer geoMapViewer;

	@PostConstruct
	public void createControl(Composite parent) {
		geoMapViewer = new GeoMapViewer(parent, SWT.NONE);
		configureMapViewer();
	}

	private static class ContributorLocation {
		public final String name;
		public PointD location;
		public final String locationText;
		public final boolean committer;
		public ContributorLocation(String name, PointD location, String locationText, boolean committer) {
			super();
			this.name = name;
			this.location = location;
			this.locationText = locationText;
			this.committer = committer;
		}
		public String toString() {
			return name + ", " + locationText + " @ " + location.x + ", " + location.y;
		}
	}
	
	private ContributorLocation[] contributorLocations = {
			new ContributorLocation("Hallvard Traetteberg", new PointD(10.4234,63.4242), 	"Trondheim, Norway", 	true),
			new ContributorLocation("Stepan Rutz",		 	new PointD(6.8222,50.9178), 	"Frechen, Germany", 	false),
			new ContributorLocation("Wim Jongman", 			new PointD(4.6410,52.3894),		"Haarlem, Netherlands", true),
			new ContributorLocation("We are here. Hi!", 	new PointD(9.1922,48.8975), 	"Nebula Talk, Germany", true),
			new ContributorLocation("Dirk Fauth", 			new PointD(9.1858,48.7775), 	"Stuttgart, Germany", 	true),
			new ContributorLocation("Tom Schindl", 			new PointD(11.4000,47.2671), 	"Innsbruck, Austria", 	true),
			new ContributorLocation("Matthew Hall",			new PointD(-111.97,40.54), 		"Riverton, Utah, USA", 	true),
			new ContributorLocation("Justin Dolezy",		new PointD(-0.34,51.48), 		"Richmond, Surrey, UK", true),
			new ContributorLocation("Edwin Park",			new PointD(-74.07,40.76), 		"Hoboken, New Jersey, USA", true),
			new ContributorLocation("Mickael Istria",		new PointD(5.7349,45.1872),	 	"Grenoble, France", 	true),
	};
	
	private int indexOfLocation(Object element) {
		for (int i = 0; i < contributorLocations.length; i++) {
			if (element == contributorLocations[i]) {
				return i;
			}
		}
		return -1;
	}
	
	private void configureMapViewer() {
		geoMapViewer.getGeoMap().setTileServer(OsmTileServer.TILESERVERS[0]);
//		geoMapViewer.getGeoMap().setTileServer(GoogleTileServer.TILESERVERS[0]);
		
		geoMapViewer.setLabelProvider(new LabelImageProvider() {
			
			private RGB contributorColor = new RGB(255, 250, 200);
			private RGB committerColor = new RGB(200, 255, 200);
			
			@Override
			public Image getImage(Object element) {
				setFillColor(((ContributorLocation) element).committer ? committerColor : contributorColor);
				return super.getImage(element);
			}
			@Override
			public String getText(Object element) {
				return ((ContributorLocation) element).name;
			}
			@Override
			public Object getToolTip(Object element) {
				if (element instanceof ContributorLocation) {
					return element.toString();
				}
				return null;
			}
		});
		geoMapViewer.setLocationProvider(new LocationProvider() {
			public PointD getLonLat(Object element) {
				int pos = indexOfLocation(element);
				return pos >= 0 ? contributorLocations[pos].location : null;
			}
			public boolean setLonLat(Object element, double lon, double lat) {
				int pos = indexOfLocation(element);
				if (pos < 0) {
					return false;
				}
				contributorLocations[pos].location = new PointD(lon, lat);
				return true;			}
		});
		geoMapViewer.setContentProvider(new ArrayContentProvider());
		geoMapViewer.getControl().getDisplay().asyncExec(new Runnable() {
			public void run() {
				geoMapViewer.setInput(contributorLocations);
			}
		});
	}
}