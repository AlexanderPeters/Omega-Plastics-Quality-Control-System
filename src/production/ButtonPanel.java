package production;

/**
* @author  Alexander Peters
* @version 3.0
* @since   2020-04-10
*/

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel implements Panel {
	private static final long serialVersionUID = 1L;
	JButton b1 = new JButton("Update Image");
	JButton b2 = new JButton("Approve Image");

	public ButtonPanel() {
		Dimension dim1 = new Dimension(400, 100);
		b1.setPreferredSize(dim1);
		b2.setPreferredSize(dim1);

		Font f1 = new Font(Font.DIALOG, Font.BOLD, 36);
		b1.setFont(f1);
		b2.setFont(f1);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		b1.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.add(b1);
		b2.setAlignmentX(Component.RIGHT_ALIGNMENT);
		this.add(b2);
	}

	@Override
	public void resizeComponents(Dimension size) {
		this.removeAll();
		Dimension newSize = new Dimension((int) (size.width / 2.0 * 0.5), (int) (size.height * 0.1));
		Font f1 = new Font(Font.DIALOG, Font.BOLD, (int) (36 * (double) size.height / 1056));
		b1.setFont(f1);
		b2.setFont(f1);
		b1.setPreferredSize(newSize);
		b2.setPreferredSize(newSize);
		this.add(b1);
		this.add(b2);
	}

	public boolean updateImageButtonPressed() {
		return this.b1.getModel().isPressed();
	}

	public boolean approvedImageButtonPressed() {
		return this.b2.getModel().isPressed();
	}
}
