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

public class Webcam_Capture extends HelperFunctions {
	private LabelWriter labelwriter = new LabelWriter();
	public Webcam webcam = null;

	// Our default image
	private BufferedImage image = new BufferedImage(WebcamResolution.HD.getSize().width,
			WebcamResolution.HD.getSize().height, BufferedImage.TRANSLUCENT);

	// Configure Data
	private final String productionLocation = "O:\\QA Docs\\Amico\\QA Pictures\\";
	private final String developmentLocation = "O:Automation Config Files\\QA Pictures\\developmentTesting";
	private final boolean productionNotDeveloping = false;
	private final String saveLocation = productionNotDeveloping ? productionLocation : developmentLocation;
	private String workOrder;
	private String operatorName;
	private String currentBoxID;
	private ContentContainerPanel ccp = Main.applicationFrame.getContentContainer();
	private SettingsPanel settingsPanel = ccp.getSettingsPanel();
	
	private Thread UIThread;

	public Webcam_Capture() throws Exception {
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

		settingsPanel.getCurrentBoxIDPanel().setText("0");
		
		// UI updating thread
		UIThread = new Thread(() -> {
			while(true) {
				ccp = Main.applicationFrame.getContentContainer();
				settingsPanel = ccp.getSettingsPanel();
				workOrder = settingsPanel.getWorkOrderPanel().getText();
				operatorName = settingsPanel.getOperatorNamePanel().getText();
				currentBoxID = settingsPanel.getCurrentBoxIDPanel().getText();
								
				BufferedImage im;
				if((im = webcam.getImage()) != null) {
					ccp.getImagePanel().setImage(im);
					Main.applicationFrame.setContentPane(ccp);
				}
			}
		});
		UIThread.start();
	}

	public void closeWebcam() {
		// Close WebCam at end of program
		if (webcam != null)
			webcam.close();
	}
	
	public void closeThreads() {
		UIThread.stop();
	}

	// Image Approved
	public void Approved() {
		// string.matches("-?\\d+(\\.\\d+)?") checks if a string is numeric
		// string.matches("^[a-zA-Z]*$") checks if a string is only alphabetic
		// characters
		boolean dataFormattedCorrectly = true;

		// Check and format operator name
		if (operatorName.equals(null) || operatorName.isEmpty() || !operatorName.matches("^[a-zA-Z]*$")) {
			new ErrorFrame("The Operator Name was not formatted correctly.");
			dataFormattedCorrectly = false;
		} else {
			if (operatorName.length() > 11)
				operatorName = operatorName.substring(0, 11);
		}

		// Check and format work order
		if (workOrder.equals(null) || workOrder.length() != 6 || !workOrder.matches("-?\\d+(\\.\\d+)?")) {
			new ErrorFrame("The Work Order was not formatted correctly.");
			dataFormattedCorrectly = false;
		}

		// Check and format boxID
		String boxIDConverted = null;
		if (currentBoxID.equals(null) || currentBoxID.isEmpty() || !currentBoxID.matches("-?\\d+(\\.\\d+)?"))
			dataFormattedCorrectly = false;
		else {
			boxIDConverted = fourDigitBoxIDConversion(currentBoxID);
			if (boxIDConverted.equals(null) || boxIDConverted.isEmpty() || !boxIDConverted.matches("-?\\d+(\\.\\d+)?"))
				dataFormattedCorrectly = false;
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
			String newBoxID = String.valueOf(Integer.valueOf(boxIDConverted) + 1);
			if (newBoxID.equals(null) || newBoxID.isEmpty() || !newBoxID.matches("-?\\d+(\\.\\d+)?")) {
				new ErrorFrame("Incorrect BoxID Format, Please Re-input the Current BoxID.");
				settingsPanel.getCurrentBoxIDPanel().setText("");
			} else
				settingsPanel.getCurrentBoxIDPanel().setText(newBoxID);
		}
	}

	// Get date as a string
	private String getDate() {
		DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		return sdf.format(date);
	}

	// Get appropriate save address
	private String getPicturePath(String saveLocation, String operatorName, String workOrder, String boxID) {
		return saveLocation + "\\Work Order_" + workOrder + "\\" + getDate().replaceAll("/", "_") + "\\BoxID_" + boxID
				+ "_" + operatorName.toUpperCase() + ".PNG";
	}
}
