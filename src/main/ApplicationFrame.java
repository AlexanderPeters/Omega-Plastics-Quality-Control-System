package main;

import java.awt.BorderLayout;
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
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		// Add content container to frame and make visible
		contentContainerPanel = new ContentContainerPanel(image, text);
		this.add(contentContainerPanel, BorderLayout.CENTER);
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Webcam_Capture.exitProgram();
			}
		});
	}

	public ContentContainerPanel getContentContainer() {
		return contentContainerPanel;
	}
}
