package com.remainsoftware.ece2013.nebula.oscilloscope;

import java.io.File;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.nebula.widgets.oscilloscope.multichannel.Oscilloscope;
import org.eclipse.nebula.widgets.oscilloscope.multichannel.OscilloscopeDispatcher;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Wim.Jongman (@remainsoftware.com)
 * 
 */
public class MultiScope_Basic {

	final String HEARTBEAT = "Heartbeat.wav";
	final String FLATLINE = "Flatline.wav";
	final String BEEP = "Beep.wav";
	protected final File BEEPFILE = new File(BEEP);

	protected Shell shell;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MultiScope_Basic window = new MultiScope_Basic();
			window.open();
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		shell = new Shell();
		shell.setImage(null);
		shell.setSize(600, 800);
		shell.setText("SWT Application");
		createContents(new Composite(shell, SWT.NONE));
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 * 
	 * @param parent
	 */
	@PostConstruct
	protected void createContents(Composite parent) {
		parent.setLayout(new GridLayout(3, false));

		int counter = 9;

		for (int i = 0; i < counter; i++) {

			int dice = new Random().nextInt(4) + 1;

			if (dice == 1) {
				new SnippetDispatcher() {
					private File HEARTBEATFILE;
					private File FLATLINEFILE;

					public void hookSetValues(int value) {
						getOscilloscope().setValues(0, Oscilloscope.HEARTBEAT);
					}

					public File getActiveSoundfile() {
						if (HEARTBEATFILE == null)
							HEARTBEATFILE = new File(HEARTBEAT);
						return HEARTBEATFILE;
					}

					public File getInactiveSoundfile() {
						if (FLATLINEFILE == null)
							FLATLINEFILE = new File(FLATLINE);
						return FLATLINEFILE;
					};

				}.dispatch(parent);
			}

			if (dice == 2) {
				new SnippetDispatcher() {

					double value = 0;
					double counter = .1;

					@Override
					public void init() {
						pulse.setSelection(1);
					};

					@Override
					public void hookSetValues(int v) {
						value += counter;
						if (value > 2 * Math.PI) {
							value = 0;
						}

						int intValue = (int) (Math.sin(value) * 100);
						if (intValue == 99)
							if (sound.getSelection()) {
								clipper.playClip(BEEPFILE, 0);
							}
						getOscilloscope().setValue(0, intValue);
					}

					@Override
					public boolean isSoundRequired() {
						if (sound.getSelection()) {
							int intValue = (int) (Math.sin(value) * 100);
							if (intValue == 99)
								return true;
						}
						return false;
					}

				}.dispatch(parent);
			}

			if (dice == 3) {
				new SnippetDispatcher() {

					double value = 0;
					double counter = .1;

					@Override
					public void init() {
						pulse.setSelection(1);
					};

					@Override
					public void hookSetValues(int v) {
						value += counter;
						if (value > 2 * Math.PI) {
							value = 0;
						}

						int intValue = (int) ((Math.sin(value) + (Math
								.sin(3 * value) / 3)) * 100);
						// intValue += ;
						if (intValue == 99)
							if (sound.getSelection()) {
								clipper.playClip(BEEPFILE, 0);
							}
						getOscilloscope().setValue(0, intValue);
					}

					@Override
					public boolean isSoundRequired() {
						if (sound.getSelection()) {
							int intValue = (int) (Math.sin(value) * 100);
							if (intValue == 99)
								return true;
						}
						return false;
					}

				}.dispatch(parent);
			}

			if (dice == 4) {
				new SnippetDispatcher() {

					double value = 0;

					@Override
					public void init() {
						pulse.setSelection(1);
					};

					@Override
					public void hookSetValues(int v) {
						value += Math.PI / gilloscope.getBounds().width;
						if (value > 2 * Math.PI) {
							value = 0;
						}

						int intValue = (int) ((Math.sin(value)
								+ (Math.sin(3 * value) / 3)
								+ (Math.sin(5 * value) / 5)
								+ (Math.sin(7 * value) / 7)
								+ (Math.sin(9 * value) / 9)
								+ (Math.sin(11 * value) / 11)
								+ (Math.sin(13 * value) / 13)
								+ (Math.sin(15 * value) / 15)
								+ (Math.sin(17 * value) / 17) + (Math
								.sin(19 * value) / 19)) * 100);

						if (intValue == 99)
							if (sound.getSelection()) {
								clipper.playClip(BEEPFILE, 0);
							}
						getOscilloscope().setValue(0, intValue);
					}

					@Override
					public boolean isSoundRequired() {
						if (sound.getSelection()) {
							int intValue = (int) (Math.sin(value) * 100);
							if (intValue == 99)
								return true;
						}
						return false;
					}
				}.dispatch(parent);
			}
		}
	}
}