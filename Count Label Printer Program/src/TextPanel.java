import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TextPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField textField = new JTextField(25);
	private JLabel label = new JLabel();

	public TextPanel(String labelText) {
		label.setText(labelText);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font("Times New Roman", Font.BOLD, 25));

		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setFont(textField.getFont().deriveFont(25f));
		textField.setEditable(true);

		this.setLayout(new BorderLayout());
		this.add(label, BorderLayout.NORTH);
		this.add(textField, BorderLayout.CENTER);
	}

	public String getText() {
		return this.textField.getText();
	}
}