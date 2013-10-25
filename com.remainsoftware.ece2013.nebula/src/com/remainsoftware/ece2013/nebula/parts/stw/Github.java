package com.remainsoftware.ece2013.nebula.parts.stw;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.nebula.effects.stw.Transition;
import org.eclipse.nebula.effects.stw.TransitionListener;
import org.eclipse.nebula.effects.stw.TransitionManager;
import org.eclipse.nebula.effects.stw.Transitionable;
import org.eclipse.nebula.effects.stw.transitions.SlideTransition;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class Github {

	public static final String ID = "githubView";
	private GithubStyleComposite comp1;

	public Github() {
	}

	@PostConstruct
	public void createPart(final Composite parent) {

		comp1 = new GithubStyleComposite(parent, SWT.None);

		TransitionManager _tm = new TransitionManager(new Transitionable() {

			int selection = 1;

			public Control getControl(int index) {
				if (selection == 0)
					return comp1;
				else {
					comp1.setInput(comp1.getSelection());
					return comp1;
				}
			}

			public Composite getComposite() {
				return parent;
			}

			public int getSelection() {
				if (selection == 0) {
					return ++selection;
				}
				return --selection;
			}

			public double getDirection(int toIndex, int fromIndex) {
				if (toIndex > fromIndex)
					return Transition.DIR_LEFT;
				else
					return Transition.DIR_RIGHT;
			}

			@Override
			public void addSelectionListener(SelectionListener listener) {
				comp1.setTransitionListener(listener);
			}

			@Override
			public void setSelection(int index) {
			}
		});
		_tm.setTransition(new SlideTransition(_tm, 250, 300));
		_tm.addTransitionListener(new TransitionListener() {
			public void transitionFinished(TransitionManager transition) {
			}
		});
	}

	@Focus
	void setFocus() {
		comp1.setFocus();
	}
}
