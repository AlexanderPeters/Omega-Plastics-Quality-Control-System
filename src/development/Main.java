package development;

public class Main {
	public static Webcam_Capture capture; // "Main code"
	public static ApplicationFrame applicationFrame; // Main GUI JFrame

	public static void main(String[] args) throws Exception {
		applicationFrame = new ApplicationFrame();
		capture = new Webcam_Capture();
	}
	
	public static void exitProgram() {
		capture.closeWebcam();
		capture.closeThreads();
		// Exit program
		System.exit(0);
	}
}
