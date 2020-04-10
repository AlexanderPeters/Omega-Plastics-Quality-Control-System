package production;

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
//TODO: Error handling for connect/disconnect of camera's

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
	private static String currentBoxID;
	private static SettingsPanel settingsPanel;

	// Loop vars
	private static ContentContainerPanel ccp = applicationFrame.getContentContainer();
	private static BufferedImage image = null;
	private static boolean newImage = false;

	public static void main(String[] args) throws Exception {
		// TODO: Add text boxes for operator name, work order, and current boxID, make
		// sure that if the box ID is overwritten back to previous
		// image that that image gets rewritten on the next write cycle.

		// Deal with windows resizing needed to make buttons appear bug.

		// Initialize camera
		try {
			Dimension[] nonStandardResolutions = new Dimension[] { WebcamResolution.HD.getSize(),
					new Dimension(1280, 720) };

			webcam = Webcam.getDefault();
			webcam.setCustomViewSizes(nonStandardResolutions);
			webcam.setViewSize(WebcamResolution.HD.getSize());
			webcam.open();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

		// Setup BoxID
		settingsPanel = applicationFrame.getContentContainer().getSettingsPanel();
		settingsPanel.getCurrentBoxIDPanel().setText("0000");

		while (true) {
			// Update variable states
			workOrder = settingsPanel.getWorkOrderPanel().getText();
			operatorName = settingsPanel.getOperatorNamePanel().getText();
			currentBoxID = settingsPanel.getCurrentBoxIDPanel().getText();

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
				settingsPanel = ccp.getSettingsPanel();
				
				if (updateImageButton) {
					image = captureImage(webcam);
					ccp.getImagePanel().setImage(image);
					newImage = true;
				}
				if (approveImageButton && image != null && newImage) {
					try {
						if (Approved()) {
							ccp.getImagePanel().setImage(blankImage);
							newImage = false;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
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
	// Returns if the data was correctly formatted and image successfully processed
	public static boolean Approved() {
		// string.matches("-?\\d+(\\.\\d+)?") checks if a string is numeric
		// string.matches("^[a-zA-Z]*$") checks if a string is only alphabetic
		// characters
		boolean dataFormattedCorrectly = true;

		// Check and format operator name
		if (operatorName == null || operatorName.isEmpty() || !operatorName.matches("^[a-zA-Z]*$")
				|| operatorName.length() < 2) {
			if(operatorName.length() < 2)
				new ErrorFrame("The Operator Name was not formatted correctly. Operator name must be at least two letters long.");
			else
				new ErrorFrame("The Operator Name was not formatted correctly.");
			dataFormattedCorrectly = false;
		} else {
			if (operatorName.length() > 11)
				operatorName = operatorName.substring(0, 11);
		}

		// Check and format work order
		if (workOrder == null || workOrder.length() != 6 || !workOrder.matches("-?\\d+(\\.\\d+)?")
				|| Double.parseDouble(workOrder) < 0) {
			new ErrorFrame("The Work Order was not formatted correctly. Must ba a six digit positive number.");
			dataFormattedCorrectly = false;
		}

		// Check and format boxID
		String boxIDConverted = null;
		if (currentBoxID == null || currentBoxID.isEmpty() || !currentBoxID.matches("-?\\d+(\\.\\d+)?")) {
			new ErrorFrame("The Box ID was not formatted correctly.");
			dataFormattedCorrectly = false;
		} else {
			boxIDConverted = fourDigitBoxIDConversion(currentBoxID);
			if (boxIDConverted == null || boxIDConverted.isEmpty()
					|| !boxIDConverted.matches("-?\\d+(\\.\\d+)?")) {
				new ErrorFrame("The Box ID was not formatted correctly.");
				dataFormattedCorrectly = false;
			}
		}

		// If the data is formatted correctly print the labels
		if (dataFormattedCorrectly) {
			// Generate filePath
			String filePath = getPicturePath(saveLocation, operatorName, workOrder, boxIDConverted);

			// Save a local copy of the image
			try {
				File file = new File(filePath);
				file.getParentFile().mkdirs();
				ImageIO.write(image, "PNG", file);
			} catch (IOException e) {
				new ErrorFrame(e.getMessage());
			}

			// Print The Label Twice
			labelwriter.printLabel(operatorName, workOrder, boxIDConverted, getDate(), 2);

			// Increment Box ID
			String newBoxID = fourDigitBoxIDConversion(String.valueOf(Integer.valueOf(boxIDConverted) + 1));
			if (newBoxID == null || newBoxID.isEmpty() || !newBoxID.matches("-?\\d+(\\.\\d+)?")) {
				new ErrorFrame("Incorrect BoxID Format, Please Re-input the Current BoxID.");
				settingsPanel.getCurrentBoxIDPanel().setText("");
			} else
				settingsPanel.getCurrentBoxIDPanel().setText(newBoxID);

			// Success!
			return true;
		}

		// Data incorrectly formatted
		return false;
	}

	// Get date as a string
	private static String getDate() {
		DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		return sdf.format(date);
	}

	// Get appropriate save address
	private static String getPicturePath(String saveLocation, String operatorName, String workOrder, String boxID) {
		return saveLocation + "\\Work Order_" + workOrder + "\\" + getDate().replaceAll("/", "_") + "\\BoxID_" + boxID
				+ "_" + operatorName.toUpperCase() + ".PNG";
	}
}
