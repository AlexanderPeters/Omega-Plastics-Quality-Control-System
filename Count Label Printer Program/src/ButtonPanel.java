import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	JButton b1 = new JButton("Print Labels");

	public ButtonPanel() {
		Dimension dim1 = new Dimension(600, 150);
		b1.setPreferredSize(dim1);
		Font f1 = new Font(Font.DIALOG, Font.BOLD, 50);
		b1.setFont(f1);
		this.add(b1);
	}

	public boolean printButtonPressed() {
		return this.b1.getModel().isPressed();
	}
}
