
//TreeForm Syntax Tree Drawing Software
//Copyright (C) 2006  Donald Derrick
//
//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU General Public License
//as published by the Free Software Foundation; either version 2
//of the License, or (at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
//package userInterface;
/**
 * @author Donald Derrick
 * @version 0.1 
 * <br>
 * This class is largely borrowed from 
 * http://www.apl.jhu.edu/~hall/java/Swing-Tutorial/Swing-Tutorial-Printing.html
 * But had to be rewritten using TreeForm's code standards because of it's complete
 * integration.
 * 
 *  
 */
package userInterface;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.RepaintManager;

import staticFunctions.Sizer;

public class PrintUtilities implements Printable {
  private Component mCTBPrinted;

/**
 * 
 * @param pC The Component to be printed.
 */
  public static void printComponent(Component pC) {
  // A strange construction that builds, and runs, the object at the same time.
    new PrintUtilities(pC).print();
  }
/**
 * Constructor
 * @param pCTBPrinted The component to be printed.  This, and all components
 * contained in this component, will be printed.
 * 
 */  
  public PrintUtilities(Component pCTBPrinted) {
    mCTBPrinted = pCTBPrinted;
  }
/**
 * The Command that controls the opening of the print dialog and does
 * the printing if the printdialog comes back true.
 *
 */  
	public void print() {
		  Object lP = null;
		  PrinterJob lPrintJob = PrinterJob.getPrinterJob();
		  lPrintJob.setPrintable(this);
		  if (lPrintJob.printDialog())
			try {
			  lPrintJob.print();
			} catch(PrinterException pe) {
			  System.out.println("Error printing: " + pe);
			}   
		}
/**
 * @return print The return value of successful or failed printing.
 * @param pG The Graphics to be printed
 * @param pPF The chosen page format (fron the print dialog)
 * @param pPI The chosen page index (from the print dialog)
 * 
 */
  public int print(Graphics pG, PageFormat pPF, int pPI) {

    if (pPI > 0) {
      return(NO_SUCH_PAGE);
    } else {
      Graphics2D lG2d = (Graphics2D)pG;
      lG2d.scale(1/Sizer.scaleWidth(),1/Sizer.scaleHeight());
      lG2d.translate(pPF.getImageableX() * Sizer.scaleWidth(), pPF.getImageableY() * Sizer.scaleHeight());
      disableDoubleBuffering(mCTBPrinted);
      mCTBPrinted.paint(lG2d);
      enableDoubleBuffering(mCTBPrinted);
      return(PAGE_EXISTS);
    }
  }
/**
 * 
 * @param pC The component and subcomponent to have it's repaint manager changed and
 * to have double buffering turned off (to allow SV printing)
 */
  public static void disableDoubleBuffering(Component pC) {
    RepaintManager currentManager = RepaintManager.currentManager(pC);
    currentManager.setDoubleBufferingEnabled(false);
  }
/**
 * 
 * @param pC The component and subcomponent to have it's repaint manager changed and
 * to have double buffering turned on (so the screen does not flicker)
 */
  public static void enableDoubleBuffering(Component pC) {
    RepaintManager currentManager = RepaintManager.currentManager(pC);
    currentManager.setDoubleBufferingEnabled(true);
  }
}