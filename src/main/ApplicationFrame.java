package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.github.sarxos.webcam.WebcamResolution;

public class ApplicationFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private ContentContainerPanel contentContainerPanel;
	// Frame dimensions
	private Dimension oldFrameSize = new Dimension();

	public ApplicationFrame() {
		// Frame definition
		this.setTitle("Basic Quality Inspection Program");
		//this.setSize(JFrame.MAXIMIZED_BOTH);//750, 500);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		makeFrameFullSize(this);
		this.setLayout(new BorderLayout());

		// Add content container to frame initialized with a blank image and make visible
		contentContainerPanel = new ContentContainerPanel(this.getSize());
		this.add(contentContainerPanel);
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Main.exitProgram();
			}
		});
		this.getRootPane().addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				resizeFrame();
			}
		});
	}

	public ContentContainerPanel getContentContainer() {
		return contentContainerPanel;
	}

	private void resizeFrame() {
		Dimension newSize = this.getSize();
		if ((newSize.width != oldFrameSize.width) || (newSize.height != oldFrameSize.height)) {
			contentContainerPanel.resizeComponents(newSize);
			this.validate();
		}
		oldFrameSize = newSize;
	}

	public void updateFrame(ContentContainerPanel ccp) {
		contentContainerPanel = ccp;
		this.validate();
	}

	public Dimension getFrameSize() {
		return this.getSize();
	}
	
	private void makeFrameFullSize(JFrame aFrame) {
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    aFrame.setSize(screenSize.width, screenSize.height);
	}
}
