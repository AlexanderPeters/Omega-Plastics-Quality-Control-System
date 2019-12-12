package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
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
		this.add(label);
	}

	public void updateImage(BufferedImage image, Dimension programFrameSize) {
		Dimension imageScaling = new Dimension((int) (programFrameSize.width * 0.8889),
				(int) (programFrameSize.height * 0.6667));
		this.remove(label);
		icon = new ImageIcon(
				image.getScaledInstance(imageScaling.width, imageScaling.height, java.awt.Image.SCALE_SMOOTH));
		label = new JLabel("", icon, JLabel.CENTER);
		this.add(label);
	}

	@Override
	public void resizeComponents(Dimension size) {
		this.removeAll();
		Dimension dim1 = new Dimension((int) (size.width * 0.8889), (int) (size.height * 0.6667));
		Image img = icon.getImage();
		Image newimg = img.getScaledInstance(dim1.width, dim1.height, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg);
		label = new JLabel("", icon, JLabel.CENTER);
		this.setPreferredSize(dim1);
		this.add(label);
	}
}