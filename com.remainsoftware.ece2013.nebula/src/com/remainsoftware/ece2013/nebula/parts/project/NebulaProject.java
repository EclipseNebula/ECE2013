package com.remainsoftware.ece2013.nebula.parts.project;

import java.util.LinkedList;
import java.util.List;

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
import org.mihalis.opal.imageSelector.ISItem;
import org.mihalis.opal.imageSelector.ImageSelector;

import com.remainsoftware.ece2013.nebula.parts.BasePart;

public class NebulaProject extends BasePart {

	public static final String ID = "projectView";

	public NebulaProject() {
	}

	private Composite comp1 = null;
	private Composite comp2 = null;
	private Composite comp3 = null;
	private Composite comp4 = null;
	private Composite comp5 = null;
	
	private TabFolder tf;
	@PostConstruct
	public void createPart(final Composite parent) {

		final Composite container = new Composite(parent, SWT.None);

		container.setLayout(new FillLayout());

		tf = new TabFolder(container, SWT.NONE);

		TabItem tbi1 = new TabItem(tf, SWT.NONE);
		tbi1.setText("Widgets");
		tbi1.setControl(getComp1(tf));

		TabItem tbi2 = new TabItem(tf, SWT.NONE);
		tbi2.setText("Project Home");
		tbi2.setControl(getComp2(tf));

		TabItem tbi3 = new TabItem(tf, SWT.NONE);
		tbi3.setText("Downloads	");
		tbi3.setControl(getComp3(tf));
		
		TabItem tbi4 = new TabItem(tf, SWT.NONE);
		tbi4.setText("NatTable");
		tbi4.setControl(getComp4(tf));
		
		TabItem tbi5 = new TabItem(tf, SWT.NONE);
		tbi5.setText("How to Contribute");
		tbi5.setControl(getComp5(tf));

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
			final ImageSelector imageSelector = new ImageSelector(comp1, SWT.NONE);
			imageSelector.setItems(items);
			imageSelector.setPageIncrement(1);
		}
		return comp1;
	}

	private Composite getComp2(Composite parent) {
		if (null == comp2) {
			comp2 = new Composite(parent, SWT.NONE);
			comp2.setLayout(new FillLayout());
			Browser b = new Browser(comp2, SWT.None);
			b.setUrl("http://eclipse.org/nebula");
		}
		return comp2;
	}

	private Composite getComp3(Composite parent) {
		if (null == comp3) {
			comp3 = new Composite(parent, SWT.NONE);
			comp3.setLayout(new FillLayout());
			Browser b = new Browser(comp3, SWT.None);
			b.setUrl("http://eclipse.org/nebula/downloads.php");
		}
		return comp3;
	}

	private Composite getComp4(Composite parent) {
		if (null == comp4) {
			comp4 = new Composite(parent, SWT.NONE);
			comp4.setLayout(new FillLayout());
			Browser b = new Browser(comp4, SWT.None);
			b.setUrl("http://eclipse.org/nattable/");
		}
		return comp4;
	}

	private Composite getComp5(Composite parent) {
		if (null == comp5) {
			comp5 = new Composite(parent, SWT.NONE);
			comp5.setLayout(new FillLayout());
			Browser b = new Browser(comp5, SWT.None);
			b.setUrl("http://eclipse.org/nebula/contrib_process.php");
		}
		return comp5;
	}

	public static void open(EPartService partService) {
		MPart part = partService.findPart(ID);
		partService.showPart(part, PartState.ACTIVATE);
	}
}
