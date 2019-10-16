package main;

import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

//TODO: Author, citations, and documentation
public class Webcam_Capture {
	private static boolean wPressed = false;
	private static boolean ePressed = false;
	private static LabelWriter labelwriter = new LabelWriter();
	private static Webcam webcam;

	public static void main(String[] args) {
		// Declarations and Instantiations
		webcam = Webcam.getDefault();
		ApplicationFrame applicationFrame = new ApplicationFrame(new BufferedImage(WebcamResolution.HD.getSize().width,
				WebcamResolution.HD.getSize().height, BufferedImage.TRANSLUCENT), "Press W to update image and E to approve image.");
		BufferedImage image = null;
		boolean newImage = false;

		// Initialize camera
		initWebCam(webcam);

		// Update on Key Press
		while (true) {
			KeyListen();
			if (wPressed) {
				image = captureImage(webcam);
				applicationFrame.getContentContainer().updateImagePanel(image);
				newImage = true;
			}
			if (ePressed && image != null && newImage) {
				Approved(image);
				newImage = false;
			}
			
			applicationFrame.validate();

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void exitProgram() {
		// Close WebCam at end of program
		closeWebCam(webcam);
		// Exit program
		System.exit(0);
	}

	// Init WebCam
	private static void initWebCam(Webcam webcam) {
		Dimension[] nonStandardResolutions = new Dimension[] { WebcamResolution.PAL.getSize(),
				WebcamResolution.HD.getSize(), new Dimension(2000, 1000), new Dimension(1000, 500) };

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
		String filePath = getPicturePath(null, null);

		// Save a local copy of the image
		try {
			ImageIO.write(image, "PNG", new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Print The Label
		labelwriter.printLabel();
	}

	// TODO
	// Get date as a string
	private static String getDate() {
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		return sdf.format(date);
	}

	// TODO
	// Get appropriate save address
	private static String getPicturePath(String saveLocation, String workOrder) {
		return "C:\\Users\\abp\\Desktop\\test.PNG";
	}

	// TODO
	// Modify the message in the window
	private static void modifyMessage(String newMessage) {

	}
}
