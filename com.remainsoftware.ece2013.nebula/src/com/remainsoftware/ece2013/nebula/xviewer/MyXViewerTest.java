package com.remainsoftware.ece2013.nebula.xviewer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.nebula.widgets.xviewer.edit.DefaultXViewerControlFactory;
import org.eclipse.nebula.widgets.xviewer.edit.XViewerControlFactory;
import org.eclipse.nebula.widgets.xviewer.edit.XViewerConverter;
import org.eclipse.nebula.widgets.xviewer.edit.XViewerEditAdapter;
import org.eclipse.nebula.widgets.xviewer.example.MyDefaultCustomizations;
import org.eclipse.nebula.widgets.xviewer.example.MyXViewer;
import org.eclipse.nebula.widgets.xviewer.example.MyXViewerContentProvider;
import org.eclipse.nebula.widgets.xviewer.example.MyXViewerConverter;
import org.eclipse.nebula.widgets.xviewer.example.MyXViewerLabelProvider;
import org.eclipse.nebula.widgets.xviewer.example.images.MyImageCache;
import org.eclipse.nebula.widgets.xviewer.example.model.ISomeTask;
import org.eclipse.nebula.widgets.xviewer.example.model.ISomeTask.RunDb;
import org.eclipse.nebula.widgets.xviewer.example.model.ISomeTask.TaskType;
import org.eclipse.nebula.widgets.xviewer.example.model.SomeTask;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

/**
 * Example use of XViewer. Run as application to see sample XViewer.
 * 
 * @author Donald G. Dunne
 */
public class MyXViewerTest {
	static MyXViewer myXviewer;

	@PostConstruct
	public void main(Composite parent) {
		// Get the Display. Create on if none exists

		parent.setLayout(new GridLayout());
		parent.setLayoutData(new GridData(GridData.FILL_BOTH
				| GridData.HORIZONTAL_ALIGN_BEGINNING));

		// Button composite for state transitions, etc
		Composite toolBarComposite = new Composite(parent, SWT.NONE);
		// bComp.setBackground(mainSComp.getDisplay().getSystemColor(SWT.COLOR_CYAN));
		toolBarComposite.setLayout(new GridLayout(2, false));
		toolBarComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		myXviewer = new MyXViewer(parent, SWT.MULTI | SWT.BORDER
				| SWT.FULL_SELECTION);
		myXviewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		myXviewer.setContentProvider(new MyXViewerContentProvider());
		myXviewer.setLabelProvider(new MyXViewerLabelProvider(myXviewer));
		// myXviewer.setLabelProvider(new
		// MyXViewerStyledTextLabelProvider(myXviewer));

		// XViewerEditAdapter
		XViewerControlFactory cFactory = new DefaultXViewerControlFactory();
		XViewerConverter converter = new MyXViewerConverter();
		myXviewer.setXViewerEditAdapter(new XViewerEditAdapter(cFactory,
				converter));

		createTaskActionBar(toolBarComposite);

		List<Object> tasks = new ArrayList<Object>();
		for (int x = 0; x < 1; x++) {
			tasks.addAll(getTestTasks());
		}
		myXviewer.setInput(tasks);
	}

