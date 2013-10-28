package com.remainsoftware.ece2013.nebula.parts;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.nebula.effects.stw.Transition;
import org.eclipse.nebula.effects.stw.TransitionListener;
import org.eclipse.nebula.effects.stw.TransitionManager;
import org.eclipse.nebula.effects.stw.Transitionable;
import org.eclipse.nebula.effects.stw.transitions.SlideTransition;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class RAPView extends BasePart {

	public static final String ID = "rapView";

	public RAPView() {
	}

	private Composite comp1 = null;
	private Composite comp2 = null;
	private Composite comp3 = null;
	private Composite comp4 = null;

	private TabFolder tf;

	@PostConstruct
	public void createPart(final Composite parent) {

		final Composite container = new Composite(parent, SWT.None);

		container.setLayout(new FillLayout());

		tf = new TabFolder(container, SWT.NONE);

		TabItem tbi1 = new TabItem(tf, SWT.NONE);
		tbi1.setText("Grid");
		tbi1.setControl(getComp1(tf));

		TabItem tbi2 = new TabItem(tf, SWT.NONE);
		tbi2.setText("Picture Control");
		tbi2.setControl(getComp2(tf));

		TabItem tbi3 = new TabItem(tf, SWT.NONE);
		tbi3.setText("Pagination Control");
		tbi3.setControl(getComp3(tf));

		TabItem tbi4 = new TabItem(tf, SWT.NONE);
		tbi4.setText("Oscilloscope in the browser");
		tbi4.setControl(getComp4(tf));

		TransitionManager _tm = new TransitionManager(new Transitionable() {
			public void addSelectionListener(SelectionListener listener) {
				tf.addSelectionListener(listener);
			}

			public Control getControl(int index) {
				return  tf.getItem(index).getControl();
			}

			public Composite getComposite() {
				return tf;
			}

			public int getSelection() {
				return tf.getSelectionIndex();
			}

			public void setSelection(int index) {
				System.out.println("set index " + index);
				tf.setSelection(index);
			}

			public double getDirection(int toIndex, int fromIndex) {
				if (toIndex > fromIndex)
					return Transition.DIR_LEFT;
				else
					return Transition.DIR_RIGHT;
			}
		});
		_tm.setTransition(new SlideTransition(_tm, 250, 1000));
		_tm.addTransitionListener(new TransitionListener() {
			public void transitionFinished(TransitionManager transition) {
			}
		});

	}

	@Focus
	void setFocus() {
		comp1.setFocus();
	}

	private Composite getComp1(Composite parent) {
		if (null == comp1) {
			comp1 = new Composite(parent, SWT.NONE);
			comp1.setLayout(new FillLayout());
			Browser b = new Browser(comp1, SWT.PUSH);
			b.setUrl("http://eclipsesource.com/blogs/2012/06/29/nebula-grid-widget-on-rap/");
		}
		return comp1;
	}

	private Composite getComp2(Composite parent) {
		if (null == comp2) {
			comp2 = new Composite(parent, SWT.NONE);
			comp2.setLayout(new FillLayout());
			Browser b = new Browser(comp2, SWT.None);
			b.setUrl("http://angelozerr.wordpress.com/2012/01/06/nebula_picture/");
		}
		return comp2;
	}

	private Composite getComp3(Composite parent) {
		if (null == comp3) {
			comp3 = new Composite(parent, SWT.NONE);
			comp3.setLayout(new FillLayout());
			Browser b = new Browser(comp3, SWT.None);
			b.setUrl("http://angelozerr.wordpress.com/2012/01/06/nebula_pagination/");
		}
		return comp3;
	}

	private Composite getComp4(Composite parent) {
		if (null == comp4) {
			comp4 = new Composite(parent, SWT.NONE);
			comp4.setLayout(new FillLayout());
			Browser b = new Browser(comp4, SWT.None);
			b.setUrl("http://eclipsesource.com/blogs/2010/08/25/an-oscilloscope-in-the-browser/");
		}
		return comp4;
	}

	public static void open(EPartService partService) {
		MPart part = partService.findPart(ID);
		partService.showPart(part, PartState.ACTIVATE);
	}
}
