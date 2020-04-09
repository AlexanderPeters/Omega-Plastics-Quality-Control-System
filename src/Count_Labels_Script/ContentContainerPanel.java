package Count_Labels_Script;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class ContentContainerPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private TextPanel inspectorNamePanel;
	private TextPanel quantityPanel;
	private ButtonPanel buttonPanel;

	public ContentContainerPanel() {
		inspectorNamePanel = new TextPanel("Inspector Name:");
		quantityPanel = new TextPanel("Quantity of Labels To Print:");
		buttonPanel = new ButtonPanel();

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		inspectorNamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(inspectorNamePanel);
		quantityPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(quantityPanel);
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(buttonPanel);
	}

	public TextPanel getInspectorNamePanel() {
		return this.inspectorNamePanel;
	}

	public TextPanel getQuantityNamePanel() {
		return this.quantityPanel;
	}

	public ButtonPanel getButtonPanel() {
		return this.buttonPanel;
	}
}
