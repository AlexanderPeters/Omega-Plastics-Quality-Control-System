package production;

/**
* @author  Alexander Peters
* @version 3.0
* @since   2020-04-10
*/

import javax.swing.JFrame;
import javax.swing.JOptionPane;

// Creates an option pane as a popup with the desired error message
public class ErrorFrame {
	public ErrorFrame(String message) {
		JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
