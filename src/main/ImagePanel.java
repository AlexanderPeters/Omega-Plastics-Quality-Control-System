package main;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private ImageIcon icon;
	private JLabel label;

	public ImagePanel(BufferedImage image) {
		icon = new ImageIcon(image);
		label = new JLabel("", icon, JLabel.CENTER);
		this.add(label, BorderLayout.CENTER);
	}

	public void updateImage(BufferedImage image) {
		icon = new ImageIcon(image);
		label = new JLabel("", icon, JLabel.CENTER);
		this.validate();
	}
}