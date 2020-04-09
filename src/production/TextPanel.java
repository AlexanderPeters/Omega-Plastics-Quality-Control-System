package production;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TextPanel extends JPanel implements Panel {
	private static final long serialVersionUID = 1L;
	private int textFieldSize = 20;
	private JTextField textField = new JTextField(textFieldSize);
	private JLabel label = new JLabel();

	public TextPanel(String labelText, String text) {
		label.setText(labelText);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font("Times New Roman", Font.BOLD, 24));

		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setFont(textField.getFont().deriveFont(24f));
		textField.setText(text);
		textField.setEditable(true);

		Dimension dim1 = new Dimension(200, 50);
		label.setPreferredSize(dim1);
		textField.setPreferredSize(dim1);
		this.add(label);
		this.add(textField);
	}

	public void updateText(String text) {
		this.removeAll();
		textField.setText(text);
		this.add(label);
		this.add(textField);
	}

	public String getText() {
		return this.textField.getText();
	}

	public void setText(String text) {
		this.textField.setText(text);
	}

	@Override
	public void resizeComponents(Dimension size) {
		this.removeAll();

		Dimension newSize = new Dimension((int) (size.width / 2.0 * 0.1), (int) (size.height * 0.03));
		double textScaling = (double) size.height / 1056;

		label.setFont(new Font("Times New Roman", Font.BOLD, (int) (24 * textScaling)));
		label.setPreferredSize(newSize);

		String previousText = textField.getText();
		int newTextFieldSize = (int) (textFieldSize * (double) size.width / 1936);

		textField = new JTextField(newTextFieldSize);
		textField.setFont(textField.getFont().deriveFont((float) (24f * textScaling)));
		textField.setText(previousText.substring(newTextFieldSize));
		textField.setPreferredSize(newSize);

		this.add(label);
		this.add(textField);
	}
}