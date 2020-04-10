package production;

/**
* @author  Alexander Peters
* @version 3.0
* @since   2020-04-10
*/

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PrinterSelectionFrame {
	private DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
	private JComboBox<String> comboBox = new JComboBox<String>(model);
	private JPanel panel = new JPanel();
	
	public PrinterSelectionFrame(final String... values) {
		panel.add(new JLabel("Please make a selection:"));
		for(String str: values) {
			model.addElement(str);
			if(str.toLowerCase().contains("dymo"))
				model.setSelectedItem(str);
		}
		panel.add(comboBox);

		JOptionPane.showConfirmDialog(null, panel, "Please Select A Printer.", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
	}
	
	public String getSelectedPrinter() {
		return comboBox.getSelectedItem().toString();
	}
}
