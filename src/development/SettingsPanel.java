package development;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class SettingsPanel extends JPanel implements Panel {
	private static final long serialVersionUID = 1L;
	private static TextPanel operatorNamePanel;
	private static TextPanel workOrderPanel;
	private static TextPanel currentBoxIDPanel;

	public SettingsPanel() {
		operatorNamePanel = new TextPanel("Operator Name:   ", "");
		workOrderPanel = new TextPanel("Work Order:       ", "");
		currentBoxIDPanel = new TextPanel("Current Box ID: ", "");

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		operatorNamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(operatorNamePanel);
		workOrderPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(workOrderPanel);
		currentBoxIDPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(currentBoxIDPanel);
	}

	@Override
	public void resizeComponents(Dimension size) {
		Dimension dim1 = new Dimension((int) (size.width), (int) (size.height / 3.0));
		operatorNamePanel.setPreferredSize(dim1);
		workOrderPanel.setPreferredSize(dim1);
		currentBoxIDPanel.setPreferredSize(dim1);
	}

	public TextPanel getOperatorNamePanel() {
		return operatorNamePanel;
	}

	public TextPanel getWorkOrderPanel() {
		return workOrderPanel;
	}

	public TextPanel getCurrentBoxIDPanel() {
		return currentBoxIDPanel;
	}
}
