package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel implements Panel {
	private static final long serialVersionUID = 1L;
	JButton b1 = new JButton("Update Image");
	JButton b2 = new JButton("Approve Image");

	public ButtonPanel() {
		Dimension dim1 = new Dimension(600, 200);
		b1.setPreferredSize(dim1);
		b2.setPreferredSize(dim1);
		
		Font f1 = new Font(Font.DIALOG, Font.BOLD, 50);
		b1.setFont(f1);
		b2.setFont(f1);
		//this.setLayout(new BorderLayout());
		this.add(b1);//, BorderLayout.EAST);
		this.add(b2);//, BorderLayout.WEST);
	}
	
	@Override
	public void resizeComponents(Dimension size) {
	    //System.out.println("Step7");
		this.removeAll();
		Dimension dim1 = new Dimension((int) (size.width * 0.31), (int) (size.height * 0.189));
		b1.setPreferredSize(dim1);
		b2.setPreferredSize(dim1);
		this.add(b1);//, BorderLayout.EAST);
		this.add(b2);//, BorderLayout.WEST);
	}
}
