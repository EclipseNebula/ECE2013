package com.remainsoftware.ece2013.nebula.parts.project;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.mihalis.opal.imageSelector.ISItem;
import org.mihalis.opal.imageSelector.ImageSelector;

public class NebulaProject {

	public static final String ID = "projectView";
	private Composite parent;

	@PostConstruct
	void createControl(Composite parent) {
		this.parent = parent;
		parent.setLayout(new FillLayout());

		// Create the list of images
		final List<ISItem> items = new LinkedList<ISItem>();
		items.add(new ISItem("CDateTime", "/img/gallery/cdatetime.png"));
		items.add(new ISItem("Gallery", "/img/gallery/gallery.png"));
		items.add(new ISItem("Gantt", "/img/gallery/gantt.png"));
		items.add(new ISItem("Grid", "/img/gallery/grid.png"));
		items.add(new ISItem("PaperClips", "/img/gallery/paperclips.png"));
		items.add(new ISItem("PGroup", "/img/gallery/pgroup.png"));
		items.add(new ISItem("PShelf", "/img/gallery/pshelf.png"));
		items.add(new ISItem("Scope", "/img/gallery/scope.png"));
		items.add(new ISItem("TableCombo", "/img/gallery/tablecombo.png"));
		items.add(new ISItem("Transition", "/img/gallery/transition.png"));
		items.add(new ISItem("XViewer", "/img/gallery/xviewer.png"));
		final ImageSelector imageSelector = new ImageSelector(parent, SWT.NONE);
		imageSelector.setItems(items);
		imageSelector.setPageIncrement(1);
	}

	@Focus
	void setFocus() {
		parent.setFocus();
	}
}
