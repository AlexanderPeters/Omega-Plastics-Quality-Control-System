package main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class ApplicationFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private ContentContainerPanel contentContainerPanel;

	public ApplicationFrame(BufferedImage image, String text) {
		// Frame definition
		this.setTitle("Basic Quality Inspection Program");
		this.setSize(750, 500);
		// 0.387, 0.473
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		this.setLayout(new BorderLayout());

		// Add content container to frame and make visible
		contentContainerPanel = new ContentContainerPanel(image, text);
		this.add(contentContainerPanel);
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Webcam_Capture.exitProgram();
			}
		});
		this.getRootPane().addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				Component c = (Component) e.getSource();
				Dimension newSize = c.getSize();
				Webcam_Capture.newFrameSize(newSize);
			}
		});
	}

	public ContentContainerPanel getContentContainer() {
		return contentContainerPanel;
	}

	public void updateFrame(ContentContainerPanel ccp) {
		this.remove(contentContainerPanel);
		this.contentContainerPanel = ccp;
		this.add(contentContainerPanel);
		this.validate();
	}
}
