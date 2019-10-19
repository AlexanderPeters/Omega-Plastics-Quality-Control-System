package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImagePanel extends JPanel implements Panel {
	private static final long serialVersionUID = 1L;
	private ImageIcon icon;
	private JLabel label;

	public ImagePanel(BufferedImage image) {
		icon = new ImageIcon(image);
		label = new JLabel("", icon, JLabel.CENTER);
		this.setSize(icon.getIconWidth(), icon.getIconHeight());
		this.setLayout(new BorderLayout());
		this.add(label, BorderLayout.CENTER);
	}

	public void updateImage(BufferedImage image) {
		this.remove(label);
		icon = new ImageIcon(image);
		label = new JLabel("", icon, JLabel.CENTER);
		this.add(label, BorderLayout.CENTER);
	}

	@Override
	public void resizeComponents(Dimension size) {
		this.removeAll();
		Dimension dim1 = new Dimension((int) (size.width * 0.661), (int) (size.height * 0.681));
		this.setPreferredSize(dim1);
		this.add(label, BorderLayout.CENTER);
	}
}