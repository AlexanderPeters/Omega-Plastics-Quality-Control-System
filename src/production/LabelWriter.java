package production;

/**
* @author  Alexander Peters
* @version 3.0
* @since   2020-04-10
*/

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.print.PrintService;

public class LabelWriter extends HelperFunctions {
	private Paper paper = new Paper();
	private PrinterJob printerJob = PrinterJob.getPrinterJob();
	private PageFormat pageFormat = printerJob.defaultPage();
	private PrintService[] printServices = PrinterJob.lookupPrintServices();

	final double widthPaper = (1.125 * 72);
	final double heightPaper = (3.5 * 72);

	LabelWriter() {
		paper.setSize(widthPaper, heightPaper);
		paper.setImageableArea(0, 0, widthPaper, heightPaper);
		pageFormat.setPaper(paper);
		pageFormat.setOrientation(PageFormat.LANDSCAPE);

		// Get printer names and add to dialog
		String[] printerNames = new String[printServices.length];
		for (int i = 0; i < printServices.length; i++)
			printerNames[i] = printServices[i].getName();
		
		// Select print service from user selection
		String selectedPrinter = new PrinterSelectionFrame(printerNames).getSelectedPrinter();
		for (int i = 0; i < printServices.length; i++)
			if (printServices[i].getName().equals(selectedPrinter))
				try {
					printerJob.setPrintService(printServices[i]);
				} catch (PrinterException e) {
					new ErrorFrame(e.getMessage());
				}

	}

	// Print the label with the desired formating and required number of copies
	public void printLabel(String operatorName, String workOrder, String boxID, String date, int copies) {
		printerJob.setPrintable(new Printable() {
			@Override
			public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
				if (pageIndex < 1) {
					Graphics2D g = (Graphics2D) graphics;
					g.translate(20, 10);

					// Add Label Data
					g.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
					g.drawString("Operator: " + operatorName.toUpperCase(), 0, 17);
					try {
						g.drawString("WO / BoxID: " + workOrder + " / " + boxID, 0, 34);
					} catch (Exception e) {
						new ErrorFrame(e.getMessage());
					}
					g.drawString("Date: " + date, 0, 51);
					return PAGE_EXISTS;
				} else {
					return NO_SUCH_PAGE;
				}
			}
		}, pageFormat); // The 2nd param is necessary for printing into a label width a right landscape
						// format.
		try {
			printerJob.setCopies(copies);
			printerJob.print();
		} catch (PrinterException e) {
			new ErrorFrame(e.getMessage());
		}

	}
}
