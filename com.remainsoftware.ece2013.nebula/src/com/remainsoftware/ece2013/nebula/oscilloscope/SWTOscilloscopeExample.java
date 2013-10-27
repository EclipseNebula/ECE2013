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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import be.hogent.tarsos.dsp.AudioDispatcher;
import be.hogent.tarsos.dsp.AudioEvent;
import be.hogent.tarsos.dsp.AudioProcessor;

public class SWTOscilloscopeExample implements ISelectionChangedListener {


	protected Shell shell;
	private Button startStop;
	private Oscilloscope scope;
	private AudioDispatcher audioDispatcher;
	private int[] scopeBuffer = new int[0];
	private Thread thread;
	private OscilloscopeDispatcher scopeDispatcher;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SWTOscilloscopeExample window = new SWTOscilloscopeExample();
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
		shell.setLayout(new GridLayout(1, false));

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false,
				1, 1));
		createContents(composite);
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		audioDispatcher.stop();
		thread.interrupt();
	}

	/**
	 * Create contents of the window.
	 */
	@PostConstruct
	protected void createContents(Composite composite) {

		composite.setLayout(new GridLayout(1, false));

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
		startStop.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true,
				false, 1, 1));
		startStop.setText("Stop");
		startStop.setEnabled(false);
		startStop.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				scopeDispatcher.stop();
				SWTOscilloscopeExample.this.audioDispatcher.stop();
				startStop.setEnabled(false);
				audioDispatcher.stop();
				thread.interrupt();
			}
		});

		scope = new Oscilloscope(composite, SWT.NONE);
		scope.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		scope.setLayout(new GridLayout(1, false));
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

		scopeDispatcher = new OscilloscopeDispatcher(
				0, scope) {

			@Override
			public void hookChangeAttributes() {
				super.hookChangeAttributes();
				scope.setProgression(0, getProgression());
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
				SWTOscilloscopeExample.this.process(audioEvent);
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
			scopeBuffer[i] = (int) (audioBuffer[i] * 100);
		}
		this.scopeBuffer = scopeBuffer;
		return true;
	}
}
