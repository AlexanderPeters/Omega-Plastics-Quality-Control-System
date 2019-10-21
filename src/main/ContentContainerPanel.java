package main;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class ContentContainerPanel extends JPanel implements Panel {
	private static final long serialVersionUID = 1L;
	private ImagePanel imagePanel;
	private ButtonPanel buttonPanel;

	public ContentContainerPanel(BufferedImage image) {
		imagePanel = new ImagePanel(image);
		buttonPanel = new ButtonPanel();

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		imagePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(imagePanel);
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(buttonPanel);
	}

	public void updateImagePanel(BufferedImage image) {
		this.remove(imagePanel);
		imagePanel.updateImage(image);
		update();
	}

	private void update() {
		this.add(imagePanel);
		this.add(buttonPanel);
	}

	@Override
	public void resizeComponents(Dimension size) {
		this.removeAll();
		imagePanel.resizeComponents(size);
		buttonPanel.resizeComponents(size);
		this.add(imagePanel);
		this.add(buttonPanel);
	}
	
	public ButtonPanel getButtonPanel() {
		return this.buttonPanel;
	}
}
