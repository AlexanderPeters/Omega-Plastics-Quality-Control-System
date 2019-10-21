package main;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

//TODO: Author, citations, and documentation
//TODO: Error handling for connect/disconnect of camera's
//TODO: System exit when an error is printed to terminal
//TODO: Building this code into a .jar file

public class Webcam_Capture extends HelperFunctions {
	private static boolean previousUpdateImageButton = false;
	private static boolean previousApproveImageButton = false;
	private static LabelWriter labelwriter = new LabelWriter();
	private static Webcam webcam;

	// Setup our Application Frame
	private static BufferedImage blankImage = new BufferedImage(WebcamResolution.HD.getSize().width,
			WebcamResolution.HD.getSize().height, BufferedImage.TRANSLUCENT);
	private static ApplicationFrame applicationFrame = new ApplicationFrame(blankImage);

	// Config File Location
	private static File configFile = new File(
			"O:\\Automation Config Files\\Quality Inspection Script Config Line 17.txt");

	// Config File Data
	private static String saveLocation;
	private static String workOrder;
	private static String qcInspectorName;
	private static String operatorName;

	// Loop vars
	private static ContentContainerPanel ccp = applicationFrame.getContentContainer();
	private static BufferedImage image = null;
	private static boolean newImage = false;

	public static void main(String[] args) throws IOException {
		// Read in settings
		BufferedReader configReader = new BufferedReader(new FileReader(configFile));
		String line;
		while ((line = configReader.readLine()) != null) {
			if (!(line.startsWith("//"))) {
				String data = line.substring(line.indexOf(":") + 2);
				switch (line.substring(0, line.indexOf(":"))) {
				case "Save Location":
					saveLocation = data;
				case "Work Order":
					workOrder = data;
				case "QC Inspector":
					qcInspectorName = data;
				case "Operator":
					operatorName = data;
				}
			}
		}

		configReader.close();

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
					try {
						Approved(image);
					} catch (Exception e) {
						e.printStackTrace();
					}
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
	private static void Approved(BufferedImage image) throws Exception {
		// Local vars
		BufferedReader configReader = new BufferedReader(new FileReader(configFile));
		String line;
		String currentBoxID = "";

		// Get current boxID
		while ((line = configReader.readLine()) != null) {
			if (!(line.startsWith("//")))
				if (line.substring(0, line.indexOf(":")).equals("Current Box ID"))
					currentBoxID = line.substring(line.indexOf(":") + 2);
		}
		configReader.close();

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

		// Print The Label
		labelwriter.printLabel(qcInspectorName, operatorName, workOrder, currentBoxID, getDate());

		// Increment current box ID
		Path replacePath = Paths.get(configFile.getPath());
		List<String> fileContent = new ArrayList<>(Files.readAllLines(replacePath, StandardCharsets.UTF_8));

		int lineNum = 0;
		configReader = new BufferedReader(new FileReader(configFile));
		while ((line = configReader.readLine()) != null) {
			if (!(line.startsWith("//")))
				if (line.substring(0, line.indexOf(":")).equals("Current Box ID"))
					fileContent.set(lineNum, "Current Box ID: "
							+ threeDigitBoxIDConversion(String.valueOf(Integer.parseInt(currentBoxID) + 1)));
			lineNum++;
		}
		configReader.close();

		Files.write(replacePath, fileContent, StandardCharsets.UTF_8);
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

	public static void newFrameSize(Dimension size) {
		// System.out.println("Step2");
		ContentContainerPanel ccp = applicationFrame.getContentContainer();
		ccp.resizeComponents(size);
		applicationFrame.updateFrame(ccp);
	}
}