	public static void createTaskActionBar(Composite parent) {

		Composite actionComp = new Composite(parent, SWT.NONE);
		actionComp.setLayout(new GridLayout());
		actionComp.setLayoutData(new GridData(GridData.END));

		ToolBar toolBar = new ToolBar(actionComp, SWT.FLAT | SWT.RIGHT);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		toolBar.setLayoutData(gd);

		ToolItem refreshItem = new ToolItem(toolBar, SWT.PUSH);
		refreshItem.setImage(MyImageCache.getImage("refresh.gif"));
		refreshItem.setToolTipText("Refresh");
		refreshItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				List<Object> tasks = new ArrayList<Object>();
				for (int x = 0; x < 1; x++) {
					tasks.addAll(getTestTasks());
				}
				myXviewer.setInput(tasks);
			}
		});

		Action dropDownAction = myXviewer.getCustomizeAction();
		new ActionContributionItem(dropDownAction).fill(toolBar, 0);

		ToolItem descriptionItem = new ToolItem(toolBar, SWT.PUSH);
		descriptionItem.setImage(MyImageCache.getImage("descriptionView.gif"));
		descriptionItem.setToolTipText("Show Description View");
		descriptionItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				myXviewer.getCustomizeMgr().loadCustomization(
						MyDefaultCustomizations.getDescriptionCustomization());
				myXviewer.refresh();
			}
		});

		ToolItem completeItem = new ToolItem(toolBar, SWT.PUSH);
		completeItem.setImage(MyImageCache.getImage("completionView.gif"));
		completeItem.setToolTipText("Show Completion View");
		completeItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				myXviewer.getCustomizeMgr().loadCustomization(
						MyDefaultCustomizations.getCompletionCustomization());
				myXviewer.refresh();
			}
		});

	}

	private static Date date = new Date();

	private static Date getDate() {
		date = new Date(date.getTime() + (60000 * 60 * 2));
		return date;
	}

	private static List<ISomeTask> getTestTasks() {
		List<ISomeTask> tasks = new ArrayList<ISomeTask>();
		SomeTask task = new SomeTask(RunDb.Test_Db, TaskType.Backup, getDate(),
				"org.eclipse.osee.test1", "10:03", "run to test this",
				"Suite A", "mark@eclipse.com", 50);
		tasks.add(task);

		for (int x = 0; x < 5; x++) {
			task.addChild(new SomeTask(RunDb.Test_Db, TaskType.Backup,
					getDate(), "org.eclipse.osee.test33", "10:03",
					"run to test isit this - child " + x, "Suite A",
					"mark@eclipse.com", 50));
		}

		tasks.add(new SomeTask(RunDb.Production_Db, TaskType.Data_Exchange,
				getDate(), "org.eclipse.osee.test2", "9:22",
				"run to test that", "Suite B", "john@eclipse.com", 0));
		tasks.add(new SomeTask(RunDb.Production_Db, TaskType.Backup, getDate(),
				"org.eclipse.osee.test4", "8:23", "in this world", "Suite A",
				"john@eclipse.com", 50));
		tasks.add(new SomeTask(RunDb.Test_Db, TaskType.Backup, getDate(),
				"org.eclipse.osee.test3", "23:01", "now is the time",
				"Suite B", "mike@eclipse.com", 30));
		tasks.add(new SomeTask(RunDb.Production_Db, TaskType.Db_Health,
				getDate(), "org.eclipse.osee.test5", "7:32", "may be never",
				"Suite A", "steve@eclipse.com", 10));
		tasks.add(new SomeTask(RunDb.Test_Db, TaskType.Data_Exchange,
				getDate(), "org.eclipse.osee.test14", "6:11", "", "Suite A",
				"steve@eclipse.com", 95));
		tasks.add(new SomeTask(RunDb.Production_Db, TaskType.Backup, getDate(),
				"org.eclipse.osee.test6", "5:13", "run to test this",
				"Suite B", "john@eclipse.com", 80));
		tasks.add(new SomeTask(RunDb.Test_Db, TaskType.Db_Health, getDate(),
				"org.eclipse.osee.test12", "23:15", "", "Suite A",
				"mike@eclipse.com", 90));
		tasks.add(new SomeTask(RunDb.Production_Db, TaskType.Backup, getDate(),
				"org.eclipse.osee.test13", "4:01", "run to test this",
				"Suite B", "steve@eclipse.com", 100));
		tasks.add(new SomeTask(RunDb.Production_Db, TaskType.Data_Exchange,
				getDate(), "org.eclipse.osee.test11", "3:16",
				"run to test this", "Suite A", "steve@eclipse.com", 53));
		tasks.add(new SomeTask(RunDb.Test_Db, TaskType.Backup, getDate(),
				"org.eclipse.osee.test10", "5:01", "run to test this",
				"Suite C", "mike@eclipse.com", 0));
		tasks.add(new SomeTask(RunDb.Production_Db, TaskType.Data_Exchange,
				getDate(), "org.eclipse.osee.test9", "4:27",
				"run to test this", "Suite C", "steve@eclipse.com", 90));
		tasks.add(new SomeTask(RunDb.Production_Db, TaskType.Regression,
				getDate(), "org.eclipse.osee.test7", "2:37",
				"run to test this", "Suite C", "john@eclipse.com", 20));
		int num = 10;
		for (String str : Arrays.asList("Now", "Cat", "Dog", "Tree", "Bike",
				"Sun", "Moon", "Grass", "Can", "Car", "Truck", "Block",
				"Earth", "Mars", "Venus", "Requirements visualization",
				"Requirements management", "Feature management", "Modeling",
				"Design", "Project Management", "Change management",
				"Configuration Management", "Software Information Management",
				"Build management", "Testing", "Release Management",
				"Software Deployment", "Issue management",
				"Monitoring and reporting", "Workflow")) {
			tasks.add(new SomeTask(RunDb.Test_Db, TaskType.Db_Health,
					getDate(), "org.eclipse.osee." + str, "24:" + num++, str
							+ " will run to test this", "Suite C" + num++, str
							.toLowerCase().replaceAll(" ", ".")
							+ "@eclipse.com", 20));
		}
		return tasks;
	}
}