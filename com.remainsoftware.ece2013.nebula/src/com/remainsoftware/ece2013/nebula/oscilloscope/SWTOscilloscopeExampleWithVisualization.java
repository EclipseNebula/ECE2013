package com.remainsoftware.ece2013.nebula.oscilloscope;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Mixer.Info;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.nebula.widgets.oscilloscope.multichannel.Oscilloscope;
import org.eclipse.nebula.widgets.oscilloscope.multichannel.OscilloscopeDispatcher;
import org.eclipse.nebula.widgets.oscilloscope.multichannel.OscilloscopeStackAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import be.hogent.tarsos.dsp.AudioDispatcher;
import be.hogent.tarsos.dsp.AudioEvent;
import be.hogent.tarsos.dsp.AudioProcessor;

import com.remainsoftware.ece2013.nebula.visualization.MeterComposite;

public class SWTOscilloscopeExampleWithVisualization implements
		ISelectionChangedListener {

	protected Shell shell;
	private Button startStop;
	private Oscilloscope scope;
	private AudioDispatcher audioDispatcher;
	private int[] scopeBuffer = new int[0];
	private Thread thread;
	private OscilloscopeDispatcher scopeDispatcher;
	private MeterComposite meterComposite;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SWTOscilloscopeExampleWithVisualization window = new SWTOscilloscopeExampleWithVisualization();
			window.open();
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
		shell.setSize(800, 600);
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout());

		Composite composite = new Composite(shell, SWT.NONE);

		createContents(composite);
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		if (audioDispatcher != null)
			audioDispatcher.stop();
	}

	/**
	 * Create contents of the window.
	 */
	@PostConstruct
	protected void createContents(Composite composite) {

		GridLayout gl_composite = new GridLayout(2, false);
		gl_composite.verticalSpacing = 0;
		gl_composite.marginWidth = 0;
		gl_composite.marginHeight = 0;
		gl_composite.horizontalSpacing = 0;
		composite.setLayout(gl_composite);

		ComboViewer comboViewer = new ComboViewer(composite, SWT.NONE);
		// comboViewer.setSorter(new Sorter());
		Combo combo = comboViewer.getCombo();
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1));
		comboViewer.setLabelProvider(MixerContentProvider.instance);
		comboViewer.setContentProvider(MixerContentProvider.instance);
		comboViewer.setInput("go");
		comboViewer.addSelectionChangedListener(this);

		startStop = new Button(composite, SWT.NONE);
		startStop.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		startStop.setText("Stop");
		startStop.setEnabled(false);
		startStop.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				scopeDispatcher.stop();
				SWTOscilloscopeExampleWithVisualization.this.audioDispatcher
						.stop();
				startStop.setEnabled(false);
				audioDispatcher.stop();
			}
		});

		scope = new Oscilloscope(composite, SWT.NONE);
		scope.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		scope.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		scope.setLayout(new GridLayout(1, false));

		meterComposite = new MeterComposite(composite, SWT.NONE);
		meterComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 1));

	}

	@Override
	public void selectionChanged(SelectionChangedEvent arg0) {
		startStop.setEnabled(true);

		IStructuredSelection sel = (IStructuredSelection) arg0.getSelection();
		Mixer mixer = AudioSystem.getMixer((Info) sel.getFirstElement());
		try {
			setNewMixer(mixer);
		} catch (Exception e1) {
		}

		scopeDispatcher = new OscilloscopeDispatcher(0, scope) {

			@Override
			public void hookChangeAttributes() {
				super.hookChangeAttributes();
				scope.setProgression(0, getProgression());
				scope.setForeground(0, getInactiveForegoundColor());
				scope.setBackground(Display.getDefault().getSystemColor(
						SWT.COLOR_WHITE));
			}

			@Override
			public boolean isTailSizeMax() {
				return true;
			}

			@Override
			public boolean getFade() {
				return false;
			}

			@Override
			public int getSteadyPosition() {
				return 0;
			}

			@Override
			public Image getBackgroundImage() {
				return null;
			}

			@Override
			public int getLineWidth() {
				return 3;
			}

			@Override
			public int getProgression() {
				return scope.getClientArea().width;
			}
		};

		scope.addStackListener(0, new OscilloscopeStackAdapter() {
			@Override
			public void stackEmpty(Oscilloscope scope, int channel) {
				scope.setValues(0, scopeBuffer);
				if (scopeBuffer.length > 0)
					if (scopeBuffer[0] > 0)
						meterComposite.setValue(scopeBuffer[0]);
			}
		});

		scopeDispatcher.dispatch();

	}

	private void setNewMixer(Mixer mixer) throws LineUnavailableException,
			UnsupportedAudioFileException {

		float sampleRate = 44100;
		int bufferSize = 2048;
		int overlap = 0;

		final AudioFormat format = new AudioFormat(sampleRate, 16, 1, true,
				true);
		final DataLine.Info dataLineInfo = new DataLine.Info(
				TargetDataLine.class, format);
		TargetDataLine line;
		line = (TargetDataLine) mixer.getLine(dataLineInfo);
		final int numberOfSamples = bufferSize;
		line.open(format, numberOfSamples);
		line.start();
		final AudioInputStream stream = new AudioInputStream(line);

		// create a new dispatcher
		audioDispatcher = new AudioDispatcher(stream, bufferSize, overlap);
		audioDispatcher.addAudioProcessor(new AudioProcessor() {

			@Override
			public void processingFinished() {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public boolean process(AudioEvent audioEvent) {
				SWTOscilloscopeExampleWithVisualization.this
						.process(audioEvent);
				return true;
			}
		});
		thread = new Thread(audioDispatcher, "Audio dispatching");
		thread.setDaemon(true);
		thread.start();
	}

	public boolean process(AudioEvent audioEvent) {
		float[] audioBuffer = audioEvent.getFloatBuffer();
		final int[] scopeBuffer = new int[audioBuffer.length];
		for (int i = 0; i < audioBuffer.length; ++i) {
			scopeBuffer[i] = (int) (audioBuffer[i] * 200);
		}
		this.scopeBuffer = scopeBuffer;
		return true;
	}
}
