public class Main {
	public static void main(String[] args) {
		ApplicationFrame applicationFrame = new ApplicationFrame();
		ContentContainerPanel ccp = applicationFrame.getContentContainerPanel();
		LabelWriter labelwriter = new LabelWriter();

		while (true) {
			String name = ccp.getInspectorNamePanel().getText();
			String quantity = ccp.getQuantityNamePanel().getText();

			boolean printButton = ccp.getButtonPanel().printButtonPressed();

			if (printButton)
				if (!name.isEmpty() && !quantity.isEmpty())
					labelwriter.printLabel(name, quantity);

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
