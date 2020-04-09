package development;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.github.sarxos.webcam.WebcamResolution;

// This is an extension of the parent class Panel which 
// displays images.
public class ImagePanel extends JPanel implements Panel {
	private static final long serialVersionUID = 1L;
	private ImageIcon icon;
	private JLabel label;
	private Dimension imageScaling; 
	
	// Constructor
	public ImagePanel(Dimension scalingSize) {
		imageScaling = new Dimension((int) (scalingSize.width * 0.8889),
				(int) (scalingSize.height * 0.6667));
		icon = new ImageIcon(new BufferedImage(WebcamResolution.HD.getSize().width,
				WebcamResolution.HD.getSize().height, BufferedImage.TRANSLUCENT));
		label = new JLabel("", icon, JLabel.CENTER);
		// Define the properties of this ImagePanel
		this.setSize(icon.getIconWidth(), icon.getIconHeight());
		this.setLayout(new BorderLayout());
		this.add(label);
	}

	// Updates the image displayed in this ImagePanel instance
	public void setImage(BufferedImage image) {
		// Remove the previous image
		this.remove(label);
		// Define and add the new image with the appropriate scaling
		icon = new ImageIcon(
				image.getScaledInstance(imageScaling.width, imageScaling.height, java.awt.Image.SCALE_SMOOTH));
		label = new JLabel("", icon, JLabel.CENTER);
		this.add(label);
		this.validate();
	}

	@Override
	public void resizeComponents(Dimension size) {		
		imageScaling = new Dimension((int) (size.width * 0.8889),
				(int) (size.height * 0.6667));
	}
}