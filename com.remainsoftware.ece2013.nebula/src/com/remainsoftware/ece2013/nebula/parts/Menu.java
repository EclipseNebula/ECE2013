package com.remainsoftware.ece2013.nebula.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.nebula.widgets.pshelf.PShelf;
import org.eclipse.nebula.widgets.pshelf.PShelfItem;
import org.eclipse.nebula.widgets.pshelf.PaletteShelfRenderer;
import org.eclipse.nebula.widgets.pshelf.RedmondShelfRenderer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Link;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

import com.remainsoftware.ece2013.nebula.gantt.GanttTester;
import com.remainsoftware.ece2013.nebula.parts.project.NebulaProject;
import com.remainsoftware.ece2013.nebula.parts.stw.Github;
import com.remainsoftware.ece2013.nebula.snippets.transition.STWDemo;
import org.eclipse.swt.widgets.Label;

public class Menu extends BasePart {

	@Inject
	public Menu(Composite parent) {
	}

	@PostConstruct
	public void postConstruct(Composite parent, final EPartService partService) {
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.horizontalSpacing = 0;
		parent.setLayout(gridLayout);

		PShelf shelf = new PShelf(parent, SWT.BORDER);
		shelf.setRenderer(new RedmondShelfRenderer());
		shelf.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		PShelfItem agendaItem = new PShelfItem(shelf, 0);
		agendaItem.setImage(ResourceManager.getPluginImage(
				"com.remainsoftware.ece2013.nebula", "icons/agenda.png"));
		agendaItem.setText("Agenda");
		agendaItem.getBody();
		agendaItem.getBody().setLayout(new GridLayout(1, false));

		Link fLinkAboutMe = new Link(agendaItem.getBody(), SWT.NONE);
		fLinkAboutMe.setFont(SWTResourceManager.getFont("Segoe UI", 13,
				SWT.NORMAL));
		fLinkAboutMe.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		fLinkAboutMe.setText("<a>About Me</a>");
		fLinkAboutMe.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				switchPerspective("perspective.about");
				openView(AboutView.ID);
			}
		});

		Link fLinkNebula = new Link(agendaItem.getBody(), 0);
		fLinkNebula.setText("<a>The Nebula Project</a>");
		fLinkNebula.setFont(SWTResourceManager.getFont("Segoe UI", 13,
				SWT.NORMAL));
		fLinkNebula.setBounds(0, 0, 428, 23);
		fLinkNebula.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				switchPerspective("perspective.about");
				openView(NebulaProject.ID);
			}
		});

		PShelfItem widgetsItem = new PShelfItem(shelf, SWT.NONE);
		widgetsItem.setImage(ResourceManager.getPluginImage(
				"com.remainsoftware.ece2013.nebula", "icons/Dashboard .png"));
		widgetsItem.setText("Widgets");
		GridLayout gridLayout_1 = new GridLayout(1, false);
		gridLayout_1.marginWidth = 0;
		gridLayout_1.marginHeight = 0;
		widgetsItem.getBody().setLayout(gridLayout_1);

		PShelf shelf_1 = new PShelf(widgetsItem.getBody(), SWT.NONE);
		shelf_1.setRenderer(new PaletteShelfRenderer());
		shelf_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		PShelfItem shelfSTW = new PShelfItem(shelf_1, 0);
		shelfSTW.setText("Transition");
		shelfSTW.setImage(ResourceManager.getPluginImage(
				"com.remainsoftware.ece2013.nebula",
				"icons/1382719497_document_move.png"));
		shelfSTW.getBody().setLayout(new GridLayout(1, false));

		Link stwLink1 = new Link(shelfSTW.getBody(), 0);
		stwLink1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		stwLink1.setText("<a>Transition Demo</a>");
		stwLink1.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.NORMAL));
		stwLink1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				switchPerspective(TRANSITION);
				openView(STWDemo.ID);
			}
		});

		Link stwLink2 = new Link(shelfSTW.getBody(), 0);
		stwLink2.setText("<a>Github Style</a>");
		stwLink2.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.NORMAL));
		stwLink2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				switchPerspective(TRANSITION);
				openView(Github.ID);
			}
		});

		PShelfItem shelfGantt = new PShelfItem(shelf_1, SWT.NONE);
		shelfGantt.setImage(ResourceManager.getPluginImage(
				"com.remainsoftware.ece2013.nebula",
				"icons/1382685779_project-plan.png"));
		shelfGantt.setText("Gantt");
		shelfGantt.getBody().setLayout(new FillLayout(SWT.HORIZONTAL));

		Link ganttLink1 = new Link(shelfGantt.getBody(), 0);
		ganttLink1.setText("<a>Extended Example</a>");
		ganttLink1.setFont(SWTResourceManager.getFont("Segoe UI", 13,
				SWT.NORMAL));
		ganttLink1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				switchPerspective(GANTT);
				openView(GanttTester.ID);
			}
		});
		
		PShelfItem shelfGrid = new PShelfItem(shelf_1, 0);
		shelfGrid.setText("Grid");
		shelfGrid.setImage(ResourceManager.getPluginImage("com.remainsoftware.ece2013.nebula", "icons/1382741463_grid-alt-2.png"));
		shelfGrid.getBody().setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Link gridLink1 = new Link(shelfGrid.getBody(), 0);
		gridLink1.setText("<a>Snippets</a>");
		gridLink1.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.NORMAL));
		gridLink1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				switchPerspective(GRID);
			}
		});
		
		PShelfItem shelfVis = new PShelfItem(shelf_1, 0);
		shelfVis.setText("Visualization");
		shelfVis.setImage(ResourceManager.getPluginImage("com.remainsoftware.ece2013.nebula", "icons/1382840419_15-Dashboard .png"));
		shelfVis.getBody().setLayout(new GridLayout(1, false));
		
		Link visLink = new Link(shelfVis.getBody(), 0);
		visLink.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		visLink.setText("<a>Examples</a>");
		visLink.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.NORMAL));
		visLink.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				switchPerspective(VIS);
				openView("intensityView");
				openView("knobView");
				openView("combinedView");
			}
		});
		
		PShelfItem shelfItem = new PShelfItem(shelf_1, 0);
		shelfItem.setText("Gallery");
		shelfItem.setImage(ResourceManager.getPluginImage("com.remainsoftware.ece2013.nebula", "icons/1382926463_Picture.png"));
		shelfItem.getBody().setLayout(new GridLayout(1, false));
		
		Link galleryLink = new Link(shelfItem.getBody(), 0);
		galleryLink.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		galleryLink.setText("<a>Gallery</a>");
		galleryLink.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.NORMAL));
		galleryLink.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				switchPerspective(GALLERY);
			}
		});
		
		PShelfItem shelfGeomap = new PShelfItem(shelf_1, 0);
		shelfGeomap.setText("GeoMap");
		shelfGeomap.setImage(ResourceManager.getPluginImage("com.remainsoftware.ece2013.nebula", "icons/1382745122_web.png"));
		shelfGeomap.getBody().setLayout(new GridLayout(1, false));
		
		Link geoLink1 = new Link(shelfGeomap.getBody(), 0);
		geoLink1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		geoLink1.setText("<a>Simple</a>");
		geoLink1.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.NORMAL));
		geoLink1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				switchPerspective(GEOMAP);
				openView("simpleGeoMap");
			}
		});
		
		Link geoLink2 = new Link(shelfGeomap.getBody(), 0);
		geoLink2.setText("<a>Configured</a>");
		geoLink2.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.NORMAL));
		geoLink2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				switchPerspective(GEOMAP);
				openView("configuredGeoMap");
			}
		});
		
		PShelfItem shelfXViewer = new PShelfItem(shelf_1, 0);
		shelfXViewer.setText("XViewer");
		shelfXViewer.setImage(ResourceManager.getPluginImage("com.remainsoftware.ece2013.nebula", "icons/1382923877_eye.png"));
		shelfXViewer.getBody().setLayout(new GridLayout(1, false));
		
		Link xviewerLink1 = new Link(shelfXViewer.getBody(), 0);
		xviewerLink1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		xviewerLink1.setText("<a>Example Test</a>");
		xviewerLink1.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.NORMAL));
		xviewerLink1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				switchPerspective(XVIEWER);
				openView("xView");
			}
		});

		PShelfItem shelfScope = new PShelfItem(shelf_1, SWT.NONE);
		shelfScope.setImage(ResourceManager.getPluginImage(
				"com.remainsoftware.ece2013.nebula", "icons/oscillograph.png"));
		shelfScope.setText("Oscilloscope");
		shelfScope.getBody().setLayout(new GridLayout(1, false));
		Link link1Scope = new Link(shelfScope.getBody(), SWT.NONE);
		link1Scope.setFont(SWTResourceManager.getFont("Segoe UI", 13,
				SWT.NORMAL));
		link1Scope.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		link1Scope.setText("<a>Many Scopes</a>");
		link1Scope.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				switchPerspective(OSCILLOSCOPE);
				openView("scopeMany");
			}
		});
		
		

		Link link2Scope = new Link(shelfScope.getBody(), SWT.NONE);
		link2Scope.setFont(SWTResourceManager.getFont("Segoe UI", 13,
				SWT.NORMAL));
		link2Scope.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		link2Scope.setText("<a>Multi Channel</a>");
		link2Scope.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				switchPerspective(OSCILLOSCOPE);
				openView("scopeMultiChannel");
			}
		});
		

		Link link3Scope = new Link(shelfScope.getBody(), SWT.NONE);
		link3Scope.setFont(SWTResourceManager.getFont("Segoe UI", 13,
				SWT.NORMAL));
		link3Scope.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		link3Scope.setText("<a>Let's hear some blues</a>");
		link3Scope.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				switchPerspective(OSCILLOSCOPE);
				openView("scopeMusicView");
			}
		});

		Link link4Scope = new Link(shelfScope.getBody(), SWT.NONE);
		link4Scope.setFont(SWTResourceManager.getFont("Segoe UI", 13,
				SWT.NORMAL));
		link4Scope.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		link4Scope.setText("<a>More Audio</a>");
		link4Scope.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				switchPerspective(OSCILLOSCOPE);
				openView("scopeMusicView2");
			}
		});

		PShelfItem lastItem = new PShelfItem(shelf, SWT.NONE);
		lastItem.setText("");

		shelf.setSelection(lastItem);
	}

	@Focus
	public void onFocus() {
		// TODO Your code here
	}
}