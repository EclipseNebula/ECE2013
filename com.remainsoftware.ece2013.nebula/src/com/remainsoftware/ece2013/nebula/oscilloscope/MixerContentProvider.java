package com.remainsoftware.ece2013.nebula.oscilloscope;

import javax.sound.sampled.Mixer.Info;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;

public class MixerContentProvider extends LabelProvider implements
		IStructuredContentProvider {

	public static MixerContentProvider instance = new MixerContentProvider();

	private MixerContentProvider() {
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return Shared.getMixerInfo(false, true).toArray();
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Image getImage(Object element) {
		return super.getImage(element);
	}

	@Override
	public String getText(Object element) {
		if (!(element instanceof Info))
			return "not a mixer";

		Info mixer = (Info) element;
		return Shared.toLocalString(mixer);
	}
}
