package main;

import java.awt.Dimension;
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
//TODO: Computing the label text
//TODO: Reading in settings
//TODO: Error handling for connect/disconnect of camera's
//TODO: Label data error checking

public class Webcam_Capture {
	private static boolean previousUpdateImageButton = false;
	private static boolean previousApproveImageButton = false;
	private static LabelWriter labelwriter = new LabelWriter();
	private static Webcam webcam;

	// Setup our Application Frame
	private static BufferedImage blankImage = new BufferedImage(WebcamResolution.HD.getSize().width,
			WebcamResolution.HD.getSize().height, BufferedImage.TRANSLUCENT);
	private static ApplicationFrame applicationFrame = new ApplicationFrame(blankImage);

	// Loop vars
	private static ContentContainerPanel ccp = applicationFrame.getContentContainer();
	private static BufferedImage image = null;
	private static boolean newImage = false;

	public static void main(String[] args) {
		// Declarations and Instantiations
		webcam = Webcam.getDefault();

		// Initialize camera
		initWebCam(webcam);

		while (true) {
			boolean buttonStateChanged = false;
			boolean updateImageButton = applicationFrame.getContentContainer().getButtonPanel()
					.updateImageButtonPressed();
			boolean approveImageButton = applicationFrame.getContentContainer().getButtonPanel()
					.approvedImageButtonPressed();

			if (updateImageButton)
				if (!previousUpdateImageButton)
					buttonStateChanged = true;
			if (approveImageButton)
				if (!previousApproveImageButton)
					buttonStateChanged = true;

			// Actions that occur when a button changes.
			if (buttonStateChanged) {
				ccp = applicationFrame.getContentContainer();
				if (updateImageButton) {
					image = captureImage(webcam);
					ccp.updateImagePanel(image);
					newImage = true;
				}
				if (approveImageButton && image != null && newImage) {
					ccp.updateImagePanel(blankImage);
					Approved(image);
					newImage = false;
				}
				applicationFrame.updateFrame(ccp);
			}
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
		Dimension[] nonStandardResolutions = new Dimension[] { WebcamResolution.HD.getSize(),
				new Dimension(1280, 720) };

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

	public static void newFrameSize(Dimension size) {
		// System.out.println("Step2");
		ContentContainerPanel ccp = applicationFrame.getContentContainer();
		ccp.resizeComponents(size);
		applicationFrame.updateFrame(ccp);
	}
}
