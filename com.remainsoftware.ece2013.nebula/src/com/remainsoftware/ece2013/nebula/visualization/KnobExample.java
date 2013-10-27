package com.remainsoftware.ece2013.nebula.visualization;

/*******************************************************************************
 * Copyright (c) 2010 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
import javax.annotation.PostConstruct;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.nebula.visualization.widgets.datadefinition.IManualValueChangeListener;
import org.eclipse.nebula.visualization.widgets.figures.KnobFigure;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * A live updated Gauge Example.
 * 
 * @author Xihui Chen
 * 
 */
public class KnobExample {

	@PostConstruct
	public void main(Composite parent) {
		

		parent.setLayout(new FillLayout());
		Canvas canvas = new Canvas(parent, SWT.NONE);

		// use LightweightSystem to create the bridge between SWT and draw2D
		final LightweightSystem lws = new LightweightSystem(canvas);

		// Create Knob
		final KnobFigure knobFigure = new KnobFigure();

		// Init Knob
		knobFigure.setRange(-100, 100);
		knobFigure.setLoLevel(-50);
		knobFigure.setLoloLevel(-80);
		knobFigure.setHiLevel(60);
		knobFigure.setHihiLevel(80);
		knobFigure.setMajorTickMarkStepHint(50);
		knobFigure.setThumbColor(ColorConstants.gray);
		knobFigure
				.addManualValueChangeListener(new IManualValueChangeListener() {
					@Override
					public void manualValueChanged(double newValue) {
						System.out.println("You set value to: " + newValue);
					}
				});

		lws.setContents(knobFigure);
	}
}
