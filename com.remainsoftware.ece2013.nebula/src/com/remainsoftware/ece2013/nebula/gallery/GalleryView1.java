package com.remainsoftware.ece2013.nebula.gallery;

import java.util.Random;

import javax.annotation.PostConstruct;

import org.eclipse.nebula.widgets.gallery.DefaultGalleryGroupRenderer;
import org.eclipse.nebula.widgets.gallery.DefaultGalleryItemRenderer;
import org.eclipse.nebula.widgets.gallery.Gallery;
import org.eclipse.nebula.widgets.gallery.GalleryItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.ResourceManager;
import org.osgi.framework.FrameworkUtil;

/**
 * This widget displays a simple gallery with some content.<br/>
 * Scrolling is vertical.<br/>
 * <br/>
 * 
 * <p>
 * NOTE: THIS WIDGET AND ITS API ARE STILL UNDER DEVELOPMENT. THIS IS A
 * PRE-RELEASE ALPHA VERSION. USERS SHOULD EXPECT API CHANGES IN FUTURE
 * VERSIONS.
 * </p>
 * 
 * @author Nicolas Richeton (nicolas.richeton@gmail.com)
 */

public class GalleryView1 {
	
	private String bundleId = FrameworkUtil.getBundle(getClass())
			.getSymbolicName();

	static String[] images = new String[] { "/img/gallery/cdatetime.png",
			"/img/gallery/gallery.png", "/img/gallery/gantt.png",
			"/img/gallery/grid.png", "/img/gallery/paperclips.png",
			"/img/gallery/pgroup.png", "/img/gallery/pshelf.png",
			"/img/gallery/scope.png", "/img/gallery/tablecombo.png",
			"/img/gallery/transition.png", "/img/gallery/xviewer.png" };

	@PostConstruct
	public void main(Composite parent) {
		
		Display display = Display.getDefault();
		Image itemImage = new Image(display, Program
				.findProgram("png").getImageData()); //$NON-NLS-1$

		Gallery gallery = new Gallery(parent, SWT.V_SCROLL | SWT.MULTI);

		// Renderers
		DefaultGalleryGroupRenderer gr = new DefaultGalleryGroupRenderer();
		gr.setMinMargin(2);
		gr.setItemHeight(256);
		gr.setItemWidth(280);
		gr.setAutoMargin(true);
		gr.setAnimation(true);
		gallery.setGroupRenderer(gr);

		DefaultGalleryItemRenderer ir = new DefaultGalleryItemRenderer();
		gallery.setItemRenderer(ir);

		for (int g = 0; g < 2; g++) {
			GalleryItem group = new GalleryItem(gallery, SWT.NONE);
			group.setText("Group " + g); //$NON-NLS-1$
			group.setExpanded(true);

			for (int i = 0; i < 50; i++) {
				GalleryItem item = new GalleryItem(group, SWT.NONE);
				if (itemImage != null) {
					item.setImage(ResourceManager.getPluginImage(bundleId, images[new Random()
							.nextInt(11)]));
				}
				item.setText("Item " + i);
			}
		}
	}
}