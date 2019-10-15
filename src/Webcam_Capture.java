import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

public class Webcam_Capture {
	static boolean	wPressed = false;
	static boolean	ePressed = false;

	// Main method
	public static void main(String[] args) {	
		// Declarations and Instantiations
		Webcam webcam = Webcam.getDefault();
		JFrame frame = new JFrame();	
		JPanel contentContainerPanel = new JPanel();
		ImagePanel imagePanel;
		JPanel textPanel;
		JTextField textField;
        BufferedImage image = null;
        boolean newImage = false;
        
        // Initialize camera
        initWebCam(webcam);
		
		// Frame definition
		frame.setTitle("Basic Quality Inspection Program");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		// Image Panel
		BufferedImage blankImage = new BufferedImage(WebcamResolution.HD.getSize().width, WebcamResolution.HD.getSize().height, BufferedImage.TRANSLUCENT);	
		imagePanel = new ImagePanel(blankImage);
		
		// Text Panel
		textPanel = new JPanel();
        textField = new JTextField(35);
        textField.setText("Press W to update image and E to approve image.");
        textField.setFont(textField.getFont().deriveFont(50f));
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setEditable(false);
        textPanel.add(textField, BorderLayout.CENTER);

        // Combine in Content Panel
        contentContainerPanel.add(imagePanel, BorderLayout.NORTH);
        contentContainerPanel.add(textPanel, BorderLayout.SOUTH);
        
        // Add to Frame and make visible
        frame.add(contentContainerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
        	@Override
        	public void windowClosing(WindowEvent e) {
        		LabelWriter labelwriter = new LabelWriter();
        		labelwriter.printLabel("Amico QC Checked", "White Punched Extrusion", "Line: ", "Die: ", "Production Run: ", "17", "256", "190", "Date: 10/9/19", "Time: 2:10PM", "QC Inspector: APeters", "Box 1: ", "32 Count");
        		// Close WebCam at end of program
        		closeWebCam(webcam);
        		// Exit program
        		System.exit(0);
        	}
        	 
        });
		
        // Update on Key Press
		while(true) {
	        KeyListen();
			if(wPressed) {
				image = captureImage(webcam);
				contentContainerPanel.remove(imagePanel);
				imagePanel = new ImagePanel(image);
		        contentContainerPanel.add(imagePanel, BorderLayout.NORTH);
		        contentContainerPanel.add(textPanel, BorderLayout.SOUTH);
		        contentContainerPanel.validate();	
		        newImage = true;
			}
			if(ePressed && image != null && newImage) {
				Approved(image);
				newImage = false;
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Init WebCam
	private static void initWebCam(Webcam webcam) {
		Dimension[] nonStandardResolutions = new Dimension[] {
				WebcamResolution.PAL.getSize(),
				WebcamResolution.HD.getSize(),
				new Dimension(2000, 1000),
				new Dimension(1000, 500)
		};
			
		webcam.setCustomViewSizes(nonStandardResolutions);
		webcam.setViewSize(WebcamResolution.HD.getSize());
		webcam.open();
	}
	
	// Capture Images
	private static BufferedImage captureImage(Webcam webcam) {	
		return webcam.getImage();
	}
	
	// Close WebCam
	private static void closeWebCam(Webcam webcam) {
		webcam.close();	
	}
	
	// Key listener
	private static void KeyListen() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
		 	public boolean dispatchKeyEvent(KeyEvent event) {
				switch (event.getID()) {
					case KeyEvent.KEY_PRESSED:
						if (event.getKeyCode() == KeyEvent.VK_W)
		     				Webcam_Capture.wPressed = true; 
						if (event.getKeyCode() == KeyEvent.VK_E)
		     				Webcam_Capture.ePressed = true;
						break;
					case KeyEvent.KEY_RELEASED:
						if (event.getKeyCode() == KeyEvent.VK_W)
		    				Webcam_Capture.wPressed = false;
						if (event.getKeyCode() == KeyEvent.VK_E)
		    				Webcam_Capture.ePressed = false;
					break;
				}
				return false;
			}
		});		
	}
	
	// Image Approved
	private static void Approved(BufferedImage image) {
		String filePath = getPicturePath(null, null, null, null);
		// Save a local copy of the image
		try {
			ImageIO.write(image, "PNG", new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		PrintLabel();
	}
	
	// Print The Label
	private static void PrintLabel() {
		
	}
	
	// Get date as a string
	private static String getDate() {
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		return sdf.format(date);
	}
	
	// Get appropriate save address
	private static String getPicturePath(String saveLocation, String lineNumber, String dieNumber, String productionRun) {
		
		return "C:\\Users\\abp\\Desktop\\test.PNG";
	}
}
