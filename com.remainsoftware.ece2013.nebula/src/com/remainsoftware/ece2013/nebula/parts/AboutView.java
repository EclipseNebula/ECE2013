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
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class AboutView extends BasePart {

	public static final String ID = "aboutView";

	public AboutView() {
	}

	private Composite comp1 = null;
	private Composite comp2 = null;
	private Composite comp3 = null;

	private TabFolder tf;

	@PostConstruct
	public void createPart(final Composite parent) {

		final Composite container = new Composite(parent, SWT.None);

		container.setLayout(new FillLayout());

		tf = new TabFolder(container, SWT.NONE);

		TabItem tbi1 = new TabItem(tf, SWT.NONE);
		tbi1.setText("Name");
		tbi1.setControl(getComp1(tf));

		TabItem tbi2 = new TabItem(tf, SWT.NONE);
		tbi2.setText("Brag Rights");
		tbi2.setControl(getComp2(tf));

		TabItem tbi3 = new TabItem(tf, SWT.NONE);
		tbi3.setText("Company");
		tbi3.setControl(getComp3(tf));

		TransitionManager _tm = new TransitionManager(new Transitionable() {
			public void addSelectionListener(SelectionListener listener) {
				tf.addSelectionListener(listener);
			}

			public Control getControl(int index) {
				return tf.getItem(index).getControl();
			}

			public Composite getComposite() {
				return tf;
			}

			public int getSelection() {
				return tf.getSelectionIndex();
			}

			public void setSelection(int index) {
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
			b.setUrl("https://www.google.nl/search?q=whim");
		}
		return comp1;
	}

	private Composite getComp2(Composite parent) {
		if (null == comp2) {
			comp2 = new Composite(parent, SWT.NONE);
			comp2.setLayout(new FillLayout());
			Browser b = new Browser(comp2, SWT.None);
			b.setUrl("https://www.google.nl/search?q=wim+jongman+eclipse&safe=off&espv=210&es_sm=93&source=lnms&tbm=isch&sa=X&ei=DMNpUuGgJKXP0QWs_oDwBQ&ved=0CAcQ_AUoAQ&biw=1440&bih=770#facrc=_&imgdii=_&imgrc=_AwejwmH5SUPIM%3A%3BQPFg4g-ZOIQWDM%3Bhttp%253A%252F%252Flh4.googleusercontent.com%252F-v7Q6Kntbg5s%252FAAAAAAAAAAI%252FAAAAAAAAARc%252FRy_iKL2MNe4%252Fs512-c%252Fphoto.jpg%3Bhttp%253A%252F%252Findustrial-tsi-wim.blogspot.com%252F2012%252F10%252Fwhy-eclipse-e4-egg-laying-woolmilkpig.html%3B320%3B320");
		}
		return comp2;
	}

	private Composite getComp3(Composite parent) {
		if (null == comp3) {
			comp3 = new Composite(parent, SWT.NONE);
			comp3.setLayout(new FillLayout());
			Browser b = new Browser(comp3, SWT.None);
			b.setUrl("http://www.weltevree.com/");
		}
		return comp3;
	}

	public static void open(EPartService partService) {
		MPart part = partService.findPart(ID);
		partService.showPart(part, PartState.ACTIVATE);
	}
}
