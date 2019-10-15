import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.print.PrintService;

public class LabelWriter {

    public static final String PRINTERNAME = "DYMO LabelWriter";
    PrinterJob printerJob = PrinterJob.getPrinterJob();
    PageFormat pageFormat = printerJob.defaultPage();
    Paper paper = new Paper();

    public void printLabel() {
        final double widthPaper = (1.125 * 72);
        final double heightPaper = (3.5 * 72);
        
        paper.setSize(widthPaper, heightPaper);
        paper.setImageableArea(0, 0, widthPaper, heightPaper);
        pageFormat.setPaper(paper);
        pageFormat.setOrientation(PageFormat.LANDSCAPE);
        PrintService[] printService = PrinterJob.lookupPrintServices();

        for (int i = 0; i < printService.length; i++) {
            if (printService[i].getName().contains(PRINTERNAME)) {
                try {
                    printerJob.setPrintService(printService[i]);
                    printerJob.setPrintable(new Printable() {
                        @Override
                        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
                                throws PrinterException {
                            if (pageIndex < 1) {
                                Graphics2D g = (Graphics2D) graphics;
                                g.translate(20, 10);                          
                                g.setFont(new Font(g.getFont().getFontName(), g.getFont().getStyle(), 60));
                                g.drawString("Ω✓", -5, 45);
                                return PAGE_EXISTS;
                            } else {
                                return NO_SUCH_PAGE;
                            }
                        }
                    }, pageFormat); // The 2nd param is necessary for printing into a label width a right landscape format.
                    printerJob.print();
                } catch (PrinterException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
