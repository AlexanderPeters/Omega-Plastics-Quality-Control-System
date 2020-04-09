package development;

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
	private JButton approvedButton = new JButton("Approve Image");

	public ButtonPanel() {
		Dimension dimension = new Dimension(400, 100);
		approvedButton.setPreferredSize(dimension);

		Font font = new Font(Font.DIALOG, Font.BOLD, 36);
		approvedButton.setFont(font);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		approvedButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		approvedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Main.capture.Approved();
				} catch (Exception e1) {
					new ErrorFrame(e1.getMessage());		
				}
			}
		});
		this.add(approvedButton);
	}

	@Override
	public void resizeComponents(Dimension size) {
		Dimension newSize = new Dimension((int) (size.width / 2.0 * 0.5), (int) (size.height * 0.1));
		Font font = new Font(Font.DIALOG, Font.BOLD, (int) (36 * (double) size.height / 1056));
		approvedButton.setFont(font);
		approvedButton.setPreferredSize(newSize);
	}
}
