package com.remainsoftware.ece2013.nebula.grid;

import javax.annotation.PostConstruct;

import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.nebula.widgets.grid.GridColumn;
import org.eclipse.nebula.widgets.grid.GridItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

/*
 * Create a grid with a checkbox in the second column.
 *
 * For a list of all Nebula Grid example snippets see
 * http://www.eclipse.org/nebula/widgets/grid/snippets.php
 */
public class Checkbox {

	@PostConstruct
	public static void main(Composite parent) {
        parent.setLayout(new FillLayout());

		Grid grid = new Grid(parent, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		grid.setHeaderVisible(true);
		GridColumn column = new GridColumn(grid, SWT.NONE);
		column.setText("Column 1");
		column.setWidth(100);
		GridColumn column2 = new GridColumn(grid, SWT.CHECK | SWT.CENTER);
		column2.setText("Column 2");
		column2.setWidth(100);
		GridItem item1 = new GridItem(grid, SWT.NONE);
		item1.setText("First Item");
		item1.setChecked(1, true);
		GridItem item2 = new GridItem(grid, SWT.NONE);
		item2.setText("Second Item");
		GridItem item3 = new GridItem(grid, SWT.NONE);
		item3.setText("Third Item");

	}
}