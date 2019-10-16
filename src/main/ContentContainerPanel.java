package main;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ContentContainerPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private ImagePanel imagePanel;
	private TextPanel textPanel;

	public ContentContainerPanel(BufferedImage image, String text) {
		imagePanel = new ImagePanel(image);
		textPanel = new TextPanel(text);

		this.add(imagePanel, BorderLayout.NORTH);
		this.add(textPanel, BorderLayout.SOUTH);
	}

	public void updateImagePanel(BufferedImage image) {
		imagePanel.updateImage(image);
		this.validate();
	}

	public void updateTextPanel(String text) {
		textPanel.updateText(text);
		this.validate();
	}
}
