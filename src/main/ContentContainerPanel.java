package main;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class ContentContainerPanel extends JPanel implements Panel {
	private static final long serialVersionUID = 1L;
	private ImagePanel imagePanel;
	private ButtonPanel buttonPanel;
	private SettingsPanel settingsPanel;

	public ContentContainerPanel(Dimension scalingSize) {
		imagePanel = new ImagePanel(scalingSize);
		buttonPanel = new ButtonPanel();
		settingsPanel = new SettingsPanel();

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		imagePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(imagePanel);
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(buttonPanel);
		settingsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(settingsPanel);
	}

	@Override
	public void resizeComponents(Dimension size) {
		imagePanel.resizeComponents(size);
		buttonPanel.resizeComponents(size);
		settingsPanel.resizeComponents(size);
	}
	
	public ImagePanel getImagePanel() {
		return imagePanel;
	}

	public ButtonPanel getButtonPanel() {
		return buttonPanel;
	}

	public SettingsPanel getSettingsPanel() {
		return settingsPanel;
	}
}
