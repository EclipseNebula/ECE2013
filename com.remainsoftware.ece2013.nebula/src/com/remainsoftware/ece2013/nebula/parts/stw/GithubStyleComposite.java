package com.remainsoftware.ece2013.nebula.parts.stw;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.wb.swt.ResourceManager;
import org.osgi.framework.FrameworkUtil;

public class GithubStyleComposite extends Composite implements
		ISelectionChangedListener {
	private static class ContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {
			return ((XFile) inputElement).getChildren().toArray();
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	private Table table;
	private TableViewer viewer;
	private SelectionListener transistionListener;
	private XFile selection;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public GithubStyleComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite composite = new Composite(this, SWT.NONE);
		TableColumnLayout tcl_composite = new TableColumnLayout();
		composite.setLayout(tcl_composite);

		viewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table = viewer.getTable();
		table.setLinesVisible(true);

		TableViewerColumn tableViewerColumn = new TableViewerColumn(viewer,
				SWT.NONE);
		tableViewerColumn.setLabelProvider(new ColumnLabelProvider() {
			private String bundleId = FrameworkUtil.getBundle(getClass())
					.getSymbolicName();

			public Image getImage(Object element) {
				if (((XFile) element).getChildren().size() > 0) {
					return ResourceManager.getPluginImage(bundleId,
							"/icons/folder.gif");
				} else
					return ResourceManager.getPluginImage(bundleId,
							"/icons/file.png");
			}

			public String getText(Object element) {
				return ((XFile) element).getName();
			}
		});
		TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
		tcl_composite.setColumnData(tblclmnNewColumn, new ColumnWeightData(1,
				ColumnWeightData.MINIMUM_WIDTH, true));
		tblclmnNewColumn.setText("name");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(viewer,
				SWT.NONE);
		tableViewerColumn_1.setLabelProvider(new ColumnLabelProvider() {
			public Image getImage(Object element) {
				return null;
			}

			public String getText(Object element) {
				return ((XFile) element).getDescription();
			}
		});
		TableColumn tblclmnNewColumn_1 = tableViewerColumn_1.getColumn();
		tcl_composite.setColumnData(tblclmnNewColumn_1, new ColumnWeightData(1,
				ColumnWeightData.MINIMUM_WIDTH, true));
		tblclmnNewColumn_1.setText("New Column");
		viewer.setContentProvider(new ContentProvider());

		viewer.addSelectionChangedListener(this);
		viewer.setInput(new XFile(null, "parent", "parent", true));

	}

	public void setInput(XFile file) {
		viewer.setInput(file);
	}

	@Override
	protected void checkSubclass() {
	}

	public void setTransitionListener(SelectionListener transistionListener) {
		this.transistionListener = transistionListener;

	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		if (event.getSelection().isEmpty()) {
			return;
		}
		if (transistionListener != null) {
			selection = (XFile) ((IStructuredSelection) viewer.getSelection())
					.getFirstElement();
			Event event2 = new Event();
			event2.data = event;
			event2.widget = this;
			transistionListener.widgetSelected(new SelectionEvent(event2));
		}
	}

	public XFile getSelection() {
		return selection;
	}
}
