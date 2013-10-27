package com.remainsoftware.ece2013.nebula.visualization;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class MeterComposite extends Composite {

	private Meter meter1;
	private Meter meter2;
	private Meter meter3;
	private Meter meter4;

	public class Runner implements Runnable {

		private int value;
		private Meter meter;

		Runner(Meter meter, int value) {
			this.meter = meter;
			this.value = value;
		}

		public void run() {
			if (meter.getValue() - 1 == value && value >= 0) {
				meter.setValue(value);
				Display.getDefault().asyncExec(new Runner(meter, value - 1));
			}
		}
	};

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public MeterComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.VERTICAL));

		meter1 = new Meter(this, SWT.NONE);

		meter2 = new Meter(this, SWT.NONE);

		meter3 = new Meter(this, SWT.NONE);

		meter4 = new Meter(this, SWT.NONE);

	}

	@Override
	protected void checkSubclass() {
	}

	static int max = 250;

	public void setValue(double value) {
		if (value == 0) {
			value = 1;
		}
		setAndRun(meter1, (int) (((value) * 50) > max ? max : ((value) * 50)));
		setAndRun(meter2, (int) (((value) * 8) > max ? max : ((value) * 8)));
		setAndRun(meter3, (int) (((value) * 5) > max ? max : ((value) * 5)));
		setAndRun(meter4, (int) (((value) * 3) > max ? max : ((value) * 3)));
	}

	/**
	 * Takes care of a smooth roll back to 0.
	 * 
	 * @param meter
	 * @param value
	 */
	private void setAndRun(Meter meter, int value) {
		if (value > meter.getValue()) {
			meter.setValue(value);
			Display.getDefault().asyncExec(new Runner(meter, value - 1));
		}
	}
}
