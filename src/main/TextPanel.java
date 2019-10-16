package main;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class TextPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField textField = new JTextField(35);

	public TextPanel(String text) {
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setFont(textField.getFont().deriveFont(50f));
		textField.setText(text);
		textField.setEditable(false);
		this.add(textField, BorderLayout.CENTER);
	}

	public void updateText(String text) {
		textField.setText(text);
		this.validate();
	}
}
