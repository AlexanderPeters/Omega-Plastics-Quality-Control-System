package Count_Labels_Script;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class ApplicationFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private ContentContainerPanel contentContainerPanel;

	public ApplicationFrame() {
		// Frame definition
		this.setTitle("Count Label Printer Program");
		this.setSize(750, 500);
		this.setLayout(new BorderLayout());

		// Add content container to frame and make visible
		contentContainerPanel = new ContentContainerPanel();
		this.add(contentContainerPanel);
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public ContentContainerPanel getContentContainerPanel() {
		return contentContainerPanel;
	}
}
