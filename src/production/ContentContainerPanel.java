package production;

/**
* @author  Alexander Peters
* @version 3.0
* @since   2020-04-10
*/

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
		this.add(imagePanel); // Top of frame
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(buttonPanel); // Middle of frame
		settingsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(settingsPanel); // Bottom of frame
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
