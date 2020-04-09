package main;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel implements Panel {
	private static final long serialVersionUID = 1L;
	private JButton b2 = new JButton("Approve Image");

	public ButtonPanel() {
		Dimension dim1 = new Dimension(400, 100);
		b2.setPreferredSize(dim1);

		Font f1 = new Font(Font.DIALOG, Font.BOLD, 36);
		b2.setFont(f1);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		b2.setAlignmentX(Component.CENTER_ALIGNMENT);
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Main.capture.Approved();
				} catch (Exception e1) {
					new ErrorFrame(e1.getMessage());		
				}
			}
		});
		this.add(b2);
	}

	@Override
	public void resizeComponents(Dimension size) {
		Dimension newSize = new Dimension((int) (size.width / 2.0 * 0.5), (int) (size.height * 0.1));
		Font f1 = new Font(Font.DIALOG, Font.BOLD, (int) (36 * (double) size.height / 1056));
		b2.setFont(f1);
		b2.setPreferredSize(newSize);
	}
}
