package main;

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
	private final String PRINTERNAME1 = "DYMO LabelWriter 450 Turbo";
	private final String PRINTERNAME2 = "DYMO LabelWriter 450";
	private Paper paper = new Paper();
	private PrinterJob printerJob = PrinterJob.getPrinterJob();
	private PageFormat pageFormat = printerJob.defaultPage();
	private PrintService[] printService = PrinterJob.lookupPrintServices();

	final double widthPaper = (1.125 * 72);
	final double heightPaper = (3.5 * 72);

	LabelWriter() {
		paper.setSize(widthPaper, heightPaper);
		paper.setImageableArea(0, 0, widthPaper, heightPaper);
		pageFormat.setPaper(paper);
		pageFormat.setOrientation(PageFormat.LANDSCAPE);

		for (int i = 0; i < printService.length; i++) {
			if (printService[i].getName().equals(PRINTERNAME1) || printService[i].getName().equals(PRINTERNAME2)) {
				try {
					printerJob.setPrintService(printService[i]);
				} catch (PrinterException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}

	public void printLabel(String operatorName, String workOrder, String boxID, String date) {
		printerJob.setPrintable(new Printable() {
			@Override
			public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
				if (pageIndex < 1) {
					Graphics2D g = (Graphics2D) graphics;
					g.translate(20, 10);

					// Add Label Data
					g.setFont(new Font(g.getFont().getFontName(), g.getFont().getStyle(), 9));
					String opName = operatorName;
					if(opName.length() > 11)
						opName = opName.substring(0, 11);					
					g.drawString("Operator: " + opName, 90, 24);
					if (workOrder.length() != 6)
						new Exception("Work Order is not the correct length.").printStackTrace();
					try {
						g.drawString("WO / BoxID: " + workOrder + " / " + fourDigitBoxIDConversion(boxID), 90, 38);
					} catch (Exception e) {
						e.printStackTrace();
					}
					g.drawString("Date: " + date, 90, 52);

					return PAGE_EXISTS;
				} else {
					return NO_SUCH_PAGE;
				}
			}
		}, pageFormat); // The 2nd param is necessary for printing into a label width a right landscape
						// format.
		try {
			printerJob.print();
		} catch (PrinterException e) {
			e.printStackTrace();
		}
	}
}
