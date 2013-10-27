package com.remainsoftware.ece2013.nebula.visualization;

/*******************************************************************************
 * Copyright (c) 2010 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.SchemeBorder;
import org.eclipse.nebula.visualization.widgets.figures.MeterFigure;
import org.eclipse.nebula.visualization.xygraph.util.XYGraphMediaFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

/**
 * A live updated Gauge Example.
 * 
 * @author Xihui Chen
 * 
 */
public class Meter extends Composite {
	private MeterFigure meterFigure;
	private int value;

	public Meter(Composite parent, int style) {
		super(parent, style);
		main(this);
	}

	public void main(Composite parent) {
		parent.setLayout(new FillLayout());
		// use LightweightSystem to create the bridge between SWT and draw2D
		Canvas canvas = new Canvas(parent, SWT.NONE);
		final LightweightSystem lws = new LightweightSystem(canvas);

		meterFigure = new MeterFigure();

		// Init gauge
		meterFigure.setBackgroundColor(XYGraphMediaFactory.getInstance()
				.getColor(255, 255, 255));

		meterFigure.setBorder(new SchemeBorder(SchemeBorder.SCHEMES.ETCHED));

		meterFigure.setRange(0, 250);
		meterFigure.setLoLevel(20);
		meterFigure.setLoloLevel(30);
		meterFigure.setHiLevel(60);
		meterFigure.setHihiLevel(80);
		meterFigure.setMajorTickMarkStepHint(50);

		lws.setContents(meterFigure);

	}

	public void setValue(int value) {
		this.value = value;
		meterFigure.setValue(value);
	}

	public int getValue() {
		return value;
	}
}