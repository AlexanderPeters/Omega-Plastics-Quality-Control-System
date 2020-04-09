package main;

public class Main {
	static Webcam_Capture capture;
	static ApplicationFrame applicationFrame = new ApplicationFrame();

	
	public static void main(String[] args) throws Exception {
		capture = new Webcam_Capture();
	}
	
	public static void exitProgram() {
		capture.closeWebcam();
		capture.closeThreads();
		// Exit program
		System.exit(0);
	}
}
