package production;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class ContentContainerPanel extends JPanel implements Panel {
	private static final long serialVersionUID = 1L;
	private ImagePanel imagePanel;
	private ButtonPanel buttonPanel;
	private SettingsPanel settingsPanel;

	public ContentContainerPanel(BufferedImage image) {
		imagePanel = new ImagePanel(image);
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

	public void updateImagePanel(BufferedImage image, Dimension programFrameSize) {
		this.removeAll();
		imagePanel.updateImage(image, programFrameSize);
		addComponents();
	}

	@Override
	public void resizeComponents(Dimension size) {
		this.removeAll();
		imagePanel.resizeComponents(size);
		buttonPanel.resizeComponents(size);
		settingsPanel.resizeComponents(size);
		addComponents();
	}

	private void addComponents() {
		this.add(imagePanel);
		this.add(buttonPanel);
		this.add(settingsPanel);
	}

	public ButtonPanel getButtonPanel() {
		return this.buttonPanel;
	}

	public SettingsPanel getSettingsPanel() {
		return this.settingsPanel;
	}
}
