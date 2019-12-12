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

//import HelperFunctions;

//TODO: Author, citations, and documentation
//TODO: Error handling for connect/disconnect of camera's
//TODO: System exit/popup when an error is printed to terminal
//TODO: Building this code into a .jar file
//TODO: Make operator names changeable with a text box

public class Webcam_Capture extends HelperFunctions {
	private static boolean previousUpdateImageButton = false;
	private static boolean previousApproveImageButton = false;
	private static LabelWriter labelwriter = new LabelWriter();
	private static Webcam webcam;

	// Setup our Application Frame
	private static BufferedImage blankImage = new BufferedImage(WebcamResolution.HD.getSize().width,
			WebcamResolution.HD.getSize().height, BufferedImage.TRANSLUCENT);
	private static ApplicationFrame applicationFrame = new ApplicationFrame(blankImage);

	// Config Data
	private static String saveLocation = "O:\\QA Docs\\Amico\\QA Pictures\\";
	private static String workOrder;
	private static String operatorName;

	// Loop vars
	private static ContentContainerPanel ccp = applicationFrame.getContentContainer();
	private static BufferedImage image = null;
	private static boolean newImage = false;

	public static void main(String[] args) throws Exception {
		// TODO: Add text boxes for operator name, work order, and current boxID, make
		// sure that if the box ID is overwritten back to previous
		// image that that image gets rewritten on the next write cycle.

		// Deal with windows resizing needed to make buttons appear bug.

		// Declarations and Instantiations
		webcam = Webcam.getDefault();

		// Initialize camera
		initWebCam(webcam);

		// Setup BoxID
		SettingsPanel settingsPanel = applicationFrame.getContentContainer().getSettingsPanel();
		settingsPanel.getCurrentBoxIDPanel().setText(fourDigitBoxIDConversion("0"));

		while (true) {
			// Update variable states
			workOrder = settingsPanel.getWorkOrderPanel().getText();
			operatorName = settingsPanel.getOperatorNamePanel().getText();

			boolean buttonStateChanged = false;
			boolean updateImageButton = applicationFrame.getContentContainer().getButtonPanel()
					.updateImageButtonPressed();
			boolean approveImageButton = applicationFrame.getContentContainer().getButtonPanel()
					.approvedImageButtonPressed();

			// Button update GUI logic
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
					ccp.updateImagePanel(image, applicationFrame.getFrameSize());
					newImage = true;
				}
				if (approveImageButton && image != null && newImage) {
					ccp.updateImagePanel(blankImage, new Dimension(blankImage.getWidth(), blankImage.getHeight()));
					try {
						Approved(image);
					} catch (Exception e) {
						e.printStackTrace();
					}
					newImage = false;
				}
				applicationFrame.updateFrame(ccp);
			}

			// Sleep to reduce system resource usage
			try {
				Thread.sleep(50); // TODO: Reduce sleep time to make buttons faster to click not click and hold?
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// TODO: set previous button states
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
		if (webcam != null)
			webcam.close();
	}

	// Image Approved
	private static void Approved(BufferedImage image) throws Exception {
		SettingsPanel settingsPanel = applicationFrame.getContentContainer().getSettingsPanel();
		// Local vars
		String currentBoxID = fourDigitBoxIDConversion(settingsPanel.getCurrentBoxIDPanel().getText());

		// Generate filePath
		String filePath = getPicturePath(saveLocation, workOrder, currentBoxID);

		// Save a local copy of the image
		try {
			File file = new File(filePath);
			file.getParentFile().mkdirs();
			ImageIO.write(image, "PNG", file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Print The Label Twice
		labelwriter.printLabel(operatorName, workOrder, currentBoxID, getDate());
		labelwriter.printLabel(operatorName, workOrder, currentBoxID, getDate());

		// Increment Box ID
		settingsPanel.getCurrentBoxIDPanel()
				.setText(fourDigitBoxIDConversion(String.valueOf(Integer.valueOf(currentBoxID) + 1)));
	}

	// Get date as a string
	private static String getDate() {
		DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		return sdf.format(date);
	}

	// Get appropriate save address
	private static String getPicturePath(String saveLocation, String workOrder, String boxID) {
		return saveLocation + "\\Work Order_" + workOrder + "\\" + getDate().replaceAll("/", "_") + "\\BoxID_" + boxID
				+ ".PNG";
	}
}
