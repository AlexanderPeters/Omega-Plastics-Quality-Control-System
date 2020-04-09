package development;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ErrorFrame {
	public ErrorFrame(String message) {
		JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
