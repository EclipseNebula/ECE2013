package com.remainsoftware.ece2013.nebula.parts;

import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public class BasePart {

	protected static final String TRANSITION = "perspective.transition";
	protected static final String INITIAL = "perspective.initial";
	protected static final String ABOUT = "perspective.about";
	protected static final String OSCILLOSCOPE = "perspective.oscilloscope";
	protected static final String GANTT = "perspective.gantt";
	protected static final String GRID = "perspective.grid";
	protected static final String VIS = "perspective.vis";
	protected static final String GEOMAP = "perspective.geomap";

	@Inject
	MApplication application;
	@Inject
	EPartService partService;
	@Inject
	EModelService modelService;
	Bundle bundle = FrameworkUtil.getBundle(getClass());

	void switchPerspective(String id) {
		MPerspective element = (MPerspective) modelService
				.find(id, application);
		partService.switchPerspective(element);
	}

	void openView(String ID) {
		MPart part = partService.findPart(ID);
		partService.showPart(part, PartState.ACTIVATE);
	}
}
