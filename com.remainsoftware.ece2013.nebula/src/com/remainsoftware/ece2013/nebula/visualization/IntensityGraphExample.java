package com.remainsoftware.ece2013.nebula.visualization;

/*******************************************************************************
 * Copyright (c) 2010 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.nebula.visualization.widgets.datadefinition.ColorMap;
import org.eclipse.nebula.visualization.widgets.datadefinition.ColorMap.PredefinedColorMap;
import org.eclipse.nebula.visualization.widgets.figures.IntensityGraphFigure;
import org.eclipse.nebula.visualization.widgets.figures.IntensityGraphFigure.IROIListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * A live updated Intensity Graph example
 * 
 * @author Xihui Chen
 * 
 */
public class IntensityGraphExample {
	private static int count = 0;
	private static final int DataHeight = 1024;
	private static final int DataWidth = 1280;
	private ScheduledFuture<?> future;
	private ScheduledExecutorService scheduler;

	@PostConstruct
	public void main(Composite parent) {

		parent.setLayout(new FillLayout());
		Canvas canvas = new Canvas(parent, SWT.NONE);

		// use LightweightSystem to create the bridge between SWT and draw2D
		final LightweightSystem lws = new LightweightSystem(canvas);

		// Create Intensity Graph
		final IntensityGraphFigure intensityGraph = new IntensityGraphFigure();

		// Create Simulation Data
		final short[] simuData = new short[DataWidth * DataHeight * 2];
		final short[] data = new short[DataWidth * DataHeight];
		int seed = count++;
		for (int i = 0; i < DataHeight; i++) {
			for (int j = 0; j < DataWidth; j++) {
				int x = j - DataWidth;
				int y = i - DataHeight;
				int p = (int) Math.sqrt(x * x + y * y);
				simuData[i * DataWidth + j] = (short) (Math.sin(p * 2 * Math.PI
						/ DataWidth + seed * Math.PI / 100) * 100);
			}
		}

		// Configure
		intensityGraph.setMax(100);
		intensityGraph.setMin(-100);
		intensityGraph.setDataHeight(DataHeight);
		intensityGraph.setDataWidth(DataWidth);
		intensityGraph.setColorMap(new ColorMap(PredefinedColorMap.JET, true,
				true));
		intensityGraph.addROI("ROI 1", new IROIListener() {

			@Override
			public void roiUpdated(int xIndex, int yIndex, int width, int height) {
				System.out.println("Region of Interest: (" + xIndex + ", "
						+ yIndex + ", " + width + ", " + height + ")");
			}
		}, null);
		lws.setContents(intensityGraph);

		scheduler = Executors.newScheduledThreadPool(1);
		future = scheduler.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				System.arraycopy(simuData, count % DataWidth, data, 0,
						DataWidth * DataHeight);

				Display.getDefault().asyncExec(new Runnable() {

					public void run() {
						count++;
						intensityGraph.setDataArray(simuData);
					}
				});
			}
		}, 100, 10, TimeUnit.MILLISECONDS);
	}

	@PreDestroy
	void dispose() {
		future.cancel(true);
		scheduler.shutdown();

	}
}
