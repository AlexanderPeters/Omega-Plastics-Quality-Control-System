import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public ImagePanel(BufferedImage image) {
    	ImageIcon icon = new ImageIcon(image);
    	JLabel label = new JLabel("", icon, JLabel.CENTER);
    	this.setLayout(new BorderLayout());
    	this.add(label, BorderLayout.CENTER);
    }
}