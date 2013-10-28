package com.remainsoftware.ece2013.nebula.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.ResourceManager;
import org.mihalis.opal.starRating.StarRating;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.mihalis.opal.starRating.StarRating.SIZE;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class GoodByePart extends BasePart {
	private Composite parent;

	@Inject
	public GoodByePart() {
		// TODO Your code here
	}

	@PostConstruct
	public void postConstruct(Composite parent) {
		this.parent = parent;

		parent.setLayout(new GridLayout(1, false));
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		Label lblIconsFoundThrough = new Label(parent, SWT.NONE);
		lblIconsFoundThrough.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblIconsFoundThrough.setText("Icons found through Iconfinder");
		lblIconsFoundThrough.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.NORMAL));
		new Label(parent, SWT.NONE);
		
		Label lblThankYouAnd = new Label(parent, SWT.NONE);
		lblThankYouAnd.setForeground(SWTResourceManager.getColor(204, 0, 0));
		lblThankYouAnd.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblThankYouAnd.setText("Thank You and Goodbye");
		lblThankYouAnd.setFont(SWTResourceManager.getFont("Segoe UI", 32, SWT.NORMAL));
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		StarRating starRating = new StarRating(parent, SWT.NONE);
		starRating.setMaxNumberOfStars(10);
		starRating.setSizeOfStars(SIZE.BIG);
		starRating.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
	}

	@Focus
	public void onFocus() {
		parent.setFocus();
	}
}