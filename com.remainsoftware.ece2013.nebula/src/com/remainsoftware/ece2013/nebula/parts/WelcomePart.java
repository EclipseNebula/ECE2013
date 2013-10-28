package com.remainsoftware.ece2013.nebula.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.ResourceManager;

public class WelcomePart extends BasePart {
	private Composite parent;

	@Inject
	public WelcomePart() {
		// TODO Your code here
	}

	@PostConstruct
	public void postConstruct(Composite parent) {
		this.parent = parent;
		parent.setLayout(null);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setBounds(0, 43, 877, 255);
		composite.setBackgroundImage(ResourceManager.getPluginImage(
				bundle.getSymbolicName(), "img/nebula_logo_main_small.png"));
		parent.setBackground(Display.getDefault().getSystemColor(
				SWT.COLOR_WHITE));
	}

	@Focus
	public void onFocus() {
		parent.setFocus();
	}

}